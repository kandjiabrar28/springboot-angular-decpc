package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.PlageCopie;
import com.mycompany.myapp.repository.PlageCopieRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PlageCopie}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PlageCopieResource {

    private final Logger log = LoggerFactory.getLogger(PlageCopieResource.class);

    private static final String ENTITY_NAME = "plageCopie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlageCopieRepository plageCopieRepository;

    public PlageCopieResource(PlageCopieRepository plageCopieRepository) {
        this.plageCopieRepository = plageCopieRepository;
    }

    /**
     * {@code POST  /plage-copies} : Create a new plageCopie.
     *
     * @param plageCopie the plageCopie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plageCopie, or with status {@code 400 (Bad Request)} if the plageCopie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plage-copies")
    public ResponseEntity<PlageCopie> createPlageCopie(@RequestBody PlageCopie plageCopie) throws URISyntaxException {
        log.debug("REST request to save PlageCopie : {}", plageCopie);
        if (plageCopie.getId() != null) {
            throw new BadRequestAlertException("A new plageCopie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlageCopie result = plageCopieRepository.save(plageCopie);
        return ResponseEntity.created(new URI("/api/plage-copies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plage-copies} : Updates an existing plageCopie.
     *
     * @param plageCopie the plageCopie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plageCopie,
     * or with status {@code 400 (Bad Request)} if the plageCopie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plageCopie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plage-copies")
    public ResponseEntity<PlageCopie> updatePlageCopie(@RequestBody PlageCopie plageCopie) throws URISyntaxException {
        log.debug("REST request to update PlageCopie : {}", plageCopie);
        if (plageCopie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlageCopie result = plageCopieRepository.save(plageCopie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plageCopie.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /plage-copies} : get all the plageCopies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plageCopies in body.
     */
    @GetMapping("/plage-copies")
    public List<PlageCopie> getAllPlageCopies() {
        log.debug("REST request to get all PlageCopies");
        return plageCopieRepository.findAll();
    }

    /**
     * {@code GET  /plage-copies/:id} : get the "id" plageCopie.
     *
     * @param id the id of the plageCopie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plageCopie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plage-copies/{id}")
    public ResponseEntity<PlageCopie> getPlageCopie(@PathVariable Long id) {
        log.debug("REST request to get PlageCopie : {}", id);
        Optional<PlageCopie> plageCopie = plageCopieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(plageCopie);
    }

    /**
     * {@code DELETE  /plage-copies/:id} : delete the "id" plageCopie.
     *
     * @param id the id of the plageCopie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plage-copies/{id}")
    public ResponseEntity<Void> deletePlageCopie(@PathVariable Long id) {
        log.debug("REST request to delete PlageCopie : {}", id);
        plageCopieRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
