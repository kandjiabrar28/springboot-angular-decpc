package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;
import com.mycompany.myapp.domain.Table;
import com.mycompany.myapp.repository.TableRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TableResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class TableResourceIT {

    private static final Integer DEFAULT_NUMTABLE = 1;
    private static final Integer UPDATED_NUMTABLE = 2;

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTableMockMvc;

    private Table table;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Table createEntity(EntityManager em) {
        Table table = new Table()
            .numtable(DEFAULT_NUMTABLE);
        return table;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Table createUpdatedEntity(EntityManager em) {
        Table table = new Table()
            .numtable(UPDATED_NUMTABLE);
        return table;
    }

    @BeforeEach
    public void initTest() {
        table = createEntity(em);
    }

    @Test
    @Transactional
    public void createTable() throws Exception {
        int databaseSizeBeforeCreate = tableRepository.findAll().size();

        // Create the Table
        restTableMockMvc.perform(post("/api/tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(table)))
            .andExpect(status().isCreated());

        // Validate the Table in the database
        List<Table> tableList = tableRepository.findAll();
        assertThat(tableList).hasSize(databaseSizeBeforeCreate + 1);
        Table testTable = tableList.get(tableList.size() - 1);
        assertThat(testTable.getNumtable()).isEqualTo(DEFAULT_NUMTABLE);
    }

    @Test
    @Transactional
    public void createTableWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tableRepository.findAll().size();

        // Create the Table with an existing ID
        table.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTableMockMvc.perform(post("/api/tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(table)))
            .andExpect(status().isBadRequest());

        // Validate the Table in the database
        List<Table> tableList = tableRepository.findAll();
        assertThat(tableList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTables() throws Exception {
        // Initialize the database
        tableRepository.saveAndFlush(table);

        // Get all the tableList
        restTableMockMvc.perform(get("/api/tables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(table.getId().intValue())))
            .andExpect(jsonPath("$.[*].numtable").value(hasItem(DEFAULT_NUMTABLE)));
    }
    
    @Test
    @Transactional
    public void getTable() throws Exception {
        // Initialize the database
        tableRepository.saveAndFlush(table);

        // Get the table
        restTableMockMvc.perform(get("/api/tables/{id}", table.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(table.getId().intValue()))
            .andExpect(jsonPath("$.numtable").value(DEFAULT_NUMTABLE));
    }

    @Test
    @Transactional
    public void getNonExistingTable() throws Exception {
        // Get the table
        restTableMockMvc.perform(get("/api/tables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTable() throws Exception {
        // Initialize the database
        tableRepository.saveAndFlush(table);

        int databaseSizeBeforeUpdate = tableRepository.findAll().size();

        // Update the table
        Table updatedTable = tableRepository.findById(table.getId()).get();
        // Disconnect from session so that the updates on updatedTable are not directly saved in db
        em.detach(updatedTable);
        updatedTable
            .numtable(UPDATED_NUMTABLE);

        restTableMockMvc.perform(put("/api/tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTable)))
            .andExpect(status().isOk());

        // Validate the Table in the database
        List<Table> tableList = tableRepository.findAll();
        assertThat(tableList).hasSize(databaseSizeBeforeUpdate);
        Table testTable = tableList.get(tableList.size() - 1);
        assertThat(testTable.getNumtable()).isEqualTo(UPDATED_NUMTABLE);
    }

    @Test
    @Transactional
    public void updateNonExistingTable() throws Exception {
        int databaseSizeBeforeUpdate = tableRepository.findAll().size();

        // Create the Table

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTableMockMvc.perform(put("/api/tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(table)))
            .andExpect(status().isBadRequest());

        // Validate the Table in the database
        List<Table> tableList = tableRepository.findAll();
        assertThat(tableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTable() throws Exception {
        // Initialize the database
        tableRepository.saveAndFlush(table);

        int databaseSizeBeforeDelete = tableRepository.findAll().size();

        // Delete the table
        restTableMockMvc.perform(delete("/api/tables/{id}", table.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Table> tableList = tableRepository.findAll();
        assertThat(tableList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
