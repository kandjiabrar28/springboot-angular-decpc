package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.PVSurveillance;
import com.mycompany.myapp.repository.PVSurveillanceRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PVSurveillance}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PVSurveillanceResource {

    private final Logger log = LoggerFactory.getLogger(PVSurveillanceResource.class);

    private static final String ENTITY_NAME = "pVSurveillance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PVSurveillanceRepository pVSurveillanceRepository;

    public PVSurveillanceResource(PVSurveillanceRepository pVSurveillanceRepository) {
        this.pVSurveillanceRepository = pVSurveillanceRepository;
    }

    /**
     * {@code POST  /pv-surveillances} : Create a new pVSurveillance.
     *
     * @param pVSurveillance the pVSurveillance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pVSurveillance, or with status {@code 400 (Bad Request)} if the pVSurveillance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pv-surveillances")
    public ResponseEntity<PVSurveillance> createPVSurveillance(@RequestBody PVSurveillance pVSurveillance) throws URISyntaxException {
        log.debug("REST request to save PVSurveillance : {}", pVSurveillance);
        if (pVSurveillance.getId() != null) {
            throw new BadRequestAlertException("A new pVSurveillance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PVSurveillance result = pVSurveillanceRepository.save(pVSurveillance);
        return ResponseEntity.created(new URI("/api/pv-surveillances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pv-surveillances} : Updates an existing pVSurveillance.
     *
     * @param pVSurveillance the pVSurveillance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pVSurveillance,
     * or with status {@code 400 (Bad Request)} if the pVSurveillance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pVSurveillance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pv-surveillances")
    public ResponseEntity<PVSurveillance> updatePVSurveillance(@RequestBody PVSurveillance pVSurveillance) throws URISyntaxException {
        log.debug("REST request to update PVSurveillance : {}", pVSurveillance);
        if (pVSurveillance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PVSurveillance result = pVSurveillanceRepository.save(pVSurveillance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pVSurveillance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pv-surveillances} : get all the pVSurveillances.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pVSurveillances in body.
     */
    @GetMapping("/pv-surveillances")
    public List<PVSurveillance> getAllPVSurveillances() {
        log.debug("REST request to get all PVSurveillances");
        return pVSurveillanceRepository.findAll();
    }

    /**
     * {@code GET  /pv-surveillances/:id} : get the "id" pVSurveillance.
     *
     * @param id the id of the pVSurveillance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pVSurveillance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pv-surveillances/{id}")
    public ResponseEntity<PVSurveillance> getPVSurveillance(@PathVariable Long id) {
        log.debug("REST request to get PVSurveillance : {}", id);
        Optional<PVSurveillance> pVSurveillance = pVSurveillanceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pVSurveillance);
    }

    /**
     * {@code DELETE  /pv-surveillances/:id} : delete the "id" pVSurveillance.
     *
     * @param id the id of the pVSurveillance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pv-surveillances/{id}")
    public ResponseEntity<Void> deletePVSurveillance(@PathVariable Long id) {
        log.debug("REST request to delete PVSurveillance : {}", id);
        pVSurveillanceRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
