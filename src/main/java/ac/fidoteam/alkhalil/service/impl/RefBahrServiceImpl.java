package ac.fidoteam.alkhalil.service.impl;

import ac.fidoteam.alkhalil.service.RefBahrService;
import ac.fidoteam.alkhalil.domain.RefBahr;
import ac.fidoteam.alkhalil.repository.RefBahrRepository;
import ac.fidoteam.alkhalil.repository.search.RefBahrSearchRepository;
import ac.fidoteam.alkhalil.service.dto.RefBahrDTO;
import ac.fidoteam.alkhalil.service.mapper.RefBahrMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link RefBahr}.
 */
@Service
@Transactional
public class RefBahrServiceImpl implements RefBahrService {

    private final Logger log = LoggerFactory.getLogger(RefBahrServiceImpl.class);

    private final RefBahrRepository refBahrRepository;

    private final RefBahrMapper refBahrMapper;

    private final RefBahrSearchRepository refBahrSearchRepository;

    public RefBahrServiceImpl(RefBahrRepository refBahrRepository, RefBahrMapper refBahrMapper, RefBahrSearchRepository refBahrSearchRepository) {
        this.refBahrRepository = refBahrRepository;
        this.refBahrMapper = refBahrMapper;
        this.refBahrSearchRepository = refBahrSearchRepository;
    }

    /**
     * Save a refBahr.
     *
     * @param refBahrDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RefBahrDTO save(RefBahrDTO refBahrDTO) {
        log.debug("Request to save RefBahr : {}", refBahrDTO);
        RefBahr refBahr = refBahrMapper.toEntity(refBahrDTO);
        refBahr = refBahrRepository.save(refBahr);
        RefBahrDTO result = refBahrMapper.toDto(refBahr);
        refBahrSearchRepository.save(refBahr);
        return result;
    }

    /**
     * Get all the refBahrs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefBahrDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RefBahrs");
        return refBahrRepository.findAll(pageable)
            .map(refBahrMapper::toDto);
    }


    /**
     * Get one refBahr by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RefBahrDTO> findOne(Long id) {
        log.debug("Request to get RefBahr : {}", id);
        return refBahrRepository.findById(id)
            .map(refBahrMapper::toDto);
    }

    /**
     * Delete the refBahr by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RefBahr : {}", id);
        refBahrRepository.deleteById(id);
        refBahrSearchRepository.deleteById(id);
    }

    /**
     * Search for the refBahr corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefBahrDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RefBahrs for query {}", query);
        return refBahrSearchRepository.search(queryStringQuery(query), pageable)
            .map(refBahrMapper::toDto);
    }
}
