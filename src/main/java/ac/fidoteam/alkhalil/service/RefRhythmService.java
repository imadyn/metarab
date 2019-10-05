package ac.fidoteam.alkhalil.service;

import ac.fidoteam.alkhalil.service.dto.RefRhythmDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ac.fidoteam.alkhalil.domain.RefRhythm}.
 */
public interface RefRhythmService {

    /**
     * Save a refRhythm.
     *
     * @param refRhythmDTO the entity to save.
     * @return the persisted entity.
     */
    RefRhythmDTO save(RefRhythmDTO refRhythmDTO);

    /**
     * Get all the refRhythms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RefRhythmDTO> findAll(Pageable pageable);


    /**
     * Get the "id" refRhythm.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RefRhythmDTO> findOne(Long id);

    /**
     * Delete the "id" refRhythm.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the refRhythm corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RefRhythmDTO> search(String query, Pageable pageable);
}
