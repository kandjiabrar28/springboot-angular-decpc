package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;
import com.mycompany.myapp.domain.Plage;
import com.mycompany.myapp.repository.PlageRepository;

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
 * Integration tests for the {@link PlageResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class PlageResourceIT {

    private static final String DEFAULT_PLAGE = "AAAAAAAAAA";
    private static final String UPDATED_PLAGE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPECOPIE = "AAAAAAAAAA";
    private static final String UPDATED_TYPECOPIE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NOMBRECOPIE = 1;
    private static final Integer UPDATED_NOMBRECOPIE = 2;

    private static final LocalDate DEFAULT_DATE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_MODIFICATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MODIFICATION = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PlageRepository plageRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlageMockMvc;

    private Plage plage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plage createEntity(EntityManager em) {
        Plage plage = new Plage()
            .plage(DEFAULT_PLAGE)
            .typecopie(DEFAULT_TYPECOPIE)
            .nombrecopie(DEFAULT_NOMBRECOPIE)
            .dateCreation(DEFAULT_DATE_CREATION)
            .dateModification(DEFAULT_DATE_MODIFICATION);
        return plage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plage createUpdatedEntity(EntityManager em) {
        Plage plage = new Plage()
            .plage(UPDATED_PLAGE)
            .typecopie(UPDATED_TYPECOPIE)
            .nombrecopie(UPDATED_NOMBRECOPIE)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateModification(UPDATED_DATE_MODIFICATION);
        return plage;
    }

    @BeforeEach
    public void initTest() {
        plage = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlage() throws Exception {
        int databaseSizeBeforeCreate = plageRepository.findAll().size();

        // Create the Plage
        restPlageMockMvc.perform(post("/api/plages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(plage)))
            .andExpect(status().isCreated());

        // Validate the Plage in the database
        List<Plage> plageList = plageRepository.findAll();
        assertThat(plageList).hasSize(databaseSizeBeforeCreate + 1);
        Plage testPlage = plageList.get(plageList.size() - 1);
        assertThat(testPlage.getPlage()).isEqualTo(DEFAULT_PLAGE);
        assertThat(testPlage.getTypecopie()).isEqualTo(DEFAULT_TYPECOPIE);
        assertThat(testPlage.getNombrecopie()).isEqualTo(DEFAULT_NOMBRECOPIE);
        assertThat(testPlage.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testPlage.getDateModification()).isEqualTo(DEFAULT_DATE_MODIFICATION);
    }

    @Test
    @Transactional
    public void createPlageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = plageRepository.findAll().size();

        // Create the Plage with an existing ID
        plage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlageMockMvc.perform(post("/api/plages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(plage)))
            .andExpect(status().isBadRequest());

        // Validate the Plage in the database
        List<Plage> plageList = plageRepository.findAll();
        assertThat(plageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPlages() throws Exception {
        // Initialize the database
        plageRepository.saveAndFlush(plage);

        // Get all the plageList
        restPlageMockMvc.perform(get("/api/plages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plage.getId().intValue())))
            .andExpect(jsonPath("$.[*].plage").value(hasItem(DEFAULT_PLAGE)))
            .andExpect(jsonPath("$.[*].typecopie").value(hasItem(DEFAULT_TYPECOPIE)))
            .andExpect(jsonPath("$.[*].nombrecopie").value(hasItem(DEFAULT_NOMBRECOPIE)))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].dateModification").value(hasItem(DEFAULT_DATE_MODIFICATION.toString())));
    }
    
    @Test
    @Transactional
    public void getPlage() throws Exception {
        // Initialize the database
        plageRepository.saveAndFlush(plage);

        // Get the plage
        restPlageMockMvc.perform(get("/api/plages/{id}", plage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plage.getId().intValue()))
            .andExpect(jsonPath("$.plage").value(DEFAULT_PLAGE))
            .andExpect(jsonPath("$.typecopie").value(DEFAULT_TYPECOPIE))
            .andExpect(jsonPath("$.nombrecopie").value(DEFAULT_NOMBRECOPIE))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION.toString()))
            .andExpect(jsonPath("$.dateModification").value(DEFAULT_DATE_MODIFICATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlage() throws Exception {
        // Get the plage
        restPlageMockMvc.perform(get("/api/plages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlage() throws Exception {
        // Initialize the database
        plageRepository.saveAndFlush(plage);

        int databaseSizeBeforeUpdate = plageRepository.findAll().size();

        // Update the plage
        Plage updatedPlage = plageRepository.findById(plage.getId()).get();
        // Disconnect from session so that the updates on updatedPlage are not directly saved in db
        em.detach(updatedPlage);
        updatedPlage
            .plage(UPDATED_PLAGE)
            .typecopie(UPDATED_TYPECOPIE)
            .nombrecopie(UPDATED_NOMBRECOPIE)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateModification(UPDATED_DATE_MODIFICATION);

        restPlageMockMvc.perform(put("/api/plages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlage)))
            .andExpect(status().isOk());

        // Validate the Plage in the database
        List<Plage> plageList = plageRepository.findAll();
        assertThat(plageList).hasSize(databaseSizeBeforeUpdate);
        Plage testPlage = plageList.get(plageList.size() - 1);
        assertThat(testPlage.getPlage()).isEqualTo(UPDATED_PLAGE);
        assertThat(testPlage.getTypecopie()).isEqualTo(UPDATED_TYPECOPIE);
        assertThat(testPlage.getNombrecopie()).isEqualTo(UPDATED_NOMBRECOPIE);
        assertThat(testPlage.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testPlage.getDateModification()).isEqualTo(UPDATED_DATE_MODIFICATION);
    }

    @Test
    @Transactional
    public void updateNonExistingPlage() throws Exception {
        int databaseSizeBeforeUpdate = plageRepository.findAll().size();

        // Create the Plage

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlageMockMvc.perform(put("/api/plages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(plage)))
            .andExpect(status().isBadRequest());

        // Validate the Plage in the database
        List<Plage> plageList = plageRepository.findAll();
        assertThat(plageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlage() throws Exception {
        // Initialize the database
        plageRepository.saveAndFlush(plage);

        int databaseSizeBeforeDelete = plageRepository.findAll().size();

        // Delete the plage
        restPlageMockMvc.perform(delete("/api/plages/{id}", plage.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Plage> plageList = plageRepository.findAll();
        assertThat(plageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
