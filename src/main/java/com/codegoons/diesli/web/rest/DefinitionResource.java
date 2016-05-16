package com.codegoons.diesli.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.codegoons.diesli.domain.Definition;
import com.codegoons.diesli.repository.DefinitionRepository;
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
 * REST controller for managing Definition.
 */
@RestController
@RequestMapping("/api")
public class DefinitionResource {

    private final Logger log = LoggerFactory.getLogger(DefinitionResource.class);
        
    @Inject
    private DefinitionRepository definitionRepository;
    
    /**
     * POST  /definitions : Create a new definition.
     *
     * @param definition the definition to create
     * @return the ResponseEntity with status 201 (Created) and with body the new definition, or with status 400 (Bad Request) if the definition has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/definitions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Definition> createDefinition(@RequestBody Definition definition) throws URISyntaxException {
        log.debug("REST request to save Definition : {}", definition);
        if (definition.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("definition", "idexists", "A new definition cannot already have an ID")).body(null);
        }
        Definition result = definitionRepository.save(definition);
        return ResponseEntity.created(new URI("/api/definitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("definition", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /definitions : Updates an existing definition.
     *
     * @param definition the definition to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated definition,
     * or with status 400 (Bad Request) if the definition is not valid,
     * or with status 500 (Internal Server Error) if the definition couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/definitions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Definition> updateDefinition(@RequestBody Definition definition) throws URISyntaxException {
        log.debug("REST request to update Definition : {}", definition);
        if (definition.getId() == null) {
            return createDefinition(definition);
        }
        Definition result = definitionRepository.save(definition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("definition", definition.getId().toString()))
            .body(result);
    }

    /**
     * GET  /definitions : get all the definitions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of definitions in body
     */
    @RequestMapping(value = "/definitions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Definition> getAllDefinitions() {
        log.debug("REST request to get all Definitions");
        List<Definition> definitions = definitionRepository.findAll();
        return definitions;
    }

    /**
     * GET  /definitions/:id : get the "id" definition.
     *
     * @param id the id of the definition to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the definition, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/definitions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Definition> getDefinition(@PathVariable Long id) {
        log.debug("REST request to get Definition : {}", id);
        Definition definition = definitionRepository.findOne(id);
        return Optional.ofNullable(definition)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /definitions/:id : delete the "id" definition.
     *
     * @param id the id of the definition to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/definitions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDefinition(@PathVariable Long id) {
        log.debug("REST request to delete Definition : {}", id);
        definitionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("definition", id.toString())).build();
    }

}
