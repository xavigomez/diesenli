package com.codegoons.diesli.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.codegoons.diesli.domain.CardReply;
import com.codegoons.diesli.repository.CardReplyRepository;
import com.codegoons.diesli.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CardReply.
 */
@RestController
@RequestMapping("/api")
public class CardReplyResource {

    private final Logger log = LoggerFactory.getLogger(CardReplyResource.class);
        
    @Inject
    private CardReplyRepository cardReplyRepository;
    
    /**
     * POST  /card-replies : Create a new cardReply.
     *
     * @param cardReply the cardReply to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cardReply, or with status 400 (Bad Request) if the cardReply has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/card-replies",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CardReply> createCardReply(@RequestBody CardReply cardReply) throws URISyntaxException {
        log.debug("REST request to save CardReply : {}", cardReply);
        if (cardReply.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cardReply", "idexists", "A new cardReply cannot already have an ID")).body(null);
        }
        CardReply result = cardReplyRepository.save(cardReply);
        return ResponseEntity.created(new URI("/api/card-replies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cardReply", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /card-replies : Updates an existing cardReply.
     *
     * @param cardReply the cardReply to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cardReply,
     * or with status 400 (Bad Request) if the cardReply is not valid,
     * or with status 500 (Internal Server Error) if the cardReply couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/card-replies",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CardReply> updateCardReply(@RequestBody CardReply cardReply) throws URISyntaxException {
        log.debug("REST request to update CardReply : {}", cardReply);
        if (cardReply.getId() == null) {
            return createCardReply(cardReply);
        }
        CardReply result = cardReplyRepository.save(cardReply);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cardReply", cardReply.getId().toString()))
            .body(result);
    }

    /**
     * GET  /card-replies : get all the cardReplies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cardReplies in body
     */
    @RequestMapping(value = "/card-replies",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CardReply> getAllCardReplies() {
        log.debug("REST request to get all CardReplies");
        List<CardReply> cardReplies = cardReplyRepository.findAll();
        return cardReplies;
    }

    /**
     * GET  /card-replies/:id : get the "id" cardReply.
     *
     * @param id the id of the cardReply to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cardReply, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/card-replies/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CardReply> getCardReply(@PathVariable Long id) {
        log.debug("REST request to get CardReply : {}", id);
        CardReply cardReply = cardReplyRepository.findOne(id);
        return Optional.ofNullable(cardReply)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /card-replies/:id : delete the "id" cardReply.
     *
     * @param id the id of the cardReply to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/card-replies/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCardReply(@PathVariable Long id) {
        log.debug("REST request to delete CardReply : {}", id);
        cardReplyRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cardReply", id.toString())).build();
    }

}
