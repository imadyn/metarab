package ac.fidoteam.alkhalil.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ac.fidoteam.alkhalil.domain.BahrCombine;
import ac.fidoteam.alkhalil.repository.BahrCombineBisRepository;
import ac.fidoteam.alkhalil.repository.BahrCombineRepository;
import ac.fidoteam.alkhalil.repository.search.BahrCombineBisSearchRepository;
import ac.fidoteam.alkhalil.repository.search.BahrCombineSearchRepository;
import ac.fidoteam.alkhalil.service.BahrCombineService;
import ac.fidoteam.alkhalil.service.dto.BahrCombineBisDTO;
import ac.fidoteam.alkhalil.service.dto.BahrCombineDTO;
import ac.fidoteam.alkhalil.service.mapper.BahrCombineBisMapper;
import ac.fidoteam.alkhalil.service.mapper.BahrCombineMapper;
import ac.fidoteam.alkhalil.service.dto.BahrBaitSearchCriteria;
import java.util.List;

/**
 * Service Implementation for managing {@link BahrCombine}.
 */
@Service
@Transactional
public class BahrCombineServiceImpl implements BahrCombineService {

    private final Logger log = LoggerFactory.getLogger(BahrCombineServiceImpl.class);

    private final BahrCombineRepository bahrCombineRepository;

    private final BahrCombineMapper bahrCombineMapper;

    private final BahrCombineSearchRepository bahrCombineSearchRepository;

    private final BahrCombineBisRepository bahrCombineBisRepository;

    private final BahrCombineBisMapper bahrCombineBisMapper;

    private final BahrCombineBisSearchRepository bahrCombineBisSearchRepository;


    public BahrCombineServiceImpl(BahrCombineRepository bahrCombineRepository, BahrCombineMapper bahrCombineMapper, BahrCombineSearchRepository bahrCombineSearchRepository,
    		BahrCombineBisRepository bahrCombineBisRepository, BahrCombineBisMapper bahrCombineBisMapper, BahrCombineBisSearchRepository bahrCombineBisSearchRepository) {
        this.bahrCombineRepository = bahrCombineRepository;
        this.bahrCombineMapper = bahrCombineMapper;
        this.bahrCombineSearchRepository = bahrCombineSearchRepository;

        this.bahrCombineBisRepository = bahrCombineBisRepository;
        this.bahrCombineBisMapper = bahrCombineBisMapper;
        this.bahrCombineBisSearchRepository = bahrCombineBisSearchRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> searchByKeyBahr(BahrBaitSearchCriteria query) {
        log.debug("Request to search for a page of BahrCombines for query {}", query);

        List<String> bahrsP1 = bahrCombineBisRepository.findBahrBaitByKey(query.getPartie1());

        if(query.getPartie2() != null && query.getPartie2() != ""){
            List<String> bahrsP2 = bahrCombineBisRepository.findBahrBaitByKey(query.getPartie2());
            bahrsP1.retainAll(bahrsP2);
        }

        return bahrsP1;
    }


    /**
     * Search for the BahrCombine corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BahrCombineDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BahrCombines for query {}", query);
        return bahrCombineSearchRepository.search(queryStringQuery(query), pageable)
            .map(bahrCombineMapper::toDto);
    }


	@Override
    @Transactional(readOnly = true)
	public Page<BahrCombineBisDTO> searchCombineBis(String query, Pageable pageable) {
		log.debug("Request to search for a page of BahrCombineBiss for query {}", query);
        return bahrCombineBisSearchRepository.search(queryStringQuery(query), pageable)
            .map(bahrCombineBisMapper::toDto);
	}

	@Override
	public List<String> searchByBait(String bait) {
		log.debug("Request to searchByBait {}", bait);
		return bahrCombineRepository.findBahrByBait(bait);
	}



}
