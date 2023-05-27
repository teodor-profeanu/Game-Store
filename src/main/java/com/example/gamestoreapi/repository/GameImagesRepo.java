package com.example.gamestoreapi.repository;

import com.example.gamestoreapi.model.GameImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A repository class for the raw operations on the Game_images table of the DB
 */
@Repository
public interface GameImagesRepo extends JpaRepository<GameImages,Integer> {
    Iterable<GameImages> findAllByGameId(Integer gameId);
}
