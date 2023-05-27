package com.example.gamestoreapi.repository;

import com.example.gamestoreapi.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A repository class for the raw operations on the User table of the DB
 */
@Repository
public interface PermissionRepo extends JpaRepository<Permission,Integer> {
    Optional<Permission> findByName(String name);
}
