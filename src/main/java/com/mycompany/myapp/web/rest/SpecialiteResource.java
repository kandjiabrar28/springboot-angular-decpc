package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Specialite;
import com.mycompany.myapp.repository.SpecialiteRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Specialite}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SpecialiteResource {

    private final Logger log = LoggerFactory.getLogger(SpecialiteResource.class);

    private static final String ENTITY_NAME = "specialite";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpecialiteRepository specialiteRepository;

    public SpecialiteResource(SpecialiteRepository specialiteRepository) {
        this.specialiteRepository = specialiteRepository;
    }

    /**
     * {@code POST  /specialites} : Create a new specialite.
     *
     * @param specialite the specialite to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new specialite, or with status {@code 400 (Bad Request)} if the specialite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/specialites")
    public ResponseEntity<Specialite> createSpecialite(@RequestBody Specialite specialite) throws URISyntaxException {
        log.debug("REST request to save Specialite : {}", specialite);
        if (specialite.getId() != null) {
            throw new BadRequestAlertException("A new specialite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Specialite result = specialiteRepository.save(specialite);
        return ResponseEntity.created(new URI("/api/specialites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /specialites} : Updates an existing specialite.
     *
     * @param specialite the specialite to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated specialite,
     * or with status {@code 400 (Bad Request)} if the specialite is not valid,
     * or with status {@code 500 (Internal Server Error)} if the specialite couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/specialites")
    public ResponseEntity<Specialite> updateSpecialite(@RequestBody Specialite specialite) throws URISyntaxException {
        log.debug("REST request to update Specialite : {}", specialite);
        if (specialite.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Specialite result = specialiteRepository.save(specialite);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, specialite.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /specialites} : get all the specialites.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of specialites in body.
     */
    @GetMapping("/specialites")
    public List<Specialite> getAllSpecialites(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Specialites");
        return specialiteRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /specialites/:id} : get the "id" specialite.
     *
     * @param id the id of the specialite to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the specialite, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/specialites/{id}")
    public ResponseEntity<Specialite> getSpecialite(@PathVariable Long id) {
        log.debug("REST request to get Specialite : {}", id);
        Optional<Specialite> specialite = specialiteRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(specialite);
    }

    /**
     * {@code DELETE  /specialites/:id} : delete the "id" specialite.
     *
     * @param id the id of the specialite to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/specialites/{id}")
    public ResponseEntity<Void> deleteSpecialite(@PathVariable Long id) {
        log.debug("REST request to delete Specialite : {}", id);
        specialiteRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
