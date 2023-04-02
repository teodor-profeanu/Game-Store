package com.example.gamestoreapi.controller;

import com.example.gamestoreapi.helper.DTO;
import com.example.gamestoreapi.model.User;
import com.example.gamestoreapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * A controller class for handling http requests for the USER table
 */
@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/login")
    public DTO<User> login(@RequestParam String usernameEmail, @RequestParam String password){
        return userService.login(usernameEmail, password);
    }

    @GetMapping("/user")
    public DTO<User> get(@RequestParam Integer id){
        return userService.get(id);
    }

    @PostMapping("/user/register")
    public DTO<Boolean> register(@RequestParam String username, @RequestParam String email, @RequestParam String password, @RequestParam String repeatPassword){
        return userService.register(username, email, password, repeatPassword);
    }

    @PutMapping("/user/edit")
    public DTO<Boolean> edit(@RequestParam Integer id, @RequestParam(defaultValue = "") String nickname, @RequestParam(defaultValue = "") String iconURL, @RequestParam(defaultValue = "") String coverURL, @RequestParam(defaultValue = "") String bio){
        return userService.edit(id, nickname, iconURL, coverURL, bio);
    }

    @PutMapping("/user/changePassword")
    public DTO<Boolean> changePassword(@RequestParam Integer id, @RequestParam String oldPassword, @RequestParam String newPassword, @RequestParam String repeatPassword){
        return userService.changePassword(id, oldPassword, newPassword, repeatPassword);
    }

    @DeleteMapping("/user/delete")
    public DTO<Boolean> delete(@RequestParam Integer id){
        return userService.delete(id);
    }
}
