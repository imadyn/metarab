package ac.fidoteam.alkhalil.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ac.fidoteam.alkhalil.service.RefBahrQueryService;
import ac.fidoteam.alkhalil.service.RefBahrService;
import ac.fidoteam.alkhalil.service.dto.RefBahrCriteria;
import ac.fidoteam.alkhalil.service.dto.RefBahrDTO;
import ac.fidoteam.alkhalil.service.dto.RefBahrSearchCriteria;
import ac.fidoteam.alkhalil.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ac.fidoteam.alkhalil.domain.RefBahr}.
 */
@RestController
@RequestMapping("/api")
public class RefBahrResource {

    private final Logger log = LoggerFactory.getLogger(RefBahrResource.class);

    private static final String ENTITY_NAME = "refBahr";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RefBahrService refBahrService;

    private final RefBahrQueryService refBahrQueryService;

    public RefBahrResource(RefBahrService refBahrService, RefBahrQueryService refBahrQueryService) {
        this.refBahrService = refBahrService;
        this.refBahrQueryService = refBahrQueryService;
    }

    /**
     * {@code POST  /ref-bahrs} : Create a new refBahr.
     *
     * @param refBahrDTO the refBahrDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new refBahrDTO, or with status {@code 400 (Bad Request)} if the refBahr has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ref-bahrs")
    public ResponseEntity<RefBahrDTO> createRefBahr(@Valid @RequestBody RefBahrDTO refBahrDTO) throws URISyntaxException {
        log.debug("REST request to save RefBahr : {}", refBahrDTO);
        if (refBahrDTO.getId() != null) {
            throw new BadRequestAlertException("A new refBahr cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RefBahrDTO result = refBahrService.save(refBahrDTO);
        return ResponseEntity.created(new URI("/api/ref-bahrs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ref-bahrs} : Updates an existing refBahr.
     *
     * @param refBahrDTO the refBahrDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated refBahrDTO,
     * or with status {@code 400 (Bad Request)} if the refBahrDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the refBahrDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ref-bahrs")
    public ResponseEntity<RefBahrDTO> updateRefBahr(@Valid @RequestBody RefBahrDTO refBahrDTO) throws URISyntaxException {
        log.debug("REST request to update RefBahr : {}", refBahrDTO);
        if (refBahrDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RefBahrDTO result = refBahrService.save(refBahrDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, refBahrDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ref-bahrs} : get all the refBahrs.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of refBahrs in body.
     */
    @GetMapping("/ref-bahrs")
    public ResponseEntity<List<RefBahrDTO>> getAllRefBahrs(RefBahrCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RefBahrs by criteria: {}", criteria);
        Page<RefBahrDTO> page = refBahrQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /ref-bahrs/count} : count all the refBahrs.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/ref-bahrs/count")
    public ResponseEntity<Long> countRefBahrs(RefBahrCriteria criteria) {
        log.debug("REST request to count RefBahrs by criteria: {}", criteria);
        return ResponseEntity.ok().body(refBahrQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ref-bahrs/:id} : get the "id" refBahr.
     *
     * @param id the id of the refBahrDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the refBahrDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ref-bahrs/{id}")
    public ResponseEntity<RefBahrDTO> getRefBahr(@PathVariable Long id) {
        log.debug("REST request to get RefBahr : {}", id);
        Optional<RefBahrDTO> refBahrDTO = refBahrService.findOne(id);
        return ResponseUtil.wrapOrNotFound(refBahrDTO);
    }

    /**
     * {@code DELETE  /ref-bahrs/:id} : delete the "id" refBahr.
     *
     * @param id the id of the refBahrDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ref-bahrs/{id}")
    public ResponseEntity<Void> deleteRefBahr(@PathVariable Long id) {
        log.debug("REST request to delete RefBahr : {}", id);
        refBahrService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/ref-bahrs?query=:query} : search for the refBahr corresponding
     * to the query.
     *
     * @param query the query of the refBahr search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/ref-bahrs")
    public ResponseEntity<List<RefBahrDTO>> searchRefBahrs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RefBahrs for query {}", query);
        Page<RefBahrDTO> page = refBahrService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    /**
     * {@code SEARCH  /_search/beneficiaires?query=:query} : search for the beneficiaire corresponding
     * to the query.
     *
     * @param query the query of the beneficiaire search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_searchadvanced/ref-bahrs")
    public ResponseEntity<List<RefBahrDTO>> searchRefBahrsByCritere(RefBahrSearchCriteria criteria, Pageable pageable) {
        log.debug("REST request to search for a page of Beneficiaires by criteria: {}", criteria);
        Page<RefBahrDTO> page = refBahrService.searchAdvanced(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }



}
