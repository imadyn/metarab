package ac.fidoteam.alkhalil.web.rest;

import ac.fidoteam.alkhalil.MetarabApp;
import ac.fidoteam.alkhalil.domain.RefRhythm;
import ac.fidoteam.alkhalil.domain.RefRhythm;
import ac.fidoteam.alkhalil.repository.RefRhythmRepository;
import ac.fidoteam.alkhalil.repository.search.RefRhythmSearchRepository;
import ac.fidoteam.alkhalil.service.RefRhythmService;
import ac.fidoteam.alkhalil.service.dto.RefRhythmDTO;
import ac.fidoteam.alkhalil.service.mapper.RefRhythmMapper;
import ac.fidoteam.alkhalil.web.rest.errors.ExceptionTranslator;
import ac.fidoteam.alkhalil.service.dto.RefRhythmCriteria;
import ac.fidoteam.alkhalil.service.RefRhythmQueryService;

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

import ac.fidoteam.alkhalil.domain.enumeration.Transform;
/**
 * Integration tests for the {@link RefRhythmResource} REST controller.
 */
@SpringBootTest(classes = MetarabApp.class)
public class RefRhythmResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALEUR = "AAAAAAAAAA";
    private static final String UPDATED_VALEUR = "BBBBBBBBBB";

    private static final Transform DEFAULT_TRANSFORM = Transform.IDENTITE;
    private static final Transform UPDATED_TRANSFORM = Transform.KHABN;

    @Autowired
    private RefRhythmRepository refRhythmRepository;

    @Autowired
    private RefRhythmMapper refRhythmMapper;

    @Autowired
    private RefRhythmService refRhythmService;

    /**
     * This repository is mocked in the ac.fidoteam.alkhalil.repository.search test package.
     *
     * @see ac.fidoteam.alkhalil.repository.search.RefRhythmSearchRepositoryMockConfiguration
     */
    @Autowired
    private RefRhythmSearchRepository mockRefRhythmSearchRepository;

    @Autowired
    private RefRhythmQueryService refRhythmQueryService;

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

    private MockMvc restRefRhythmMockMvc;

    private RefRhythm refRhythm;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RefRhythmResource refRhythmResource = new RefRhythmResource(refRhythmService, refRhythmQueryService);
        this.restRefRhythmMockMvc = MockMvcBuilders.standaloneSetup(refRhythmResource)
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
    public static RefRhythm createEntity(EntityManager em) {
        RefRhythm refRhythm = new RefRhythm()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .valeur(DEFAULT_VALEUR)
            .transform(DEFAULT_TRANSFORM);
        return refRhythm;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RefRhythm createUpdatedEntity(EntityManager em) {
        RefRhythm refRhythm = new RefRhythm()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .valeur(UPDATED_VALEUR)
            .transform(UPDATED_TRANSFORM);
        return refRhythm;
    }

    @BeforeEach
    public void initTest() {
        refRhythm = createEntity(em);
    }

    @Test
    @Transactional
    public void createRefRhythm() throws Exception {
        int databaseSizeBeforeCreate = refRhythmRepository.findAll().size();

        // Create the RefRhythm
        RefRhythmDTO refRhythmDTO = refRhythmMapper.toDto(refRhythm);
        restRefRhythmMockMvc.perform(post("/api/ref-rhythms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refRhythmDTO)))
            .andExpect(status().isCreated());

        // Validate the RefRhythm in the database
        List<RefRhythm> refRhythmList = refRhythmRepository.findAll();
        assertThat(refRhythmList).hasSize(databaseSizeBeforeCreate + 1);
        RefRhythm testRefRhythm = refRhythmList.get(refRhythmList.size() - 1);
        assertThat(testRefRhythm.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRefRhythm.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRefRhythm.getValeur()).isEqualTo(DEFAULT_VALEUR);
        assertThat(testRefRhythm.getTransform()).isEqualTo(DEFAULT_TRANSFORM);

        // Validate the RefRhythm in Elasticsearch
        verify(mockRefRhythmSearchRepository, times(1)).save(testRefRhythm);
    }

    @Test
    @Transactional
    public void createRefRhythmWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = refRhythmRepository.findAll().size();

        // Create the RefRhythm with an existing ID
        refRhythm.setId(1L);
        RefRhythmDTO refRhythmDTO = refRhythmMapper.toDto(refRhythm);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefRhythmMockMvc.perform(post("/api/ref-rhythms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refRhythmDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RefRhythm in the database
        List<RefRhythm> refRhythmList = refRhythmRepository.findAll();
        assertThat(refRhythmList).hasSize(databaseSizeBeforeCreate);

        // Validate the RefRhythm in Elasticsearch
        verify(mockRefRhythmSearchRepository, times(0)).save(refRhythm);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = refRhythmRepository.findAll().size();
        // set the field null
        refRhythm.setCode(null);

        // Create the RefRhythm, which fails.
        RefRhythmDTO refRhythmDTO = refRhythmMapper.toDto(refRhythm);

        restRefRhythmMockMvc.perform(post("/api/ref-rhythms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refRhythmDTO)))
            .andExpect(status().isBadRequest());

        List<RefRhythm> refRhythmList = refRhythmRepository.findAll();
        assertThat(refRhythmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = refRhythmRepository.findAll().size();
        // set the field null
        refRhythm.setName(null);

        // Create the RefRhythm, which fails.
        RefRhythmDTO refRhythmDTO = refRhythmMapper.toDto(refRhythm);

        restRefRhythmMockMvc.perform(post("/api/ref-rhythms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refRhythmDTO)))
            .andExpect(status().isBadRequest());

        List<RefRhythm> refRhythmList = refRhythmRepository.findAll();
        assertThat(refRhythmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValeurIsRequired() throws Exception {
        int databaseSizeBeforeTest = refRhythmRepository.findAll().size();
        // set the field null
        refRhythm.setValeur(null);

        // Create the RefRhythm, which fails.
        RefRhythmDTO refRhythmDTO = refRhythmMapper.toDto(refRhythm);

        restRefRhythmMockMvc.perform(post("/api/ref-rhythms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refRhythmDTO)))
            .andExpect(status().isBadRequest());

        List<RefRhythm> refRhythmList = refRhythmRepository.findAll();
        assertThat(refRhythmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransformIsRequired() throws Exception {
        int databaseSizeBeforeTest = refRhythmRepository.findAll().size();
        // set the field null
        refRhythm.setTransform(null);

        // Create the RefRhythm, which fails.
        RefRhythmDTO refRhythmDTO = refRhythmMapper.toDto(refRhythm);

        restRefRhythmMockMvc.perform(post("/api/ref-rhythms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refRhythmDTO)))
            .andExpect(status().isBadRequest());

        List<RefRhythm> refRhythmList = refRhythmRepository.findAll();
        assertThat(refRhythmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRefRhythms() throws Exception {
        // Initialize the database
        refRhythmRepository.saveAndFlush(refRhythm);

        // Get all the refRhythmList
        restRefRhythmMockMvc.perform(get("/api/ref-rhythms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refRhythm.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].valeur").value(hasItem(DEFAULT_VALEUR.toString())))
            .andExpect(jsonPath("$.[*].transform").value(hasItem(DEFAULT_TRANSFORM.toString())));
    }
    
    @Test
    @Transactional
    public void getRefRhythm() throws Exception {
        // Initialize the database
        refRhythmRepository.saveAndFlush(refRhythm);

        // Get the refRhythm
        restRefRhythmMockMvc.perform(get("/api/ref-rhythms/{id}", refRhythm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(refRhythm.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.valeur").value(DEFAULT_VALEUR.toString()))
            .andExpect(jsonPath("$.transform").value(DEFAULT_TRANSFORM.toString()));
    }

    @Test
    @Transactional
    public void getAllRefRhythmsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        refRhythmRepository.saveAndFlush(refRhythm);

        // Get all the refRhythmList where code equals to DEFAULT_CODE
        defaultRefRhythmShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the refRhythmList where code equals to UPDATED_CODE
        defaultRefRhythmShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllRefRhythmsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        refRhythmRepository.saveAndFlush(refRhythm);

        // Get all the refRhythmList where code in DEFAULT_CODE or UPDATED_CODE
        defaultRefRhythmShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the refRhythmList where code equals to UPDATED_CODE
        defaultRefRhythmShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllRefRhythmsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        refRhythmRepository.saveAndFlush(refRhythm);

        // Get all the refRhythmList where code is not null
        defaultRefRhythmShouldBeFound("code.specified=true");

        // Get all the refRhythmList where code is null
        defaultRefRhythmShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllRefRhythmsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        refRhythmRepository.saveAndFlush(refRhythm);

        // Get all the refRhythmList where name equals to DEFAULT_NAME
        defaultRefRhythmShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the refRhythmList where name equals to UPDATED_NAME
        defaultRefRhythmShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRefRhythmsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        refRhythmRepository.saveAndFlush(refRhythm);

        // Get all the refRhythmList where name in DEFAULT_NAME or UPDATED_NAME
        defaultRefRhythmShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the refRhythmList where name equals to UPDATED_NAME
        defaultRefRhythmShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRefRhythmsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        refRhythmRepository.saveAndFlush(refRhythm);

        // Get all the refRhythmList where name is not null
        defaultRefRhythmShouldBeFound("name.specified=true");

        // Get all the refRhythmList where name is null
        defaultRefRhythmShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllRefRhythmsByValeurIsEqualToSomething() throws Exception {
        // Initialize the database
        refRhythmRepository.saveAndFlush(refRhythm);

        // Get all the refRhythmList where valeur equals to DEFAULT_VALEUR
        defaultRefRhythmShouldBeFound("valeur.equals=" + DEFAULT_VALEUR);

        // Get all the refRhythmList where valeur equals to UPDATED_VALEUR
        defaultRefRhythmShouldNotBeFound("valeur.equals=" + UPDATED_VALEUR);
    }

    @Test
    @Transactional
    public void getAllRefRhythmsByValeurIsInShouldWork() throws Exception {
        // Initialize the database
        refRhythmRepository.saveAndFlush(refRhythm);

        // Get all the refRhythmList where valeur in DEFAULT_VALEUR or UPDATED_VALEUR
        defaultRefRhythmShouldBeFound("valeur.in=" + DEFAULT_VALEUR + "," + UPDATED_VALEUR);

        // Get all the refRhythmList where valeur equals to UPDATED_VALEUR
        defaultRefRhythmShouldNotBeFound("valeur.in=" + UPDATED_VALEUR);
    }

    @Test
    @Transactional
    public void getAllRefRhythmsByValeurIsNullOrNotNull() throws Exception {
        // Initialize the database
        refRhythmRepository.saveAndFlush(refRhythm);

        // Get all the refRhythmList where valeur is not null
        defaultRefRhythmShouldBeFound("valeur.specified=true");

        // Get all the refRhythmList where valeur is null
        defaultRefRhythmShouldNotBeFound("valeur.specified=false");
    }

    @Test
    @Transactional
    public void getAllRefRhythmsByTransformIsEqualToSomething() throws Exception {
        // Initialize the database
        refRhythmRepository.saveAndFlush(refRhythm);

        // Get all the refRhythmList where transform equals to DEFAULT_TRANSFORM
        defaultRefRhythmShouldBeFound("transform.equals=" + DEFAULT_TRANSFORM);

        // Get all the refRhythmList where transform equals to UPDATED_TRANSFORM
        defaultRefRhythmShouldNotBeFound("transform.equals=" + UPDATED_TRANSFORM);
    }

    @Test
    @Transactional
    public void getAllRefRhythmsByTransformIsInShouldWork() throws Exception {
        // Initialize the database
        refRhythmRepository.saveAndFlush(refRhythm);

        // Get all the refRhythmList where transform in DEFAULT_TRANSFORM or UPDATED_TRANSFORM
        defaultRefRhythmShouldBeFound("transform.in=" + DEFAULT_TRANSFORM + "," + UPDATED_TRANSFORM);

        // Get all the refRhythmList where transform equals to UPDATED_TRANSFORM
        defaultRefRhythmShouldNotBeFound("transform.in=" + UPDATED_TRANSFORM);
    }

    @Test
    @Transactional
    public void getAllRefRhythmsByTransformIsNullOrNotNull() throws Exception {
        // Initialize the database
        refRhythmRepository.saveAndFlush(refRhythm);

        // Get all the refRhythmList where transform is not null
        defaultRefRhythmShouldBeFound("transform.specified=true");

        // Get all the refRhythmList where transform is null
        defaultRefRhythmShouldNotBeFound("transform.specified=false");
    }

    @Test
    @Transactional
    public void getAllRefRhythmsByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        refRhythmRepository.saveAndFlush(refRhythm);
        RefRhythm parent = RefRhythmResourceIT.createEntity(em);
        em.persist(parent);
        em.flush();
        refRhythm.setParent(parent);
        refRhythmRepository.saveAndFlush(refRhythm);
        Long parentId = parent.getId();

        // Get all the refRhythmList where parent equals to parentId
        defaultRefRhythmShouldBeFound("parentId.equals=" + parentId);

        // Get all the refRhythmList where parent equals to parentId + 1
        defaultRefRhythmShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRefRhythmShouldBeFound(String filter) throws Exception {
        restRefRhythmMockMvc.perform(get("/api/ref-rhythms?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refRhythm.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].valeur").value(hasItem(DEFAULT_VALEUR)))
            .andExpect(jsonPath("$.[*].transform").value(hasItem(DEFAULT_TRANSFORM.toString())));

        // Check, that the count call also returns 1
        restRefRhythmMockMvc.perform(get("/api/ref-rhythms/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRefRhythmShouldNotBeFound(String filter) throws Exception {
        restRefRhythmMockMvc.perform(get("/api/ref-rhythms?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRefRhythmMockMvc.perform(get("/api/ref-rhythms/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRefRhythm() throws Exception {
        // Get the refRhythm
        restRefRhythmMockMvc.perform(get("/api/ref-rhythms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRefRhythm() throws Exception {
        // Initialize the database
        refRhythmRepository.saveAndFlush(refRhythm);

        int databaseSizeBeforeUpdate = refRhythmRepository.findAll().size();

        // Update the refRhythm
        RefRhythm updatedRefRhythm = refRhythmRepository.findById(refRhythm.getId()).get();
        // Disconnect from session so that the updates on updatedRefRhythm are not directly saved in db
        em.detach(updatedRefRhythm);
        updatedRefRhythm
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .valeur(UPDATED_VALEUR)
            .transform(UPDATED_TRANSFORM);
        RefRhythmDTO refRhythmDTO = refRhythmMapper.toDto(updatedRefRhythm);

        restRefRhythmMockMvc.perform(put("/api/ref-rhythms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refRhythmDTO)))
            .andExpect(status().isOk());

        // Validate the RefRhythm in the database
        List<RefRhythm> refRhythmList = refRhythmRepository.findAll();
        assertThat(refRhythmList).hasSize(databaseSizeBeforeUpdate);
        RefRhythm testRefRhythm = refRhythmList.get(refRhythmList.size() - 1);
        assertThat(testRefRhythm.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRefRhythm.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRefRhythm.getValeur()).isEqualTo(UPDATED_VALEUR);
        assertThat(testRefRhythm.getTransform()).isEqualTo(UPDATED_TRANSFORM);

        // Validate the RefRhythm in Elasticsearch
        verify(mockRefRhythmSearchRepository, times(1)).save(testRefRhythm);
    }

    @Test
    @Transactional
    public void updateNonExistingRefRhythm() throws Exception {
        int databaseSizeBeforeUpdate = refRhythmRepository.findAll().size();

        // Create the RefRhythm
        RefRhythmDTO refRhythmDTO = refRhythmMapper.toDto(refRhythm);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRefRhythmMockMvc.perform(put("/api/ref-rhythms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refRhythmDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RefRhythm in the database
        List<RefRhythm> refRhythmList = refRhythmRepository.findAll();
        assertThat(refRhythmList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RefRhythm in Elasticsearch
        verify(mockRefRhythmSearchRepository, times(0)).save(refRhythm);
    }

    @Test
    @Transactional
    public void deleteRefRhythm() throws Exception {
        // Initialize the database
        refRhythmRepository.saveAndFlush(refRhythm);

        int databaseSizeBeforeDelete = refRhythmRepository.findAll().size();

        // Delete the refRhythm
        restRefRhythmMockMvc.perform(delete("/api/ref-rhythms/{id}", refRhythm.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RefRhythm> refRhythmList = refRhythmRepository.findAll();
        assertThat(refRhythmList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RefRhythm in Elasticsearch
        verify(mockRefRhythmSearchRepository, times(1)).deleteById(refRhythm.getId());
    }

    @Test
    @Transactional
    public void searchRefRhythm() throws Exception {
        // Initialize the database
        refRhythmRepository.saveAndFlush(refRhythm);
        when(mockRefRhythmSearchRepository.search(queryStringQuery("id:" + refRhythm.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(refRhythm), PageRequest.of(0, 1), 1));
        // Search the refRhythm
        restRefRhythmMockMvc.perform(get("/api/_search/ref-rhythms?query=id:" + refRhythm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refRhythm.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].valeur").value(hasItem(DEFAULT_VALEUR)))
            .andExpect(jsonPath("$.[*].transform").value(hasItem(DEFAULT_TRANSFORM.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefRhythm.class);
        RefRhythm refRhythm1 = new RefRhythm();
        refRhythm1.setId(1L);
        RefRhythm refRhythm2 = new RefRhythm();
        refRhythm2.setId(refRhythm1.getId());
        assertThat(refRhythm1).isEqualTo(refRhythm2);
        refRhythm2.setId(2L);
        assertThat(refRhythm1).isNotEqualTo(refRhythm2);
        refRhythm1.setId(null);
        assertThat(refRhythm1).isNotEqualTo(refRhythm2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefRhythmDTO.class);
        RefRhythmDTO refRhythmDTO1 = new RefRhythmDTO();
        refRhythmDTO1.setId(1L);
        RefRhythmDTO refRhythmDTO2 = new RefRhythmDTO();
        assertThat(refRhythmDTO1).isNotEqualTo(refRhythmDTO2);
        refRhythmDTO2.setId(refRhythmDTO1.getId());
        assertThat(refRhythmDTO1).isEqualTo(refRhythmDTO2);
        refRhythmDTO2.setId(2L);
        assertThat(refRhythmDTO1).isNotEqualTo(refRhythmDTO2);
        refRhythmDTO1.setId(null);
        assertThat(refRhythmDTO1).isNotEqualTo(refRhythmDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(refRhythmMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(refRhythmMapper.fromId(null)).isNull();
    }
}
