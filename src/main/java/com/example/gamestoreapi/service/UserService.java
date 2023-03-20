package com.example.gamestoreapi.service;

import com.example.gamestoreapi.model.User;
import com.example.gamestoreapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepo;

    public User login(String usernameEmail, String password){
        Optional<User> optionalUser=userRepo.findByUsername(usernameEmail);
        if(optionalUser.isEmpty()){
            optionalUser = userRepo.findByEmail(usernameEmail);
            if(optionalUser.isEmpty())
                return null;
        }
        User user = optionalUser.get();
        if(user.getPassword().compareTo(password)!=0)
            return null;
        return user;
    }

    public Boolean register(String username, String email, String password, String repeatPassword, Integer countryID){
        if(password.compareTo(repeatPassword)!=0)
            return false;
        Optional<User> usernameOptional=userRepo.findByUsername(username);
        if(usernameOptional.isPresent())
            return false;
        Optional<User> emailOptional=userRepo.findByEmail(email);
        if(emailOptional.isPresent())
            return false;
        User user = new User(0, username, email, password, 1, 0, new Date(System.currentTimeMillis()), countryID, null, null, null);
        userRepo.save(user);
        user=userRepo.findByUsername(username).get();
        if(user == null)
            return false;
        return true;
    }
}
