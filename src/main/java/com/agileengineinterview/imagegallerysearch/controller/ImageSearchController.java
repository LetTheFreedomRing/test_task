package com.agileengineinterview.imagegallerysearch.controller;

import com.agileengineinterview.imagegallerysearch.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ImageSearchController {

    private final ImageService searchService;

    public ImageSearchController(ImageService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search/{searchTerm}")
    public ResponseEntity<List> searchImages(@PathVariable(name = "searchTerm") String searchTerm) {
        return new ResponseEntity<>(searchService.findMatching(searchTerm), HttpStatus.OK);
    }
}
