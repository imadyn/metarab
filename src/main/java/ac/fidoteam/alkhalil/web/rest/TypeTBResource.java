package ac.fidoteam.alkhalil.web.rest;

import ac.fidoteam.alkhalil.service.TypeTBService;
import ac.fidoteam.alkhalil.web.rest.errors.BadRequestAlertException;
import ac.fidoteam.alkhalil.service.dto.TypeTBDTO;
import ac.fidoteam.alkhalil.service.dto.TypeTBCriteria;
import ac.fidoteam.alkhalil.service.TypeTBQueryService;

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
 * REST controller for managing {@link ac.fidoteam.alkhalil.domain.TypeTB}.
 */
@RestController
@RequestMapping("/api")
public class TypeTBResource {

    private final Logger log = LoggerFactory.getLogger(TypeTBResource.class);

    private static final String ENTITY_NAME = "typeTB";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeTBService typeTBService;

    private final TypeTBQueryService typeTBQueryService;

    public TypeTBResource(TypeTBService typeTBService, TypeTBQueryService typeTBQueryService) {
        this.typeTBService = typeTBService;
        this.typeTBQueryService = typeTBQueryService;
    }

    /**
     * {@code POST  /type-tbs} : Create a new typeTB.
     *
     * @param typeTBDTO the typeTBDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeTBDTO, or with status {@code 400 (Bad Request)} if the typeTB has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-tbs")
    public ResponseEntity<TypeTBDTO> createTypeTB(@Valid @RequestBody TypeTBDTO typeTBDTO) throws URISyntaxException {
        log.debug("REST request to save TypeTB : {}", typeTBDTO);
        if (typeTBDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeTB cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeTBDTO result = typeTBService.save(typeTBDTO);
        return ResponseEntity.created(new URI("/api/type-tbs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-tbs} : Updates an existing typeTB.
     *
     * @param typeTBDTO the typeTBDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeTBDTO,
     * or with status {@code 400 (Bad Request)} if the typeTBDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeTBDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-tbs")
    public ResponseEntity<TypeTBDTO> updateTypeTB(@Valid @RequestBody TypeTBDTO typeTBDTO) throws URISyntaxException {
        log.debug("REST request to update TypeTB : {}", typeTBDTO);
        if (typeTBDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeTBDTO result = typeTBService.save(typeTBDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeTBDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-tbs} : get all the typeTBS.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeTBS in body.
     */
    @GetMapping("/type-tbs")
    public ResponseEntity<List<TypeTBDTO>> getAllTypeTBS(TypeTBCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TypeTBS by criteria: {}", criteria);
        Page<TypeTBDTO> page = typeTBQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /type-tbs/count} : count all the typeTBS.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/type-tbs/count")
    public ResponseEntity<Long> countTypeTBS(TypeTBCriteria criteria) {
        log.debug("REST request to count TypeTBS by criteria: {}", criteria);
        return ResponseEntity.ok().body(typeTBQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /type-tbs/:id} : get the "id" typeTB.
     *
     * @param id the id of the typeTBDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeTBDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-tbs/{id}")
    public ResponseEntity<TypeTBDTO> getTypeTB(@PathVariable Long id) {
        log.debug("REST request to get TypeTB : {}", id);
        Optional<TypeTBDTO> typeTBDTO = typeTBService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeTBDTO);
    }

    /**
     * {@code DELETE  /type-tbs/:id} : delete the "id" typeTB.
     *
     * @param id the id of the typeTBDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-tbs/{id}")
    public ResponseEntity<Void> deleteTypeTB(@PathVariable Long id) {
        log.debug("REST request to delete TypeTB : {}", id);
        typeTBService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/type-tbs?query=:query} : search for the typeTB corresponding
     * to the query.
     *
     * @param query the query of the typeTB search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/type-tbs")
    public ResponseEntity<List<TypeTBDTO>> searchTypeTBS(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TypeTBS for query {}", query);
        Page<TypeTBDTO> page = typeTBService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
