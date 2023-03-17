package com.example.gamestoreapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Entity
@Table(name = "USER")
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ICON_URL")
    private String iconURL;

    @Column(name = "COVER_URL")
    private String coverURL;

    @Column(name = "BIO")
    private String bio;

    @Column(name = "PERMISSIONS")
    private int permissions;

    @Column(name = "HOURS_THIS_WEEK")
    private float hoursThisWeek;

    @Column(name = "COUNTRY_ID")
    private int countryID;

    @Column(name = "DATE_JOINED")
    private Date dateJoined;
}
