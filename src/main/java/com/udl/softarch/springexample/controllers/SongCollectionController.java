package com.udl.softarch.springexample.controllers;

import com.google.common.base.Preconditions;
import com.udl.softarch.springexample.models.SongCollection;
import com.udl.softarch.springexample.repositories.SongCollectionRepository;
import com.udl.softarch.springexample.services.UserSongCollectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;

/**
 * Created by debian-jordi on 4/05/15.
 */
@Controller
@RequestMapping(value = "/songCollection")
public class SongCollectionController {
    final Logger logger = LoggerFactory.getLogger(SongCollectionController.class);

    @Autowired
    SongCollectionRepository songCollectionRepository;
    @Autowired
    UserSongCollectionService userSongCollectionService;
    // LIST
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Iterable<SongCollection> list(@RequestParam(required=false, defaultValue="0") int page,
                                    @RequestParam(required=false, defaultValue="10") int size) {
        PageRequest request = new PageRequest(page, size);
        System.out.println(songCollectionRepository.findAll(request).getContent());
        return songCollectionRepository.findAll(request).getContent();
    }
    @RequestMapping(method = RequestMethod.GET, produces = "text/html")
    public ModelAndView listHTML(@RequestParam(required=false, defaultValue="0") int page,
                                 @RequestParam(required=false, defaultValue="10") int size) {
        return new ModelAndView("SongCollectionList", "SongCollection", list(page, size));
    }

    // RETRIEVE
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SongCollection retrieve(@PathVariable("id") Long id) {
        logger.info("Retrieving Song list number {}", id);
        Preconditions.checkNotNull(songCollectionRepository.findOne(id), "ListCollection with id %s not found", id);
        return songCollectionRepository.findOne(id);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "text/html")
    public ModelAndView retrieveHTML(@PathVariable( "id" ) Long id) {
        return new ModelAndView("SongCollectionView", "SongCollection", retrieve(id));
    }

    // CREATE
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public SongCollection create(@Valid @RequestBody SongCollection songCollection, HttpServletResponse response) {
        logger.info("Creating song colletion with content'{}'", songCollection.getName());
        SongCollection newSongCollection = userSongCollectionService.addSongCollectionToUser(songCollection);
        songCollectionRepository.save(newSongCollection);
        response.setHeader("Location", "/songCollection/" + newSongCollection.getId());
        return newSongCollection;
    }
    @RequestMapping(method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded", produces="text/html")
    public String createHTML(@Valid @ModelAttribute("SongCollection") SongCollection songCollection, BindingResult binding, HttpServletResponse response) {
        if (binding.hasErrors()) {
            logger.info("Validation error: {}", binding);
            return "SongCollectionForm";
        }
        return "redirect:/songCollection/"+create(songCollection, response).getId();
    }
    // Create form
    @RequestMapping(value = "/form", method = RequestMethod.GET, produces = "text/html")
    public ModelAndView createForm() {
        logger.info("Generating form for Song list creation");
        SongCollection emptyListSongs = new SongCollection();
        return new ModelAndView("SongCollectionForm", "SongCollection", emptyListSongs);
    }

    // UPDATE
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public SongCollection update(@PathVariable("id") Long id, @Valid @RequestBody SongCollection songCollection) {
        logger.info("Updating song lists {}, new content is '{}'", id, songCollection.getName());
        Preconditions.checkNotNull(songCollectionRepository.findOne(id), "SongColletcion with id %s not found", id);

        return userSongCollectionService.updateSongCollectionFromUser(songCollection, id);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/x-www-form-urlencoded")
    @ResponseStatus(HttpStatus.OK)
    public String updateHTML(@PathVariable("id") Long id, @Valid @ModelAttribute("listSong") SongCollection songCollection,
                             BindingResult binding) {
        if (binding.hasErrors()) {
            logger.info("Validation error: {}", binding);
            return "SongCollectionForm";
        }
        return "redirect:/songCollection/"+update(id, songCollection).getId();
    }
    // Update form
    // TODO Url isn't good CKECK
    @RequestMapping(value = "/{id}/form", method = RequestMethod.GET, produces = "text/html")
    public ModelAndView updateForm(@PathVariable("id") Long id) {
        logger.info("Generating form for updating Song Collection number {}", id);
        Preconditions.checkNotNull(songCollectionRepository.findOne(id), "Song Collection with id %s not found", id);
        return new ModelAndView("form", "songCollection", songCollectionRepository.findOne(id));
    }

    // DELETE
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        logger.info("Deleting song collection number {}", id);
        Preconditions.checkNotNull(songCollectionRepository.findOne(id), "Song Collection with id %s not found", id);
        userSongCollectionService.removeSongCollectionFromUser(id);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    @ResponseStatus(HttpStatus.OK)
    public String deleteHTML(@PathVariable("id") Long id) {
        delete(id);
        return "redirect:/songCollection";
    }
}
