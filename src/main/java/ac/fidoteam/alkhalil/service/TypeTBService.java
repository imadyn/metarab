package ac.fidoteam.alkhalil.service;

import ac.fidoteam.alkhalil.service.dto.TypeTBDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ac.fidoteam.alkhalil.domain.TypeTB}.
 */
public interface TypeTBService {

    /**
     * Save a typeTB.
     *
     * @param typeTBDTO the entity to save.
     * @return the persisted entity.
     */
    TypeTBDTO save(TypeTBDTO typeTBDTO);

    /**
     * Get all the typeTBS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeTBDTO> findAll(Pageable pageable);


    /**
     * Get the "id" typeTB.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeTBDTO> findOne(Long id);

    /**
     * Delete the "id" typeTB.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the typeTB corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeTBDTO> search(String query, Pageable pageable);
}
