package ac.fidoteam.alkhalil.web.rest;

import ac.fidoteam.alkhalil.service.RefRhythmService;
import ac.fidoteam.alkhalil.web.rest.errors.BadRequestAlertException;
import ac.fidoteam.alkhalil.service.dto.RefRhythmDTO;
import ac.fidoteam.alkhalil.service.dto.RefRhythmCriteria;
import ac.fidoteam.alkhalil.service.RefRhythmQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link ac.fidoteam.alkhalil.domain.RefRhythm}.
 */
@RestController
@RequestMapping("/api")
public class RefRhythmResource {

    private final Logger log = LoggerFactory.getLogger(RefRhythmResource.class);

    private static final String ENTITY_NAME = "refRhythm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RefRhythmService refRhythmService;

    private final RefRhythmQueryService refRhythmQueryService;

    public RefRhythmResource(RefRhythmService refRhythmService, RefRhythmQueryService refRhythmQueryService) {
        this.refRhythmService = refRhythmService;
        this.refRhythmQueryService = refRhythmQueryService;
    }

    /**
     * {@code POST  /ref-rhythms} : Create a new refRhythm.
     *
     * @param refRhythmDTO the refRhythmDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new refRhythmDTO, or with status {@code 400 (Bad Request)} if the refRhythm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ref-rhythms")
    public ResponseEntity<RefRhythmDTO> createRefRhythm(@Valid @RequestBody RefRhythmDTO refRhythmDTO) throws URISyntaxException {
        log.debug("REST request to save RefRhythm : {}", refRhythmDTO);
        if (refRhythmDTO.getId() != null) {
            throw new BadRequestAlertException("A new refRhythm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RefRhythmDTO result = refRhythmService.save(refRhythmDTO);
        return ResponseEntity.created(new URI("/api/ref-rhythms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ref-rhythms} : Updates an existing refRhythm.
     *
     * @param refRhythmDTO the refRhythmDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated refRhythmDTO,
     * or with status {@code 400 (Bad Request)} if the refRhythmDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the refRhythmDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ref-rhythms")
    public ResponseEntity<RefRhythmDTO> updateRefRhythm(@Valid @RequestBody RefRhythmDTO refRhythmDTO) throws URISyntaxException {
        log.debug("REST request to update RefRhythm : {}", refRhythmDTO);
        if (refRhythmDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RefRhythmDTO result = refRhythmService.save(refRhythmDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, refRhythmDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ref-rhythms} : get all the refRhythms.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of refRhythms in body.
     */
    @GetMapping("/ref-rhythms")
    public ResponseEntity<List<RefRhythmDTO>> getAllRefRhythms(RefRhythmCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RefRhythms by criteria: {}", criteria);
        Page<RefRhythmDTO> page = refRhythmQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /ref-rhythms/count} : count all the refRhythms.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/ref-rhythms/count")
    public ResponseEntity<Long> countRefRhythms(RefRhythmCriteria criteria) {
        log.debug("REST request to count RefRhythms by criteria: {}", criteria);
        return ResponseEntity.ok().body(refRhythmQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ref-rhythms/:id} : get the "id" refRhythm.
     *
     * @param id the id of the refRhythmDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the refRhythmDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ref-rhythms/{id}")
    public ResponseEntity<RefRhythmDTO> getRefRhythm(@PathVariable Long id) {
        log.debug("REST request to get RefRhythm : {}", id);
        Optional<RefRhythmDTO> refRhythmDTO = refRhythmService.findOne(id);
        return ResponseUtil.wrapOrNotFound(refRhythmDTO);
    }

    /**
     * {@code DELETE  /ref-rhythms/:id} : delete the "id" refRhythm.
     *
     * @param id the id of the refRhythmDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ref-rhythms/{id}")
    public ResponseEntity<Void> deleteRefRhythm(@PathVariable Long id) {
        log.debug("REST request to delete RefRhythm : {}", id);
        refRhythmService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/ref-rhythms?query=:query} : search for the refRhythm corresponding
     * to the query.
     *
     * @param query the query of the refRhythm search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/ref-rhythms")
    public ResponseEntity<List<RefRhythmDTO>> searchRefRhythms(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RefRhythms for query {}", query);
        Page<RefRhythmDTO> page = refRhythmService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
