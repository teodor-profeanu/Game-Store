package com.example.gamestoreapi.repository;

import com.example.gamestoreapi.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A repository class for the raw operations on the Game table of the DB
 */
@Repository
public interface GameRepo extends JpaRepository<Game,Integer> {
}
