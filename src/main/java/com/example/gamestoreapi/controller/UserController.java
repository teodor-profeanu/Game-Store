package com.example.gamestoreapi.controller;

import com.example.gamestoreapi.model.User;
import com.example.gamestoreapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/login")
    public User login(@RequestParam @DefaultValue("") String usernameEmail, @RequestParam(defaultValue = "") String password){
        return userService.login(usernameEmail, password);
    }

    @PostMapping("/user/register")
    public Boolean register(@RequestParam(defaultValue = "") String username, @RequestParam(defaultValue = "") String email, @RequestParam(defaultValue = "") String password, @RequestParam(defaultValue = "") String repeatPassword, @RequestParam(defaultValue = "0") Integer countryID){
        if(email.length()==0||username.length()==0||password.length()==0||repeatPassword.length()==0)
            return false;
        return userService.register(username, email, password, repeatPassword, countryID);
    }
}
