package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Correcteur;
import com.mycompany.myapp.repository.CorrecteurRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Correcteur}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CorrecteurResource {

    private final Logger log = LoggerFactory.getLogger(CorrecteurResource.class);

    private static final String ENTITY_NAME = "correcteur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CorrecteurRepository correcteurRepository;

    public CorrecteurResource(CorrecteurRepository correcteurRepository) {
        this.correcteurRepository = correcteurRepository;
    }

    /**
     * {@code POST  /correcteurs} : Create a new correcteur.
     *
     * @param correcteur the correcteur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new correcteur, or with status {@code 400 (Bad Request)} if the correcteur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/correcteurs")
    public ResponseEntity<Correcteur> createCorrecteur(@RequestBody Correcteur correcteur) throws URISyntaxException {
        log.debug("REST request to save Correcteur : {}", correcteur);
        if (correcteur.getId() != null) {
            throw new BadRequestAlertException("A new correcteur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Correcteur result = correcteurRepository.save(correcteur);
        return ResponseEntity.created(new URI("/api/correcteurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /correcteurs} : Updates an existing correcteur.
     *
     * @param correcteur the correcteur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated correcteur,
     * or with status {@code 400 (Bad Request)} if the correcteur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the correcteur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/correcteurs")
    public ResponseEntity<Correcteur> updateCorrecteur(@RequestBody Correcteur correcteur) throws URISyntaxException {
        log.debug("REST request to update Correcteur : {}", correcteur);
        if (correcteur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Correcteur result = correcteurRepository.save(correcteur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, correcteur.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /correcteurs} : get all the correcteurs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of correcteurs in body.
     */
    @GetMapping("/correcteurs")
    public List<Correcteur> getAllCorrecteurs() {
        log.debug("REST request to get all Correcteurs");
        return correcteurRepository.findAll();
    }

    /**
     * {@code GET  /correcteurs/:id} : get the "id" correcteur.
     *
     * @param id the id of the correcteur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the correcteur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/correcteurs/{id}")
    public ResponseEntity<Correcteur> getCorrecteur(@PathVariable Long id) {
        log.debug("REST request to get Correcteur : {}", id);
        Optional<Correcteur> correcteur = correcteurRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(correcteur);
    }

    /**
     * {@code DELETE  /correcteurs/:id} : delete the "id" correcteur.
     *
     * @param id the id of the correcteur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/correcteurs/{id}")
    public ResponseEntity<Void> deleteCorrecteur(@PathVariable Long id) {
        log.debug("REST request to delete Correcteur : {}", id);
        correcteurRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
