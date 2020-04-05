package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;
import com.mycompany.myapp.domain.PVSurveillance;
import com.mycompany.myapp.repository.PVSurveillanceRepository;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PVSurveillanceResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class PVSurveillanceResourceIT {

    private static final String DEFAULT_EPREUVE = "AAAAAAAAAA";
    private static final String UPDATED_EPREUVE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_HEURE_DEB = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_HEURE_DEB = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_HEURE_FIN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_HEURE_FIN = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final LocalDate DEFAULT_DATESURV = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATESURV = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PVSurveillanceRepository pVSurveillanceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPVSurveillanceMockMvc;

    private PVSurveillance pVSurveillance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PVSurveillance createEntity(EntityManager em) {
        PVSurveillance pVSurveillance = new PVSurveillance()
            .epreuve(DEFAULT_EPREUVE)
            .heureDeb(DEFAULT_HEURE_DEB)
            .heureFin(DEFAULT_HEURE_FIN)
            .datesurv(DEFAULT_DATESURV);
        return pVSurveillance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PVSurveillance createUpdatedEntity(EntityManager em) {
        PVSurveillance pVSurveillance = new PVSurveillance()
            .epreuve(UPDATED_EPREUVE)
            .heureDeb(UPDATED_HEURE_DEB)
            .heureFin(UPDATED_HEURE_FIN)
            .datesurv(UPDATED_DATESURV);
        return pVSurveillance;
    }

    @BeforeEach
    public void initTest() {
        pVSurveillance = createEntity(em);
    }

    @Test
    @Transactional
    public void createPVSurveillance() throws Exception {
        int databaseSizeBeforeCreate = pVSurveillanceRepository.findAll().size();

        // Create the PVSurveillance
        restPVSurveillanceMockMvc.perform(post("/api/pv-surveillances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pVSurveillance)))
            .andExpect(status().isCreated());

        // Validate the PVSurveillance in the database
        List<PVSurveillance> pVSurveillanceList = pVSurveillanceRepository.findAll();
        assertThat(pVSurveillanceList).hasSize(databaseSizeBeforeCreate + 1);
        PVSurveillance testPVSurveillance = pVSurveillanceList.get(pVSurveillanceList.size() - 1);
        assertThat(testPVSurveillance.getEpreuve()).isEqualTo(DEFAULT_EPREUVE);
        assertThat(testPVSurveillance.getHeureDeb()).isEqualTo(DEFAULT_HEURE_DEB);
        assertThat(testPVSurveillance.getHeureFin()).isEqualTo(DEFAULT_HEURE_FIN);
        assertThat(testPVSurveillance.getDatesurv()).isEqualTo(DEFAULT_DATESURV);
    }

    @Test
    @Transactional
    public void createPVSurveillanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pVSurveillanceRepository.findAll().size();

        // Create the PVSurveillance with an existing ID
        pVSurveillance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPVSurveillanceMockMvc.perform(post("/api/pv-surveillances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pVSurveillance)))
            .andExpect(status().isBadRequest());

        // Validate the PVSurveillance in the database
        List<PVSurveillance> pVSurveillanceList = pVSurveillanceRepository.findAll();
        assertThat(pVSurveillanceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPVSurveillances() throws Exception {
        // Initialize the database
        pVSurveillanceRepository.saveAndFlush(pVSurveillance);

        // Get all the pVSurveillanceList
        restPVSurveillanceMockMvc.perform(get("/api/pv-surveillances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pVSurveillance.getId().intValue())))
            .andExpect(jsonPath("$.[*].epreuve").value(hasItem(DEFAULT_EPREUVE)))
            .andExpect(jsonPath("$.[*].heureDeb").value(hasItem(sameInstant(DEFAULT_HEURE_DEB))))
            .andExpect(jsonPath("$.[*].heureFin").value(hasItem(sameInstant(DEFAULT_HEURE_FIN))))
            .andExpect(jsonPath("$.[*].datesurv").value(hasItem(DEFAULT_DATESURV.toString())));
    }
    
    @Test
    @Transactional
    public void getPVSurveillance() throws Exception {
        // Initialize the database
        pVSurveillanceRepository.saveAndFlush(pVSurveillance);

        // Get the pVSurveillance
        restPVSurveillanceMockMvc.perform(get("/api/pv-surveillances/{id}", pVSurveillance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pVSurveillance.getId().intValue()))
            .andExpect(jsonPath("$.epreuve").value(DEFAULT_EPREUVE))
            .andExpect(jsonPath("$.heureDeb").value(sameInstant(DEFAULT_HEURE_DEB)))
            .andExpect(jsonPath("$.heureFin").value(sameInstant(DEFAULT_HEURE_FIN)))
            .andExpect(jsonPath("$.datesurv").value(DEFAULT_DATESURV.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPVSurveillance() throws Exception {
        // Get the pVSurveillance
        restPVSurveillanceMockMvc.perform(get("/api/pv-surveillances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePVSurveillance() throws Exception {
        // Initialize the database
        pVSurveillanceRepository.saveAndFlush(pVSurveillance);

        int databaseSizeBeforeUpdate = pVSurveillanceRepository.findAll().size();

        // Update the pVSurveillance
        PVSurveillance updatedPVSurveillance = pVSurveillanceRepository.findById(pVSurveillance.getId()).get();
        // Disconnect from session so that the updates on updatedPVSurveillance are not directly saved in db
        em.detach(updatedPVSurveillance);
        updatedPVSurveillance
            .epreuve(UPDATED_EPREUVE)
            .heureDeb(UPDATED_HEURE_DEB)
            .heureFin(UPDATED_HEURE_FIN)
            .datesurv(UPDATED_DATESURV);

        restPVSurveillanceMockMvc.perform(put("/api/pv-surveillances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPVSurveillance)))
            .andExpect(status().isOk());

        // Validate the PVSurveillance in the database
        List<PVSurveillance> pVSurveillanceList = pVSurveillanceRepository.findAll();
        assertThat(pVSurveillanceList).hasSize(databaseSizeBeforeUpdate);
        PVSurveillance testPVSurveillance = pVSurveillanceList.get(pVSurveillanceList.size() - 1);
        assertThat(testPVSurveillance.getEpreuve()).isEqualTo(UPDATED_EPREUVE);
        assertThat(testPVSurveillance.getHeureDeb()).isEqualTo(UPDATED_HEURE_DEB);
        assertThat(testPVSurveillance.getHeureFin()).isEqualTo(UPDATED_HEURE_FIN);
        assertThat(testPVSurveillance.getDatesurv()).isEqualTo(UPDATED_DATESURV);
    }

    @Test
    @Transactional
    public void updateNonExistingPVSurveillance() throws Exception {
        int databaseSizeBeforeUpdate = pVSurveillanceRepository.findAll().size();

        // Create the PVSurveillance

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPVSurveillanceMockMvc.perform(put("/api/pv-surveillances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pVSurveillance)))
            .andExpect(status().isBadRequest());

        // Validate the PVSurveillance in the database
        List<PVSurveillance> pVSurveillanceList = pVSurveillanceRepository.findAll();
        assertThat(pVSurveillanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePVSurveillance() throws Exception {
        // Initialize the database
        pVSurveillanceRepository.saveAndFlush(pVSurveillance);

        int databaseSizeBeforeDelete = pVSurveillanceRepository.findAll().size();

        // Delete the pVSurveillance
        restPVSurveillanceMockMvc.perform(delete("/api/pv-surveillances/{id}", pVSurveillance.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PVSurveillance> pVSurveillanceList = pVSurveillanceRepository.findAll();
        assertThat(pVSurveillanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
