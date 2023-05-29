package com.example.gamestoreapi.controller;

import com.example.gamestoreapi.helper.DTO;
import com.example.gamestoreapi.model.Review;
import com.example.gamestoreapi.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * A controller class for handling http requests related to the USER table
 */
@RestController
@CrossOrigin("http://localhost:3000")
public class ReviewController {

    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/reviews")
    public DTO<List<Review>> getAllByGameId(@RequestParam(defaultValue = "0") Integer gameId, @RequestParam(defaultValue = "0") Integer userId){
        return reviewService.getAllByGameId(gameId, userId);
    }

    @PostMapping("/review/add")
    public DTO<Boolean> addReview(@RequestParam() Integer gameId, @RequestParam() Integer userId, @RequestParam() Integer rating, @RequestParam() String message){
        return reviewService.add(gameId, userId, rating, message);
    }

    @DeleteMapping("/review/delete")
    public DTO<Boolean> addReview(@RequestParam() Integer id){
        return reviewService.delete(id);
    }
}
