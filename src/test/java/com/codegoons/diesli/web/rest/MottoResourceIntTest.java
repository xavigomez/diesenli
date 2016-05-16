package com.codegoons.diesli.web.rest;

import com.codegoons.diesli.DiesliApp;
import com.codegoons.diesli.domain.Motto;
import com.codegoons.diesli.repository.MottoRepository;

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
 * Test class for the MottoResource REST controller.
 *
 * @see MottoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DiesliApp.class)
@WebAppConfiguration
@IntegrationTest
public class MottoResourceIntTest {

    private static final String DEFAULT_INFORMACION_ETIMOLOGICA = "AAAAA";
    private static final String UPDATED_INFORMACION_ETIMOLOGICA = "BBBBB";
    private static final String DEFAULT_NOMBRE = "AAAAA";
    private static final String UPDATED_NOMBRE = "BBBBB";

    @Inject
    private MottoRepository mottoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMottoMockMvc;

    private Motto motto;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MottoResource mottoResource = new MottoResource();
        ReflectionTestUtils.setField(mottoResource, "mottoRepository", mottoRepository);
        this.restMottoMockMvc = MockMvcBuilders.standaloneSetup(mottoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        motto = new Motto();
        motto.setInformacionEtimologica(DEFAULT_INFORMACION_ETIMOLOGICA);
        motto.setNombre(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createMotto() throws Exception {
        int databaseSizeBeforeCreate = mottoRepository.findAll().size();

        // Create the Motto

        restMottoMockMvc.perform(post("/api/mottos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(motto)))
                .andExpect(status().isCreated());

        // Validate the Motto in the database
        List<Motto> mottos = mottoRepository.findAll();
        assertThat(mottos).hasSize(databaseSizeBeforeCreate + 1);
        Motto testMotto = mottos.get(mottos.size() - 1);
        assertThat(testMotto.getInformacionEtimologica()).isEqualTo(DEFAULT_INFORMACION_ETIMOLOGICA);
        assertThat(testMotto.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllMottos() throws Exception {
        // Initialize the database
        mottoRepository.saveAndFlush(motto);

        // Get all the mottos
        restMottoMockMvc.perform(get("/api/mottos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(motto.getId().intValue())))
                .andExpect(jsonPath("$.[*].informacionEtimologica").value(hasItem(DEFAULT_INFORMACION_ETIMOLOGICA.toString())))
                .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    @Test
    @Transactional
    public void getMotto() throws Exception {
        // Initialize the database
        mottoRepository.saveAndFlush(motto);

        // Get the motto
        restMottoMockMvc.perform(get("/api/mottos/{id}", motto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(motto.getId().intValue()))
            .andExpect(jsonPath("$.informacionEtimologica").value(DEFAULT_INFORMACION_ETIMOLOGICA.toString()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMotto() throws Exception {
        // Get the motto
        restMottoMockMvc.perform(get("/api/mottos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMotto() throws Exception {
        // Initialize the database
        mottoRepository.saveAndFlush(motto);
        int databaseSizeBeforeUpdate = mottoRepository.findAll().size();

        // Update the motto
        Motto updatedMotto = new Motto();
        updatedMotto.setId(motto.getId());
        updatedMotto.setInformacionEtimologica(UPDATED_INFORMACION_ETIMOLOGICA);
        updatedMotto.setNombre(UPDATED_NOMBRE);

        restMottoMockMvc.perform(put("/api/mottos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedMotto)))
                .andExpect(status().isOk());

        // Validate the Motto in the database
        List<Motto> mottos = mottoRepository.findAll();
        assertThat(mottos).hasSize(databaseSizeBeforeUpdate);
        Motto testMotto = mottos.get(mottos.size() - 1);
        assertThat(testMotto.getInformacionEtimologica()).isEqualTo(UPDATED_INFORMACION_ETIMOLOGICA);
        assertThat(testMotto.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void deleteMotto() throws Exception {
        // Initialize the database
        mottoRepository.saveAndFlush(motto);
        int databaseSizeBeforeDelete = mottoRepository.findAll().size();

        // Get the motto
        restMottoMockMvc.perform(delete("/api/mottos/{id}", motto.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Motto> mottos = mottoRepository.findAll();
        assertThat(mottos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
