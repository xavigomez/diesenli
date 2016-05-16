package com.codegoons.diesli.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.codegoons.diesli.domain.Motto;
import com.codegoons.diesli.domain.MottoDefinition;
import com.codegoons.diesli.repository.MottoRepository;
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
 * REST controller for managing Motto.
 */
@RestController
@RequestMapping("/api")
public class MottoResource {

    private final Logger log = LoggerFactory.getLogger(MottoResource.class);

    @Inject
    private MottoRepository mottoRepository;

    /**
     * POST  /mottos : Create a new motto.
     *
     * @param motto the motto to create
     * @return the ResponseEntity with status 201 (Created) and with body the new motto, or with status 400 (Bad Request) if the motto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/mottos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Motto> createMotto(@RequestBody Motto motto) throws URISyntaxException {
        log.debug("REST request to save Motto : {}", motto);
        if (motto.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("motto", "idexists", "A new motto cannot already have an ID")).body(null);
        }
        Motto result = mottoRepository.save(motto);
        return ResponseEntity.created(new URI("/api/mottos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("motto", result.getId().toString()))
            .body(result);
    }




    /**
     * PUT  /mottos : Updates an existing motto.
     *
     * @param motto the motto to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated motto,
     * or with status 400 (Bad Request) if the motto is not valid,
     * or with status 500 (Internal Server Error) if the motto couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/mottos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Motto> updateMotto(@RequestBody Motto motto) throws URISyntaxException {
        log.debug("REST request to update Motto : {}", motto);
        if (motto.getId() == null) {
            return createMotto(motto);
        }
        Motto result = mottoRepository.save(motto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("motto", motto.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mottos : get all the mottos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of mottos in body
     */
    @RequestMapping(value = "/mottos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Motto> getAllMottos() {
        log.debug("REST request to get all Mottos");
        List<Motto> mottos = mottoRepository.findAll();
        return mottos;
    }

    /**
     * GET  /mottos/:id : get the "id" motto.
     *
     * @param id the id of the motto to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the motto, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/mottos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Motto> getMotto(@PathVariable Long id) {
        log.debug("REST request to get Motto : {}", id);
        Motto motto = mottoRepository.findOne(id);
        return Optional.ofNullable(motto)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

        /*
    * GET  /motto-definitions/:searchTerm : get the "searchTerm" mottoDefinition.
    * @param id the id of the mottoDefinition to retrieve
    * @return the ResponseEntity with status 200 (OK) and with body the mottoDefinition, or with status 404 (Not Found)
    */



    @RequestMapping(value = "/motto/search/{searchTerm}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Motto>> searchTerm(@PathVariable String searchTerm) {
        log.debug("REST request to get Motto which contains : {}", searchTerm);
        List<Motto> motto = mottoRepository.searchTerm(searchTerm);
        return Optional.ofNullable(motto)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /mottos/:id : delete the "id" motto.
     *
     * @param id the id of the motto to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/mottos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMotto(@PathVariable Long id) {
        log.debug("REST request to delete Motto : {}", id);
        mottoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("motto", id.toString())).build();
    }

}
