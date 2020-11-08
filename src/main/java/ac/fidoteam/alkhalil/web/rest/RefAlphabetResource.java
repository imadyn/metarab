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

import ac.fidoteam.alkhalil.service.RefAlphabetQueryService;
import ac.fidoteam.alkhalil.service.RefAlphabetService;
import ac.fidoteam.alkhalil.service.dto.RefAlphabetCriteria;
import ac.fidoteam.alkhalil.service.dto.RefAlphabetDTO;
import ac.fidoteam.alkhalil.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ac.fidoteam.alkhalil.domain.RefAlphabet}.
 */
@RestController
@RequestMapping("/api")
public class RefAlphabetResource {

    private final Logger log = LoggerFactory.getLogger(RefAlphabetResource.class);

    private static final String ENTITY_NAME = "refAlphabet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RefAlphabetService refAlphabetService;

    private final RefAlphabetQueryService refAlphabetQueryService;

    public RefAlphabetResource(RefAlphabetService refAlphabetService, RefAlphabetQueryService refAlphabetQueryService) {
        this.refAlphabetService = refAlphabetService;
        this.refAlphabetQueryService = refAlphabetQueryService;
    }

    /**
     * {@code POST  /ref-alphabets} : Create a new refAlphabet.
     *
     * @param refAlphabetDTO the refAlphabetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new refAlphabetDTO, or with status {@code 400 (Bad Request)} if the refAlphabet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ref-alphabets")
    public ResponseEntity<RefAlphabetDTO> createRefAlphabet(@Valid @RequestBody RefAlphabetDTO refAlphabetDTO) throws URISyntaxException {
        log.debug("REST request to save RefAlphabet : {}", refAlphabetDTO);
        if (refAlphabetDTO.getId() != null) {
            throw new BadRequestAlertException("A new refAlphabet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RefAlphabetDTO result = refAlphabetService.save(refAlphabetDTO);
        return ResponseEntity.created(new URI("/api/ref-alphabets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ref-alphabets} : Updates an existing refAlphabet.
     *
     * @param refAlphabetDTO the refAlphabetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated refAlphabetDTO,
     * or with status {@code 400 (Bad Request)} if the refAlphabetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the refAlphabetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ref-alphabets")
    public ResponseEntity<RefAlphabetDTO> updateRefAlphabet(@Valid @RequestBody RefAlphabetDTO refAlphabetDTO) throws URISyntaxException {
        log.debug("REST request to update RefAlphabet : {}", refAlphabetDTO);
        if (refAlphabetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RefAlphabetDTO result = refAlphabetService.save(refAlphabetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, refAlphabetDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ref-alphabets} : get all the refAlphabets.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of refAlphabets in body.
     */
    @GetMapping("/ref-alphabets")
    public ResponseEntity<List<RefAlphabetDTO>> getAllRefAlphabets(RefAlphabetCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RefAlphabets by criteria: {}", criteria);
        Page<RefAlphabetDTO> page = refAlphabetQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /ref-alphabets/count} : count all the refAlphabets.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/ref-alphabets/count")
    public ResponseEntity<Long> countRefAlphabets(RefAlphabetCriteria criteria) {
        log.debug("REST request to count RefAlphabets by criteria: {}", criteria);
        return ResponseEntity.ok().body(refAlphabetQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ref-alphabets/:id} : get the "id" refAlphabet.
     *
     * @param id the id of the refAlphabetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the refAlphabetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ref-alphabets/{id}")
    public ResponseEntity<RefAlphabetDTO> getRefAlphabet(@PathVariable Long id) {
        log.debug("REST request to get RefAlphabet : {}", id);
        Optional<RefAlphabetDTO> refAlphabetDTO = refAlphabetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(refAlphabetDTO);
    }

    /**
     * {@code DELETE  /ref-alphabets/:id} : delete the "id" refAlphabet.
     *
     * @param id the id of the refAlphabetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ref-alphabets/{id}")
    public ResponseEntity<Void> deleteRefAlphabet(@PathVariable Long id) {
        log.debug("REST request to delete RefAlphabet : {}", id);
        refAlphabetService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/ref-alphabets?query=:query} : search for the refAlphabet corresponding
     * to the query.
     *
     * @param query the query of the refAlphabet search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/ref-alphabets")
    public ResponseEntity<List<RefAlphabetDTO>> searchRefAlphabets(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RefAlphabets for query {}", query);
        Page<RefAlphabetDTO> page = refAlphabetService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
