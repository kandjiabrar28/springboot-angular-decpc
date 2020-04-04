package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Surveillant;
import com.mycompany.myapp.repository.SurveillantRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Surveillant}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SurveillantResource {

    private final Logger log = LoggerFactory.getLogger(SurveillantResource.class);

    private static final String ENTITY_NAME = "surveillant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SurveillantRepository surveillantRepository;

    public SurveillantResource(SurveillantRepository surveillantRepository) {
        this.surveillantRepository = surveillantRepository;
    }

    /**
     * {@code POST  /surveillants} : Create a new surveillant.
     *
     * @param surveillant the surveillant to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new surveillant, or with status {@code 400 (Bad Request)} if the surveillant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/surveillants")
    public ResponseEntity<Surveillant> createSurveillant(@RequestBody Surveillant surveillant) throws URISyntaxException {
        log.debug("REST request to save Surveillant : {}", surveillant);
        if (surveillant.getId() != null) {
            throw new BadRequestAlertException("A new surveillant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Surveillant result = surveillantRepository.save(surveillant);
        return ResponseEntity.created(new URI("/api/surveillants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /surveillants} : Updates an existing surveillant.
     *
     * @param surveillant the surveillant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated surveillant,
     * or with status {@code 400 (Bad Request)} if the surveillant is not valid,
     * or with status {@code 500 (Internal Server Error)} if the surveillant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/surveillants")
    public ResponseEntity<Surveillant> updateSurveillant(@RequestBody Surveillant surveillant) throws URISyntaxException {
        log.debug("REST request to update Surveillant : {}", surveillant);
        if (surveillant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Surveillant result = surveillantRepository.save(surveillant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, surveillant.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /surveillants} : get all the surveillants.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of surveillants in body.
     */
    @GetMapping("/surveillants")
    public List<Surveillant> getAllSurveillants() {
        log.debug("REST request to get all Surveillants");
        return surveillantRepository.findAll();
    }

    /**
     * {@code GET  /surveillants/:id} : get the "id" surveillant.
     *
     * @param id the id of the surveillant to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the surveillant, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/surveillants/{id}")
    public ResponseEntity<Surveillant> getSurveillant(@PathVariable Long id) {
        log.debug("REST request to get Surveillant : {}", id);
        Optional<Surveillant> surveillant = surveillantRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(surveillant);
    }

    /**
     * {@code DELETE  /surveillants/:id} : delete the "id" surveillant.
     *
     * @param id the id of the surveillant to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/surveillants/{id}")
    public ResponseEntity<Void> deleteSurveillant(@PathVariable Long id) {
        log.debug("REST request to delete Surveillant : {}", id);
        surveillantRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
