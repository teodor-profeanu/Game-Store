package com.example.gamestoreapi.repository;

import com.example.gamestoreapi.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A repository class for the raw operations on the Review table of the DB
 */
@Repository
public interface ReviewRepo extends JpaRepository<Review,Integer> {
    /**
     * Finds all the reviews to a specified game
     */
    Iterable<Review> findAllByGameId(Integer gameId);

    /**
     * Finds all the reviews to a specified game and posted by a certain user
     */
    Optional<Review> findByUserIdAndGameId(Integer userId, Integer gameId);
}
