package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.TAnonym;
import com.mycompany.myapp.repository.TAnonymRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.TAnonym}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TAnonymResource {

    private final Logger log = LoggerFactory.getLogger(TAnonymResource.class);

    private static final String ENTITY_NAME = "tAnonym";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TAnonymRepository tAnonymRepository;

    public TAnonymResource(TAnonymRepository tAnonymRepository) {
        this.tAnonymRepository = tAnonymRepository;
    }

    /**
     * {@code POST  /t-anonyms} : Create a new tAnonym.
     *
     * @param tAnonym the tAnonym to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tAnonym, or with status {@code 400 (Bad Request)} if the tAnonym has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/t-anonyms")
    public ResponseEntity<TAnonym> createTAnonym(@RequestBody TAnonym tAnonym) throws URISyntaxException {
        log.debug("REST request to save TAnonym : {}", tAnonym);
        if (tAnonym.getId() != null) {
            throw new BadRequestAlertException("A new tAnonym cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TAnonym result = tAnonymRepository.save(tAnonym);
        return ResponseEntity.created(new URI("/api/t-anonyms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /t-anonyms} : Updates an existing tAnonym.
     *
     * @param tAnonym the tAnonym to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tAnonym,
     * or with status {@code 400 (Bad Request)} if the tAnonym is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tAnonym couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/t-anonyms")
    public ResponseEntity<TAnonym> updateTAnonym(@RequestBody TAnonym tAnonym) throws URISyntaxException {
        log.debug("REST request to update TAnonym : {}", tAnonym);
        if (tAnonym.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TAnonym result = tAnonymRepository.save(tAnonym);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tAnonym.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /t-anonyms} : get all the tAnonyms.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tAnonyms in body.
     */
    @GetMapping("/t-anonyms")
    public List<TAnonym> getAllTAnonyms() {
        log.debug("REST request to get all TAnonyms");
        return tAnonymRepository.findAll();
    }

    /**
     * {@code GET  /t-anonyms/:id} : get the "id" tAnonym.
     *
     * @param id the id of the tAnonym to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tAnonym, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/t-anonyms/{id}")
    public ResponseEntity<TAnonym> getTAnonym(@PathVariable Long id) {
        log.debug("REST request to get TAnonym : {}", id);
        Optional<TAnonym> tAnonym = tAnonymRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tAnonym);
    }

    /**
     * {@code DELETE  /t-anonyms/:id} : delete the "id" tAnonym.
     *
     * @param id the id of the tAnonym to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/t-anonyms/{id}")
    public ResponseEntity<Void> deleteTAnonym(@PathVariable Long id) {
        log.debug("REST request to delete TAnonym : {}", id);
        tAnonymRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
