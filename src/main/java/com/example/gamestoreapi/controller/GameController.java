package com.example.gamestoreapi.controller;

import com.example.gamestoreapi.GlobalTags;
import com.example.gamestoreapi.helper.DTO;
import com.example.gamestoreapi.model.Game;
import com.example.gamestoreapi.service.GameService;
import com.example.gamestoreapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GameController {
    private GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/game/add")
    public DTO<Boolean> add(@RequestParam String name, @RequestParam Integer devId, @RequestParam float price, @RequestParam(defaultValue = "") String iconURL, @RequestParam(defaultValue = "") String description){
        return gameService.add(name, devId, price, iconURL, description);
    }

    @GetMapping("/game/filter")
    public DTO<List<Game>> filter(@RequestParam(defaultValue = "0") int popularity, @RequestParam(defaultValue = "0") int rating, @RequestParam(defaultValue = "0") int date, @RequestParam(defaultValue = "0") int price, @RequestParam(defaultValue = "0") int priceMin, @RequestParam(defaultValue = "1000") int priceMax, @RequestParam(defaultValue = "False") Boolean discount, @RequestParam(defaultValue = "") String tagIds){
        return gameService.filter(popularity, rating, date, price, priceMin, priceMax, discount, tagIds);
    }
}
