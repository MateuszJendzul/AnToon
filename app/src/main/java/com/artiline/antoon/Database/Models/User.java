package com.artiline.antoon.Database.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "User")
public class User implements Serializable {
    @PrimaryKey()
    private int userID;
    @ColumnInfo(name = "name")
    private String name = "Name";
    @ColumnInfo(name = "email")
    private String email = "Email";
    @ColumnInfo(name = "password")
    private String password = "Password";
    @ColumnInfo(name = "userCreated")
    private boolean userCreated = false;
    @ColumnInfo(name = "font")
    private String font = "Default";

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String eMail) {
        this.email = eMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isUserCreated() {
        return userCreated;
    }

    public void setUserCreated(boolean userCreated) {
        this.userCreated = userCreated;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "ID=" + userID +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userCreated=" + userCreated + '\'' +
                ", font='" + font +
                '}';
    }
}
