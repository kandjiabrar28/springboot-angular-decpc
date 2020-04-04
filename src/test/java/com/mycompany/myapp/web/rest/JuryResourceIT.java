package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;
import com.mycompany.myapp.domain.Jury;
import com.mycompany.myapp.repository.JuryRepository;

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
 * Integration tests for the {@link JuryResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class JuryResourceIT {

    private static final String DEFAULT_NUMJURY = "AAAAAAAAAA";
    private static final String UPDATED_NUMJURY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_MODIFICATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MODIFICATION = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private JuryRepository juryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJuryMockMvc;

    private Jury jury;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Jury createEntity(EntityManager em) {
        Jury jury = new Jury()
            .numjury(DEFAULT_NUMJURY)
            .dateCreation(DEFAULT_DATE_CREATION)
            .dateModification(DEFAULT_DATE_MODIFICATION);
        return jury;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Jury createUpdatedEntity(EntityManager em) {
        Jury jury = new Jury()
            .numjury(UPDATED_NUMJURY)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateModification(UPDATED_DATE_MODIFICATION);
        return jury;
    }

    @BeforeEach
    public void initTest() {
        jury = createEntity(em);
    }

    @Test
    @Transactional
    public void createJury() throws Exception {
        int databaseSizeBeforeCreate = juryRepository.findAll().size();

        // Create the Jury
        restJuryMockMvc.perform(post("/api/juries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jury)))
            .andExpect(status().isCreated());

        // Validate the Jury in the database
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeCreate + 1);
        Jury testJury = juryList.get(juryList.size() - 1);
        assertThat(testJury.getNumjury()).isEqualTo(DEFAULT_NUMJURY);
        assertThat(testJury.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testJury.getDateModification()).isEqualTo(DEFAULT_DATE_MODIFICATION);
    }

    @Test
    @Transactional
    public void createJuryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = juryRepository.findAll().size();

        // Create the Jury with an existing ID
        jury.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJuryMockMvc.perform(post("/api/juries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jury)))
            .andExpect(status().isBadRequest());

        // Validate the Jury in the database
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllJuries() throws Exception {
        // Initialize the database
        juryRepository.saveAndFlush(jury);

        // Get all the juryList
        restJuryMockMvc.perform(get("/api/juries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jury.getId().intValue())))
            .andExpect(jsonPath("$.[*].numjury").value(hasItem(DEFAULT_NUMJURY)))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].dateModification").value(hasItem(DEFAULT_DATE_MODIFICATION.toString())));
    }
    
    @Test
    @Transactional
    public void getJury() throws Exception {
        // Initialize the database
        juryRepository.saveAndFlush(jury);

        // Get the jury
        restJuryMockMvc.perform(get("/api/juries/{id}", jury.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jury.getId().intValue()))
            .andExpect(jsonPath("$.numjury").value(DEFAULT_NUMJURY))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION.toString()))
            .andExpect(jsonPath("$.dateModification").value(DEFAULT_DATE_MODIFICATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJury() throws Exception {
        // Get the jury
        restJuryMockMvc.perform(get("/api/juries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJury() throws Exception {
        // Initialize the database
        juryRepository.saveAndFlush(jury);

        int databaseSizeBeforeUpdate = juryRepository.findAll().size();

        // Update the jury
        Jury updatedJury = juryRepository.findById(jury.getId()).get();
        // Disconnect from session so that the updates on updatedJury are not directly saved in db
        em.detach(updatedJury);
        updatedJury
            .numjury(UPDATED_NUMJURY)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateModification(UPDATED_DATE_MODIFICATION);

        restJuryMockMvc.perform(put("/api/juries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedJury)))
            .andExpect(status().isOk());

        // Validate the Jury in the database
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeUpdate);
        Jury testJury = juryList.get(juryList.size() - 1);
        assertThat(testJury.getNumjury()).isEqualTo(UPDATED_NUMJURY);
        assertThat(testJury.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testJury.getDateModification()).isEqualTo(UPDATED_DATE_MODIFICATION);
    }

    @Test
    @Transactional
    public void updateNonExistingJury() throws Exception {
        int databaseSizeBeforeUpdate = juryRepository.findAll().size();

        // Create the Jury

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJuryMockMvc.perform(put("/api/juries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jury)))
            .andExpect(status().isBadRequest());

        // Validate the Jury in the database
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJury() throws Exception {
        // Initialize the database
        juryRepository.saveAndFlush(jury);

        int databaseSizeBeforeDelete = juryRepository.findAll().size();

        // Delete the jury
        restJuryMockMvc.perform(delete("/api/juries/{id}", jury.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
