package com.codegoons.diesli.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.codegoons.diesli.domain.MottoFav;
import com.codegoons.diesli.repository.MottoFavRepository;
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
 * REST controller for managing MottoFav.
 */
@RestController
@RequestMapping("/api")
public class MottoFavResource {

    private final Logger log = LoggerFactory.getLogger(MottoFavResource.class);
        
    @Inject
    private MottoFavRepository mottoFavRepository;
    
    /**
     * POST  /motto-favs : Create a new mottoFav.
     *
     * @param mottoFav the mottoFav to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mottoFav, or with status 400 (Bad Request) if the mottoFav has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/motto-favs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MottoFav> createMottoFav(@RequestBody MottoFav mottoFav) throws URISyntaxException {
        log.debug("REST request to save MottoFav : {}", mottoFav);
        if (mottoFav.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("mottoFav", "idexists", "A new mottoFav cannot already have an ID")).body(null);
        }
        MottoFav result = mottoFavRepository.save(mottoFav);
        return ResponseEntity.created(new URI("/api/motto-favs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("mottoFav", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /motto-favs : Updates an existing mottoFav.
     *
     * @param mottoFav the mottoFav to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mottoFav,
     * or with status 400 (Bad Request) if the mottoFav is not valid,
     * or with status 500 (Internal Server Error) if the mottoFav couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/motto-favs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MottoFav> updateMottoFav(@RequestBody MottoFav mottoFav) throws URISyntaxException {
        log.debug("REST request to update MottoFav : {}", mottoFav);
        if (mottoFav.getId() == null) {
            return createMottoFav(mottoFav);
        }
        MottoFav result = mottoFavRepository.save(mottoFav);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("mottoFav", mottoFav.getId().toString()))
            .body(result);
    }

    /**
     * GET  /motto-favs : get all the mottoFavs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of mottoFavs in body
     */
    @RequestMapping(value = "/motto-favs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MottoFav> getAllMottoFavs() {
        log.debug("REST request to get all MottoFavs");
        List<MottoFav> mottoFavs = mottoFavRepository.findAll();
        return mottoFavs;
    }

    /**
     * GET  /motto-favs/:id : get the "id" mottoFav.
     *
     * @param id the id of the mottoFav to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mottoFav, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/motto-favs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MottoFav> getMottoFav(@PathVariable Long id) {
        log.debug("REST request to get MottoFav : {}", id);
        MottoFav mottoFav = mottoFavRepository.findOne(id);
        return Optional.ofNullable(mottoFav)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /motto-favs/:id : delete the "id" mottoFav.
     *
     * @param id the id of the mottoFav to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/motto-favs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMottoFav(@PathVariable Long id) {
        log.debug("REST request to delete MottoFav : {}", id);
        mottoFavRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("mottoFav", id.toString())).build();
    }

}
