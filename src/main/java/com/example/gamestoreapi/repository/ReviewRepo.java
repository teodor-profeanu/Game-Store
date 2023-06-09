package com.example.gamestoreapi.repository;

import com.example.gamestoreapi.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A repository class for the raw operations on the Review table of the DB
 */
@Repository
public interface ReviewRepo extends JpaRepository<Review,Integer> {
}
