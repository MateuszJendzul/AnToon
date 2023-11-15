package com.artiline.antoon.Models;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.artiline.antoon.Database.PrimaryKeyGenerator;

import java.io.Serializable;

@Entity(tableName = "User")
public class User implements Serializable {
    private static final String TAG = "UsersDatabase";
    @PrimaryKey
    private int ID = 0;
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

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
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

    public User() {
        Log.i(TAG, "User No Args Constructor:");
    }

    public User(String name, String email, String password) {
        this.ID = PrimaryKeyGenerator.generatePrimaryKey();
        this.name = name;
        this.email = email;
        this.password = password;
        Log.i(TAG, "User: Constructor: " + "ID: " + getID() + " Name: " + getName() +
                " Password: " + getPassword() + " Email: " + getEmail());
    }


    @NonNull
    @Override
    public String toString() {
        return "User{" + "ID=" + ID + ", name=" + name + ", email=" + email + " userCreated= " +
                userCreated + '}';
    }
}
