package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;
import com.mycompany.myapp.domain.PlageCopie;
import com.mycompany.myapp.repository.PlageCopieRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PlageCopieResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class PlageCopieResourceIT {

    private static final String DEFAULT_PLAGE = "AAAAAAAAAA";
    private static final String UPDATED_PLAGE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPECOPIE = "AAAAAAAAAA";
    private static final String UPDATED_TYPECOPIE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NOMBRECOPIE = 1;
    private static final Integer UPDATED_NOMBRECOPIE = 2;

    @Autowired
    private PlageCopieRepository plageCopieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlageCopieMockMvc;

    private PlageCopie plageCopie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlageCopie createEntity(EntityManager em) {
        PlageCopie plageCopie = new PlageCopie()
            .plage(DEFAULT_PLAGE)
            .typecopie(DEFAULT_TYPECOPIE)
            .nombrecopie(DEFAULT_NOMBRECOPIE);
        return plageCopie;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlageCopie createUpdatedEntity(EntityManager em) {
        PlageCopie plageCopie = new PlageCopie()
            .plage(UPDATED_PLAGE)
            .typecopie(UPDATED_TYPECOPIE)
            .nombrecopie(UPDATED_NOMBRECOPIE);
        return plageCopie;
    }

    @BeforeEach
    public void initTest() {
        plageCopie = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlageCopie() throws Exception {
        int databaseSizeBeforeCreate = plageCopieRepository.findAll().size();

        // Create the PlageCopie
        restPlageCopieMockMvc.perform(post("/api/plage-copies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(plageCopie)))
            .andExpect(status().isCreated());

        // Validate the PlageCopie in the database
        List<PlageCopie> plageCopieList = plageCopieRepository.findAll();
        assertThat(plageCopieList).hasSize(databaseSizeBeforeCreate + 1);
        PlageCopie testPlageCopie = plageCopieList.get(plageCopieList.size() - 1);
        assertThat(testPlageCopie.getPlage()).isEqualTo(DEFAULT_PLAGE);
        assertThat(testPlageCopie.getTypecopie()).isEqualTo(DEFAULT_TYPECOPIE);
        assertThat(testPlageCopie.getNombrecopie()).isEqualTo(DEFAULT_NOMBRECOPIE);
    }

    @Test
    @Transactional
    public void createPlageCopieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = plageCopieRepository.findAll().size();

        // Create the PlageCopie with an existing ID
        plageCopie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlageCopieMockMvc.perform(post("/api/plage-copies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(plageCopie)))
            .andExpect(status().isBadRequest());

        // Validate the PlageCopie in the database
        List<PlageCopie> plageCopieList = plageCopieRepository.findAll();
        assertThat(plageCopieList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPlageCopies() throws Exception {
        // Initialize the database
        plageCopieRepository.saveAndFlush(plageCopie);

        // Get all the plageCopieList
        restPlageCopieMockMvc.perform(get("/api/plage-copies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plageCopie.getId().intValue())))
            .andExpect(jsonPath("$.[*].plage").value(hasItem(DEFAULT_PLAGE)))
            .andExpect(jsonPath("$.[*].typecopie").value(hasItem(DEFAULT_TYPECOPIE)))
            .andExpect(jsonPath("$.[*].nombrecopie").value(hasItem(DEFAULT_NOMBRECOPIE)));
    }
    
    @Test
    @Transactional
    public void getPlageCopie() throws Exception {
        // Initialize the database
        plageCopieRepository.saveAndFlush(plageCopie);

        // Get the plageCopie
        restPlageCopieMockMvc.perform(get("/api/plage-copies/{id}", plageCopie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plageCopie.getId().intValue()))
            .andExpect(jsonPath("$.plage").value(DEFAULT_PLAGE))
            .andExpect(jsonPath("$.typecopie").value(DEFAULT_TYPECOPIE))
            .andExpect(jsonPath("$.nombrecopie").value(DEFAULT_NOMBRECOPIE));
    }

    @Test
    @Transactional
    public void getNonExistingPlageCopie() throws Exception {
        // Get the plageCopie
        restPlageCopieMockMvc.perform(get("/api/plage-copies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlageCopie() throws Exception {
        // Initialize the database
        plageCopieRepository.saveAndFlush(plageCopie);

        int databaseSizeBeforeUpdate = plageCopieRepository.findAll().size();

        // Update the plageCopie
        PlageCopie updatedPlageCopie = plageCopieRepository.findById(plageCopie.getId()).get();
        // Disconnect from session so that the updates on updatedPlageCopie are not directly saved in db
        em.detach(updatedPlageCopie);
        updatedPlageCopie
            .plage(UPDATED_PLAGE)
            .typecopie(UPDATED_TYPECOPIE)
            .nombrecopie(UPDATED_NOMBRECOPIE);

        restPlageCopieMockMvc.perform(put("/api/plage-copies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlageCopie)))
            .andExpect(status().isOk());

        // Validate the PlageCopie in the database
        List<PlageCopie> plageCopieList = plageCopieRepository.findAll();
        assertThat(plageCopieList).hasSize(databaseSizeBeforeUpdate);
        PlageCopie testPlageCopie = plageCopieList.get(plageCopieList.size() - 1);
        assertThat(testPlageCopie.getPlage()).isEqualTo(UPDATED_PLAGE);
        assertThat(testPlageCopie.getTypecopie()).isEqualTo(UPDATED_TYPECOPIE);
        assertThat(testPlageCopie.getNombrecopie()).isEqualTo(UPDATED_NOMBRECOPIE);
    }

    @Test
    @Transactional
    public void updateNonExistingPlageCopie() throws Exception {
        int databaseSizeBeforeUpdate = plageCopieRepository.findAll().size();

        // Create the PlageCopie

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlageCopieMockMvc.perform(put("/api/plage-copies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(plageCopie)))
            .andExpect(status().isBadRequest());

        // Validate the PlageCopie in the database
        List<PlageCopie> plageCopieList = plageCopieRepository.findAll();
        assertThat(plageCopieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlageCopie() throws Exception {
        // Initialize the database
        plageCopieRepository.saveAndFlush(plageCopie);

        int databaseSizeBeforeDelete = plageCopieRepository.findAll().size();

        // Delete the plageCopie
        restPlageCopieMockMvc.perform(delete("/api/plage-copies/{id}", plageCopie.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlageCopie> plageCopieList = plageCopieRepository.findAll();
        assertThat(plageCopieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
