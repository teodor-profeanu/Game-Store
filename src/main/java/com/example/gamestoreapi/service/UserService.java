package com.example.gamestoreapi.service;

import com.example.gamestoreapi.dto.DTO;
import com.example.gamestoreapi.model.User;
import com.example.gamestoreapi.repository.PermissionRepo;
import com.example.gamestoreapi.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;
import java.util.regex.Pattern;
import com.example.gamestoreapi.GlobalTags;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final PermissionRepo permissionRepo;

    /**
     * This method checks whether the username or email given as parameter can be found in the DB, and checks if the password is correct
     * @param usernameEmail Username or email
     * @return Data transfer object with the status, message and user
     */
    public DTO login(String usernameEmail, String password){
        Optional<User> optionalUser=userRepo.findByUsername(usernameEmail);
        if(optionalUser.isEmpty()){
            optionalUser = userRepo.findByEmail(usernameEmail);
            if(optionalUser.isEmpty())
                return new DTO<User>(403, "Wrong user or password.", null);
        }
        User user = optionalUser.get();
        if(user.getPassword().compareTo(password)!=0)
            return new DTO<User>(403, "Wrong user or password.", null);
        return new DTO<User>(200, "Login successful.", user);
    }

    /**
     * Checks whether the given data is invalid or already taken, and registers the user into the db.
     * @return Data transfer object with the status, message and a Boolean value
     */
    public DTO<Boolean> register(String username, String email, String password, String repeatPassword){
        if(!Pattern.compile("^[a-zA-Z0-9.-]+@[a-zA-Z0-9.-]+$").matcher(email).matches())
            return new DTO<Boolean>(400, "Invalid email.", false);
        if(!Pattern.compile("^[a-zA-Z0-9.-]+$").matcher(username).matches() || username.length()<3)
            return new DTO<Boolean>(400, "Invalid username.", false);
        if(password.length()<8)
            return new DTO<Boolean>(400, "Password must be at least 8 characters long.", false);
        if(password.compareTo(repeatPassword)!=0)
            return new DTO<Boolean>(400, "Repeat password not the same as password.", false);
        Optional<User> usernameOptional=userRepo.findByUsername(username);
        if(usernameOptional.isPresent())
            return new DTO<Boolean>(400, "Username already taken.", false);
        Optional<User> emailOptional=userRepo.findByEmail(email);
        if(emailOptional.isPresent())
            return new DTO<Boolean>(400, "Email already taken.", false);

        User user = new User(0, username, email, password, permissionRepo.findByName(GlobalTags.USER).get().getId(), new Date(System.currentTimeMillis()), null, null, "");
        userRepo.save(user);
        return new DTO<Boolean>(200, "Register successful.", true);
    }

    /**
     * This method retrieves the user with the given ID, it makes the necessary changes, and then it saves it back in the DB.
     * @return Data transfer object with the status, message and a Boolean value
     */
    public DTO<Boolean> edit(Integer userId, String iconURL, String coverURL, String bio){
        Optional<User> optionalUser = userRepo.findById(userId);
        if(optionalUser.isEmpty())
            return new DTO<Boolean>(404, "User not found.", false);

        User user = optionalUser.get();
        user.setIconURL(iconURL);
        user.setCoverURL(coverURL);
        user.setBio(bio);
        userRepo.save(user);

        return new DTO<Boolean>(200, "Edit successful.", true);
    }

    public DTO<Boolean> changePassword(Integer userId, String oldPassword, String newPassword, String repeatPassword){
        Optional<User> optionalUser = userRepo.findById(userId);
        if(optionalUser.isEmpty())
            return new DTO<Boolean>(404, "User not found.", false);
        User user = optionalUser.get();
        if(user.getPassword().compareTo(oldPassword)!=0)
            return new DTO<Boolean>(400, "Wrong password.", false);
        if(newPassword.length()<8)
            return new DTO<Boolean>(400, "Password must be at least 8 characters long.", false);
        if(newPassword.compareTo(repeatPassword)!=0)
            return new DTO<Boolean>(400, "Repeat password not the same as password.", false);

        user.setPassword(newPassword);
        userRepo.save(user);
        return new DTO<Boolean>(200, "Password change successful.", true);
    }

    /**
     * This method retrieves a user from the DB, and then it deletes it.
     * @return True if the deletion was successful.
     */
    public DTO<Boolean> delete(Integer userId){
        Optional<User> optionalUser = userRepo.findById(userId);
        if(optionalUser.isEmpty())
            return new DTO<Boolean>(404, "User not found.", false);
        userRepo.delete(optionalUser.get());
        return new DTO<Boolean>(200, "Delete successful.", true);
    }
}
