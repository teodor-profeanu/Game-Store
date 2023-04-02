package com.example.gamestoreapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * A model class for representing entries from the Game_ownership table
 */
@Entity
@Table(name = "Game_ownership")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameOwnership {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "game_id")
    private int gameId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "hours_played")
    private float hoursPlayed;

    @Column(name = "last_played")
    private Date lastPlayed;
}
