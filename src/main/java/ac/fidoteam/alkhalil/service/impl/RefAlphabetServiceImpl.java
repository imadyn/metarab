package ac.fidoteam.alkhalil.service.impl;

import ac.fidoteam.alkhalil.service.RefAlphabetService;
import ac.fidoteam.alkhalil.domain.RefAlphabet;
import ac.fidoteam.alkhalil.repository.RefAlphabetRepository;
import ac.fidoteam.alkhalil.repository.search.RefAlphabetSearchRepository;
import ac.fidoteam.alkhalil.service.dto.RefAlphabetDTO;
import ac.fidoteam.alkhalil.service.mapper.RefAlphabetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link RefAlphabet}.
 */
@Service
@Transactional
public class RefAlphabetServiceImpl implements RefAlphabetService {

    private final Logger log = LoggerFactory.getLogger(RefAlphabetServiceImpl.class);

    private final RefAlphabetRepository refAlphabetRepository;

    private final RefAlphabetMapper refAlphabetMapper;

    private final RefAlphabetSearchRepository refAlphabetSearchRepository;

    public RefAlphabetServiceImpl(RefAlphabetRepository refAlphabetRepository, RefAlphabetMapper refAlphabetMapper, RefAlphabetSearchRepository refAlphabetSearchRepository) {
        this.refAlphabetRepository = refAlphabetRepository;
        this.refAlphabetMapper = refAlphabetMapper;
        this.refAlphabetSearchRepository = refAlphabetSearchRepository;
    }

    /**
     * Save a refAlphabet.
     *
     * @param refAlphabetDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RefAlphabetDTO save(RefAlphabetDTO refAlphabetDTO) {
        log.debug("Request to save RefAlphabet : {}", refAlphabetDTO);
        RefAlphabet refAlphabet = refAlphabetMapper.toEntity(refAlphabetDTO);
        refAlphabet = refAlphabetRepository.save(refAlphabet);
        RefAlphabetDTO result = refAlphabetMapper.toDto(refAlphabet);
        refAlphabetSearchRepository.save(refAlphabet);
        return result;
    }

    /**
     * Get all the refAlphabets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefAlphabetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RefAlphabets");
        return refAlphabetRepository.findAll(pageable)
            .map(refAlphabetMapper::toDto);
    }


    /**
     * Get one refAlphabet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RefAlphabetDTO> findOne(Long id) {
        log.debug("Request to get RefAlphabet : {}", id);
        return refAlphabetRepository.findById(id)
            .map(refAlphabetMapper::toDto);
    }

    /**
     * Delete the refAlphabet by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RefAlphabet : {}", id);
        refAlphabetRepository.deleteById(id);
        refAlphabetSearchRepository.deleteById(id);
    }

    /**
     * Search for the refAlphabet corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefAlphabetDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RefAlphabets for query {}", query);
        return refAlphabetSearchRepository.search(queryStringQuery(query), pageable)
            .map(refAlphabetMapper::toDto);
    }
}
