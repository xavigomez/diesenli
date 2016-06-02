package com.codegoons.diesli.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.codegoons.diesli.domain.Motto;
import com.codegoons.diesli.domain.MottoDefinition;
import com.codegoons.diesli.repository.MottoDefinitionRepository;
import com.codegoons.diesli.repository.MottoDefinitionCriteriaRepository;
import com.codegoons.diesli.web.rest.util.HeaderUtil;
import com.codegoons.diesli.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing MottoDefinition.
 */
@RestController
@RequestMapping("/api")
public class MottoDefinitionResource {

    private final Logger log = LoggerFactory.getLogger(MottoDefinitionResource.class);

    @Inject
    private MottoDefinitionRepository mottoDefinitionRepository;
    @Inject
    private MottoDefinitionCriteriaRepository mottoDefinitionCriteriaRepository;

    /**
     * POST  /motto-definitions : Create a new mottoDefinition.
     *
     * @param mottoDefinition the mottoDefinition to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mottoDefinition, or with status 400 (Bad Request) if the mottoDefinition has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/motto-definitions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MottoDefinition> createMottoDefinition(@RequestBody MottoDefinition mottoDefinition) throws URISyntaxException {
        log.debug("REST request to save MottoDefinition : {}", mottoDefinition);
        if (mottoDefinition.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("mottoDefinition", "idexists", "A new mottoDefinition cannot already have an ID")).body(null);
        }
        MottoDefinition result = mottoDefinitionRepository.save(mottoDefinition);
        return ResponseEntity.created(new URI("/api/motto-definitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("mottoDefinition", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /motto-definitions : Updates an existing mottoDefinition.
     *
     * @param mottoDefinition the mottoDefinition to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mottoDefinition,
     * or with status 400 (Bad Request) if the mottoDefinition is not valid,
     * or with status 500 (Internal Server Error) if the mottoDefinition couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/motto-definitions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MottoDefinition> updateMottoDefinition(@RequestBody MottoDefinition mottoDefinition) throws URISyntaxException {
        log.debug("REST request to update MottoDefinition : {}", mottoDefinition);
        if (mottoDefinition.getId() == null) {
            return createMottoDefinition(mottoDefinition);
        }
        MottoDefinition result = mottoDefinitionRepository.save(mottoDefinition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("mottoDefinition", mottoDefinition.getId().toString()))
            .body(result);
    }

    /**
     * GET  /motto-definitions : get all the mottoDefinitions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of mottoDefinitions in body
     */
    @RequestMapping(value = "/motto-definitions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MottoDefinition> getAllMottoDefinitions() {
        log.debug("REST request to get all MottoDefinitions");
        List<MottoDefinition> mottoDefinitions = mottoDefinitionRepository.findAll();
        return mottoDefinitions;
    }

    /**
     * GET  /motto-definitions/:id : get the "id" mottoDefinition.
     *
     * @param id the id of the mottoDefinition to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mottoDefinition, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/motto-definitions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MottoDefinition> getMottoDefinition(@PathVariable Long id) {
        log.debug("REST request to get MottoDefinition : {}", id);
        MottoDefinition mottoDefinition = mottoDefinitionRepository.findOne(id);
        return Optional.ofNullable(mottoDefinition)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /motto-definitions/:selectedMottoId : get the "selectedMottoId" mottoDefinition.
     *
     * @param selectedMottoId the id of the mottoDefinition to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mottoDefinition, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/motto-definitions/search/{selectedMottoId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<MottoDefinition>> selectedMottoId(@PathVariable Long selectedMottoId) {
        log.debug("REST request to get MottoDefinition which id is : {}", selectedMottoId);
        List<MottoDefinition> mottoDefinitions = mottoDefinitionRepository.selectedMottoId(selectedMottoId);
        return Optional.ofNullable(mottoDefinitions)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    

    /**
     * DELETE  /motto-definitions/:id : delete the "id" mottoDefinition.
     *
     * @param id the id of the mottoDefinition to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/motto-definitions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMottoDefinition(@PathVariable Long id) {
        log.debug("REST request to delete MottoDefinition : {}", id);
        mottoDefinitionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("mottoDefinition", id.toString())).build();
    }

}
