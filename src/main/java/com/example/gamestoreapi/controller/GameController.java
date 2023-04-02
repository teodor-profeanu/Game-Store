package com.example.gamestoreapi.controller;

import com.example.gamestoreapi.helper.DTO;
import com.example.gamestoreapi.model.Game;
import com.example.gamestoreapi.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * A controller class for handling http requests related to the Game table
 */
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

    @GetMapping("/game/search")
    public DTO<List<Game>> search(@RequestParam(defaultValue = "") String keywords , @RequestParam(defaultValue = "0") int popularity, @RequestParam(defaultValue = "0") int rating, @RequestParam(defaultValue = "0") int reviews, @RequestParam(defaultValue = "0") int date, @RequestParam(defaultValue = "0") int price, @RequestParam(defaultValue = "0") int priceMin, @RequestParam(defaultValue = "1000") int priceMax, @RequestParam(defaultValue = "False") Boolean discount, @RequestParam(defaultValue = "") String tagIds){
        return gameService.search(popularity, rating, reviews, date, price, keywords, priceMin, priceMax, discount, tagIds);
    }

    @GetMapping("/game")
    public DTO<Game> get(@RequestParam Integer id){
        return gameService.get(id);
    }

    @PutMapping("/game/edit")
    public DTO<Boolean> edit(@RequestParam Integer id , @RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "0") float price, @RequestParam(defaultValue = "") String iconURL, @RequestParam(defaultValue = "") String description){
        return gameService.edit(id, name, price, iconURL, description);
    }

    @DeleteMapping("/game/delete")
    public DTO<Boolean> edit(@RequestParam Integer id){
        return gameService.delete(id);
    }
}
