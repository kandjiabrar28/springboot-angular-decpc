package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Plage;
import com.mycompany.myapp.repository.PlageRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Plage}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PlageResource {

    private final Logger log = LoggerFactory.getLogger(PlageResource.class);

    private static final String ENTITY_NAME = "plage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlageRepository plageRepository;

    public PlageResource(PlageRepository plageRepository) {
        this.plageRepository = plageRepository;
    }

    /**
     * {@code POST  /plages} : Create a new plage.
     *
     * @param plage the plage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plage, or with status {@code 400 (Bad Request)} if the plage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plages")
    public ResponseEntity<Plage> createPlage(@RequestBody Plage plage) throws URISyntaxException {
        log.debug("REST request to save Plage : {}", plage);
        if (plage.getId() != null) {
            throw new BadRequestAlertException("A new plage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Plage result = plageRepository.save(plage);
        return ResponseEntity.created(new URI("/api/plages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plages} : Updates an existing plage.
     *
     * @param plage the plage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plage,
     * or with status {@code 400 (Bad Request)} if the plage is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plages")
    public ResponseEntity<Plage> updatePlage(@RequestBody Plage plage) throws URISyntaxException {
        log.debug("REST request to update Plage : {}", plage);
        if (plage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Plage result = plageRepository.save(plage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plage.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /plages} : get all the plages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plages in body.
     */
    @GetMapping("/plages")
    public List<Plage> getAllPlages() {
        log.debug("REST request to get all Plages");
        return plageRepository.findAll();
    }

    /**
     * {@code GET  /plages/:id} : get the "id" plage.
     *
     * @param id the id of the plage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plages/{id}")
    public ResponseEntity<Plage> getPlage(@PathVariable Long id) {
        log.debug("REST request to get Plage : {}", id);
        Optional<Plage> plage = plageRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(plage);
    }

    /**
     * {@code DELETE  /plages/:id} : delete the "id" plage.
     *
     * @param id the id of the plage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plages/{id}")
    public ResponseEntity<Void> deletePlage(@PathVariable Long id) {
        log.debug("REST request to delete Plage : {}", id);
        plageRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
