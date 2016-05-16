package com.codegoons.diesli.web.rest;

import com.codegoons.diesli.DiesliApp;
import com.codegoons.diesli.domain.MottoDefinition;
import com.codegoons.diesli.repository.MottoDefinitionRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the MottoDefinitionResource REST controller.
 *
 * @see MottoDefinitionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DiesliApp.class)
@WebAppConfiguration
@IntegrationTest
public class MottoDefinitionResourceIntTest {

    private static final String DEFAULT_MATERIA = "AAAAA";
    private static final String UPDATED_MATERIA = "BBBBB";
    private static final String DEFAULT_REGISTRO = "AAAAA";
    private static final String UPDATED_REGISTRO = "BBBBB";
    private static final String DEFAULT_REGION = "AAAAA";
    private static final String UPDATED_REGION = "BBBBB";
    private static final String DEFAULT_EJEMPLO = "AAAAA";
    private static final String UPDATED_EJEMPLO = "BBBBB";
    private static final String DEFAULT_CATEGORIA = "AAAAA";
    private static final String UPDATED_CATEGORIA = "BBBBB";

    private static final Long DEFAULT_POPULARITY = 1L;
    private static final Long UPDATED_POPULARITY = 2L;

    @Inject
    private MottoDefinitionRepository mottoDefinitionRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMottoDefinitionMockMvc;

    private MottoDefinition mottoDefinition;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MottoDefinitionResource mottoDefinitionResource = new MottoDefinitionResource();
        ReflectionTestUtils.setField(mottoDefinitionResource, "mottoDefinitionRepository", mottoDefinitionRepository);
        this.restMottoDefinitionMockMvc = MockMvcBuilders.standaloneSetup(mottoDefinitionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        mottoDefinition = new MottoDefinition();
        mottoDefinition.setMateria(DEFAULT_MATERIA);
        mottoDefinition.setRegistro(DEFAULT_REGISTRO);
        mottoDefinition.setRegion(DEFAULT_REGION);
        mottoDefinition.setEjemplo(DEFAULT_EJEMPLO);
        mottoDefinition.setCategoria(DEFAULT_CATEGORIA);
        mottoDefinition.setPopularity(DEFAULT_POPULARITY);
    }

    @Test
    @Transactional
    public void createMottoDefinition() throws Exception {
        int databaseSizeBeforeCreate = mottoDefinitionRepository.findAll().size();

        // Create the MottoDefinition

        restMottoDefinitionMockMvc.perform(post("/api/motto-definitions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mottoDefinition)))
                .andExpect(status().isCreated());

        // Validate the MottoDefinition in the database
        List<MottoDefinition> mottoDefinitions = mottoDefinitionRepository.findAll();
        assertThat(mottoDefinitions).hasSize(databaseSizeBeforeCreate + 1);
        MottoDefinition testMottoDefinition = mottoDefinitions.get(mottoDefinitions.size() - 1);
        assertThat(testMottoDefinition.getMateria()).isEqualTo(DEFAULT_MATERIA);
        assertThat(testMottoDefinition.getRegistro()).isEqualTo(DEFAULT_REGISTRO);
        assertThat(testMottoDefinition.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testMottoDefinition.getEjemplo()).isEqualTo(DEFAULT_EJEMPLO);
        assertThat(testMottoDefinition.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);
        assertThat(testMottoDefinition.getPopularity()).isEqualTo(DEFAULT_POPULARITY);
    }

    @Test
    @Transactional
    public void getAllMottoDefinitions() throws Exception {
        // Initialize the database
        mottoDefinitionRepository.saveAndFlush(mottoDefinition);

        // Get all the mottoDefinitions
        restMottoDefinitionMockMvc.perform(get("/api/motto-definitions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(mottoDefinition.getId().intValue())))
                .andExpect(jsonPath("$.[*].materia").value(hasItem(DEFAULT_MATERIA.toString())))
                .andExpect(jsonPath("$.[*].registro").value(hasItem(DEFAULT_REGISTRO.toString())))
                .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
                .andExpect(jsonPath("$.[*].ejemplo").value(hasItem(DEFAULT_EJEMPLO.toString())))
                .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA.toString())))
                .andExpect(jsonPath("$.[*].popularity").value(hasItem(DEFAULT_POPULARITY.intValue())));
    }

    @Test
    @Transactional
    public void getMottoDefinition() throws Exception {
        // Initialize the database
        mottoDefinitionRepository.saveAndFlush(mottoDefinition);

        // Get the mottoDefinition
        restMottoDefinitionMockMvc.perform(get("/api/motto-definitions/{id}", mottoDefinition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(mottoDefinition.getId().intValue()))
            .andExpect(jsonPath("$.materia").value(DEFAULT_MATERIA.toString()))
            .andExpect(jsonPath("$.registro").value(DEFAULT_REGISTRO.toString()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()))
            .andExpect(jsonPath("$.ejemplo").value(DEFAULT_EJEMPLO.toString()))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA.toString()))
            .andExpect(jsonPath("$.popularity").value(DEFAULT_POPULARITY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMottoDefinition() throws Exception {
        // Get the mottoDefinition
        restMottoDefinitionMockMvc.perform(get("/api/motto-definitions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMottoDefinition() throws Exception {
        // Initialize the database
        mottoDefinitionRepository.saveAndFlush(mottoDefinition);
        int databaseSizeBeforeUpdate = mottoDefinitionRepository.findAll().size();

        // Update the mottoDefinition
        MottoDefinition updatedMottoDefinition = new MottoDefinition();
        updatedMottoDefinition.setId(mottoDefinition.getId());
        updatedMottoDefinition.setMateria(UPDATED_MATERIA);
        updatedMottoDefinition.setRegistro(UPDATED_REGISTRO);
        updatedMottoDefinition.setRegion(UPDATED_REGION);
        updatedMottoDefinition.setEjemplo(UPDATED_EJEMPLO);
        updatedMottoDefinition.setCategoria(UPDATED_CATEGORIA);
        updatedMottoDefinition.setPopularity(UPDATED_POPULARITY);

        restMottoDefinitionMockMvc.perform(put("/api/motto-definitions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedMottoDefinition)))
                .andExpect(status().isOk());

        // Validate the MottoDefinition in the database
        List<MottoDefinition> mottoDefinitions = mottoDefinitionRepository.findAll();
        assertThat(mottoDefinitions).hasSize(databaseSizeBeforeUpdate);
        MottoDefinition testMottoDefinition = mottoDefinitions.get(mottoDefinitions.size() - 1);
        assertThat(testMottoDefinition.getMateria()).isEqualTo(UPDATED_MATERIA);
        assertThat(testMottoDefinition.getRegistro()).isEqualTo(UPDATED_REGISTRO);
        assertThat(testMottoDefinition.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testMottoDefinition.getEjemplo()).isEqualTo(UPDATED_EJEMPLO);
        assertThat(testMottoDefinition.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
        assertThat(testMottoDefinition.getPopularity()).isEqualTo(UPDATED_POPULARITY);
    }

    @Test
    @Transactional
    public void deleteMottoDefinition() throws Exception {
        // Initialize the database
        mottoDefinitionRepository.saveAndFlush(mottoDefinition);
        int databaseSizeBeforeDelete = mottoDefinitionRepository.findAll().size();

        // Get the mottoDefinition
        restMottoDefinitionMockMvc.perform(delete("/api/motto-definitions/{id}", mottoDefinition.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MottoDefinition> mottoDefinitions = mottoDefinitionRepository.findAll();
        assertThat(mottoDefinitions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
