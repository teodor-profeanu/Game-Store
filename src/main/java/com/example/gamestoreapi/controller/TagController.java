package com.example.gamestoreapi.controller;

import com.example.gamestoreapi.helper.DTO;
import com.example.gamestoreapi.model.Tag;
import com.example.gamestoreapi.service.ReviewService;
import com.example.gamestoreapi.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * A controller class for handling http requests related to the TAG table
 */
@RestController
@CrossOrigin("http://localhost:3000")
public class TagController {

    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/tags")
    public DTO<Iterable<Tag>> getAll(){
        return tagService.getAll();
    }
}
