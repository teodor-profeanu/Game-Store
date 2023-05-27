package com.example.gamestoreapi.controller;

import com.example.gamestoreapi.helper.DTO;
import com.example.gamestoreapi.helper.GlobalTags;
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
    public DTO<List<Game>> search(@RequestParam(defaultValue = "") String keywords, @RequestParam(defaultValue = "0") Integer userId, @RequestParam(defaultValue = "0") int popularity, @RequestParam(defaultValue = "0") int rating, @RequestParam(defaultValue = "0") int reviews, @RequestParam(defaultValue = "0") int date, @RequestParam(defaultValue = "0") int price, @RequestParam(defaultValue = "0") int trending, @RequestParam(defaultValue = "0") int priceMin, @RequestParam(defaultValue = "9999") int priceMax, @RequestParam(defaultValue = "False") Boolean discount, @RequestParam(defaultValue = "") String tagIds){
        return gameService.search(userId, popularity, rating, reviews, date, price, trending, keywords, priceMin, priceMax, discount, tagIds);
    }

    @GetMapping("/game")
    public DTO<Game> get(@RequestParam Integer id, @RequestParam(defaultValue = "0") Integer userId){
        return gameService.get(id, userId);
    }

    @PutMapping("/game/edit")
    public DTO<Boolean> edit(@RequestParam Integer id , @RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "0") float price, @RequestParam(defaultValue = "") String iconURL, @RequestParam(defaultValue = "") String description){
        return gameService.edit(id, name, price, iconURL, description);
    }

    @DeleteMapping("/game/delete")
    public DTO<Boolean> edit(@RequestParam Integer id){
        return gameService.delete(id);
    }

    @GetMapping("/game/top-sellers")
    public DTO<List<Game>> topSellers(){
        return gameService.search(0, GlobalTags.DESCENDING, 0, 0, 0, 0, 0, "", 0, 9999, false, "");
    }

    @GetMapping("/game/new-releases")
    public DTO<List<Game>> newReleases(){
        return gameService.search(0, 0, 0, 0, GlobalTags.DESCENDING, 0, 0, "", 0, 9999, false, "");
    }

    @GetMapping("/game/trending")
    public DTO<List<Game>> trending(){
        return gameService.search(0, 0, 0, 0, 0, 0, GlobalTags.ASCENDING, "", 0, 9999, false, "");
    }

    @GetMapping("/game/featured")
    public DTO<List<Game>> featured(@RequestParam(defaultValue = "0") Integer userId){
        return gameService.featured(userId);
    }
}
