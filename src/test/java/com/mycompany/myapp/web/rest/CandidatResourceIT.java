package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;
import com.mycompany.myapp.domain.Candidat;
import com.mycompany.myapp.repository.CandidatRepository;

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
 * Integration tests for the {@link CandidatResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CandidatResourceIT {

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

    private static final String DEFAULT_NIVEAU = "AAAAAAAAAA";
    private static final String UPDATED_NIVEAU = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_MODIFICATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MODIFICATION = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CandidatRepository candidatRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCandidatMockMvc;

    private Candidat candidat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Candidat createEntity(EntityManager em) {
        Candidat candidat = new Candidat()
            .prenom(DEFAULT_PRENOM)
            .nom(DEFAULT_NOM)
            .provenance(DEFAULT_PROVENANCE)
            .cni(DEFAULT_CNI)
            .telephone(DEFAULT_TELEPHONE)
            .sexe(DEFAULT_SEXE)
            .datenais(DEFAULT_DATENAIS)
            .niveau(DEFAULT_NIVEAU)
            .dateCreation(DEFAULT_DATE_CREATION)
            .dateModification(DEFAULT_DATE_MODIFICATION);
        return candidat;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Candidat createUpdatedEntity(EntityManager em) {
        Candidat candidat = new Candidat()
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .provenance(UPDATED_PROVENANCE)
            .cni(UPDATED_CNI)
            .telephone(UPDATED_TELEPHONE)
            .sexe(UPDATED_SEXE)
            .datenais(UPDATED_DATENAIS)
            .niveau(UPDATED_NIVEAU)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateModification(UPDATED_DATE_MODIFICATION);
        return candidat;
    }

    @BeforeEach
    public void initTest() {
        candidat = createEntity(em);
    }

    @Test
    @Transactional
    public void createCandidat() throws Exception {
        int databaseSizeBeforeCreate = candidatRepository.findAll().size();

        // Create the Candidat
        restCandidatMockMvc.perform(post("/api/candidats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(candidat)))
            .andExpect(status().isCreated());

        // Validate the Candidat in the database
        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeCreate + 1);
        Candidat testCandidat = candidatList.get(candidatList.size() - 1);
        assertThat(testCandidat.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testCandidat.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testCandidat.getProvenance()).isEqualTo(DEFAULT_PROVENANCE);
        assertThat(testCandidat.getCni()).isEqualTo(DEFAULT_CNI);
        assertThat(testCandidat.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testCandidat.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testCandidat.getDatenais()).isEqualTo(DEFAULT_DATENAIS);
        assertThat(testCandidat.getNiveau()).isEqualTo(DEFAULT_NIVEAU);
        assertThat(testCandidat.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testCandidat.getDateModification()).isEqualTo(DEFAULT_DATE_MODIFICATION);
    }

    @Test
    @Transactional
    public void createCandidatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = candidatRepository.findAll().size();

        // Create the Candidat with an existing ID
        candidat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCandidatMockMvc.perform(post("/api/candidats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(candidat)))
            .andExpect(status().isBadRequest());

        // Validate the Candidat in the database
        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCandidats() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList
        restCandidatMockMvc.perform(get("/api/candidats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidat.getId().intValue())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].provenance").value(hasItem(DEFAULT_PROVENANCE)))
            .andExpect(jsonPath("$.[*].cni").value(hasItem(DEFAULT_CNI)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE)))
            .andExpect(jsonPath("$.[*].datenais").value(hasItem(DEFAULT_DATENAIS.toString())))
            .andExpect(jsonPath("$.[*].niveau").value(hasItem(DEFAULT_NIVEAU)))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].dateModification").value(hasItem(DEFAULT_DATE_MODIFICATION.toString())));
    }
    
    @Test
    @Transactional
    public void getCandidat() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get the candidat
        restCandidatMockMvc.perform(get("/api/candidats/{id}", candidat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(candidat.getId().intValue()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.provenance").value(DEFAULT_PROVENANCE))
            .andExpect(jsonPath("$.cni").value(DEFAULT_CNI))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE))
            .andExpect(jsonPath("$.datenais").value(DEFAULT_DATENAIS.toString()))
            .andExpect(jsonPath("$.niveau").value(DEFAULT_NIVEAU))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION.toString()))
            .andExpect(jsonPath("$.dateModification").value(DEFAULT_DATE_MODIFICATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCandidat() throws Exception {
        // Get the candidat
        restCandidatMockMvc.perform(get("/api/candidats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCandidat() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        int databaseSizeBeforeUpdate = candidatRepository.findAll().size();

        // Update the candidat
        Candidat updatedCandidat = candidatRepository.findById(candidat.getId()).get();
        // Disconnect from session so that the updates on updatedCandidat are not directly saved in db
        em.detach(updatedCandidat);
        updatedCandidat
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .provenance(UPDATED_PROVENANCE)
            .cni(UPDATED_CNI)
            .telephone(UPDATED_TELEPHONE)
            .sexe(UPDATED_SEXE)
            .datenais(UPDATED_DATENAIS)
            .niveau(UPDATED_NIVEAU)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateModification(UPDATED_DATE_MODIFICATION);

        restCandidatMockMvc.perform(put("/api/candidats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCandidat)))
            .andExpect(status().isOk());

        // Validate the Candidat in the database
        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeUpdate);
        Candidat testCandidat = candidatList.get(candidatList.size() - 1);
        assertThat(testCandidat.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testCandidat.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testCandidat.getProvenance()).isEqualTo(UPDATED_PROVENANCE);
        assertThat(testCandidat.getCni()).isEqualTo(UPDATED_CNI);
        assertThat(testCandidat.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testCandidat.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testCandidat.getDatenais()).isEqualTo(UPDATED_DATENAIS);
        assertThat(testCandidat.getNiveau()).isEqualTo(UPDATED_NIVEAU);
        assertThat(testCandidat.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testCandidat.getDateModification()).isEqualTo(UPDATED_DATE_MODIFICATION);
    }

    @Test
    @Transactional
    public void updateNonExistingCandidat() throws Exception {
        int databaseSizeBeforeUpdate = candidatRepository.findAll().size();

        // Create the Candidat

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCandidatMockMvc.perform(put("/api/candidats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(candidat)))
            .andExpect(status().isBadRequest());

        // Validate the Candidat in the database
        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCandidat() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        int databaseSizeBeforeDelete = candidatRepository.findAll().size();

        // Delete the candidat
        restCandidatMockMvc.perform(delete("/api/candidats/{id}", candidat.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
