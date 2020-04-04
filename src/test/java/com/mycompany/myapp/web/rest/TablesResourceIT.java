package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;
import com.mycompany.myapp.domain.Tables;
import com.mycompany.myapp.repository.TablesRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TablesResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class TablesResourceIT {

    private static final Integer DEFAULT_NUMTABLE = 1;
    private static final Integer UPDATED_NUMTABLE = 2;

    private static final LocalDate DEFAULT_DATE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_MODIFICATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MODIFICATION = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TablesRepository tablesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTablesMockMvc;

    private Tables tables;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tables createEntity(EntityManager em) {
        Tables tables = new Tables()
            .numtable(DEFAULT_NUMTABLE)
            .dateCreation(DEFAULT_DATE_CREATION)
            .dateModification(DEFAULT_DATE_MODIFICATION);
        return tables;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tables createUpdatedEntity(EntityManager em) {
        Tables tables = new Tables()
            .numtable(UPDATED_NUMTABLE)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateModification(UPDATED_DATE_MODIFICATION);
        return tables;
    }

    @BeforeEach
    public void initTest() {
        tables = createEntity(em);
    }

    @Test
    @Transactional
    public void createTables() throws Exception {
        int databaseSizeBeforeCreate = tablesRepository.findAll().size();

        // Create the Tables
        restTablesMockMvc.perform(post("/api/tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tables)))
            .andExpect(status().isCreated());

        // Validate the Tables in the database
        List<Tables> tablesList = tablesRepository.findAll();
        assertThat(tablesList).hasSize(databaseSizeBeforeCreate + 1);
        Tables testTables = tablesList.get(tablesList.size() - 1);
        assertThat(testTables.getNumtable()).isEqualTo(DEFAULT_NUMTABLE);
        assertThat(testTables.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testTables.getDateModification()).isEqualTo(DEFAULT_DATE_MODIFICATION);
    }

    @Test
    @Transactional
    public void createTablesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tablesRepository.findAll().size();

        // Create the Tables with an existing ID
        tables.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTablesMockMvc.perform(post("/api/tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tables)))
            .andExpect(status().isBadRequest());

        // Validate the Tables in the database
        List<Tables> tablesList = tablesRepository.findAll();
        assertThat(tablesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTables() throws Exception {
        // Initialize the database
        tablesRepository.saveAndFlush(tables);

        // Get all the tablesList
        restTablesMockMvc.perform(get("/api/tables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tables.getId().intValue())))
            .andExpect(jsonPath("$.[*].numtable").value(hasItem(DEFAULT_NUMTABLE)))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].dateModification").value(hasItem(DEFAULT_DATE_MODIFICATION.toString())));
    }
    
    @Test
    @Transactional
    public void getTables() throws Exception {
        // Initialize the database
        tablesRepository.saveAndFlush(tables);

        // Get the tables
        restTablesMockMvc.perform(get("/api/tables/{id}", tables.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tables.getId().intValue()))
            .andExpect(jsonPath("$.numtable").value(DEFAULT_NUMTABLE))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION.toString()))
            .andExpect(jsonPath("$.dateModification").value(DEFAULT_DATE_MODIFICATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTables() throws Exception {
        // Get the tables
        restTablesMockMvc.perform(get("/api/tables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTables() throws Exception {
        // Initialize the database
        tablesRepository.saveAndFlush(tables);

        int databaseSizeBeforeUpdate = tablesRepository.findAll().size();

        // Update the tables
        Tables updatedTables = tablesRepository.findById(tables.getId()).get();
        // Disconnect from session so that the updates on updatedTables are not directly saved in db
        em.detach(updatedTables);
        updatedTables
            .numtable(UPDATED_NUMTABLE)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateModification(UPDATED_DATE_MODIFICATION);

        restTablesMockMvc.perform(put("/api/tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTables)))
            .andExpect(status().isOk());

        // Validate the Tables in the database
        List<Tables> tablesList = tablesRepository.findAll();
        assertThat(tablesList).hasSize(databaseSizeBeforeUpdate);
        Tables testTables = tablesList.get(tablesList.size() - 1);
        assertThat(testTables.getNumtable()).isEqualTo(UPDATED_NUMTABLE);
        assertThat(testTables.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testTables.getDateModification()).isEqualTo(UPDATED_DATE_MODIFICATION);
    }

    @Test
    @Transactional
    public void updateNonExistingTables() throws Exception {
        int databaseSizeBeforeUpdate = tablesRepository.findAll().size();

        // Create the Tables

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTablesMockMvc.perform(put("/api/tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tables)))
            .andExpect(status().isBadRequest());

        // Validate the Tables in the database
        List<Tables> tablesList = tablesRepository.findAll();
        assertThat(tablesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTables() throws Exception {
        // Initialize the database
        tablesRepository.saveAndFlush(tables);

        int databaseSizeBeforeDelete = tablesRepository.findAll().size();

        // Delete the tables
        restTablesMockMvc.perform(delete("/api/tables/{id}", tables.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tables> tablesList = tablesRepository.findAll();
        assertThat(tablesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
