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

import ac.fidoteam.alkhalil.domain.TypeTB;
import ac.fidoteam.alkhalil.domain.*; // for static metamodels
import ac.fidoteam.alkhalil.repository.TypeTBRepository;
import ac.fidoteam.alkhalil.repository.search.TypeTBSearchRepository;
import ac.fidoteam.alkhalil.service.dto.TypeTBCriteria;
import ac.fidoteam.alkhalil.service.dto.TypeTBDTO;
import ac.fidoteam.alkhalil.service.mapper.TypeTBMapper;

/**
 * Service for executing complex queries for {@link TypeTB} entities in the database.
 * The main input is a {@link TypeTBCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TypeTBDTO} or a {@link Page} of {@link TypeTBDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TypeTBQueryService extends QueryService<TypeTB> {

    private final Logger log = LoggerFactory.getLogger(TypeTBQueryService.class);

    private final TypeTBRepository typeTBRepository;

    private final TypeTBMapper typeTBMapper;

    private final TypeTBSearchRepository typeTBSearchRepository;

    public TypeTBQueryService(TypeTBRepository typeTBRepository, TypeTBMapper typeTBMapper, TypeTBSearchRepository typeTBSearchRepository) {
        this.typeTBRepository = typeTBRepository;
        this.typeTBMapper = typeTBMapper;
        this.typeTBSearchRepository = typeTBSearchRepository;
    }

    /**
     * Return a {@link List} of {@link TypeTBDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TypeTBDTO> findByCriteria(TypeTBCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TypeTB> specification = createSpecification(criteria);
        return typeTBMapper.toDto(typeTBRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TypeTBDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TypeTBDTO> findByCriteria(TypeTBCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TypeTB> specification = createSpecification(criteria);
        return typeTBRepository.findAll(specification, page)
            .map(typeTBMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TypeTBCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TypeTB> specification = createSpecification(criteria);
        return typeTBRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<TypeTB> createSpecification(TypeTBCriteria criteria) {
        Specification<TypeTB> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TypeTB_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), TypeTB_.code));
            }
            if (criteria.getOrdre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrdre(), TypeTB_.ordre));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), TypeTB_.type));
            }
            if (criteria.getRefBahrId() != null) {
                specification = specification.and(buildSpecification(criteria.getRefBahrId(),
                    root -> root.join(TypeTB_.refBahr, JoinType.LEFT).get(RefBahr_.id)));
            }
            if (criteria.getRefRhythmId() != null) {
                specification = specification.and(buildSpecification(criteria.getRefRhythmId(),
                    root -> root.join(TypeTB_.refRhythm, JoinType.LEFT).get(RefRhythm_.id)));
            }
        }
        return specification;
    }
}
