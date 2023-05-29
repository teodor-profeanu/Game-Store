package com.example.gamestoreapi.repository;

import com.example.gamestoreapi.model.GameOwnership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A repository class for the raw operations on the Game_ownership table of the DB
 */
@Repository
public interface GameOwnershipRepo extends JpaRepository<GameOwnership,Integer> {
    Iterable<GameOwnership> findAllByGameIdAndUserId(Integer gameId, Integer userId);

    /**
     * Returns the GameOwnership that has the specified game and user ID
     */
    Optional<GameOwnership> findByGameIdAndUserId(Integer gameId, Integer userId);
    Iterable<GameOwnership> findAllByUserId(Integer userId);
}
