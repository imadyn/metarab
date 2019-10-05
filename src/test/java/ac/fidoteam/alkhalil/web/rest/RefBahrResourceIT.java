package ac.fidoteam.alkhalil.web.rest;

import ac.fidoteam.alkhalil.MetarabApp;
import ac.fidoteam.alkhalil.domain.RefBahr;
import ac.fidoteam.alkhalil.domain.RefBahr;
import ac.fidoteam.alkhalil.repository.RefBahrRepository;
import ac.fidoteam.alkhalil.repository.search.RefBahrSearchRepository;
import ac.fidoteam.alkhalil.service.RefBahrService;
import ac.fidoteam.alkhalil.service.dto.RefBahrDTO;
import ac.fidoteam.alkhalil.service.mapper.RefBahrMapper;
import ac.fidoteam.alkhalil.web.rest.errors.ExceptionTranslator;
import ac.fidoteam.alkhalil.service.dto.RefBahrCriteria;
import ac.fidoteam.alkhalil.service.RefBahrQueryService;

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

import ac.fidoteam.alkhalil.domain.enumeration.Style;
/**
 * Integration tests for the {@link RefBahrResource} REST controller.
 */
@SpringBootTest(classes = MetarabApp.class)
public class RefBahrResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SIGNATURE = "AAAAAAAAAA";
    private static final String UPDATED_SIGNATURE = "BBBBBBBBBB";

    private static final Style DEFAULT_STYLE = Style.TAME;
    private static final Style UPDATED_STYLE = Style.WAFI;

    @Autowired
    private RefBahrRepository refBahrRepository;

    @Autowired
    private RefBahrMapper refBahrMapper;

    @Autowired
    private RefBahrService refBahrService;

    /**
     * This repository is mocked in the ac.fidoteam.alkhalil.repository.search test package.
     *
     * @see ac.fidoteam.alkhalil.repository.search.RefBahrSearchRepositoryMockConfiguration
     */
    @Autowired
    private RefBahrSearchRepository mockRefBahrSearchRepository;

    @Autowired
    private RefBahrQueryService refBahrQueryService;

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

    private MockMvc restRefBahrMockMvc;

    private RefBahr refBahr;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RefBahrResource refBahrResource = new RefBahrResource(refBahrService, refBahrQueryService);
        this.restRefBahrMockMvc = MockMvcBuilders.standaloneSetup(refBahrResource)
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
    public static RefBahr createEntity(EntityManager em) {
        RefBahr refBahr = new RefBahr()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .signature(DEFAULT_SIGNATURE)
            .style(DEFAULT_STYLE);
        return refBahr;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RefBahr createUpdatedEntity(EntityManager em) {
        RefBahr refBahr = new RefBahr()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .signature(UPDATED_SIGNATURE)
            .style(UPDATED_STYLE);
        return refBahr;
    }

    @BeforeEach
    public void initTest() {
        refBahr = createEntity(em);
    }

    @Test
    @Transactional
    public void createRefBahr() throws Exception {
        int databaseSizeBeforeCreate = refBahrRepository.findAll().size();

        // Create the RefBahr
        RefBahrDTO refBahrDTO = refBahrMapper.toDto(refBahr);
        restRefBahrMockMvc.perform(post("/api/ref-bahrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refBahrDTO)))
            .andExpect(status().isCreated());

        // Validate the RefBahr in the database
        List<RefBahr> refBahrList = refBahrRepository.findAll();
        assertThat(refBahrList).hasSize(databaseSizeBeforeCreate + 1);
        RefBahr testRefBahr = refBahrList.get(refBahrList.size() - 1);
        assertThat(testRefBahr.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRefBahr.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRefBahr.getSignature()).isEqualTo(DEFAULT_SIGNATURE);
        assertThat(testRefBahr.getStyle()).isEqualTo(DEFAULT_STYLE);

        // Validate the RefBahr in Elasticsearch
        verify(mockRefBahrSearchRepository, times(1)).save(testRefBahr);
    }

    @Test
    @Transactional
    public void createRefBahrWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = refBahrRepository.findAll().size();

        // Create the RefBahr with an existing ID
        refBahr.setId(1L);
        RefBahrDTO refBahrDTO = refBahrMapper.toDto(refBahr);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefBahrMockMvc.perform(post("/api/ref-bahrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refBahrDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RefBahr in the database
        List<RefBahr> refBahrList = refBahrRepository.findAll();
        assertThat(refBahrList).hasSize(databaseSizeBeforeCreate);

        // Validate the RefBahr in Elasticsearch
        verify(mockRefBahrSearchRepository, times(0)).save(refBahr);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = refBahrRepository.findAll().size();
        // set the field null
        refBahr.setCode(null);

        // Create the RefBahr, which fails.
        RefBahrDTO refBahrDTO = refBahrMapper.toDto(refBahr);

        restRefBahrMockMvc.perform(post("/api/ref-bahrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refBahrDTO)))
            .andExpect(status().isBadRequest());

        List<RefBahr> refBahrList = refBahrRepository.findAll();
        assertThat(refBahrList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = refBahrRepository.findAll().size();
        // set the field null
        refBahr.setName(null);

        // Create the RefBahr, which fails.
        RefBahrDTO refBahrDTO = refBahrMapper.toDto(refBahr);

        restRefBahrMockMvc.perform(post("/api/ref-bahrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refBahrDTO)))
            .andExpect(status().isBadRequest());

        List<RefBahr> refBahrList = refBahrRepository.findAll();
        assertThat(refBahrList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSignatureIsRequired() throws Exception {
        int databaseSizeBeforeTest = refBahrRepository.findAll().size();
        // set the field null
        refBahr.setSignature(null);

        // Create the RefBahr, which fails.
        RefBahrDTO refBahrDTO = refBahrMapper.toDto(refBahr);

        restRefBahrMockMvc.perform(post("/api/ref-bahrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refBahrDTO)))
            .andExpect(status().isBadRequest());

        List<RefBahr> refBahrList = refBahrRepository.findAll();
        assertThat(refBahrList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStyleIsRequired() throws Exception {
        int databaseSizeBeforeTest = refBahrRepository.findAll().size();
        // set the field null
        refBahr.setStyle(null);

        // Create the RefBahr, which fails.
        RefBahrDTO refBahrDTO = refBahrMapper.toDto(refBahr);

        restRefBahrMockMvc.perform(post("/api/ref-bahrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refBahrDTO)))
            .andExpect(status().isBadRequest());

        List<RefBahr> refBahrList = refBahrRepository.findAll();
        assertThat(refBahrList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRefBahrs() throws Exception {
        // Initialize the database
        refBahrRepository.saveAndFlush(refBahr);

        // Get all the refBahrList
        restRefBahrMockMvc.perform(get("/api/ref-bahrs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refBahr.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].signature").value(hasItem(DEFAULT_SIGNATURE.toString())))
            .andExpect(jsonPath("$.[*].style").value(hasItem(DEFAULT_STYLE.toString())));
    }
    
    @Test
    @Transactional
    public void getRefBahr() throws Exception {
        // Initialize the database
        refBahrRepository.saveAndFlush(refBahr);

        // Get the refBahr
        restRefBahrMockMvc.perform(get("/api/ref-bahrs/{id}", refBahr.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(refBahr.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.signature").value(DEFAULT_SIGNATURE.toString()))
            .andExpect(jsonPath("$.style").value(DEFAULT_STYLE.toString()));
    }

    @Test
    @Transactional
    public void getAllRefBahrsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        refBahrRepository.saveAndFlush(refBahr);

        // Get all the refBahrList where code equals to DEFAULT_CODE
        defaultRefBahrShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the refBahrList where code equals to UPDATED_CODE
        defaultRefBahrShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllRefBahrsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        refBahrRepository.saveAndFlush(refBahr);

        // Get all the refBahrList where code in DEFAULT_CODE or UPDATED_CODE
        defaultRefBahrShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the refBahrList where code equals to UPDATED_CODE
        defaultRefBahrShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllRefBahrsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        refBahrRepository.saveAndFlush(refBahr);

        // Get all the refBahrList where code is not null
        defaultRefBahrShouldBeFound("code.specified=true");

        // Get all the refBahrList where code is null
        defaultRefBahrShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllRefBahrsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        refBahrRepository.saveAndFlush(refBahr);

        // Get all the refBahrList where name equals to DEFAULT_NAME
        defaultRefBahrShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the refBahrList where name equals to UPDATED_NAME
        defaultRefBahrShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRefBahrsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        refBahrRepository.saveAndFlush(refBahr);

        // Get all the refBahrList where name in DEFAULT_NAME or UPDATED_NAME
        defaultRefBahrShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the refBahrList where name equals to UPDATED_NAME
        defaultRefBahrShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRefBahrsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        refBahrRepository.saveAndFlush(refBahr);

        // Get all the refBahrList where name is not null
        defaultRefBahrShouldBeFound("name.specified=true");

        // Get all the refBahrList where name is null
        defaultRefBahrShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllRefBahrsBySignatureIsEqualToSomething() throws Exception {
        // Initialize the database
        refBahrRepository.saveAndFlush(refBahr);

        // Get all the refBahrList where signature equals to DEFAULT_SIGNATURE
        defaultRefBahrShouldBeFound("signature.equals=" + DEFAULT_SIGNATURE);

        // Get all the refBahrList where signature equals to UPDATED_SIGNATURE
        defaultRefBahrShouldNotBeFound("signature.equals=" + UPDATED_SIGNATURE);
    }

    @Test
    @Transactional
    public void getAllRefBahrsBySignatureIsInShouldWork() throws Exception {
        // Initialize the database
        refBahrRepository.saveAndFlush(refBahr);

        // Get all the refBahrList where signature in DEFAULT_SIGNATURE or UPDATED_SIGNATURE
        defaultRefBahrShouldBeFound("signature.in=" + DEFAULT_SIGNATURE + "," + UPDATED_SIGNATURE);

        // Get all the refBahrList where signature equals to UPDATED_SIGNATURE
        defaultRefBahrShouldNotBeFound("signature.in=" + UPDATED_SIGNATURE);
    }

    @Test
    @Transactional
    public void getAllRefBahrsBySignatureIsNullOrNotNull() throws Exception {
        // Initialize the database
        refBahrRepository.saveAndFlush(refBahr);

        // Get all the refBahrList where signature is not null
        defaultRefBahrShouldBeFound("signature.specified=true");

        // Get all the refBahrList where signature is null
        defaultRefBahrShouldNotBeFound("signature.specified=false");
    }

    @Test
    @Transactional
    public void getAllRefBahrsByStyleIsEqualToSomething() throws Exception {
        // Initialize the database
        refBahrRepository.saveAndFlush(refBahr);

        // Get all the refBahrList where style equals to DEFAULT_STYLE
        defaultRefBahrShouldBeFound("style.equals=" + DEFAULT_STYLE);

        // Get all the refBahrList where style equals to UPDATED_STYLE
        defaultRefBahrShouldNotBeFound("style.equals=" + UPDATED_STYLE);
    }

    @Test
    @Transactional
    public void getAllRefBahrsByStyleIsInShouldWork() throws Exception {
        // Initialize the database
        refBahrRepository.saveAndFlush(refBahr);

        // Get all the refBahrList where style in DEFAULT_STYLE or UPDATED_STYLE
        defaultRefBahrShouldBeFound("style.in=" + DEFAULT_STYLE + "," + UPDATED_STYLE);

        // Get all the refBahrList where style equals to UPDATED_STYLE
        defaultRefBahrShouldNotBeFound("style.in=" + UPDATED_STYLE);
    }

    @Test
    @Transactional
    public void getAllRefBahrsByStyleIsNullOrNotNull() throws Exception {
        // Initialize the database
        refBahrRepository.saveAndFlush(refBahr);

        // Get all the refBahrList where style is not null
        defaultRefBahrShouldBeFound("style.specified=true");

        // Get all the refBahrList where style is null
        defaultRefBahrShouldNotBeFound("style.specified=false");
    }

    @Test
    @Transactional
    public void getAllRefBahrsByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        refBahrRepository.saveAndFlush(refBahr);
        RefBahr parent = RefBahrResourceIT.createEntity(em);
        em.persist(parent);
        em.flush();
        refBahr.setParent(parent);
        refBahrRepository.saveAndFlush(refBahr);
        Long parentId = parent.getId();

        // Get all the refBahrList where parent equals to parentId
        defaultRefBahrShouldBeFound("parentId.equals=" + parentId);

        // Get all the refBahrList where parent equals to parentId + 1
        defaultRefBahrShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRefBahrShouldBeFound(String filter) throws Exception {
        restRefBahrMockMvc.perform(get("/api/ref-bahrs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refBahr.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].signature").value(hasItem(DEFAULT_SIGNATURE)))
            .andExpect(jsonPath("$.[*].style").value(hasItem(DEFAULT_STYLE.toString())));

        // Check, that the count call also returns 1
        restRefBahrMockMvc.perform(get("/api/ref-bahrs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRefBahrShouldNotBeFound(String filter) throws Exception {
        restRefBahrMockMvc.perform(get("/api/ref-bahrs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRefBahrMockMvc.perform(get("/api/ref-bahrs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRefBahr() throws Exception {
        // Get the refBahr
        restRefBahrMockMvc.perform(get("/api/ref-bahrs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRefBahr() throws Exception {
        // Initialize the database
        refBahrRepository.saveAndFlush(refBahr);

        int databaseSizeBeforeUpdate = refBahrRepository.findAll().size();

        // Update the refBahr
        RefBahr updatedRefBahr = refBahrRepository.findById(refBahr.getId()).get();
        // Disconnect from session so that the updates on updatedRefBahr are not directly saved in db
        em.detach(updatedRefBahr);
        updatedRefBahr
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .signature(UPDATED_SIGNATURE)
            .style(UPDATED_STYLE);
        RefBahrDTO refBahrDTO = refBahrMapper.toDto(updatedRefBahr);

        restRefBahrMockMvc.perform(put("/api/ref-bahrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refBahrDTO)))
            .andExpect(status().isOk());

        // Validate the RefBahr in the database
        List<RefBahr> refBahrList = refBahrRepository.findAll();
        assertThat(refBahrList).hasSize(databaseSizeBeforeUpdate);
        RefBahr testRefBahr = refBahrList.get(refBahrList.size() - 1);
        assertThat(testRefBahr.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRefBahr.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRefBahr.getSignature()).isEqualTo(UPDATED_SIGNATURE);
        assertThat(testRefBahr.getStyle()).isEqualTo(UPDATED_STYLE);

        // Validate the RefBahr in Elasticsearch
        verify(mockRefBahrSearchRepository, times(1)).save(testRefBahr);
    }

    @Test
    @Transactional
    public void updateNonExistingRefBahr() throws Exception {
        int databaseSizeBeforeUpdate = refBahrRepository.findAll().size();

        // Create the RefBahr
        RefBahrDTO refBahrDTO = refBahrMapper.toDto(refBahr);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRefBahrMockMvc.perform(put("/api/ref-bahrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refBahrDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RefBahr in the database
        List<RefBahr> refBahrList = refBahrRepository.findAll();
        assertThat(refBahrList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RefBahr in Elasticsearch
        verify(mockRefBahrSearchRepository, times(0)).save(refBahr);
    }

    @Test
    @Transactional
    public void deleteRefBahr() throws Exception {
        // Initialize the database
        refBahrRepository.saveAndFlush(refBahr);

        int databaseSizeBeforeDelete = refBahrRepository.findAll().size();

        // Delete the refBahr
        restRefBahrMockMvc.perform(delete("/api/ref-bahrs/{id}", refBahr.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RefBahr> refBahrList = refBahrRepository.findAll();
        assertThat(refBahrList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RefBahr in Elasticsearch
        verify(mockRefBahrSearchRepository, times(1)).deleteById(refBahr.getId());
    }

    @Test
    @Transactional
    public void searchRefBahr() throws Exception {
        // Initialize the database
        refBahrRepository.saveAndFlush(refBahr);
        when(mockRefBahrSearchRepository.search(queryStringQuery("id:" + refBahr.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(refBahr), PageRequest.of(0, 1), 1));
        // Search the refBahr
        restRefBahrMockMvc.perform(get("/api/_search/ref-bahrs?query=id:" + refBahr.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refBahr.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].signature").value(hasItem(DEFAULT_SIGNATURE)))
            .andExpect(jsonPath("$.[*].style").value(hasItem(DEFAULT_STYLE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefBahr.class);
        RefBahr refBahr1 = new RefBahr();
        refBahr1.setId(1L);
        RefBahr refBahr2 = new RefBahr();
        refBahr2.setId(refBahr1.getId());
        assertThat(refBahr1).isEqualTo(refBahr2);
        refBahr2.setId(2L);
        assertThat(refBahr1).isNotEqualTo(refBahr2);
        refBahr1.setId(null);
        assertThat(refBahr1).isNotEqualTo(refBahr2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefBahrDTO.class);
        RefBahrDTO refBahrDTO1 = new RefBahrDTO();
        refBahrDTO1.setId(1L);
        RefBahrDTO refBahrDTO2 = new RefBahrDTO();
        assertThat(refBahrDTO1).isNotEqualTo(refBahrDTO2);
        refBahrDTO2.setId(refBahrDTO1.getId());
        assertThat(refBahrDTO1).isEqualTo(refBahrDTO2);
        refBahrDTO2.setId(2L);
        assertThat(refBahrDTO1).isNotEqualTo(refBahrDTO2);
        refBahrDTO1.setId(null);
        assertThat(refBahrDTO1).isNotEqualTo(refBahrDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(refBahrMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(refBahrMapper.fromId(null)).isNull();
    }
}
