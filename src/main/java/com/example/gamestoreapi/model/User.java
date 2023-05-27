package com.example.gamestoreapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import jakarta.validation.constraints.Pattern;

import java.sql.Date;
import java.util.List;

/**
 * A model class for representing entries from the User table
 */
@Entity
@Table(name = "USER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    public User(int id, String username, String email, String password, int permissionId, Date dateJoined, String nickname, String iconURL, String bio) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.permissionId = permissionId;
        this.dateJoined = dateJoined;
        this.nickname = nickname;
        this.iconURL = iconURL;
        this.bio = bio;
    }

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

    @Column(name = "NICKNAME")
    private String nickname;

    @Column(name = "ICON_URL")
    private String iconURL;

    @Column(name = "BIO")
    private String bio;

    @Transient
    private List<Game> games;
}
