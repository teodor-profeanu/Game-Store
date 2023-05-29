package com.example.gamestoreapi.service;

import com.example.gamestoreapi.helper.DTO;
import com.example.gamestoreapi.model.Review;
import com.example.gamestoreapi.model.User;
import com.example.gamestoreapi.repository.PermissionRepo;
import com.example.gamestoreapi.repository.ReviewRepo;
import com.example.gamestoreapi.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A service class for the logic behind Review operations.
 */
@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewRepo reviewRepo;
    private final UserRepo userRepo;

    /**
     * Returns the list of reviews for the specified game, with the review left by the user with the ID userId being first
     * @param gameId The game for which we are fetching the reviews
     * @param userId The user whose review should be first
     * @return A DTO containing the list of reviews
     */
    public DTO<List<Review>> getAllByGameId(Integer gameId, Integer userId){
        Iterable<Review> reviews = reviewRepo.findAllByGameId(gameId);
        for(Review review : reviews){
            Optional<User> optional = userRepo.findById(review.getUserId());
            if (optional.isPresent())
                review.setUser(optional.get());
        }
        List<Review> finalList = new ArrayList<>();
        Optional<Review> optional = reviewRepo.findByUserIdAndGameId(userId, gameId);
        if(optional.isPresent())
            finalList.add(optional.get());
        for(Review review : reviews){
            if(!(optional.isPresent() && review.getUserId() == optional.get().getUserId()))
                finalList.add(review);
        }
        return new DTO<>(200, "Reviews found", finalList);
    }

    /**
     * Adds a new review
     * @param gameId Game's ID
     * @param userId User's ID
     * @param rating The rating the user is leaving
     * @param message The message the user wrote with the rating
     * @return True or False
     */
    public DTO<Boolean> add(Integer gameId, Integer userId, Integer rating, String message){
        if(reviewRepo.findByUserIdAndGameId(userId, gameId).isPresent())
            return new DTO<Boolean>(402, "Already posted a review for this game.", Boolean.FALSE);
        if(rating < 1 || rating > 10)
            return new DTO<Boolean>(402, "Give the game a rating between 1 and 10.", Boolean.FALSE);
        Review review = reviewRepo.save(new Review(0, gameId, userId, rating, message));
        return new DTO<Boolean>(200, "Review posted.", Boolean.TRUE);
    }

    /**
     * Deletes a certain review
     */
    public DTO<Boolean> delete(Integer id){
        Optional<Review> optional = reviewRepo.findById(id);
        if(optional.isEmpty())
            return new DTO<>(404, "Review not found.", Boolean.FALSE);
        reviewRepo.delete(optional.get());
        return new DTO<>(200, "Review deleted.", Boolean.TRUE);
    }
}
