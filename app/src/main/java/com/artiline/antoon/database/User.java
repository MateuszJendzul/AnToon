package com.artiline.antoon.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.artiline.antoon.exceptions.IDLessThanOneException;

public class User {
    private static final String TAG = "UsersDatabase";

    public User() {
        Log.i(TAG, "User No Args Constructor:");
    }

    public User(String name, String eMail, String password) throws IDLessThanOneException {
        this.name = name;
        this.eMail = eMail;
        this.password = password;

        try {
            this.userID = currentListID;
        Log.i(TAG, "User: Constructor: " + "ID: " + getID() + " Name: " + getName() +
                " Password: " + getPassword() + " Email: " + getEMail());
            currentListID++;
        } catch (Exception e) {
            throw new IDLessThanOneException(
                    "User ID cannot be less than 1! Detected ID: " + currentListID, new RuntimeException());
        }
    }

    private static int currentListID = 0;
    private int userID;
    private String name;
    private String eMail;
    private String password;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    private void setID(int IDNumber) {
        currentListID = IDNumber;
    }

    private int getID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NonNull
    @Override
    public String toString() {
        return "ID: " + getID() + " Name: " + getName() + " Password: " + getPassword()
                + " Email: " + getEMail();
    }
}
