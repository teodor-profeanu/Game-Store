package com.example.gamestoreapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A model class for representing entries from the Game_tags table
 */
@Entity
@Table(name = "Game_tags")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameTags {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name = "game_id")
    private int gameId;

    @Column(name = "tag_id")
    private int tagId;
}
