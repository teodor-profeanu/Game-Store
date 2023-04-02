package com.example.gamestoreapi.repository;

import com.example.gamestoreapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A repository class for the raw operations on the User table of the DB
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    /**
     * Retrieves the first user from the DB that has the username given as parameter.
     * @return An Optional that either contains the found User, or is empty.
     */
    Optional<User> findByUsername(String username);

    /**
     * Retrieves the first user from the DB that has the email given as parameter.
     * @return An Optional that either contains the found user, or is empty.
     */
    Optional<User> findByEmail(String email);
}
