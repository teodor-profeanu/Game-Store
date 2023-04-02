package com.example.gamestoreapi.repository;

import com.example.gamestoreapi.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A repository class for the raw operations on the Discount table of the DB
 */
@Repository
public interface DiscountRepo extends JpaRepository<Discount,Integer> {
}
