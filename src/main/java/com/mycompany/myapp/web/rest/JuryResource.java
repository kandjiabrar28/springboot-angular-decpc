package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Jury;
import com.mycompany.myapp.repository.JuryRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Jury}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class JuryResource {

    private final Logger log = LoggerFactory.getLogger(JuryResource.class);

    private static final String ENTITY_NAME = "jury";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JuryRepository juryRepository;

    public JuryResource(JuryRepository juryRepository) {
        this.juryRepository = juryRepository;
    }

    /**
     * {@code POST  /juries} : Create a new jury.
     *
     * @param jury the jury to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jury, or with status {@code 400 (Bad Request)} if the jury has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/juries")
    public ResponseEntity<Jury> createJury(@RequestBody Jury jury) throws URISyntaxException {
        log.debug("REST request to save Jury : {}", jury);
        if (jury.getId() != null) {
            throw new BadRequestAlertException("A new jury cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Jury result = juryRepository.save(jury);
        return ResponseEntity.created(new URI("/api/juries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /juries} : Updates an existing jury.
     *
     * @param jury the jury to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jury,
     * or with status {@code 400 (Bad Request)} if the jury is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jury couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/juries")
    public ResponseEntity<Jury> updateJury(@RequestBody Jury jury) throws URISyntaxException {
        log.debug("REST request to update Jury : {}", jury);
        if (jury.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Jury result = juryRepository.save(jury);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jury.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /juries} : get all the juries.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of juries in body.
     */
    @GetMapping("/juries")
    public List<Jury> getAllJuries(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Juries");
        return juryRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /juries/:id} : get the "id" jury.
     *
     * @param id the id of the jury to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jury, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/juries/{id}")
    public ResponseEntity<Jury> getJury(@PathVariable Long id) {
        log.debug("REST request to get Jury : {}", id);
        Optional<Jury> jury = juryRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(jury);
    }

    /**
     * {@code DELETE  /juries/:id} : delete the "id" jury.
     *
     * @param id the id of the jury to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/juries/{id}")
    public ResponseEntity<Void> deleteJury(@PathVariable Long id) {
        log.debug("REST request to delete Jury : {}", id);
        juryRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
