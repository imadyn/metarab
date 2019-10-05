package ac.fidoteam.alkhalil.service.impl;

import ac.fidoteam.alkhalil.service.TypeTBService;
import ac.fidoteam.alkhalil.domain.TypeTB;
import ac.fidoteam.alkhalil.repository.TypeTBRepository;
import ac.fidoteam.alkhalil.repository.search.TypeTBSearchRepository;
import ac.fidoteam.alkhalil.service.dto.TypeTBDTO;
import ac.fidoteam.alkhalil.service.mapper.TypeTBMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link TypeTB}.
 */
@Service
@Transactional
public class TypeTBServiceImpl implements TypeTBService {

    private final Logger log = LoggerFactory.getLogger(TypeTBServiceImpl.class);

    private final TypeTBRepository typeTBRepository;

    private final TypeTBMapper typeTBMapper;

    private final TypeTBSearchRepository typeTBSearchRepository;

    public TypeTBServiceImpl(TypeTBRepository typeTBRepository, TypeTBMapper typeTBMapper, TypeTBSearchRepository typeTBSearchRepository) {
        this.typeTBRepository = typeTBRepository;
        this.typeTBMapper = typeTBMapper;
        this.typeTBSearchRepository = typeTBSearchRepository;
    }

    /**
     * Save a typeTB.
     *
     * @param typeTBDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypeTBDTO save(TypeTBDTO typeTBDTO) {
        log.debug("Request to save TypeTB : {}", typeTBDTO);
        TypeTB typeTB = typeTBMapper.toEntity(typeTBDTO);
        typeTB = typeTBRepository.save(typeTB);
        TypeTBDTO result = typeTBMapper.toDto(typeTB);
        typeTBSearchRepository.save(typeTB);
        return result;
    }

    /**
     * Get all the typeTBS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeTBDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeTBS");
        return typeTBRepository.findAll(pageable)
            .map(typeTBMapper::toDto);
    }


    /**
     * Get one typeTB by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeTBDTO> findOne(Long id) {
        log.debug("Request to get TypeTB : {}", id);
        return typeTBRepository.findById(id)
            .map(typeTBMapper::toDto);
    }

    /**
     * Delete the typeTB by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeTB : {}", id);
        typeTBRepository.deleteById(id);
        typeTBSearchRepository.deleteById(id);
    }

    /**
     * Search for the typeTB corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeTBDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TypeTBS for query {}", query);
        return typeTBSearchRepository.search(queryStringQuery(query), pageable)
            .map(typeTBMapper::toDto);
    }
}
