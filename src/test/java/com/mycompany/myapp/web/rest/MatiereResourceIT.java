package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;
import com.mycompany.myapp.domain.Matiere;
import com.mycompany.myapp.repository.MatiereRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MatiereResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MatiereResourceIT {

    private static final String DEFAULT_LIBMATIERE = "AAAAAAAAAA";
    private static final String UPDATED_LIBMATIERE = "BBBBBBBBBB";

    private static final Double DEFAULT_NOTEELIMIN = 1D;
    private static final Double UPDATED_NOTEELIMIN = 2D;

    private static final Double DEFAULT_COEFFICIENT = 1D;
    private static final Double UPDATED_COEFFICIENT = 2D;

    private static final LocalDate DEFAULT_DATE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_MODIFICATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MODIFICATION = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MatiereRepository matiereRepository;

    @Mock
    private MatiereRepository matiereRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMatiereMockMvc;

    private Matiere matiere;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Matiere createEntity(EntityManager em) {
        Matiere matiere = new Matiere()
            .libmatiere(DEFAULT_LIBMATIERE)
            .noteelimin(DEFAULT_NOTEELIMIN)
            .coefficient(DEFAULT_COEFFICIENT)
            .dateCreation(DEFAULT_DATE_CREATION)
            .dateModification(DEFAULT_DATE_MODIFICATION);
        return matiere;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Matiere createUpdatedEntity(EntityManager em) {
        Matiere matiere = new Matiere()
            .libmatiere(UPDATED_LIBMATIERE)
            .noteelimin(UPDATED_NOTEELIMIN)
            .coefficient(UPDATED_COEFFICIENT)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateModification(UPDATED_DATE_MODIFICATION);
        return matiere;
    }

    @BeforeEach
    public void initTest() {
        matiere = createEntity(em);
    }

    @Test
    @Transactional
    public void createMatiere() throws Exception {
        int databaseSizeBeforeCreate = matiereRepository.findAll().size();

        // Create the Matiere
        restMatiereMockMvc.perform(post("/api/matieres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matiere)))
            .andExpect(status().isCreated());

        // Validate the Matiere in the database
        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeCreate + 1);
        Matiere testMatiere = matiereList.get(matiereList.size() - 1);
        assertThat(testMatiere.getLibmatiere()).isEqualTo(DEFAULT_LIBMATIERE);
        assertThat(testMatiere.getNoteelimin()).isEqualTo(DEFAULT_NOTEELIMIN);
        assertThat(testMatiere.getCoefficient()).isEqualTo(DEFAULT_COEFFICIENT);
        assertThat(testMatiere.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testMatiere.getDateModification()).isEqualTo(DEFAULT_DATE_MODIFICATION);
    }

    @Test
    @Transactional
    public void createMatiereWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = matiereRepository.findAll().size();

        // Create the Matiere with an existing ID
        matiere.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatiereMockMvc.perform(post("/api/matieres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matiere)))
            .andExpect(status().isBadRequest());

        // Validate the Matiere in the database
        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMatieres() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList
        restMatiereMockMvc.perform(get("/api/matieres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matiere.getId().intValue())))
            .andExpect(jsonPath("$.[*].libmatiere").value(hasItem(DEFAULT_LIBMATIERE)))
            .andExpect(jsonPath("$.[*].noteelimin").value(hasItem(DEFAULT_NOTEELIMIN.doubleValue())))
            .andExpect(jsonPath("$.[*].coefficient").value(hasItem(DEFAULT_COEFFICIENT.doubleValue())))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].dateModification").value(hasItem(DEFAULT_DATE_MODIFICATION.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllMatieresWithEagerRelationshipsIsEnabled() throws Exception {
        MatiereResource matiereResource = new MatiereResource(matiereRepositoryMock);
        when(matiereRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMatiereMockMvc.perform(get("/api/matieres?eagerload=true"))
            .andExpect(status().isOk());

        verify(matiereRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllMatieresWithEagerRelationshipsIsNotEnabled() throws Exception {
        MatiereResource matiereResource = new MatiereResource(matiereRepositoryMock);
        when(matiereRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMatiereMockMvc.perform(get("/api/matieres?eagerload=true"))
            .andExpect(status().isOk());

        verify(matiereRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getMatiere() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get the matiere
        restMatiereMockMvc.perform(get("/api/matieres/{id}", matiere.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(matiere.getId().intValue()))
            .andExpect(jsonPath("$.libmatiere").value(DEFAULT_LIBMATIERE))
            .andExpect(jsonPath("$.noteelimin").value(DEFAULT_NOTEELIMIN.doubleValue()))
            .andExpect(jsonPath("$.coefficient").value(DEFAULT_COEFFICIENT.doubleValue()))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION.toString()))
            .andExpect(jsonPath("$.dateModification").value(DEFAULT_DATE_MODIFICATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMatiere() throws Exception {
        // Get the matiere
        restMatiereMockMvc.perform(get("/api/matieres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMatiere() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        int databaseSizeBeforeUpdate = matiereRepository.findAll().size();

        // Update the matiere
        Matiere updatedMatiere = matiereRepository.findById(matiere.getId()).get();
        // Disconnect from session so that the updates on updatedMatiere are not directly saved in db
        em.detach(updatedMatiere);
        updatedMatiere
            .libmatiere(UPDATED_LIBMATIERE)
            .noteelimin(UPDATED_NOTEELIMIN)
            .coefficient(UPDATED_COEFFICIENT)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateModification(UPDATED_DATE_MODIFICATION);

        restMatiereMockMvc.perform(put("/api/matieres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMatiere)))
            .andExpect(status().isOk());

        // Validate the Matiere in the database
        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeUpdate);
        Matiere testMatiere = matiereList.get(matiereList.size() - 1);
        assertThat(testMatiere.getLibmatiere()).isEqualTo(UPDATED_LIBMATIERE);
        assertThat(testMatiere.getNoteelimin()).isEqualTo(UPDATED_NOTEELIMIN);
        assertThat(testMatiere.getCoefficient()).isEqualTo(UPDATED_COEFFICIENT);
        assertThat(testMatiere.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testMatiere.getDateModification()).isEqualTo(UPDATED_DATE_MODIFICATION);
    }

    @Test
    @Transactional
    public void updateNonExistingMatiere() throws Exception {
        int databaseSizeBeforeUpdate = matiereRepository.findAll().size();

        // Create the Matiere

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatiereMockMvc.perform(put("/api/matieres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matiere)))
            .andExpect(status().isBadRequest());

        // Validate the Matiere in the database
        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMatiere() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        int databaseSizeBeforeDelete = matiereRepository.findAll().size();

        // Delete the matiere
        restMatiereMockMvc.perform(delete("/api/matieres/{id}", matiere.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
