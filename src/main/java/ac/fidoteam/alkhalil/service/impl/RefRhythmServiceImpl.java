package ac.fidoteam.alkhalil.service.impl;

import ac.fidoteam.alkhalil.service.RefRhythmService;
import ac.fidoteam.alkhalil.domain.RefRhythm;
import ac.fidoteam.alkhalil.repository.RefRhythmRepository;
import ac.fidoteam.alkhalil.repository.search.RefRhythmSearchRepository;
import ac.fidoteam.alkhalil.service.dto.RefRhythmDTO;
import ac.fidoteam.alkhalil.service.mapper.RefRhythmMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link RefRhythm}.
 */
@Service
@Transactional
public class RefRhythmServiceImpl implements RefRhythmService {

    private final Logger log = LoggerFactory.getLogger(RefRhythmServiceImpl.class);

    private final RefRhythmRepository refRhythmRepository;

    private final RefRhythmMapper refRhythmMapper;

    private final RefRhythmSearchRepository refRhythmSearchRepository;

    public RefRhythmServiceImpl(RefRhythmRepository refRhythmRepository, RefRhythmMapper refRhythmMapper, RefRhythmSearchRepository refRhythmSearchRepository) {
        this.refRhythmRepository = refRhythmRepository;
        this.refRhythmMapper = refRhythmMapper;
        this.refRhythmSearchRepository = refRhythmSearchRepository;
    }

    /**
     * Save a refRhythm.
     *
     * @param refRhythmDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RefRhythmDTO save(RefRhythmDTO refRhythmDTO) {
        log.debug("Request to save RefRhythm : {}", refRhythmDTO);
        RefRhythm refRhythm = refRhythmMapper.toEntity(refRhythmDTO);
        refRhythm = refRhythmRepository.save(refRhythm);
        RefRhythmDTO result = refRhythmMapper.toDto(refRhythm);
        refRhythmSearchRepository.save(refRhythm);
        return result;
    }

    /**
     * Get all the refRhythms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefRhythmDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RefRhythms");
        return refRhythmRepository.findAll(pageable)
            .map(refRhythmMapper::toDto);
    }


    /**
     * Get one refRhythm by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RefRhythmDTO> findOne(Long id) {
        log.debug("Request to get RefRhythm : {}", id);
        return refRhythmRepository.findById(id)
            .map(refRhythmMapper::toDto);
    }

    /**
     * Delete the refRhythm by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RefRhythm : {}", id);
        refRhythmRepository.deleteById(id);
        refRhythmSearchRepository.deleteById(id);
    }

    /**
     * Search for the refRhythm corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefRhythmDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RefRhythms for query {}", query);
        return refRhythmSearchRepository.search(queryStringQuery(query), pageable)
            .map(refRhythmMapper::toDto);
    }
}
