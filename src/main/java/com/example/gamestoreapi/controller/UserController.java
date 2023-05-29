package com.example.gamestoreapi.controller;

import com.example.gamestoreapi.helper.DTO;
import com.example.gamestoreapi.model.Game;
import com.example.gamestoreapi.model.User;
import com.example.gamestoreapi.service.GameService;
import com.example.gamestoreapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * A controller class for handling http requests related to the USER table
 */
@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {

    private UserService userService;
    private GameService gameService;

    @Autowired
    public UserController(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @GetMapping("/user/login")
    public DTO<User> login(@RequestParam String usernameEmail, @RequestParam String password){
        return userService.login(usernameEmail, password);
    }

    @GetMapping("/user")
    public DTO<User> get(@RequestParam Integer id){
        DTO<User> dto = userService.get(id);
        if(dto.getObject() == null)
            return dto;
        List<Game> games = gameService.getGamesByUserId(id);
        dto.getObject().setGames(games);
        return dto;
    }

    @PostMapping("/user/register")
    public DTO<Integer> register(@RequestParam String username, @RequestParam String email, @RequestParam String password, @RequestParam String repeatPassword){
        return userService.register(username, email, password, repeatPassword);
    }

    @PutMapping("/user/edit")
    public DTO<Boolean> edit(@RequestParam Integer id, @RequestParam(defaultValue = "") String nickname, @RequestParam(defaultValue = "") String iconURL, @RequestParam(defaultValue = "") String bio){
        return userService.edit(id, nickname, iconURL, bio);
    }

    @PutMapping("/user/change-password")
    public DTO<Boolean> changePassword(@RequestParam Integer id, @RequestParam String oldPassword, @RequestParam String newPassword){
        return userService.changePassword(id, oldPassword, newPassword);
    }

    @DeleteMapping("/user/delete")
    public DTO<Boolean> delete(@RequestParam Integer id){
        return userService.delete(id);
    }
}
