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

import ac.fidoteam.alkhalil.domain.RefAlphabet;
import ac.fidoteam.alkhalil.domain.*; // for static metamodels
import ac.fidoteam.alkhalil.repository.RefAlphabetRepository;
import ac.fidoteam.alkhalil.repository.search.RefAlphabetSearchRepository;
import ac.fidoteam.alkhalil.service.dto.RefAlphabetCriteria;
import ac.fidoteam.alkhalil.service.dto.RefAlphabetDTO;
import ac.fidoteam.alkhalil.service.mapper.RefAlphabetMapper;

/**
 * Service for executing complex queries for {@link RefAlphabet} entities in the database.
 * The main input is a {@link RefAlphabetCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RefAlphabetDTO} or a {@link Page} of {@link RefAlphabetDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RefAlphabetQueryService extends QueryService<RefAlphabet> {

    private final Logger log = LoggerFactory.getLogger(RefAlphabetQueryService.class);

    private final RefAlphabetRepository refAlphabetRepository;

    private final RefAlphabetMapper refAlphabetMapper;

    private final RefAlphabetSearchRepository refAlphabetSearchRepository;

    public RefAlphabetQueryService(RefAlphabetRepository refAlphabetRepository, RefAlphabetMapper refAlphabetMapper, RefAlphabetSearchRepository refAlphabetSearchRepository) {
        this.refAlphabetRepository = refAlphabetRepository;
        this.refAlphabetMapper = refAlphabetMapper;
        this.refAlphabetSearchRepository = refAlphabetSearchRepository;
    }

    /**
     * Return a {@link List} of {@link RefAlphabetDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RefAlphabetDTO> findByCriteria(RefAlphabetCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RefAlphabet> specification = createSpecification(criteria);
        return refAlphabetMapper.toDto(refAlphabetRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RefAlphabetDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RefAlphabetDTO> findByCriteria(RefAlphabetCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RefAlphabet> specification = createSpecification(criteria);
        return refAlphabetRepository.findAll(specification, page)
            .map(refAlphabetMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RefAlphabetCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RefAlphabet> specification = createSpecification(criteria);
        return refAlphabetRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<RefAlphabet> createSpecification(RefAlphabetCriteria criteria) {
        Specification<RefAlphabet> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), RefAlphabet_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), RefAlphabet_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), RefAlphabet_.name));
            }
            if (criteria.getRhythm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRhythm(), RefAlphabet_.rhythm));
            }
            if (criteria.getLanguage() != null) {
                specification = specification.and(buildSpecification(criteria.getLanguage(), RefAlphabet_.language));
            }
        }
        return specification;
    }
}
