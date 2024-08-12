package com.example.brainbuzz.model;

import java.io.Serializable;

public class UsersModel {
    int id;
    String name, userName, email, bio, password,img;

    public UsersModel() {
    }

    public UsersModel(String name, String userName, String email, String bio, String password, String img) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.bio = bio;
        this.password = password;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
