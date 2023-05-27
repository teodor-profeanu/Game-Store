package com.example.gamestoreapi.repository;

import com.example.gamestoreapi.model.GameTags;
import com.example.gamestoreapi.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A repository class for the raw operations on the Tag table of the DB
 */
@Repository
public interface GameTagsRepo extends JpaRepository<GameTags,Integer> {
    Iterable<GameTags> findAllByGameId(Integer gameId);
}
