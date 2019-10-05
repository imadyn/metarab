package ac.fidoteam.alkhalil.web.rest;

import ac.fidoteam.alkhalil.MetarabApp;
import ac.fidoteam.alkhalil.domain.RefAlphabet;
import ac.fidoteam.alkhalil.repository.RefAlphabetRepository;
import ac.fidoteam.alkhalil.repository.search.RefAlphabetSearchRepository;
import ac.fidoteam.alkhalil.service.RefAlphabetService;
import ac.fidoteam.alkhalil.service.dto.RefAlphabetDTO;
import ac.fidoteam.alkhalil.service.mapper.RefAlphabetMapper;
import ac.fidoteam.alkhalil.web.rest.errors.ExceptionTranslator;
import ac.fidoteam.alkhalil.service.dto.RefAlphabetCriteria;
import ac.fidoteam.alkhalil.service.RefAlphabetQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static ac.fidoteam.alkhalil.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ac.fidoteam.alkhalil.domain.enumeration.Language;
/**
 * Integration tests for the {@link RefAlphabetResource} REST controller.
 */
@SpringBootTest(classes = MetarabApp.class)
public class RefAlphabetResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RHYTHM = "AAA";
    private static final String UPDATED_RHYTHM = "BBB";

    private static final Language DEFAULT_LANGUAGE = Language.FRENCH;
    private static final Language UPDATED_LANGUAGE = Language.ENGLISH;

    @Autowired
    private RefAlphabetRepository refAlphabetRepository;

    @Autowired
    private RefAlphabetMapper refAlphabetMapper;

    @Autowired
    private RefAlphabetService refAlphabetService;

    /**
     * This repository is mocked in the ac.fidoteam.alkhalil.repository.search test package.
     *
     * @see ac.fidoteam.alkhalil.repository.search.RefAlphabetSearchRepositoryMockConfiguration
     */
    @Autowired
    private RefAlphabetSearchRepository mockRefAlphabetSearchRepository;

    @Autowired
    private RefAlphabetQueryService refAlphabetQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restRefAlphabetMockMvc;

    private RefAlphabet refAlphabet;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RefAlphabetResource refAlphabetResource = new RefAlphabetResource(refAlphabetService, refAlphabetQueryService);
        this.restRefAlphabetMockMvc = MockMvcBuilders.standaloneSetup(refAlphabetResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RefAlphabet createEntity(EntityManager em) {
        RefAlphabet refAlphabet = new RefAlphabet()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .rhythm(DEFAULT_RHYTHM)
            .language(DEFAULT_LANGUAGE);
        return refAlphabet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RefAlphabet createUpdatedEntity(EntityManager em) {
        RefAlphabet refAlphabet = new RefAlphabet()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .rhythm(UPDATED_RHYTHM)
            .language(UPDATED_LANGUAGE);
        return refAlphabet;
    }

    @BeforeEach
    public void initTest() {
        refAlphabet = createEntity(em);
    }

    @Test
    @Transactional
    public void createRefAlphabet() throws Exception {
        int databaseSizeBeforeCreate = refAlphabetRepository.findAll().size();

        // Create the RefAlphabet
        RefAlphabetDTO refAlphabetDTO = refAlphabetMapper.toDto(refAlphabet);
        restRefAlphabetMockMvc.perform(post("/api/ref-alphabets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refAlphabetDTO)))
            .andExpect(status().isCreated());

        // Validate the RefAlphabet in the database
        List<RefAlphabet> refAlphabetList = refAlphabetRepository.findAll();
        assertThat(refAlphabetList).hasSize(databaseSizeBeforeCreate + 1);
        RefAlphabet testRefAlphabet = refAlphabetList.get(refAlphabetList.size() - 1);
        assertThat(testRefAlphabet.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRefAlphabet.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRefAlphabet.getRhythm()).isEqualTo(DEFAULT_RHYTHM);
        assertThat(testRefAlphabet.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);

        // Validate the RefAlphabet in Elasticsearch
        verify(mockRefAlphabetSearchRepository, times(1)).save(testRefAlphabet);
    }

    @Test
    @Transactional
    public void createRefAlphabetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = refAlphabetRepository.findAll().size();

        // Create the RefAlphabet with an existing ID
        refAlphabet.setId(1L);
        RefAlphabetDTO refAlphabetDTO = refAlphabetMapper.toDto(refAlphabet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefAlphabetMockMvc.perform(post("/api/ref-alphabets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refAlphabetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RefAlphabet in the database
        List<RefAlphabet> refAlphabetList = refAlphabetRepository.findAll();
        assertThat(refAlphabetList).hasSize(databaseSizeBeforeCreate);

        // Validate the RefAlphabet in Elasticsearch
        verify(mockRefAlphabetSearchRepository, times(0)).save(refAlphabet);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = refAlphabetRepository.findAll().size();
        // set the field null
        refAlphabet.setCode(null);

        // Create the RefAlphabet, which fails.
        RefAlphabetDTO refAlphabetDTO = refAlphabetMapper.toDto(refAlphabet);

        restRefAlphabetMockMvc.perform(post("/api/ref-alphabets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refAlphabetDTO)))
            .andExpect(status().isBadRequest());

        List<RefAlphabet> refAlphabetList = refAlphabetRepository.findAll();
        assertThat(refAlphabetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = refAlphabetRepository.findAll().size();
        // set the field null
        refAlphabet.setName(null);

        // Create the RefAlphabet, which fails.
        RefAlphabetDTO refAlphabetDTO = refAlphabetMapper.toDto(refAlphabet);

        restRefAlphabetMockMvc.perform(post("/api/ref-alphabets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refAlphabetDTO)))
            .andExpect(status().isBadRequest());

        List<RefAlphabet> refAlphabetList = refAlphabetRepository.findAll();
        assertThat(refAlphabetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRhythmIsRequired() throws Exception {
        int databaseSizeBeforeTest = refAlphabetRepository.findAll().size();
        // set the field null
        refAlphabet.setRhythm(null);

        // Create the RefAlphabet, which fails.
        RefAlphabetDTO refAlphabetDTO = refAlphabetMapper.toDto(refAlphabet);

        restRefAlphabetMockMvc.perform(post("/api/ref-alphabets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refAlphabetDTO)))
            .andExpect(status().isBadRequest());

        List<RefAlphabet> refAlphabetList = refAlphabetRepository.findAll();
        assertThat(refAlphabetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRefAlphabets() throws Exception {
        // Initialize the database
        refAlphabetRepository.saveAndFlush(refAlphabet);

        // Get all the refAlphabetList
        restRefAlphabetMockMvc.perform(get("/api/ref-alphabets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refAlphabet.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].rhythm").value(hasItem(DEFAULT_RHYTHM.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }
    
    @Test
    @Transactional
    public void getRefAlphabet() throws Exception {
        // Initialize the database
        refAlphabetRepository.saveAndFlush(refAlphabet);

        // Get the refAlphabet
        restRefAlphabetMockMvc.perform(get("/api/ref-alphabets/{id}", refAlphabet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(refAlphabet.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.rhythm").value(DEFAULT_RHYTHM.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    public void getAllRefAlphabetsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        refAlphabetRepository.saveAndFlush(refAlphabet);

        // Get all the refAlphabetList where code equals to DEFAULT_CODE
        defaultRefAlphabetShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the refAlphabetList where code equals to UPDATED_CODE
        defaultRefAlphabetShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllRefAlphabetsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        refAlphabetRepository.saveAndFlush(refAlphabet);

        // Get all the refAlphabetList where code in DEFAULT_CODE or UPDATED_CODE
        defaultRefAlphabetShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the refAlphabetList where code equals to UPDATED_CODE
        defaultRefAlphabetShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllRefAlphabetsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        refAlphabetRepository.saveAndFlush(refAlphabet);

        // Get all the refAlphabetList where code is not null
        defaultRefAlphabetShouldBeFound("code.specified=true");

        // Get all the refAlphabetList where code is null
        defaultRefAlphabetShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllRefAlphabetsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        refAlphabetRepository.saveAndFlush(refAlphabet);

        // Get all the refAlphabetList where name equals to DEFAULT_NAME
        defaultRefAlphabetShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the refAlphabetList where name equals to UPDATED_NAME
        defaultRefAlphabetShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRefAlphabetsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        refAlphabetRepository.saveAndFlush(refAlphabet);

        // Get all the refAlphabetList where name in DEFAULT_NAME or UPDATED_NAME
        defaultRefAlphabetShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the refAlphabetList where name equals to UPDATED_NAME
        defaultRefAlphabetShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRefAlphabetsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        refAlphabetRepository.saveAndFlush(refAlphabet);

        // Get all the refAlphabetList where name is not null
        defaultRefAlphabetShouldBeFound("name.specified=true");

        // Get all the refAlphabetList where name is null
        defaultRefAlphabetShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllRefAlphabetsByRhythmIsEqualToSomething() throws Exception {
        // Initialize the database
        refAlphabetRepository.saveAndFlush(refAlphabet);

        // Get all the refAlphabetList where rhythm equals to DEFAULT_RHYTHM
        defaultRefAlphabetShouldBeFound("rhythm.equals=" + DEFAULT_RHYTHM);

        // Get all the refAlphabetList where rhythm equals to UPDATED_RHYTHM
        defaultRefAlphabetShouldNotBeFound("rhythm.equals=" + UPDATED_RHYTHM);
    }

    @Test
    @Transactional
    public void getAllRefAlphabetsByRhythmIsInShouldWork() throws Exception {
        // Initialize the database
        refAlphabetRepository.saveAndFlush(refAlphabet);

        // Get all the refAlphabetList where rhythm in DEFAULT_RHYTHM or UPDATED_RHYTHM
        defaultRefAlphabetShouldBeFound("rhythm.in=" + DEFAULT_RHYTHM + "," + UPDATED_RHYTHM);

        // Get all the refAlphabetList where rhythm equals to UPDATED_RHYTHM
        defaultRefAlphabetShouldNotBeFound("rhythm.in=" + UPDATED_RHYTHM);
    }

    @Test
    @Transactional
    public void getAllRefAlphabetsByRhythmIsNullOrNotNull() throws Exception {
        // Initialize the database
        refAlphabetRepository.saveAndFlush(refAlphabet);

        // Get all the refAlphabetList where rhythm is not null
        defaultRefAlphabetShouldBeFound("rhythm.specified=true");

        // Get all the refAlphabetList where rhythm is null
        defaultRefAlphabetShouldNotBeFound("rhythm.specified=false");
    }

    @Test
    @Transactional
    public void getAllRefAlphabetsByLanguageIsEqualToSomething() throws Exception {
        // Initialize the database
        refAlphabetRepository.saveAndFlush(refAlphabet);

        // Get all the refAlphabetList where language equals to DEFAULT_LANGUAGE
        defaultRefAlphabetShouldBeFound("language.equals=" + DEFAULT_LANGUAGE);

        // Get all the refAlphabetList where language equals to UPDATED_LANGUAGE
        defaultRefAlphabetShouldNotBeFound("language.equals=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    public void getAllRefAlphabetsByLanguageIsInShouldWork() throws Exception {
        // Initialize the database
        refAlphabetRepository.saveAndFlush(refAlphabet);

        // Get all the refAlphabetList where language in DEFAULT_LANGUAGE or UPDATED_LANGUAGE
        defaultRefAlphabetShouldBeFound("language.in=" + DEFAULT_LANGUAGE + "," + UPDATED_LANGUAGE);

        // Get all the refAlphabetList where language equals to UPDATED_LANGUAGE
        defaultRefAlphabetShouldNotBeFound("language.in=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    public void getAllRefAlphabetsByLanguageIsNullOrNotNull() throws Exception {
        // Initialize the database
        refAlphabetRepository.saveAndFlush(refAlphabet);

        // Get all the refAlphabetList where language is not null
        defaultRefAlphabetShouldBeFound("language.specified=true");

        // Get all the refAlphabetList where language is null
        defaultRefAlphabetShouldNotBeFound("language.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRefAlphabetShouldBeFound(String filter) throws Exception {
        restRefAlphabetMockMvc.perform(get("/api/ref-alphabets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refAlphabet.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].rhythm").value(hasItem(DEFAULT_RHYTHM)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));

        // Check, that the count call also returns 1
        restRefAlphabetMockMvc.perform(get("/api/ref-alphabets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRefAlphabetShouldNotBeFound(String filter) throws Exception {
        restRefAlphabetMockMvc.perform(get("/api/ref-alphabets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRefAlphabetMockMvc.perform(get("/api/ref-alphabets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRefAlphabet() throws Exception {
        // Get the refAlphabet
        restRefAlphabetMockMvc.perform(get("/api/ref-alphabets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRefAlphabet() throws Exception {
        // Initialize the database
        refAlphabetRepository.saveAndFlush(refAlphabet);

        int databaseSizeBeforeUpdate = refAlphabetRepository.findAll().size();

        // Update the refAlphabet
        RefAlphabet updatedRefAlphabet = refAlphabetRepository.findById(refAlphabet.getId()).get();
        // Disconnect from session so that the updates on updatedRefAlphabet are not directly saved in db
        em.detach(updatedRefAlphabet);
        updatedRefAlphabet
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .rhythm(UPDATED_RHYTHM)
            .language(UPDATED_LANGUAGE);
        RefAlphabetDTO refAlphabetDTO = refAlphabetMapper.toDto(updatedRefAlphabet);

        restRefAlphabetMockMvc.perform(put("/api/ref-alphabets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refAlphabetDTO)))
            .andExpect(status().isOk());

        // Validate the RefAlphabet in the database
        List<RefAlphabet> refAlphabetList = refAlphabetRepository.findAll();
        assertThat(refAlphabetList).hasSize(databaseSizeBeforeUpdate);
        RefAlphabet testRefAlphabet = refAlphabetList.get(refAlphabetList.size() - 1);
        assertThat(testRefAlphabet.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRefAlphabet.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRefAlphabet.getRhythm()).isEqualTo(UPDATED_RHYTHM);
        assertThat(testRefAlphabet.getLanguage()).isEqualTo(UPDATED_LANGUAGE);

        // Validate the RefAlphabet in Elasticsearch
        verify(mockRefAlphabetSearchRepository, times(1)).save(testRefAlphabet);
    }

    @Test
    @Transactional
    public void updateNonExistingRefAlphabet() throws Exception {
        int databaseSizeBeforeUpdate = refAlphabetRepository.findAll().size();

        // Create the RefAlphabet
        RefAlphabetDTO refAlphabetDTO = refAlphabetMapper.toDto(refAlphabet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRefAlphabetMockMvc.perform(put("/api/ref-alphabets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refAlphabetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RefAlphabet in the database
        List<RefAlphabet> refAlphabetList = refAlphabetRepository.findAll();
        assertThat(refAlphabetList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RefAlphabet in Elasticsearch
        verify(mockRefAlphabetSearchRepository, times(0)).save(refAlphabet);
    }

    @Test
    @Transactional
    public void deleteRefAlphabet() throws Exception {
        // Initialize the database
        refAlphabetRepository.saveAndFlush(refAlphabet);

        int databaseSizeBeforeDelete = refAlphabetRepository.findAll().size();

        // Delete the refAlphabet
        restRefAlphabetMockMvc.perform(delete("/api/ref-alphabets/{id}", refAlphabet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RefAlphabet> refAlphabetList = refAlphabetRepository.findAll();
        assertThat(refAlphabetList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RefAlphabet in Elasticsearch
        verify(mockRefAlphabetSearchRepository, times(1)).deleteById(refAlphabet.getId());
    }

    @Test
    @Transactional
    public void searchRefAlphabet() throws Exception {
        // Initialize the database
        refAlphabetRepository.saveAndFlush(refAlphabet);
        when(mockRefAlphabetSearchRepository.search(queryStringQuery("id:" + refAlphabet.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(refAlphabet), PageRequest.of(0, 1), 1));
        // Search the refAlphabet
        restRefAlphabetMockMvc.perform(get("/api/_search/ref-alphabets?query=id:" + refAlphabet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refAlphabet.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].rhythm").value(hasItem(DEFAULT_RHYTHM)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefAlphabet.class);
        RefAlphabet refAlphabet1 = new RefAlphabet();
        refAlphabet1.setId(1L);
        RefAlphabet refAlphabet2 = new RefAlphabet();
        refAlphabet2.setId(refAlphabet1.getId());
        assertThat(refAlphabet1).isEqualTo(refAlphabet2);
        refAlphabet2.setId(2L);
        assertThat(refAlphabet1).isNotEqualTo(refAlphabet2);
        refAlphabet1.setId(null);
        assertThat(refAlphabet1).isNotEqualTo(refAlphabet2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefAlphabetDTO.class);
        RefAlphabetDTO refAlphabetDTO1 = new RefAlphabetDTO();
        refAlphabetDTO1.setId(1L);
        RefAlphabetDTO refAlphabetDTO2 = new RefAlphabetDTO();
        assertThat(refAlphabetDTO1).isNotEqualTo(refAlphabetDTO2);
        refAlphabetDTO2.setId(refAlphabetDTO1.getId());
        assertThat(refAlphabetDTO1).isEqualTo(refAlphabetDTO2);
        refAlphabetDTO2.setId(2L);
        assertThat(refAlphabetDTO1).isNotEqualTo(refAlphabetDTO2);
        refAlphabetDTO1.setId(null);
        assertThat(refAlphabetDTO1).isNotEqualTo(refAlphabetDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(refAlphabetMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(refAlphabetMapper.fromId(null)).isNull();
    }
}
