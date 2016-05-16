package com.codegoons.diesli.web.rest;

import com.codegoons.diesli.DiesliApp;
import com.codegoons.diesli.domain.Definition;
import com.codegoons.diesli.repository.DefinitionRepository;

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
 * Test class for the DefinitionResource REST controller.
 *
 * @see DefinitionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DiesliApp.class)
@WebAppConfiguration
@IntegrationTest
public class DefinitionResourceIntTest {

    private static final String DEFAULT_DEFINITION = "AAAAA";
    private static final String UPDATED_DEFINITION = "BBBBB";

    @Inject
    private DefinitionRepository definitionRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDefinitionMockMvc;

    private Definition definition;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DefinitionResource definitionResource = new DefinitionResource();
        ReflectionTestUtils.setField(definitionResource, "definitionRepository", definitionRepository);
        this.restDefinitionMockMvc = MockMvcBuilders.standaloneSetup(definitionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        definition = new Definition();
        definition.setDefinition(DEFAULT_DEFINITION);
    }

    @Test
    @Transactional
    public void createDefinition() throws Exception {
        int databaseSizeBeforeCreate = definitionRepository.findAll().size();

        // Create the Definition

        restDefinitionMockMvc.perform(post("/api/definitions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(definition)))
                .andExpect(status().isCreated());

        // Validate the Definition in the database
        List<Definition> definitions = definitionRepository.findAll();
        assertThat(definitions).hasSize(databaseSizeBeforeCreate + 1);
        Definition testDefinition = definitions.get(definitions.size() - 1);
        assertThat(testDefinition.getDefinition()).isEqualTo(DEFAULT_DEFINITION);
    }

    @Test
    @Transactional
    public void getAllDefinitions() throws Exception {
        // Initialize the database
        definitionRepository.saveAndFlush(definition);

        // Get all the definitions
        restDefinitionMockMvc.perform(get("/api/definitions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(definition.getId().intValue())))
                .andExpect(jsonPath("$.[*].definition").value(hasItem(DEFAULT_DEFINITION.toString())));
    }

    @Test
    @Transactional
    public void getDefinition() throws Exception {
        // Initialize the database
        definitionRepository.saveAndFlush(definition);

        // Get the definition
        restDefinitionMockMvc.perform(get("/api/definitions/{id}", definition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(definition.getId().intValue()))
            .andExpect(jsonPath("$.definition").value(DEFAULT_DEFINITION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDefinition() throws Exception {
        // Get the definition
        restDefinitionMockMvc.perform(get("/api/definitions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDefinition() throws Exception {
        // Initialize the database
        definitionRepository.saveAndFlush(definition);
        int databaseSizeBeforeUpdate = definitionRepository.findAll().size();

        // Update the definition
        Definition updatedDefinition = new Definition();
        updatedDefinition.setId(definition.getId());
        updatedDefinition.setDefinition(UPDATED_DEFINITION);

        restDefinitionMockMvc.perform(put("/api/definitions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedDefinition)))
                .andExpect(status().isOk());

        // Validate the Definition in the database
        List<Definition> definitions = definitionRepository.findAll();
        assertThat(definitions).hasSize(databaseSizeBeforeUpdate);
        Definition testDefinition = definitions.get(definitions.size() - 1);
        assertThat(testDefinition.getDefinition()).isEqualTo(UPDATED_DEFINITION);
    }

    @Test
    @Transactional
    public void deleteDefinition() throws Exception {
        // Initialize the database
        definitionRepository.saveAndFlush(definition);
        int databaseSizeBeforeDelete = definitionRepository.findAll().size();

        // Get the definition
        restDefinitionMockMvc.perform(delete("/api/definitions/{id}", definition.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Definition> definitions = definitionRepository.findAll();
        assertThat(definitions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
