package com.example.gamestoreapi.repository;

import com.example.gamestoreapi.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A repository class for the raw operations on the Tag table of the DB
 */
@Repository
public interface TagRepo extends JpaRepository<Tag,Integer> {
}
