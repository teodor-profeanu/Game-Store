package com.example.gamestoreapi.controller;

import com.example.gamestoreapi.helper.DTO;
import com.example.gamestoreapi.model.Game;
import com.example.gamestoreapi.model.Review;
import com.example.gamestoreapi.service.GameOwnershipService;
import com.example.gamestoreapi.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * A controller class for handling http requests related to the Game_Ownership table
 */
@RestController
@CrossOrigin("http://localhost:3000")
public class GameOwnershipController {

    private GameOwnershipService gameOwnershipService;

    @Autowired
    public GameOwnershipController(GameOwnershipService gameOwnershipService) {
        this.gameOwnershipService = gameOwnershipService;
    }

    @PostMapping("/game/buy")
    public DTO<Boolean> buy(@RequestParam(defaultValue = "0") Integer userId, @RequestParam(defaultValue = "0") Integer gameId){
        return gameOwnershipService.buy(userId, gameId);
    }

    @PutMapping("/game/play")
    public DTO<Boolean> buy(@RequestParam(defaultValue = "0") Integer userId, @RequestParam(defaultValue = "0") Integer gameId, @RequestParam(defaultValue = "0") Float hours){
        return gameOwnershipService.play(userId, gameId, hours);
    }
}
