package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Examen;
import com.mycompany.myapp.repository.ExamenRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Examen}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ExamenResource {

    private final Logger log = LoggerFactory.getLogger(ExamenResource.class);

    private static final String ENTITY_NAME = "examen";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExamenRepository examenRepository;

    public ExamenResource(ExamenRepository examenRepository) {
        this.examenRepository = examenRepository;
    }

    /**
     * {@code POST  /examen} : Create a new examen.
     *
     * @param examen the examen to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new examen, or with status {@code 400 (Bad Request)} if the examen has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/examen")
    public ResponseEntity<Examen> createExamen(@RequestBody Examen examen) throws URISyntaxException {
        log.debug("REST request to save Examen : {}", examen);
        if (examen.getId() != null) {
            throw new BadRequestAlertException("A new examen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Examen result = examenRepository.save(examen);
        return ResponseEntity.created(new URI("/api/examen/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /examen} : Updates an existing examen.
     *
     * @param examen the examen to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated examen,
     * or with status {@code 400 (Bad Request)} if the examen is not valid,
     * or with status {@code 500 (Internal Server Error)} if the examen couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/examen")
    public ResponseEntity<Examen> updateExamen(@RequestBody Examen examen) throws URISyntaxException {
        log.debug("REST request to update Examen : {}", examen);
        if (examen.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Examen result = examenRepository.save(examen);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, examen.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /examen} : get all the examen.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of examen in body.
     */
    @GetMapping("/examen")
    public List<Examen> getAllExamen(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Examen");
        return examenRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /examen/:id} : get the "id" examen.
     *
     * @param id the id of the examen to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the examen, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/examen/{id}")
    public ResponseEntity<Examen> getExamen(@PathVariable Long id) {
        log.debug("REST request to get Examen : {}", id);
        Optional<Examen> examen = examenRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(examen);
    }

    /**
     * {@code DELETE  /examen/:id} : delete the "id" examen.
     *
     * @param id the id of the examen to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/examen/{id}")
    public ResponseEntity<Void> deleteExamen(@PathVariable Long id) {
        log.debug("REST request to delete Examen : {}", id);
        examenRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
