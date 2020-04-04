package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Tour;
import com.mycompany.myapp.repository.TourRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Tour}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TourResource {

    private final Logger log = LoggerFactory.getLogger(TourResource.class);

    private static final String ENTITY_NAME = "tour";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TourRepository tourRepository;

    public TourResource(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    /**
     * {@code POST  /tours} : Create a new tour.
     *
     * @param tour the tour to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tour, or with status {@code 400 (Bad Request)} if the tour has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tours")
    public ResponseEntity<Tour> createTour(@RequestBody Tour tour) throws URISyntaxException {
        log.debug("REST request to save Tour : {}", tour);
        if (tour.getId() != null) {
            throw new BadRequestAlertException("A new tour cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tour result = tourRepository.save(tour);
        return ResponseEntity.created(new URI("/api/tours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tours} : Updates an existing tour.
     *
     * @param tour the tour to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tour,
     * or with status {@code 400 (Bad Request)} if the tour is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tour couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tours")
    public ResponseEntity<Tour> updateTour(@RequestBody Tour tour) throws URISyntaxException {
        log.debug("REST request to update Tour : {}", tour);
        if (tour.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Tour result = tourRepository.save(tour);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tour.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tours} : get all the tours.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tours in body.
     */
    @GetMapping("/tours")
    public List<Tour> getAllTours() {
        log.debug("REST request to get all Tours");
        return tourRepository.findAll();
    }

    /**
     * {@code GET  /tours/:id} : get the "id" tour.
     *
     * @param id the id of the tour to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tour, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tours/{id}")
    public ResponseEntity<Tour> getTour(@PathVariable Long id) {
        log.debug("REST request to get Tour : {}", id);
        Optional<Tour> tour = tourRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tour);
    }

    /**
     * {@code DELETE  /tours/:id} : delete the "id" tour.
     *
     * @param id the id of the tour to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tours/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable Long id) {
        log.debug("REST request to delete Tour : {}", id);
        tourRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
