package com.codegoons.diesli.web.rest;

import com.codegoons.diesli.DiesliApp;
import com.codegoons.diesli.domain.CardReply;
import com.codegoons.diesli.repository.CardReplyRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the CardReplyResource REST controller.
 *
 * @see CardReplyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DiesliApp.class)
@WebAppConfiguration
@IntegrationTest
public class CardReplyResourceIntTest {


    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_CONTENT = "AAAAA";
    private static final String UPDATED_CONTENT = "BBBBB";

    private static final Long DEFAULT_POPULARITY = 1L;
    private static final Long UPDATED_POPULARITY = 2L;

    @Inject
    private CardReplyRepository cardReplyRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCardReplyMockMvc;

    private CardReply cardReply;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CardReplyResource cardReplyResource = new CardReplyResource();
        ReflectionTestUtils.setField(cardReplyResource, "cardReplyRepository", cardReplyRepository);
        this.restCardReplyMockMvc = MockMvcBuilders.standaloneSetup(cardReplyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cardReply = new CardReply();
        cardReply.setDate(DEFAULT_DATE);
        cardReply.setContent(DEFAULT_CONTENT);
        cardReply.setPopularity(DEFAULT_POPULARITY);
    }

    @Test
    @Transactional
    public void createCardReply() throws Exception {
        int databaseSizeBeforeCreate = cardReplyRepository.findAll().size();

        // Create the CardReply

        restCardReplyMockMvc.perform(post("/api/card-replies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cardReply)))
                .andExpect(status().isCreated());

        // Validate the CardReply in the database
        List<CardReply> cardReplies = cardReplyRepository.findAll();
        assertThat(cardReplies).hasSize(databaseSizeBeforeCreate + 1);
        CardReply testCardReply = cardReplies.get(cardReplies.size() - 1);
        assertThat(testCardReply.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testCardReply.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testCardReply.getPopularity()).isEqualTo(DEFAULT_POPULARITY);
    }

    @Test
    @Transactional
    public void getAllCardReplies() throws Exception {
        // Initialize the database
        cardReplyRepository.saveAndFlush(cardReply);

        // Get all the cardReplies
        restCardReplyMockMvc.perform(get("/api/card-replies?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cardReply.getId().intValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
                .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
                .andExpect(jsonPath("$.[*].popularity").value(hasItem(DEFAULT_POPULARITY.intValue())));
    }

    @Test
    @Transactional
    public void getCardReply() throws Exception {
        // Initialize the database
        cardReplyRepository.saveAndFlush(cardReply);

        // Get the cardReply
        restCardReplyMockMvc.perform(get("/api/card-replies/{id}", cardReply.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cardReply.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.popularity").value(DEFAULT_POPULARITY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCardReply() throws Exception {
        // Get the cardReply
        restCardReplyMockMvc.perform(get("/api/card-replies/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCardReply() throws Exception {
        // Initialize the database
        cardReplyRepository.saveAndFlush(cardReply);
        int databaseSizeBeforeUpdate = cardReplyRepository.findAll().size();

        // Update the cardReply
        CardReply updatedCardReply = new CardReply();
        updatedCardReply.setId(cardReply.getId());
        updatedCardReply.setDate(UPDATED_DATE);
        updatedCardReply.setContent(UPDATED_CONTENT);
        updatedCardReply.setPopularity(UPDATED_POPULARITY);

        restCardReplyMockMvc.perform(put("/api/card-replies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCardReply)))
                .andExpect(status().isOk());

        // Validate the CardReply in the database
        List<CardReply> cardReplies = cardReplyRepository.findAll();
        assertThat(cardReplies).hasSize(databaseSizeBeforeUpdate);
        CardReply testCardReply = cardReplies.get(cardReplies.size() - 1);
        assertThat(testCardReply.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCardReply.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testCardReply.getPopularity()).isEqualTo(UPDATED_POPULARITY);
    }

    @Test
    @Transactional
    public void deleteCardReply() throws Exception {
        // Initialize the database
        cardReplyRepository.saveAndFlush(cardReply);
        int databaseSizeBeforeDelete = cardReplyRepository.findAll().size();

        // Get the cardReply
        restCardReplyMockMvc.perform(delete("/api/card-replies/{id}", cardReply.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CardReply> cardReplies = cardReplyRepository.findAll();
        assertThat(cardReplies).hasSize(databaseSizeBeforeDelete - 1);
    }
}
