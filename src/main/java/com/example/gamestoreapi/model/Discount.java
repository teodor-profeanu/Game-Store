package com.example.gamestoreapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * A model class for representing entries from the Discount table
 */
@Entity
@Table(name = "Discount")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name = "game_id")
    private int gameId;

    @Column(name = "discount_percent")
    private int discountPercent;

    @Column(name = "discount_end")
    private Date discountEnd;
}
