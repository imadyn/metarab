package ac.fidoteam.alkhalil.web.rest;

import ac.fidoteam.alkhalil.MetarabApp;
import ac.fidoteam.alkhalil.domain.TypeTB;
import ac.fidoteam.alkhalil.domain.RefBahr;
import ac.fidoteam.alkhalil.domain.RefRhythm;
import ac.fidoteam.alkhalil.repository.TypeTBRepository;
import ac.fidoteam.alkhalil.repository.search.TypeTBSearchRepository;
import ac.fidoteam.alkhalil.service.TypeTBService;
import ac.fidoteam.alkhalil.service.dto.TypeTBDTO;
import ac.fidoteam.alkhalil.service.mapper.TypeTBMapper;
import ac.fidoteam.alkhalil.web.rest.errors.ExceptionTranslator;
import ac.fidoteam.alkhalil.service.dto.TypeTBCriteria;
import ac.fidoteam.alkhalil.service.TypeTBQueryService;

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

import ac.fidoteam.alkhalil.domain.enumeration.Type;
/**
 * Integration tests for the {@link TypeTBResource} REST controller.
 */
@SpringBootTest(classes = MetarabApp.class)
public class TypeTBResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ORDRE = "A";
    private static final String UPDATED_ORDRE = "B";

    private static final Type DEFAULT_TYPE = Type.HACHW;
    private static final Type UPDATED_TYPE = Type.AROUD;

    @Autowired
    private TypeTBRepository typeTBRepository;

    @Autowired
    private TypeTBMapper typeTBMapper;

    @Autowired
    private TypeTBService typeTBService;

    /**
     * This repository is mocked in the ac.fidoteam.alkhalil.repository.search test package.
     *
     * @see ac.fidoteam.alkhalil.repository.search.TypeTBSearchRepositoryMockConfiguration
     */
    @Autowired
    private TypeTBSearchRepository mockTypeTBSearchRepository;

    @Autowired
    private TypeTBQueryService typeTBQueryService;

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

    private MockMvc restTypeTBMockMvc;

    private TypeTB typeTB;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeTBResource typeTBResource = new TypeTBResource(typeTBService, typeTBQueryService);
        this.restTypeTBMockMvc = MockMvcBuilders.standaloneSetup(typeTBResource)
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
    public static TypeTB createEntity(EntityManager em) {
        TypeTB typeTB = new TypeTB()
            .code(DEFAULT_CODE)
            .ordre(DEFAULT_ORDRE)
            .type(DEFAULT_TYPE);
        return typeTB;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeTB createUpdatedEntity(EntityManager em) {
        TypeTB typeTB = new TypeTB()
            .code(UPDATED_CODE)
            .ordre(UPDATED_ORDRE)
            .type(UPDATED_TYPE);
        return typeTB;
    }

    @BeforeEach
    public void initTest() {
        typeTB = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeTB() throws Exception {
        int databaseSizeBeforeCreate = typeTBRepository.findAll().size();

        // Create the TypeTB
        TypeTBDTO typeTBDTO = typeTBMapper.toDto(typeTB);
        restTypeTBMockMvc.perform(post("/api/type-tbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeTBDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeTB in the database
        List<TypeTB> typeTBList = typeTBRepository.findAll();
        assertThat(typeTBList).hasSize(databaseSizeBeforeCreate + 1);
        TypeTB testTypeTB = typeTBList.get(typeTBList.size() - 1);
        assertThat(testTypeTB.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypeTB.getOrdre()).isEqualTo(DEFAULT_ORDRE);
        assertThat(testTypeTB.getType()).isEqualTo(DEFAULT_TYPE);

        // Validate the TypeTB in Elasticsearch
        verify(mockTypeTBSearchRepository, times(1)).save(testTypeTB);
    }

    @Test
    @Transactional
    public void createTypeTBWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeTBRepository.findAll().size();

        // Create the TypeTB with an existing ID
        typeTB.setId(1L);
        TypeTBDTO typeTBDTO = typeTBMapper.toDto(typeTB);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeTBMockMvc.perform(post("/api/type-tbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeTBDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeTB in the database
        List<TypeTB> typeTBList = typeTBRepository.findAll();
        assertThat(typeTBList).hasSize(databaseSizeBeforeCreate);

        // Validate the TypeTB in Elasticsearch
        verify(mockTypeTBSearchRepository, times(0)).save(typeTB);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeTBRepository.findAll().size();
        // set the field null
        typeTB.setCode(null);

        // Create the TypeTB, which fails.
        TypeTBDTO typeTBDTO = typeTBMapper.toDto(typeTB);

        restTypeTBMockMvc.perform(post("/api/type-tbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeTBDTO)))
            .andExpect(status().isBadRequest());

        List<TypeTB> typeTBList = typeTBRepository.findAll();
        assertThat(typeTBList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrdreIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeTBRepository.findAll().size();
        // set the field null
        typeTB.setOrdre(null);

        // Create the TypeTB, which fails.
        TypeTBDTO typeTBDTO = typeTBMapper.toDto(typeTB);

        restTypeTBMockMvc.perform(post("/api/type-tbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeTBDTO)))
            .andExpect(status().isBadRequest());

        List<TypeTB> typeTBList = typeTBRepository.findAll();
        assertThat(typeTBList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeTBS() throws Exception {
        // Initialize the database
        typeTBRepository.saveAndFlush(typeTB);

        // Get all the typeTBList
        restTypeTBMockMvc.perform(get("/api/type-tbs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeTB.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].ordre").value(hasItem(DEFAULT_ORDRE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getTypeTB() throws Exception {
        // Initialize the database
        typeTBRepository.saveAndFlush(typeTB);

        // Get the typeTB
        restTypeTBMockMvc.perform(get("/api/type-tbs/{id}", typeTB.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeTB.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.ordre").value(DEFAULT_ORDRE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getAllTypeTBSByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        typeTBRepository.saveAndFlush(typeTB);

        // Get all the typeTBList where code equals to DEFAULT_CODE
        defaultTypeTBShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the typeTBList where code equals to UPDATED_CODE
        defaultTypeTBShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllTypeTBSByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        typeTBRepository.saveAndFlush(typeTB);

        // Get all the typeTBList where code in DEFAULT_CODE or UPDATED_CODE
        defaultTypeTBShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the typeTBList where code equals to UPDATED_CODE
        defaultTypeTBShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllTypeTBSByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        typeTBRepository.saveAndFlush(typeTB);

        // Get all the typeTBList where code is not null
        defaultTypeTBShouldBeFound("code.specified=true");

        // Get all the typeTBList where code is null
        defaultTypeTBShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllTypeTBSByOrdreIsEqualToSomething() throws Exception {
        // Initialize the database
        typeTBRepository.saveAndFlush(typeTB);

        // Get all the typeTBList where ordre equals to DEFAULT_ORDRE
        defaultTypeTBShouldBeFound("ordre.equals=" + DEFAULT_ORDRE);

        // Get all the typeTBList where ordre equals to UPDATED_ORDRE
        defaultTypeTBShouldNotBeFound("ordre.equals=" + UPDATED_ORDRE);
    }

    @Test
    @Transactional
    public void getAllTypeTBSByOrdreIsInShouldWork() throws Exception {
        // Initialize the database
        typeTBRepository.saveAndFlush(typeTB);

        // Get all the typeTBList where ordre in DEFAULT_ORDRE or UPDATED_ORDRE
        defaultTypeTBShouldBeFound("ordre.in=" + DEFAULT_ORDRE + "," + UPDATED_ORDRE);

        // Get all the typeTBList where ordre equals to UPDATED_ORDRE
        defaultTypeTBShouldNotBeFound("ordre.in=" + UPDATED_ORDRE);
    }

    @Test
    @Transactional
    public void getAllTypeTBSByOrdreIsNullOrNotNull() throws Exception {
        // Initialize the database
        typeTBRepository.saveAndFlush(typeTB);

        // Get all the typeTBList where ordre is not null
        defaultTypeTBShouldBeFound("ordre.specified=true");

        // Get all the typeTBList where ordre is null
        defaultTypeTBShouldNotBeFound("ordre.specified=false");
    }

    @Test
    @Transactional
    public void getAllTypeTBSByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        typeTBRepository.saveAndFlush(typeTB);

        // Get all the typeTBList where type equals to DEFAULT_TYPE
        defaultTypeTBShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the typeTBList where type equals to UPDATED_TYPE
        defaultTypeTBShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllTypeTBSByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        typeTBRepository.saveAndFlush(typeTB);

        // Get all the typeTBList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultTypeTBShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the typeTBList where type equals to UPDATED_TYPE
        defaultTypeTBShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllTypeTBSByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        typeTBRepository.saveAndFlush(typeTB);

        // Get all the typeTBList where type is not null
        defaultTypeTBShouldBeFound("type.specified=true");

        // Get all the typeTBList where type is null
        defaultTypeTBShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllTypeTBSByRefBahrIsEqualToSomething() throws Exception {
        // Initialize the database
        typeTBRepository.saveAndFlush(typeTB);
        RefBahr refBahr = RefBahrResourceIT.createEntity(em);
        em.persist(refBahr);
        em.flush();
        typeTB.setRefBahr(refBahr);
        typeTBRepository.saveAndFlush(typeTB);
        Long refBahrId = refBahr.getId();

        // Get all the typeTBList where refBahr equals to refBahrId
        defaultTypeTBShouldBeFound("refBahrId.equals=" + refBahrId);

        // Get all the typeTBList where refBahr equals to refBahrId + 1
        defaultTypeTBShouldNotBeFound("refBahrId.equals=" + (refBahrId + 1));
    }


    @Test
    @Transactional
    public void getAllTypeTBSByRefRhythmIsEqualToSomething() throws Exception {
        // Initialize the database
        typeTBRepository.saveAndFlush(typeTB);
        RefRhythm refRhythm = RefRhythmResourceIT.createEntity(em);
        em.persist(refRhythm);
        em.flush();
        typeTB.setRefRhythm(refRhythm);
        typeTBRepository.saveAndFlush(typeTB);
        Long refRhythmId = refRhythm.getId();

        // Get all the typeTBList where refRhythm equals to refRhythmId
        defaultTypeTBShouldBeFound("refRhythmId.equals=" + refRhythmId);

        // Get all the typeTBList where refRhythm equals to refRhythmId + 1
        defaultTypeTBShouldNotBeFound("refRhythmId.equals=" + (refRhythmId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTypeTBShouldBeFound(String filter) throws Exception {
        restTypeTBMockMvc.perform(get("/api/type-tbs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeTB.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].ordre").value(hasItem(DEFAULT_ORDRE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));

        // Check, that the count call also returns 1
        restTypeTBMockMvc.perform(get("/api/type-tbs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTypeTBShouldNotBeFound(String filter) throws Exception {
        restTypeTBMockMvc.perform(get("/api/type-tbs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTypeTBMockMvc.perform(get("/api/type-tbs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTypeTB() throws Exception {
        // Get the typeTB
        restTypeTBMockMvc.perform(get("/api/type-tbs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeTB() throws Exception {
        // Initialize the database
        typeTBRepository.saveAndFlush(typeTB);

        int databaseSizeBeforeUpdate = typeTBRepository.findAll().size();

        // Update the typeTB
        TypeTB updatedTypeTB = typeTBRepository.findById(typeTB.getId()).get();
        // Disconnect from session so that the updates on updatedTypeTB are not directly saved in db
        em.detach(updatedTypeTB);
        updatedTypeTB
            .code(UPDATED_CODE)
            .ordre(UPDATED_ORDRE)
            .type(UPDATED_TYPE);
        TypeTBDTO typeTBDTO = typeTBMapper.toDto(updatedTypeTB);

        restTypeTBMockMvc.perform(put("/api/type-tbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeTBDTO)))
            .andExpect(status().isOk());

        // Validate the TypeTB in the database
        List<TypeTB> typeTBList = typeTBRepository.findAll();
        assertThat(typeTBList).hasSize(databaseSizeBeforeUpdate);
        TypeTB testTypeTB = typeTBList.get(typeTBList.size() - 1);
        assertThat(testTypeTB.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeTB.getOrdre()).isEqualTo(UPDATED_ORDRE);
        assertThat(testTypeTB.getType()).isEqualTo(UPDATED_TYPE);

        // Validate the TypeTB in Elasticsearch
        verify(mockTypeTBSearchRepository, times(1)).save(testTypeTB);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeTB() throws Exception {
        int databaseSizeBeforeUpdate = typeTBRepository.findAll().size();

        // Create the TypeTB
        TypeTBDTO typeTBDTO = typeTBMapper.toDto(typeTB);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeTBMockMvc.perform(put("/api/type-tbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeTBDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeTB in the database
        List<TypeTB> typeTBList = typeTBRepository.findAll();
        assertThat(typeTBList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TypeTB in Elasticsearch
        verify(mockTypeTBSearchRepository, times(0)).save(typeTB);
    }

    @Test
    @Transactional
    public void deleteTypeTB() throws Exception {
        // Initialize the database
        typeTBRepository.saveAndFlush(typeTB);

        int databaseSizeBeforeDelete = typeTBRepository.findAll().size();

        // Delete the typeTB
        restTypeTBMockMvc.perform(delete("/api/type-tbs/{id}", typeTB.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeTB> typeTBList = typeTBRepository.findAll();
        assertThat(typeTBList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TypeTB in Elasticsearch
        verify(mockTypeTBSearchRepository, times(1)).deleteById(typeTB.getId());
    }

    @Test
    @Transactional
    public void searchTypeTB() throws Exception {
        // Initialize the database
        typeTBRepository.saveAndFlush(typeTB);
        when(mockTypeTBSearchRepository.search(queryStringQuery("id:" + typeTB.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(typeTB), PageRequest.of(0, 1), 1));
        // Search the typeTB
        restTypeTBMockMvc.perform(get("/api/_search/type-tbs?query=id:" + typeTB.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeTB.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].ordre").value(hasItem(DEFAULT_ORDRE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeTB.class);
        TypeTB typeTB1 = new TypeTB();
        typeTB1.setId(1L);
        TypeTB typeTB2 = new TypeTB();
        typeTB2.setId(typeTB1.getId());
        assertThat(typeTB1).isEqualTo(typeTB2);
        typeTB2.setId(2L);
        assertThat(typeTB1).isNotEqualTo(typeTB2);
        typeTB1.setId(null);
        assertThat(typeTB1).isNotEqualTo(typeTB2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeTBDTO.class);
        TypeTBDTO typeTBDTO1 = new TypeTBDTO();
        typeTBDTO1.setId(1L);
        TypeTBDTO typeTBDTO2 = new TypeTBDTO();
        assertThat(typeTBDTO1).isNotEqualTo(typeTBDTO2);
        typeTBDTO2.setId(typeTBDTO1.getId());
        assertThat(typeTBDTO1).isEqualTo(typeTBDTO2);
        typeTBDTO2.setId(2L);
        assertThat(typeTBDTO1).isNotEqualTo(typeTBDTO2);
        typeTBDTO1.setId(null);
        assertThat(typeTBDTO1).isNotEqualTo(typeTBDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(typeTBMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(typeTBMapper.fromId(null)).isNull();
    }
}
