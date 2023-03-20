package com.example.gamestoreapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotNull;

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
    @NotNull
    private String username;

    @Column(name = "EMAIL")
    @NotNull
    @Pattern(regexp = "\\w+([\\.-]?\\w+)@\\w+([\\.-]?\\w+)(\\.\\w{2,3})")
    private String email;

    @Column(name = "PASSWORD")
    @NotNull
    private String password;

    @Column(name = "PERMISSIONS")
    @NotNull
    private int permissions;

    @Column(name = "HOURS_THIS_WEEK")
    private float hoursThisWeek;

    @Column(name = "DATE_JOINED")
    private Date dateJoined;

    @Column(name = "COUNTRY_ID")
    private int countryID;

    @Column(name = "ICON_URL")
    private String iconURL;

    @Column(name = "COVER_URL")
    private String coverURL;

    @Column(name = "BIO")
    private String bio;
}
