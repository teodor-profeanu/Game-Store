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

    /**
     * This method checks whether the username or email given as parameter can be found in the DB, and checks if the password is correct
     * @return A boolean value for whether the login was successful or not
     */
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

    /**
     * This method first checks if the given username and email may have already been added to the DB, and then it creates a new User and saves it to the DB.
     * @return A boolean value for whether the registration was successful or not
     */
    public Boolean register(String username, String email, String password, String repeatPassword, Integer countryId){
        if(password.compareTo(repeatPassword)!=0)
            return false;
        Optional<User> usernameOptional=userRepo.findByUsername(username);
        if(usernameOptional.isPresent())
            return false;
        Optional<User> emailOptional=userRepo.findByEmail(email);
        if(emailOptional.isPresent())
            return false;
        User user = new User(0, username, email, password, 1, 0, new Date(System.currentTimeMillis()), null,countryId, null, null, null);
        userRepo.save(user);
        user=userRepo.findByUsername(username).get();
        if(user == null)
            return false;
        return true;
    }

    /**
     * This method retrieves the user with the given ID, it makes the necessary changes and then it saves it back in the DB. If the User is not initially found, the method will return false.
     * @return A boolean value for whether the changes was successful or not
     */
    public Boolean edit(Integer userId, String nickname, Integer countryId, String iconURL, String coverURL, String bio){
        Optional<User> optionalUser = userRepo.findById(userId);
        if(optionalUser.isEmpty())
            return false;
        User user = optionalUser.get();
        user.setNickname(nickname);
        user.setCountryId(countryId);
        user.setIconURL(iconURL);
        user.setCoverURL(coverURL);
        user.setBio(bio);
        userRepo.save(user);

        if(!user.equals(userRepo.findById(userId).get()))
            return false;
        return true;
    }

    /**
     * This method retrieves a user from the DB, and then it deletes it.
     * @return True if the deletion was successful.
     */
    public Boolean delete(Integer userId){
        Optional<User> optionalUser = userRepo.findById(userId);
        if(optionalUser.isEmpty())
            return true;
        userRepo.delete(optionalUser.get());
        return true;
    }
}
