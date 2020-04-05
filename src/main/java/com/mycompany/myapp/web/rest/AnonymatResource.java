package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Anonymat;
import com.mycompany.myapp.repository.AnonymatRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Anonymat}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AnonymatResource {

    private final Logger log = LoggerFactory.getLogger(AnonymatResource.class);

    private static final String ENTITY_NAME = "anonymat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnonymatRepository anonymatRepository;

    public AnonymatResource(AnonymatRepository anonymatRepository) {
        this.anonymatRepository = anonymatRepository;
    }

    /**
     * {@code POST  /anonymats} : Create a new anonymat.
     *
     * @param anonymat the anonymat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anonymat, or with status {@code 400 (Bad Request)} if the anonymat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/anonymats")
    public ResponseEntity<Anonymat> createAnonymat(@RequestBody Anonymat anonymat) throws URISyntaxException {
        log.debug("REST request to save Anonymat : {}", anonymat);
        if (anonymat.getId() != null) {
            throw new BadRequestAlertException("A new anonymat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Anonymat result = anonymatRepository.save(anonymat);
        return ResponseEntity.created(new URI("/api/anonymats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /anonymats} : Updates an existing anonymat.
     *
     * @param anonymat the anonymat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anonymat,
     * or with status {@code 400 (Bad Request)} if the anonymat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anonymat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/anonymats")
    public ResponseEntity<Anonymat> updateAnonymat(@RequestBody Anonymat anonymat) throws URISyntaxException {
        log.debug("REST request to update Anonymat : {}", anonymat);
        if (anonymat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Anonymat result = anonymatRepository.save(anonymat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anonymat.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /anonymats} : get all the anonymats.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anonymats in body.
     */
    @GetMapping("/anonymats")
    public List<Anonymat> getAllAnonymats() {
        log.debug("REST request to get all Anonymats");
        return anonymatRepository.findAll();
    }

    /**
     * {@code GET  /anonymats/:id} : get the "id" anonymat.
     *
     * @param id the id of the anonymat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anonymat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/anonymats/{id}")
    public ResponseEntity<Anonymat> getAnonymat(@PathVariable Long id) {
        log.debug("REST request to get Anonymat : {}", id);
        Optional<Anonymat> anonymat = anonymatRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(anonymat);
    }

    /**
     * {@code DELETE  /anonymats/:id} : delete the "id" anonymat.
     *
     * @param id the id of the anonymat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/anonymats/{id}")
    public ResponseEntity<Void> deleteAnonymat(@PathVariable Long id) {
        log.debug("REST request to delete Anonymat : {}", id);
        anonymatRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
