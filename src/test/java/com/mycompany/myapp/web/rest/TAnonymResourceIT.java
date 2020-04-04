package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;
import com.mycompany.myapp.domain.TAnonym;
import com.mycompany.myapp.repository.TAnonymRepository;

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
 * Integration tests for the {@link TAnonymResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class TAnonymResourceIT {

    private static final String DEFAULT_NUMANONYM = "AAAAAAAAAA";
    private static final String UPDATED_NUMANONYM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_MODIFICATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MODIFICATION = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TAnonymRepository tAnonymRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTAnonymMockMvc;

    private TAnonym tAnonym;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TAnonym createEntity(EntityManager em) {
        TAnonym tAnonym = new TAnonym()
            .numanonym(DEFAULT_NUMANONYM)
            .dateCreation(DEFAULT_DATE_CREATION)
            .dateModification(DEFAULT_DATE_MODIFICATION);
        return tAnonym;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TAnonym createUpdatedEntity(EntityManager em) {
        TAnonym tAnonym = new TAnonym()
            .numanonym(UPDATED_NUMANONYM)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateModification(UPDATED_DATE_MODIFICATION);
        return tAnonym;
    }

    @BeforeEach
    public void initTest() {
        tAnonym = createEntity(em);
    }

    @Test
    @Transactional
    public void createTAnonym() throws Exception {
        int databaseSizeBeforeCreate = tAnonymRepository.findAll().size();

        // Create the TAnonym
        restTAnonymMockMvc.perform(post("/api/t-anonyms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tAnonym)))
            .andExpect(status().isCreated());

        // Validate the TAnonym in the database
        List<TAnonym> tAnonymList = tAnonymRepository.findAll();
        assertThat(tAnonymList).hasSize(databaseSizeBeforeCreate + 1);
        TAnonym testTAnonym = tAnonymList.get(tAnonymList.size() - 1);
        assertThat(testTAnonym.getNumanonym()).isEqualTo(DEFAULT_NUMANONYM);
        assertThat(testTAnonym.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testTAnonym.getDateModification()).isEqualTo(DEFAULT_DATE_MODIFICATION);
    }

    @Test
    @Transactional
    public void createTAnonymWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tAnonymRepository.findAll().size();

        // Create the TAnonym with an existing ID
        tAnonym.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTAnonymMockMvc.perform(post("/api/t-anonyms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tAnonym)))
            .andExpect(status().isBadRequest());

        // Validate the TAnonym in the database
        List<TAnonym> tAnonymList = tAnonymRepository.findAll();
        assertThat(tAnonymList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTAnonyms() throws Exception {
        // Initialize the database
        tAnonymRepository.saveAndFlush(tAnonym);

        // Get all the tAnonymList
        restTAnonymMockMvc.perform(get("/api/t-anonyms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tAnonym.getId().intValue())))
            .andExpect(jsonPath("$.[*].numanonym").value(hasItem(DEFAULT_NUMANONYM)))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].dateModification").value(hasItem(DEFAULT_DATE_MODIFICATION.toString())));
    }
    
    @Test
    @Transactional
    public void getTAnonym() throws Exception {
        // Initialize the database
        tAnonymRepository.saveAndFlush(tAnonym);

        // Get the tAnonym
        restTAnonymMockMvc.perform(get("/api/t-anonyms/{id}", tAnonym.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tAnonym.getId().intValue()))
            .andExpect(jsonPath("$.numanonym").value(DEFAULT_NUMANONYM))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION.toString()))
            .andExpect(jsonPath("$.dateModification").value(DEFAULT_DATE_MODIFICATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTAnonym() throws Exception {
        // Get the tAnonym
        restTAnonymMockMvc.perform(get("/api/t-anonyms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTAnonym() throws Exception {
        // Initialize the database
        tAnonymRepository.saveAndFlush(tAnonym);

        int databaseSizeBeforeUpdate = tAnonymRepository.findAll().size();

        // Update the tAnonym
        TAnonym updatedTAnonym = tAnonymRepository.findById(tAnonym.getId()).get();
        // Disconnect from session so that the updates on updatedTAnonym are not directly saved in db
        em.detach(updatedTAnonym);
        updatedTAnonym
            .numanonym(UPDATED_NUMANONYM)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateModification(UPDATED_DATE_MODIFICATION);

        restTAnonymMockMvc.perform(put("/api/t-anonyms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTAnonym)))
            .andExpect(status().isOk());

        // Validate the TAnonym in the database
        List<TAnonym> tAnonymList = tAnonymRepository.findAll();
        assertThat(tAnonymList).hasSize(databaseSizeBeforeUpdate);
        TAnonym testTAnonym = tAnonymList.get(tAnonymList.size() - 1);
        assertThat(testTAnonym.getNumanonym()).isEqualTo(UPDATED_NUMANONYM);
        assertThat(testTAnonym.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testTAnonym.getDateModification()).isEqualTo(UPDATED_DATE_MODIFICATION);
    }

    @Test
    @Transactional
    public void updateNonExistingTAnonym() throws Exception {
        int databaseSizeBeforeUpdate = tAnonymRepository.findAll().size();

        // Create the TAnonym

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTAnonymMockMvc.perform(put("/api/t-anonyms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tAnonym)))
            .andExpect(status().isBadRequest());

        // Validate the TAnonym in the database
        List<TAnonym> tAnonymList = tAnonymRepository.findAll();
        assertThat(tAnonymList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTAnonym() throws Exception {
        // Initialize the database
        tAnonymRepository.saveAndFlush(tAnonym);

        int databaseSizeBeforeDelete = tAnonymRepository.findAll().size();

        // Delete the tAnonym
        restTAnonymMockMvc.perform(delete("/api/t-anonyms/{id}", tAnonym.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TAnonym> tAnonymList = tAnonymRepository.findAll();
        assertThat(tAnonymList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
