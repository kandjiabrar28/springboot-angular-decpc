package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Table;
import com.mycompany.myapp.repository.TableRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Table}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TableResource {

    private final Logger log = LoggerFactory.getLogger(TableResource.class);

    private static final String ENTITY_NAME = "table";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TableRepository tableRepository;

    public TableResource(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    /**
     * {@code POST  /tables} : Create a new table.
     *
     * @param table the table to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new table, or with status {@code 400 (Bad Request)} if the table has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tables")
    public ResponseEntity<Table> createTable(@RequestBody Table table) throws URISyntaxException {
        log.debug("REST request to save Table : {}", table);
        if (table.getId() != null) {
            throw new BadRequestAlertException("A new table cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Table result = tableRepository.save(table);
        return ResponseEntity.created(new URI("/api/tables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tables} : Updates an existing table.
     *
     * @param table the table to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated table,
     * or with status {@code 400 (Bad Request)} if the table is not valid,
     * or with status {@code 500 (Internal Server Error)} if the table couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tables")
    public ResponseEntity<Table> updateTable(@RequestBody Table table) throws URISyntaxException {
        log.debug("REST request to update Table : {}", table);
        if (table.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Table result = tableRepository.save(table);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, table.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tables} : get all the tables.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tables in body.
     */
    @GetMapping("/tables")
    public List<Table> getAllTables(@RequestParam(required = false) String filter) {
        if ("candidat-is-null".equals(filter)) {
            log.debug("REST request to get all Tables where candidat is null");
            return StreamSupport
                .stream(tableRepository.findAll().spliterator(), false)
                .filter(table -> table.getCandidat() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Tables");
        return tableRepository.findAll();
    }

    /**
     * {@code GET  /tables/:id} : get the "id" table.
     *
     * @param id the id of the table to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the table, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tables/{id}")
    public ResponseEntity<Table> getTable(@PathVariable Long id) {
        log.debug("REST request to get Table : {}", id);
        Optional<Table> table = tableRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(table);
    }

    /**
     * {@code DELETE  /tables/:id} : delete the "id" table.
     *
     * @param id the id of the table to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tables/{id}")
    public ResponseEntity<Void> deleteTable(@PathVariable Long id) {
        log.debug("REST request to delete Table : {}", id);
        tableRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
