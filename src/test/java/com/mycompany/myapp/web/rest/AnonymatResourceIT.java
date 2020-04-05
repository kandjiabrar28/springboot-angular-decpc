package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;
import com.mycompany.myapp.domain.Anonymat;
import com.mycompany.myapp.repository.AnonymatRepository;

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
 * Integration tests for the {@link AnonymatResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class AnonymatResourceIT {

    private static final Integer DEFAULT_NUMANOYMAT = 1;
    private static final Integer UPDATED_NUMANOYMAT = 2;

    @Autowired
    private AnonymatRepository anonymatRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnonymatMockMvc;

    private Anonymat anonymat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Anonymat createEntity(EntityManager em) {
        Anonymat anonymat = new Anonymat()
            .numanoymat(DEFAULT_NUMANOYMAT);
        return anonymat;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Anonymat createUpdatedEntity(EntityManager em) {
        Anonymat anonymat = new Anonymat()
            .numanoymat(UPDATED_NUMANOYMAT);
        return anonymat;
    }

    @BeforeEach
    public void initTest() {
        anonymat = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnonymat() throws Exception {
        int databaseSizeBeforeCreate = anonymatRepository.findAll().size();

        // Create the Anonymat
        restAnonymatMockMvc.perform(post("/api/anonymats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(anonymat)))
            .andExpect(status().isCreated());

        // Validate the Anonymat in the database
        List<Anonymat> anonymatList = anonymatRepository.findAll();
        assertThat(anonymatList).hasSize(databaseSizeBeforeCreate + 1);
        Anonymat testAnonymat = anonymatList.get(anonymatList.size() - 1);
        assertThat(testAnonymat.getNumanoymat()).isEqualTo(DEFAULT_NUMANOYMAT);
    }

    @Test
    @Transactional
    public void createAnonymatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = anonymatRepository.findAll().size();

        // Create the Anonymat with an existing ID
        anonymat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnonymatMockMvc.perform(post("/api/anonymats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(anonymat)))
            .andExpect(status().isBadRequest());

        // Validate the Anonymat in the database
        List<Anonymat> anonymatList = anonymatRepository.findAll();
        assertThat(anonymatList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAnonymats() throws Exception {
        // Initialize the database
        anonymatRepository.saveAndFlush(anonymat);

        // Get all the anonymatList
        restAnonymatMockMvc.perform(get("/api/anonymats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anonymat.getId().intValue())))
            .andExpect(jsonPath("$.[*].numanoymat").value(hasItem(DEFAULT_NUMANOYMAT)));
    }
    
    @Test
    @Transactional
    public void getAnonymat() throws Exception {
        // Initialize the database
        anonymatRepository.saveAndFlush(anonymat);

        // Get the anonymat
        restAnonymatMockMvc.perform(get("/api/anonymats/{id}", anonymat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(anonymat.getId().intValue()))
            .andExpect(jsonPath("$.numanoymat").value(DEFAULT_NUMANOYMAT));
    }

    @Test
    @Transactional
    public void getNonExistingAnonymat() throws Exception {
        // Get the anonymat
        restAnonymatMockMvc.perform(get("/api/anonymats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnonymat() throws Exception {
        // Initialize the database
        anonymatRepository.saveAndFlush(anonymat);

        int databaseSizeBeforeUpdate = anonymatRepository.findAll().size();

        // Update the anonymat
        Anonymat updatedAnonymat = anonymatRepository.findById(anonymat.getId()).get();
        // Disconnect from session so that the updates on updatedAnonymat are not directly saved in db
        em.detach(updatedAnonymat);
        updatedAnonymat
            .numanoymat(UPDATED_NUMANOYMAT);

        restAnonymatMockMvc.perform(put("/api/anonymats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnonymat)))
            .andExpect(status().isOk());

        // Validate the Anonymat in the database
        List<Anonymat> anonymatList = anonymatRepository.findAll();
        assertThat(anonymatList).hasSize(databaseSizeBeforeUpdate);
        Anonymat testAnonymat = anonymatList.get(anonymatList.size() - 1);
        assertThat(testAnonymat.getNumanoymat()).isEqualTo(UPDATED_NUMANOYMAT);
    }

    @Test
    @Transactional
    public void updateNonExistingAnonymat() throws Exception {
        int databaseSizeBeforeUpdate = anonymatRepository.findAll().size();

        // Create the Anonymat

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnonymatMockMvc.perform(put("/api/anonymats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(anonymat)))
            .andExpect(status().isBadRequest());

        // Validate the Anonymat in the database
        List<Anonymat> anonymatList = anonymatRepository.findAll();
        assertThat(anonymatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnonymat() throws Exception {
        // Initialize the database
        anonymatRepository.saveAndFlush(anonymat);

        int databaseSizeBeforeDelete = anonymatRepository.findAll().size();

        // Delete the anonymat
        restAnonymatMockMvc.perform(delete("/api/anonymats/{id}", anonymat.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Anonymat> anonymatList = anonymatRepository.findAll();
        assertThat(anonymatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
