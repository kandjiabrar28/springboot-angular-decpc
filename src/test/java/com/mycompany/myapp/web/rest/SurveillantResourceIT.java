package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;
import com.mycompany.myapp.domain.Surveillant;
import com.mycompany.myapp.repository.SurveillantRepository;

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
 * Integration tests for the {@link SurveillantResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class SurveillantResourceIT {

    private static final String DEFAULT_FONCTION = "AAAAAAAAAA";
    private static final String UPDATED_FONCTION = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PROVENANCE = "AAAAAAAAAA";
    private static final String UPDATED_PROVENANCE = "BBBBBBBBBB";

    private static final String DEFAULT_CNI = "AAAAAAAAAA";
    private static final String UPDATED_CNI = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_SEXE = "AAAAAAAAAA";
    private static final String UPDATED_SEXE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATENAIS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATENAIS = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SurveillantRepository surveillantRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSurveillantMockMvc;

    private Surveillant surveillant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Surveillant createEntity(EntityManager em) {
        Surveillant surveillant = new Surveillant()
            .fonction(DEFAULT_FONCTION)
            .prenom(DEFAULT_PRENOM)
            .nom(DEFAULT_NOM)
            .provenance(DEFAULT_PROVENANCE)
            .cni(DEFAULT_CNI)
            .telephone(DEFAULT_TELEPHONE)
            .sexe(DEFAULT_SEXE)
            .datenais(DEFAULT_DATENAIS);
        return surveillant;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Surveillant createUpdatedEntity(EntityManager em) {
        Surveillant surveillant = new Surveillant()
            .fonction(UPDATED_FONCTION)
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .provenance(UPDATED_PROVENANCE)
            .cni(UPDATED_CNI)
            .telephone(UPDATED_TELEPHONE)
            .sexe(UPDATED_SEXE)
            .datenais(UPDATED_DATENAIS);
        return surveillant;
    }

    @BeforeEach
    public void initTest() {
        surveillant = createEntity(em);
    }

    @Test
    @Transactional
    public void createSurveillant() throws Exception {
        int databaseSizeBeforeCreate = surveillantRepository.findAll().size();

        // Create the Surveillant
        restSurveillantMockMvc.perform(post("/api/surveillants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(surveillant)))
            .andExpect(status().isCreated());

        // Validate the Surveillant in the database
        List<Surveillant> surveillantList = surveillantRepository.findAll();
        assertThat(surveillantList).hasSize(databaseSizeBeforeCreate + 1);
        Surveillant testSurveillant = surveillantList.get(surveillantList.size() - 1);
        assertThat(testSurveillant.getFonction()).isEqualTo(DEFAULT_FONCTION);
        assertThat(testSurveillant.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testSurveillant.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testSurveillant.getProvenance()).isEqualTo(DEFAULT_PROVENANCE);
        assertThat(testSurveillant.getCni()).isEqualTo(DEFAULT_CNI);
        assertThat(testSurveillant.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testSurveillant.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testSurveillant.getDatenais()).isEqualTo(DEFAULT_DATENAIS);
    }

    @Test
    @Transactional
    public void createSurveillantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = surveillantRepository.findAll().size();

        // Create the Surveillant with an existing ID
        surveillant.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSurveillantMockMvc.perform(post("/api/surveillants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(surveillant)))
            .andExpect(status().isBadRequest());

        // Validate the Surveillant in the database
        List<Surveillant> surveillantList = surveillantRepository.findAll();
        assertThat(surveillantList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSurveillants() throws Exception {
        // Initialize the database
        surveillantRepository.saveAndFlush(surveillant);

        // Get all the surveillantList
        restSurveillantMockMvc.perform(get("/api/surveillants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(surveillant.getId().intValue())))
            .andExpect(jsonPath("$.[*].fonction").value(hasItem(DEFAULT_FONCTION)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].provenance").value(hasItem(DEFAULT_PROVENANCE)))
            .andExpect(jsonPath("$.[*].cni").value(hasItem(DEFAULT_CNI)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE)))
            .andExpect(jsonPath("$.[*].datenais").value(hasItem(DEFAULT_DATENAIS.toString())));
    }
    
    @Test
    @Transactional
    public void getSurveillant() throws Exception {
        // Initialize the database
        surveillantRepository.saveAndFlush(surveillant);

        // Get the surveillant
        restSurveillantMockMvc.perform(get("/api/surveillants/{id}", surveillant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(surveillant.getId().intValue()))
            .andExpect(jsonPath("$.fonction").value(DEFAULT_FONCTION))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.provenance").value(DEFAULT_PROVENANCE))
            .andExpect(jsonPath("$.cni").value(DEFAULT_CNI))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE))
            .andExpect(jsonPath("$.datenais").value(DEFAULT_DATENAIS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSurveillant() throws Exception {
        // Get the surveillant
        restSurveillantMockMvc.perform(get("/api/surveillants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSurveillant() throws Exception {
        // Initialize the database
        surveillantRepository.saveAndFlush(surveillant);

        int databaseSizeBeforeUpdate = surveillantRepository.findAll().size();

        // Update the surveillant
        Surveillant updatedSurveillant = surveillantRepository.findById(surveillant.getId()).get();
        // Disconnect from session so that the updates on updatedSurveillant are not directly saved in db
        em.detach(updatedSurveillant);
        updatedSurveillant
            .fonction(UPDATED_FONCTION)
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .provenance(UPDATED_PROVENANCE)
            .cni(UPDATED_CNI)
            .telephone(UPDATED_TELEPHONE)
            .sexe(UPDATED_SEXE)
            .datenais(UPDATED_DATENAIS);

        restSurveillantMockMvc.perform(put("/api/surveillants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSurveillant)))
            .andExpect(status().isOk());

        // Validate the Surveillant in the database
        List<Surveillant> surveillantList = surveillantRepository.findAll();
        assertThat(surveillantList).hasSize(databaseSizeBeforeUpdate);
        Surveillant testSurveillant = surveillantList.get(surveillantList.size() - 1);
        assertThat(testSurveillant.getFonction()).isEqualTo(UPDATED_FONCTION);
        assertThat(testSurveillant.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testSurveillant.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testSurveillant.getProvenance()).isEqualTo(UPDATED_PROVENANCE);
        assertThat(testSurveillant.getCni()).isEqualTo(UPDATED_CNI);
        assertThat(testSurveillant.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testSurveillant.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testSurveillant.getDatenais()).isEqualTo(UPDATED_DATENAIS);
    }

    @Test
    @Transactional
    public void updateNonExistingSurveillant() throws Exception {
        int databaseSizeBeforeUpdate = surveillantRepository.findAll().size();

        // Create the Surveillant

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSurveillantMockMvc.perform(put("/api/surveillants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(surveillant)))
            .andExpect(status().isBadRequest());

        // Validate the Surveillant in the database
        List<Surveillant> surveillantList = surveillantRepository.findAll();
        assertThat(surveillantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSurveillant() throws Exception {
        // Initialize the database
        surveillantRepository.saveAndFlush(surveillant);

        int databaseSizeBeforeDelete = surveillantRepository.findAll().size();

        // Delete the surveillant
        restSurveillantMockMvc.perform(delete("/api/surveillants/{id}", surveillant.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Surveillant> surveillantList = surveillantRepository.findAll();
        assertThat(surveillantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
