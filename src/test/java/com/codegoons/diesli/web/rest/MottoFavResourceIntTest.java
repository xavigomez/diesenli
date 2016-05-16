package com.codegoons.diesli.web.rest;

import com.codegoons.diesli.DiesliApp;
import com.codegoons.diesli.domain.MottoFav;
import com.codegoons.diesli.repository.MottoFavRepository;

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
 * Test class for the MottoFavResource REST controller.
 *
 * @see MottoFavResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DiesliApp.class)
@WebAppConfiguration
@IntegrationTest
public class MottoFavResourceIntTest {


    @Inject
    private MottoFavRepository mottoFavRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMottoFavMockMvc;

    private MottoFav mottoFav;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MottoFavResource mottoFavResource = new MottoFavResource();
        ReflectionTestUtils.setField(mottoFavResource, "mottoFavRepository", mottoFavRepository);
        this.restMottoFavMockMvc = MockMvcBuilders.standaloneSetup(mottoFavResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        mottoFav = new MottoFav();
    }

    @Test
    @Transactional
    public void createMottoFav() throws Exception {
        int databaseSizeBeforeCreate = mottoFavRepository.findAll().size();

        // Create the MottoFav

        restMottoFavMockMvc.perform(post("/api/motto-favs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mottoFav)))
                .andExpect(status().isCreated());

        // Validate the MottoFav in the database
        List<MottoFav> mottoFavs = mottoFavRepository.findAll();
        assertThat(mottoFavs).hasSize(databaseSizeBeforeCreate + 1);
        MottoFav testMottoFav = mottoFavs.get(mottoFavs.size() - 1);
    }

    @Test
    @Transactional
    public void getAllMottoFavs() throws Exception {
        // Initialize the database
        mottoFavRepository.saveAndFlush(mottoFav);

        // Get all the mottoFavs
        restMottoFavMockMvc.perform(get("/api/motto-favs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(mottoFav.getId().intValue())));
    }

    @Test
    @Transactional
    public void getMottoFav() throws Exception {
        // Initialize the database
        mottoFavRepository.saveAndFlush(mottoFav);

        // Get the mottoFav
        restMottoFavMockMvc.perform(get("/api/motto-favs/{id}", mottoFav.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(mottoFav.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMottoFav() throws Exception {
        // Get the mottoFav
        restMottoFavMockMvc.perform(get("/api/motto-favs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMottoFav() throws Exception {
        // Initialize the database
        mottoFavRepository.saveAndFlush(mottoFav);
        int databaseSizeBeforeUpdate = mottoFavRepository.findAll().size();

        // Update the mottoFav
        MottoFav updatedMottoFav = new MottoFav();
        updatedMottoFav.setId(mottoFav.getId());

        restMottoFavMockMvc.perform(put("/api/motto-favs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedMottoFav)))
                .andExpect(status().isOk());

        // Validate the MottoFav in the database
        List<MottoFav> mottoFavs = mottoFavRepository.findAll();
        assertThat(mottoFavs).hasSize(databaseSizeBeforeUpdate);
        MottoFav testMottoFav = mottoFavs.get(mottoFavs.size() - 1);
    }

    @Test
    @Transactional
    public void deleteMottoFav() throws Exception {
        // Initialize the database
        mottoFavRepository.saveAndFlush(mottoFav);
        int databaseSizeBeforeDelete = mottoFavRepository.findAll().size();

        // Get the mottoFav
        restMottoFavMockMvc.perform(delete("/api/motto-favs/{id}", mottoFav.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MottoFav> mottoFavs = mottoFavRepository.findAll();
        assertThat(mottoFavs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
