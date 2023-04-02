package com.example.gamestoreapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A model class for representing entries from the Game_images table
 */
@Entity
@Table(name = "Game_images")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameImages {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "game_id")
    private int gameId;

    @Column(name = "image_url")
    private String imageURL;
}
