package ac.fidoteam.alkhalil.service;

import ac.fidoteam.alkhalil.service.dto.RefAlphabetDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ac.fidoteam.alkhalil.domain.RefAlphabet}.
 */
public interface RefAlphabetService {

    /**
     * Save a refAlphabet.
     *
     * @param refAlphabetDTO the entity to save.
     * @return the persisted entity.
     */
    RefAlphabetDTO save(RefAlphabetDTO refAlphabetDTO);

    /**
     * Get all the refAlphabets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RefAlphabetDTO> findAll(Pageable pageable);


    /**
     * Get the "id" refAlphabet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RefAlphabetDTO> findOne(Long id);

    /**
     * Delete the "id" refAlphabet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the refAlphabet corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RefAlphabetDTO> search(String query, Pageable pageable);
}
