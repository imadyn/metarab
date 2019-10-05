package ac.fidoteam.alkhalil.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import ac.fidoteam.alkhalil.domain.RefRhythm;
import ac.fidoteam.alkhalil.domain.*; // for static metamodels
import ac.fidoteam.alkhalil.repository.RefRhythmRepository;
import ac.fidoteam.alkhalil.repository.search.RefRhythmSearchRepository;
import ac.fidoteam.alkhalil.service.dto.RefRhythmCriteria;
import ac.fidoteam.alkhalil.service.dto.RefRhythmDTO;
import ac.fidoteam.alkhalil.service.mapper.RefRhythmMapper;

/**
 * Service for executing complex queries for {@link RefRhythm} entities in the database.
 * The main input is a {@link RefRhythmCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RefRhythmDTO} or a {@link Page} of {@link RefRhythmDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RefRhythmQueryService extends QueryService<RefRhythm> {

    private final Logger log = LoggerFactory.getLogger(RefRhythmQueryService.class);

    private final RefRhythmRepository refRhythmRepository;

    private final RefRhythmMapper refRhythmMapper;

    private final RefRhythmSearchRepository refRhythmSearchRepository;

    public RefRhythmQueryService(RefRhythmRepository refRhythmRepository, RefRhythmMapper refRhythmMapper, RefRhythmSearchRepository refRhythmSearchRepository) {
        this.refRhythmRepository = refRhythmRepository;
        this.refRhythmMapper = refRhythmMapper;
        this.refRhythmSearchRepository = refRhythmSearchRepository;
    }

    /**
     * Return a {@link List} of {@link RefRhythmDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RefRhythmDTO> findByCriteria(RefRhythmCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RefRhythm> specification = createSpecification(criteria);
        return refRhythmMapper.toDto(refRhythmRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RefRhythmDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RefRhythmDTO> findByCriteria(RefRhythmCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RefRhythm> specification = createSpecification(criteria);
        return refRhythmRepository.findAll(specification, page)
            .map(refRhythmMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RefRhythmCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RefRhythm> specification = createSpecification(criteria);
        return refRhythmRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<RefRhythm> createSpecification(RefRhythmCriteria criteria) {
        Specification<RefRhythm> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), RefRhythm_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), RefRhythm_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), RefRhythm_.name));
            }
            if (criteria.getValeur() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValeur(), RefRhythm_.valeur));
            }
            if (criteria.getTransform() != null) {
                specification = specification.and(buildSpecification(criteria.getTransform(), RefRhythm_.transform));
            }
            if (criteria.getParentId() != null) {
                specification = specification.and(buildSpecification(criteria.getParentId(),
                    root -> root.join(RefRhythm_.parent, JoinType.LEFT).get(RefRhythm_.id)));
            }
        }
        return specification;
    }
}
