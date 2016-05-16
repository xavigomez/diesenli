package com.codegoons.diesli.web.rest;

import com.codegoons.diesli.DiesliApp;
import com.codegoons.diesli.domain.Board;
import com.codegoons.diesli.repository.BoardRepository;

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
 * Test class for the BoardResource REST controller.
 *
 * @see BoardResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DiesliApp.class)
@WebAppConfiguration
@IntegrationTest
public class BoardResourceIntTest {

    private static final String DEFAULT_BOARD_NAME = "AAAAA";
    private static final String UPDATED_BOARD_NAME = "BBBBB";

    @Inject
    private BoardRepository boardRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBoardMockMvc;

    private Board board;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BoardResource boardResource = new BoardResource();
        ReflectionTestUtils.setField(boardResource, "boardRepository", boardRepository);
        this.restBoardMockMvc = MockMvcBuilders.standaloneSetup(boardResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        board = new Board();
        board.setBoardName(DEFAULT_BOARD_NAME);
    }

    @Test
    @Transactional
    public void createBoard() throws Exception {
        int databaseSizeBeforeCreate = boardRepository.findAll().size();

        // Create the Board

        restBoardMockMvc.perform(post("/api/boards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(board)))
                .andExpect(status().isCreated());

        // Validate the Board in the database
        List<Board> boards = boardRepository.findAll();
        assertThat(boards).hasSize(databaseSizeBeforeCreate + 1);
        Board testBoard = boards.get(boards.size() - 1);
        assertThat(testBoard.getBoardName()).isEqualTo(DEFAULT_BOARD_NAME);
    }

    @Test
    @Transactional
    public void getAllBoards() throws Exception {
        // Initialize the database
        boardRepository.saveAndFlush(board);

        // Get all the boards
        restBoardMockMvc.perform(get("/api/boards?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(board.getId().intValue())))
                .andExpect(jsonPath("$.[*].boardName").value(hasItem(DEFAULT_BOARD_NAME.toString())));
    }

    @Test
    @Transactional
    public void getBoard() throws Exception {
        // Initialize the database
        boardRepository.saveAndFlush(board);

        // Get the board
        restBoardMockMvc.perform(get("/api/boards/{id}", board.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(board.getId().intValue()))
            .andExpect(jsonPath("$.boardName").value(DEFAULT_BOARD_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBoard() throws Exception {
        // Get the board
        restBoardMockMvc.perform(get("/api/boards/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBoard() throws Exception {
        // Initialize the database
        boardRepository.saveAndFlush(board);
        int databaseSizeBeforeUpdate = boardRepository.findAll().size();

        // Update the board
        Board updatedBoard = new Board();
        updatedBoard.setId(board.getId());
        updatedBoard.setBoardName(UPDATED_BOARD_NAME);

        restBoardMockMvc.perform(put("/api/boards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedBoard)))
                .andExpect(status().isOk());

        // Validate the Board in the database
        List<Board> boards = boardRepository.findAll();
        assertThat(boards).hasSize(databaseSizeBeforeUpdate);
        Board testBoard = boards.get(boards.size() - 1);
        assertThat(testBoard.getBoardName()).isEqualTo(UPDATED_BOARD_NAME);
    }

    @Test
    @Transactional
    public void deleteBoard() throws Exception {
        // Initialize the database
        boardRepository.saveAndFlush(board);
        int databaseSizeBeforeDelete = boardRepository.findAll().size();

        // Get the board
        restBoardMockMvc.perform(delete("/api/boards/{id}", board.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Board> boards = boardRepository.findAll();
        assertThat(boards).hasSize(databaseSizeBeforeDelete - 1);
    }
}
