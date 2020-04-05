package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;
import com.mycompany.myapp.domain.Jury;
import com.mycompany.myapp.repository.JuryRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link JuryResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class JuryResourceIT {

    private static final Integer DEFAULT_NUMJURY = 1;
    private static final Integer UPDATED_NUMJURY = 2;

    @Autowired
    private JuryRepository juryRepository;

    @Mock
    private JuryRepository juryRepositoryMock;

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
            .numjury(DEFAULT_NUMJURY);
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
            .numjury(UPDATED_NUMJURY);
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
            .andExpect(jsonPath("$.[*].numjury").value(hasItem(DEFAULT_NUMJURY)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllJuriesWithEagerRelationshipsIsEnabled() throws Exception {
        JuryResource juryResource = new JuryResource(juryRepositoryMock);
        when(juryRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restJuryMockMvc.perform(get("/api/juries?eagerload=true"))
            .andExpect(status().isOk());

        verify(juryRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllJuriesWithEagerRelationshipsIsNotEnabled() throws Exception {
        JuryResource juryResource = new JuryResource(juryRepositoryMock);
        when(juryRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restJuryMockMvc.perform(get("/api/juries?eagerload=true"))
            .andExpect(status().isOk());

        verify(juryRepositoryMock, times(1)).findAllWithEagerRelationships(any());
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
            .andExpect(jsonPath("$.numjury").value(DEFAULT_NUMJURY));
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
            .numjury(UPDATED_NUMJURY);

        restJuryMockMvc.perform(put("/api/juries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedJury)))
            .andExpect(status().isOk());

        // Validate the Jury in the database
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeUpdate);
        Jury testJury = juryList.get(juryList.size() - 1);
        assertThat(testJury.getNumjury()).isEqualTo(UPDATED_NUMJURY);
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
