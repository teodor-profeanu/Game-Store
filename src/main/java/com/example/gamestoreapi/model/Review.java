package com.example.gamestoreapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A model class for representing entries from the Review table
 */
@Entity
@Table(name = "Review")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    public Review(int id, int gameId, int userId, int rating, String message) {
        this.id = id;
        this.gameId = gameId;
        this.userId = userId;
        this.rating = rating;
        this.message = message;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "game_id")
    private int gameId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "rating")
    private int rating;

    @Column(name = "message")
    private String message;

    @Transient
    private User user;
}
