package com.example.gamestoreapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;
import java.util.Objects;

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

    @Column(name = "NICKNAME")
    private String nickname;

    @Column(name = "COUNTRY_ID")
    private int countryId;

    @Column(name = "ICON_URL")
    private String iconURL;

    @Column(name = "COVER_URL")
    private String coverURL;

    @Column(name = "BIO")
    private String bio;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && permissions == user.permissions && Float.compare(user.hoursThisWeek, hoursThisWeek) == 0 && countryId == user.countryId && username.equals(user.username) && email.equals(user.email) && password.equals(user.password) && Objects.equals(dateJoined, user.dateJoined) && Objects.equals(nickname, user.nickname) && Objects.equals(iconURL, user.iconURL) && Objects.equals(coverURL, user.coverURL) && Objects.equals(bio, user.bio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, permissions, hoursThisWeek, dateJoined, nickname, countryId, iconURL, coverURL, bio);
    }
}
