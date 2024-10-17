package ru.aesaq.messengerx.authservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "profile_picture")
    private String profile_picture;
    @Column(name = "ebb_level")
    private int ebbLevel;

    public User(String username, String password, String profile_picture) {
        this.username = username;
        this.password = password;
        this.profile_picture = profile_picture;
    }

    public User() {
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public int getEbbLevel() {
        return ebbLevel;
    }

    public void setEbbLevel(int ebbLevel) {
        this.ebbLevel = ebbLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePicture() {
        return profile_picture;
    }

    public void setProfilePicture(String profile_picture) {
        this.profile_picture = profile_picture;
    }
}
