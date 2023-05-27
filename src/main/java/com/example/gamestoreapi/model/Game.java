package com.example.gamestoreapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * A model class for representing entries from the Game table
 */
@Entity
@Table(name = "Game")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    public Game(int id, String name, int developerId, Date releaseDate, float priceEuro, int sales, String iconURL, String description) {
        this.id = id;
        this.name = name;
        this.developerId = developerId;
        this.releaseDate = releaseDate;
        this.priceEuro = priceEuro;
        this.sales = sales;
        this.iconURL = iconURL;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "developer_id")
    private int developerId;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "price_euro")
    private float priceEuro;

    @Column(name = "sales")
    private int sales;

    @Column(name = "icon_url")
    private String iconURL;

    @Column(name = "description")
    private String description;

    private int nrOfReviews;
    private float rating;
    private int discountPercent;
}
