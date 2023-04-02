package com.example.gamestoreapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import jakarta.validation.constraints.Pattern;

import java.sql.Date;

/**
 * A model class for representing entries from the User table
 */
@Entity
@Table(name = "USER")
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

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "PERMISSION_ID")
    private int permissionId;

    @Column(name = "DATE_JOINED")
    private Date dateJoined;

    @Column(name = "ICON_URL")
    private String iconURL;

    @Column(name = "COVER_URL")
    private String coverURL;

    @Column(name = "BIO")
    private String bio;
}
