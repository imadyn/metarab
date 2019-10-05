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

import ac.fidoteam.alkhalil.domain.RefBahr;
import ac.fidoteam.alkhalil.domain.*; // for static metamodels
import ac.fidoteam.alkhalil.repository.RefBahrRepository;
import ac.fidoteam.alkhalil.repository.search.RefBahrSearchRepository;
import ac.fidoteam.alkhalil.service.dto.RefBahrCriteria;
import ac.fidoteam.alkhalil.service.dto.RefBahrDTO;
import ac.fidoteam.alkhalil.service.mapper.RefBahrMapper;

/**
 * Service for executing complex queries for {@link RefBahr} entities in the database.
 * The main input is a {@link RefBahrCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RefBahrDTO} or a {@link Page} of {@link RefBahrDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RefBahrQueryService extends QueryService<RefBahr> {

    private final Logger log = LoggerFactory.getLogger(RefBahrQueryService.class);

    private final RefBahrRepository refBahrRepository;

    private final RefBahrMapper refBahrMapper;

    private final RefBahrSearchRepository refBahrSearchRepository;

    public RefBahrQueryService(RefBahrRepository refBahrRepository, RefBahrMapper refBahrMapper, RefBahrSearchRepository refBahrSearchRepository) {
        this.refBahrRepository = refBahrRepository;
        this.refBahrMapper = refBahrMapper;
        this.refBahrSearchRepository = refBahrSearchRepository;
    }

    /**
     * Return a {@link List} of {@link RefBahrDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RefBahrDTO> findByCriteria(RefBahrCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RefBahr> specification = createSpecification(criteria);
        return refBahrMapper.toDto(refBahrRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RefBahrDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RefBahrDTO> findByCriteria(RefBahrCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RefBahr> specification = createSpecification(criteria);
        return refBahrRepository.findAll(specification, page)
            .map(refBahrMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RefBahrCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RefBahr> specification = createSpecification(criteria);
        return refBahrRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<RefBahr> createSpecification(RefBahrCriteria criteria) {
        Specification<RefBahr> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), RefBahr_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), RefBahr_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), RefBahr_.name));
            }
            if (criteria.getSignature() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSignature(), RefBahr_.signature));
            }
            if (criteria.getStyle() != null) {
                specification = specification.and(buildSpecification(criteria.getStyle(), RefBahr_.style));
            }
            if (criteria.getParentId() != null) {
                specification = specification.and(buildSpecification(criteria.getParentId(),
                    root -> root.join(RefBahr_.parent, JoinType.LEFT).get(RefBahr_.id)));
            }
        }
        return specification;
    }
}
