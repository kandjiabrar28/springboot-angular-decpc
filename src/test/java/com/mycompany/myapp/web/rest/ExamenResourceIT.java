package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;
import com.mycompany.myapp.domain.Examen;
import com.mycompany.myapp.repository.ExamenRepository;

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
 * Integration tests for the {@link ExamenResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ExamenResourceIT {

    private static final String DEFAULT_NOMEXAMEN = "AAAAAAAAAA";
    private static final String UPDATED_NOMEXAMEN = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_MODIFICATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MODIFICATION = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ExamenRepository examenRepository;

    @Mock
    private ExamenRepository examenRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExamenMockMvc;

    private Examen examen;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Examen createEntity(EntityManager em) {
        Examen examen = new Examen()
            .nomexamen(DEFAULT_NOMEXAMEN)
            .dateCreation(DEFAULT_DATE_CREATION)
            .dateModification(DEFAULT_DATE_MODIFICATION);
        return examen;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Examen createUpdatedEntity(EntityManager em) {
        Examen examen = new Examen()
            .nomexamen(UPDATED_NOMEXAMEN)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateModification(UPDATED_DATE_MODIFICATION);
        return examen;
    }

    @BeforeEach
    public void initTest() {
        examen = createEntity(em);
    }

    @Test
    @Transactional
    public void createExamen() throws Exception {
        int databaseSizeBeforeCreate = examenRepository.findAll().size();

        // Create the Examen
        restExamenMockMvc.perform(post("/api/examen")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examen)))
            .andExpect(status().isCreated());

        // Validate the Examen in the database
        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeCreate + 1);
        Examen testExamen = examenList.get(examenList.size() - 1);
        assertThat(testExamen.getNomexamen()).isEqualTo(DEFAULT_NOMEXAMEN);
        assertThat(testExamen.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testExamen.getDateModification()).isEqualTo(DEFAULT_DATE_MODIFICATION);
    }

    @Test
    @Transactional
    public void createExamenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = examenRepository.findAll().size();

        // Create the Examen with an existing ID
        examen.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExamenMockMvc.perform(post("/api/examen")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examen)))
            .andExpect(status().isBadRequest());

        // Validate the Examen in the database
        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllExamen() throws Exception {
        // Initialize the database
        examenRepository.saveAndFlush(examen);

        // Get all the examenList
        restExamenMockMvc.perform(get("/api/examen?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examen.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomexamen").value(hasItem(DEFAULT_NOMEXAMEN)))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].dateModification").value(hasItem(DEFAULT_DATE_MODIFICATION.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllExamenWithEagerRelationshipsIsEnabled() throws Exception {
        ExamenResource examenResource = new ExamenResource(examenRepositoryMock);
        when(examenRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restExamenMockMvc.perform(get("/api/examen?eagerload=true"))
            .andExpect(status().isOk());

        verify(examenRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllExamenWithEagerRelationshipsIsNotEnabled() throws Exception {
        ExamenResource examenResource = new ExamenResource(examenRepositoryMock);
        when(examenRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restExamenMockMvc.perform(get("/api/examen?eagerload=true"))
            .andExpect(status().isOk());

        verify(examenRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getExamen() throws Exception {
        // Initialize the database
        examenRepository.saveAndFlush(examen);

        // Get the examen
        restExamenMockMvc.perform(get("/api/examen/{id}", examen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(examen.getId().intValue()))
            .andExpect(jsonPath("$.nomexamen").value(DEFAULT_NOMEXAMEN))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION.toString()))
            .andExpect(jsonPath("$.dateModification").value(DEFAULT_DATE_MODIFICATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExamen() throws Exception {
        // Get the examen
        restExamenMockMvc.perform(get("/api/examen/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExamen() throws Exception {
        // Initialize the database
        examenRepository.saveAndFlush(examen);

        int databaseSizeBeforeUpdate = examenRepository.findAll().size();

        // Update the examen
        Examen updatedExamen = examenRepository.findById(examen.getId()).get();
        // Disconnect from session so that the updates on updatedExamen are not directly saved in db
        em.detach(updatedExamen);
        updatedExamen
            .nomexamen(UPDATED_NOMEXAMEN)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateModification(UPDATED_DATE_MODIFICATION);

        restExamenMockMvc.perform(put("/api/examen")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedExamen)))
            .andExpect(status().isOk());

        // Validate the Examen in the database
        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeUpdate);
        Examen testExamen = examenList.get(examenList.size() - 1);
        assertThat(testExamen.getNomexamen()).isEqualTo(UPDATED_NOMEXAMEN);
        assertThat(testExamen.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testExamen.getDateModification()).isEqualTo(UPDATED_DATE_MODIFICATION);
    }

    @Test
    @Transactional
    public void updateNonExistingExamen() throws Exception {
        int databaseSizeBeforeUpdate = examenRepository.findAll().size();

        // Create the Examen

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExamenMockMvc.perform(put("/api/examen")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examen)))
            .andExpect(status().isBadRequest());

        // Validate the Examen in the database
        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExamen() throws Exception {
        // Initialize the database
        examenRepository.saveAndFlush(examen);

        int databaseSizeBeforeDelete = examenRepository.findAll().size();

        // Delete the examen
        restExamenMockMvc.perform(delete("/api/examen/{id}", examen.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
