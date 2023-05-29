package com.example.gamestoreapi.repository;

import com.example.gamestoreapi.model.GameTags;
import com.example.gamestoreapi.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A repository class for the raw operations on the Game_Tags table of the DB
 */
@Repository
public interface GameTagsRepo extends JpaRepository<GameTags,Integer> {
    /**
     * Returns a list of tags for the specified game
     */
    Iterable<GameTags> findAllByGameId(Integer gameId);
}
