package ac.fidoteam.alkhalil.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ac.fidoteam.alkhalil.service.BahrCombineService;
import ac.fidoteam.alkhalil.service.dto.BahrCombineDTO;
import io.github.jhipster.web.util.PaginationUtil;

import ac.fidoteam.alkhalil.service.dto.BahrBaitSearchCriteria;

/**
 * REST controller for managing {@link com.fidoteam.metarab.domain.BahrCombine}.
 */
@RestController
@RequestMapping("/api")
public class SearchBahrBaitResource {

	private final Logger logger = LoggerFactory.getLogger(SearchBahrBaitResource.class);

	private final BahrCombineService bahrCombineService;

	public SearchBahrBaitResource(BahrCombineService bahrCombineService) {
		this.bahrCombineService = bahrCombineService;
	}

	/**
	 * {@code SEARCH  /_search/ref-bahrs/bait?query=:query} : search for the refBahr
	 * corresponding to the query.
	 *
	 * @param query    the query of the refBahr search.
	 * @param pageable the pagination information.
	 * @return the result of the search.
	 */
	@GetMapping("/_search/ref-bahrs/bait")
	public ResponseEntity<List<BahrCombineDTO>> searchBahrsBait(@RequestParam String query, Pageable pageable) {
		logger.debug("REST request to search for a page of BahrsBait for query {}", query);
		Page<BahrCombineDTO> page = bahrCombineService.search(query, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code SEARCH  /_search/ref-bahrs/bait?query=:query} : search for the refBahr
	 * corresponding to the query.
	 *
	 * @param query    the query of the refBahr search.
	 * @param pageable the pagination information.
	 * @return the result of the search.
	 */
	@PostMapping("/_search/ref-bahrs/baitbykey")
	public ResponseEntity<List<String>> searchBahrsBaitByCritere(@RequestBody BahrBaitSearchCriteria query) {
		logger.debug("REST request to searchBahrsBaitByCritere for a page of BahrsBait for query {}", query);
		return ResponseEntity.ok().body(bahrCombineService.searchByKeyBahr(query));
	}

	/**
	 * {@code SEARCH  /_search/ref-bahrs/bait?query=:query} : search for the refBahr
	 * corresponding to the query.
	 *
	 * @param query    the query of the refBahr search.
	 * @param pageable the pagination information.
	 * @return the result of the search.
	 */
	@PostMapping("/_search/ref-bahrs/bahrbybait")
	public ResponseEntity<List<String>> searchBahrsByBait(@RequestBody String bait) {
		logger.debug("REST request to searchBahrsByBait by bayt {}", bait);
		return ResponseEntity.ok().body(bahrCombineService.searchByBait(bait));
	}
}
