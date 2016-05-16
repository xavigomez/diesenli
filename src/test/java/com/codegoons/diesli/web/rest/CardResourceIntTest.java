package com.codegoons.diesli.web.rest;

import com.codegoons.diesli.DiesliApp;
import com.codegoons.diesli.domain.Card;
import com.codegoons.diesli.repository.CardRepository;

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
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the CardResource REST controller.
 *
 * @see CardResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DiesliApp.class)
@WebAppConfiguration
@IntegrationTest
public class CardResourceIntTest {


    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_CONTENT = "AAAAA";
    private static final String UPDATED_CONTENT = "BBBBB";
    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";
    private static final String DEFAULT_CUSTOM_EXCERPT = "AAAAA";
    private static final String UPDATED_CUSTOM_EXCERPT = "BBBBB";

    private static final byte[] DEFAULT_FEATURED_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FEATURED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FEATURED_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FEATURED_IMAGE_CONTENT_TYPE = "image/png";

    private static final Long DEFAULT_VIEWS = 1L;
    private static final Long UPDATED_VIEWS = 2L;

    private static final Long DEFAULT_POPULARITY = 1L;
    private static final Long UPDATED_POPULARITY = 2L;

    @Inject
    private CardRepository cardRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCardMockMvc;

    private Card card;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CardResource cardResource = new CardResource();
        ReflectionTestUtils.setField(cardResource, "cardRepository", cardRepository);
        this.restCardMockMvc = MockMvcBuilders.standaloneSetup(cardResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        card = new Card();
        card.setDate(DEFAULT_DATE);
        card.setContent(DEFAULT_CONTENT);
        card.setTitle(DEFAULT_TITLE);
        card.setCustomExcerpt(DEFAULT_CUSTOM_EXCERPT);
        card.setFeaturedImage(DEFAULT_FEATURED_IMAGE);
        card.setFeaturedImageContentType(DEFAULT_FEATURED_IMAGE_CONTENT_TYPE);
        card.setViews(DEFAULT_VIEWS);
        card.setPopularity(DEFAULT_POPULARITY);
    }

    @Test
    @Transactional
    public void createCard() throws Exception {
        int databaseSizeBeforeCreate = cardRepository.findAll().size();

        // Create the Card

        restCardMockMvc.perform(post("/api/cards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(card)))
                .andExpect(status().isCreated());

        // Validate the Card in the database
        List<Card> cards = cardRepository.findAll();
        assertThat(cards).hasSize(databaseSizeBeforeCreate + 1);
        Card testCard = cards.get(cards.size() - 1);
        assertThat(testCard.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testCard.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testCard.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCard.getCustomExcerpt()).isEqualTo(DEFAULT_CUSTOM_EXCERPT);
        assertThat(testCard.getFeaturedImage()).isEqualTo(DEFAULT_FEATURED_IMAGE);
        assertThat(testCard.getFeaturedImageContentType()).isEqualTo(DEFAULT_FEATURED_IMAGE_CONTENT_TYPE);
        assertThat(testCard.getViews()).isEqualTo(DEFAULT_VIEWS);
        assertThat(testCard.getPopularity()).isEqualTo(DEFAULT_POPULARITY);
    }

    @Test
    @Transactional
    public void getAllCards() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cards
        restCardMockMvc.perform(get("/api/cards?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(card.getId().intValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
                .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].customExcerpt").value(hasItem(DEFAULT_CUSTOM_EXCERPT.toString())))
                .andExpect(jsonPath("$.[*].featuredImageContentType").value(hasItem(DEFAULT_FEATURED_IMAGE_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].featuredImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_FEATURED_IMAGE))))
                .andExpect(jsonPath("$.[*].views").value(hasItem(DEFAULT_VIEWS.intValue())))
                .andExpect(jsonPath("$.[*].popularity").value(hasItem(DEFAULT_POPULARITY.intValue())));
    }

    @Test
    @Transactional
    public void getCard() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get the card
        restCardMockMvc.perform(get("/api/cards/{id}", card.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(card.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.customExcerpt").value(DEFAULT_CUSTOM_EXCERPT.toString()))
            .andExpect(jsonPath("$.featuredImageContentType").value(DEFAULT_FEATURED_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.featuredImage").value(Base64Utils.encodeToString(DEFAULT_FEATURED_IMAGE)))
            .andExpect(jsonPath("$.views").value(DEFAULT_VIEWS.intValue()))
            .andExpect(jsonPath("$.popularity").value(DEFAULT_POPULARITY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCard() throws Exception {
        // Get the card
        restCardMockMvc.perform(get("/api/cards/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCard() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);
        int databaseSizeBeforeUpdate = cardRepository.findAll().size();

        // Update the card
        Card updatedCard = new Card();
        updatedCard.setId(card.getId());
        updatedCard.setDate(UPDATED_DATE);
        updatedCard.setContent(UPDATED_CONTENT);
        updatedCard.setTitle(UPDATED_TITLE);
        updatedCard.setCustomExcerpt(UPDATED_CUSTOM_EXCERPT);
        updatedCard.setFeaturedImage(UPDATED_FEATURED_IMAGE);
        updatedCard.setFeaturedImageContentType(UPDATED_FEATURED_IMAGE_CONTENT_TYPE);
        updatedCard.setViews(UPDATED_VIEWS);
        updatedCard.setPopularity(UPDATED_POPULARITY);

        restCardMockMvc.perform(put("/api/cards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCard)))
                .andExpect(status().isOk());

        // Validate the Card in the database
        List<Card> cards = cardRepository.findAll();
        assertThat(cards).hasSize(databaseSizeBeforeUpdate);
        Card testCard = cards.get(cards.size() - 1);
        assertThat(testCard.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCard.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testCard.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCard.getCustomExcerpt()).isEqualTo(UPDATED_CUSTOM_EXCERPT);
        assertThat(testCard.getFeaturedImage()).isEqualTo(UPDATED_FEATURED_IMAGE);
        assertThat(testCard.getFeaturedImageContentType()).isEqualTo(UPDATED_FEATURED_IMAGE_CONTENT_TYPE);
        assertThat(testCard.getViews()).isEqualTo(UPDATED_VIEWS);
        assertThat(testCard.getPopularity()).isEqualTo(UPDATED_POPULARITY);
    }

    @Test
    @Transactional
    public void deleteCard() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);
        int databaseSizeBeforeDelete = cardRepository.findAll().size();

        // Get the card
        restCardMockMvc.perform(delete("/api/cards/{id}", card.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Card> cards = cardRepository.findAll();
        assertThat(cards).hasSize(databaseSizeBeforeDelete - 1);
    }
}
