package ac.fidoteam.alkhalil.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ac.fidoteam.alkhalil.service.dto.BahrCombineBisDTO;
import ac.fidoteam.alkhalil.service.dto.BahrCombineDTO;

import ac.fidoteam.alkhalil.service.dto.BahrBaitSearchCriteria;
import java.util.List;

/**
 * Service Interface for managing {@link ac.fidoteam.alkhalil.domain.BahrCombine}.
 */
public interface BahrCombineService {


    /**
     * Search for the bahrCombine corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BahrCombineDTO> search(String query, Pageable pageable);


    /**
     * Search for the bahrCombine corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BahrCombineBisDTO> searchCombineBis(String query, Pageable pageable);

    List<String> searchByKeyBahr(BahrBaitSearchCriteria query);

    List<String> searchByBait(String bait);


}
