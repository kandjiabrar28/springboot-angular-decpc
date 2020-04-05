package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;
import com.mycompany.myapp.domain.Correcteur;
import com.mycompany.myapp.repository.CorrecteurRepository;

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
 * Integration tests for the {@link CorrecteurResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CorrecteurResourceIT {

    private static final String DEFAULT_GRADE = "AAAAAAAAAA";
    private static final String UPDATED_GRADE = "BBBBBBBBBB";

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

    @Autowired
    private CorrecteurRepository correcteurRepository;

    @Mock
    private CorrecteurRepository correcteurRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCorrecteurMockMvc;

    private Correcteur correcteur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Correcteur createEntity(EntityManager em) {
        Correcteur correcteur = new Correcteur()
            .grade(DEFAULT_GRADE)
            .prenom(DEFAULT_PRENOM)
            .nom(DEFAULT_NOM)
            .provenance(DEFAULT_PROVENANCE)
            .cni(DEFAULT_CNI)
            .telephone(DEFAULT_TELEPHONE)
            .sexe(DEFAULT_SEXE);
        return correcteur;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Correcteur createUpdatedEntity(EntityManager em) {
        Correcteur correcteur = new Correcteur()
            .grade(UPDATED_GRADE)
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .provenance(UPDATED_PROVENANCE)
            .cni(UPDATED_CNI)
            .telephone(UPDATED_TELEPHONE)
            .sexe(UPDATED_SEXE);
        return correcteur;
    }

    @BeforeEach
    public void initTest() {
        correcteur = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorrecteur() throws Exception {
        int databaseSizeBeforeCreate = correcteurRepository.findAll().size();

        // Create the Correcteur
        restCorrecteurMockMvc.perform(post("/api/correcteurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(correcteur)))
            .andExpect(status().isCreated());

        // Validate the Correcteur in the database
        List<Correcteur> correcteurList = correcteurRepository.findAll();
        assertThat(correcteurList).hasSize(databaseSizeBeforeCreate + 1);
        Correcteur testCorrecteur = correcteurList.get(correcteurList.size() - 1);
        assertThat(testCorrecteur.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testCorrecteur.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testCorrecteur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testCorrecteur.getProvenance()).isEqualTo(DEFAULT_PROVENANCE);
        assertThat(testCorrecteur.getCni()).isEqualTo(DEFAULT_CNI);
        assertThat(testCorrecteur.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testCorrecteur.getSexe()).isEqualTo(DEFAULT_SEXE);
    }

    @Test
    @Transactional
    public void createCorrecteurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = correcteurRepository.findAll().size();

        // Create the Correcteur with an existing ID
        correcteur.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorrecteurMockMvc.perform(post("/api/correcteurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(correcteur)))
            .andExpect(status().isBadRequest());

        // Validate the Correcteur in the database
        List<Correcteur> correcteurList = correcteurRepository.findAll();
        assertThat(correcteurList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCorrecteurs() throws Exception {
        // Initialize the database
        correcteurRepository.saveAndFlush(correcteur);

        // Get all the correcteurList
        restCorrecteurMockMvc.perform(get("/api/correcteurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(correcteur.getId().intValue())))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].provenance").value(hasItem(DEFAULT_PROVENANCE)))
            .andExpect(jsonPath("$.[*].cni").value(hasItem(DEFAULT_CNI)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllCorrecteursWithEagerRelationshipsIsEnabled() throws Exception {
        CorrecteurResource correcteurResource = new CorrecteurResource(correcteurRepositoryMock);
        when(correcteurRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCorrecteurMockMvc.perform(get("/api/correcteurs?eagerload=true"))
            .andExpect(status().isOk());

        verify(correcteurRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllCorrecteursWithEagerRelationshipsIsNotEnabled() throws Exception {
        CorrecteurResource correcteurResource = new CorrecteurResource(correcteurRepositoryMock);
        when(correcteurRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCorrecteurMockMvc.perform(get("/api/correcteurs?eagerload=true"))
            .andExpect(status().isOk());

        verify(correcteurRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getCorrecteur() throws Exception {
        // Initialize the database
        correcteurRepository.saveAndFlush(correcteur);

        // Get the correcteur
        restCorrecteurMockMvc.perform(get("/api/correcteurs/{id}", correcteur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(correcteur.getId().intValue()))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.provenance").value(DEFAULT_PROVENANCE))
            .andExpect(jsonPath("$.cni").value(DEFAULT_CNI))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE));
    }

    @Test
    @Transactional
    public void getNonExistingCorrecteur() throws Exception {
        // Get the correcteur
        restCorrecteurMockMvc.perform(get("/api/correcteurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorrecteur() throws Exception {
        // Initialize the database
        correcteurRepository.saveAndFlush(correcteur);

        int databaseSizeBeforeUpdate = correcteurRepository.findAll().size();

        // Update the correcteur
        Correcteur updatedCorrecteur = correcteurRepository.findById(correcteur.getId()).get();
        // Disconnect from session so that the updates on updatedCorrecteur are not directly saved in db
        em.detach(updatedCorrecteur);
        updatedCorrecteur
            .grade(UPDATED_GRADE)
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .provenance(UPDATED_PROVENANCE)
            .cni(UPDATED_CNI)
            .telephone(UPDATED_TELEPHONE)
            .sexe(UPDATED_SEXE);

        restCorrecteurMockMvc.perform(put("/api/correcteurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCorrecteur)))
            .andExpect(status().isOk());

        // Validate the Correcteur in the database
        List<Correcteur> correcteurList = correcteurRepository.findAll();
        assertThat(correcteurList).hasSize(databaseSizeBeforeUpdate);
        Correcteur testCorrecteur = correcteurList.get(correcteurList.size() - 1);
        assertThat(testCorrecteur.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testCorrecteur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testCorrecteur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testCorrecteur.getProvenance()).isEqualTo(UPDATED_PROVENANCE);
        assertThat(testCorrecteur.getCni()).isEqualTo(UPDATED_CNI);
        assertThat(testCorrecteur.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testCorrecteur.getSexe()).isEqualTo(UPDATED_SEXE);
    }

    @Test
    @Transactional
    public void updateNonExistingCorrecteur() throws Exception {
        int databaseSizeBeforeUpdate = correcteurRepository.findAll().size();

        // Create the Correcteur

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCorrecteurMockMvc.perform(put("/api/correcteurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(correcteur)))
            .andExpect(status().isBadRequest());

        // Validate the Correcteur in the database
        List<Correcteur> correcteurList = correcteurRepository.findAll();
        assertThat(correcteurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCorrecteur() throws Exception {
        // Initialize the database
        correcteurRepository.saveAndFlush(correcteur);

        int databaseSizeBeforeDelete = correcteurRepository.findAll().size();

        // Delete the correcteur
        restCorrecteurMockMvc.perform(delete("/api/correcteurs/{id}", correcteur.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Correcteur> correcteurList = correcteurRepository.findAll();
        assertThat(correcteurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
