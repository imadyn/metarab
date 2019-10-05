package ac.fidoteam.alkhalil.service;

import ac.fidoteam.alkhalil.service.dto.RefBahrDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ac.fidoteam.alkhalil.domain.RefBahr}.
 */
public interface RefBahrService {

    /**
     * Save a refBahr.
     *
     * @param refBahrDTO the entity to save.
     * @return the persisted entity.
     */
    RefBahrDTO save(RefBahrDTO refBahrDTO);

    /**
     * Get all the refBahrs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RefBahrDTO> findAll(Pageable pageable);


    /**
     * Get the "id" refBahr.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RefBahrDTO> findOne(Long id);

    /**
     * Delete the "id" refBahr.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the refBahr corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RefBahrDTO> search(String query, Pageable pageable);
}
