package com.example.gamestoreapi.controller;

import com.example.gamestoreapi.model.User;
import com.example.gamestoreapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
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

    /**
     *
     * @param usernameEmail The username or email for authentication
     * @param password The password for authentication
     * @return Null if the authentication failed, or the user that was found
     */
    @GetMapping("/user/login")
    public User login(@RequestParam @DefaultValue("") String usernameEmail, @RequestParam(defaultValue = "") String password){
        return userService.login(usernameEmail, password);
    }

    /**
     * If any parameter has a length of 0 or is missing besides countryId, registration will fail.
     * @param countryId only optional parameter, any missing one from the others will cause an automatic fail
     * @return A boolean value for whether the registration was successful or not
     */
    @PostMapping("/user/register")
    public Boolean register(@RequestParam(defaultValue = "") String username, @RequestParam(defaultValue = "") String email, @RequestParam(defaultValue = "") String password, @RequestParam(defaultValue = "") String repeatPassword, @RequestParam(defaultValue = "0") Integer countryId){
        if(email.length()==0||username.length()==0||password.length()==0||repeatPassword.length()==0)
            return false;
        return userService.register(username, email, password, repeatPassword, countryId);
    }

    /**
     * This method edits some less permanent fields of a User. If any parameter is missing, it will replace the information in the Database with an empty string.
     * @param userId User table primary key
     * @param iconURL A link to the chosen profile picture
     * @param coverURL A link to the chosen cover picture
     * @param bio A short description for the user's page
     * @return A boolean value for whether the change was successful or not
     */
    @PutMapping("/user/edit")
    public Boolean edit(@RequestParam Integer userId,@RequestParam(defaultValue = "") String nickname, @RequestParam(defaultValue = "0") Integer countryId, @RequestParam(defaultValue = "") String iconURL, @RequestParam(defaultValue = "") String coverURL, @RequestParam(defaultValue = "") String bio){
        return userService.edit(userId, nickname, countryId, iconURL, coverURL, bio);
    }

    /**
     * This method deletes a user from the table and returns true if the user mentioned is not present at the end
     * @param userId User table primary key
     * @return A boolean value for whether the deletion was successful or not
     */
    @DeleteMapping("/user/delete")
    public Boolean delete(@RequestParam Integer userId){
        return userService.delete(userId);
    }
}
