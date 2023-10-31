package com.artiline.antoon.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.artiline.antoon.exceptions.IDLessThanOneException;

import java.io.Serializable;

public class User implements Serializable {
    private static final String TAG = "UsersDatabase";

    public User() {
        Log.i(TAG, "User No Args Constructor:");
    }

    public User(String name, String e_mail, String password) throws IDLessThanOneException {
        this.name = name;
        this.e_mail = e_mail;
        this.password = password;

        try {
            //TODO fix custom exceptions
            this.userID = currentListID;
            Log.i(TAG, "User: Constructor: " + "ID: " + getID() + " Name: " + getName() +
                    " Password: " + getPassword() + " Email: " + getEMail());
        } catch (Exception e) {
            throw new IDLessThanOneException(
                    "User ID cannot be less than 1! Detected ID: " + currentListID, new RuntimeException());
        }
        currentListID++;
    }

    private static int currentListID = 0;
    private int userID;
    private String name;
    private String e_mail;
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
        return e_mail;
    }

    public void setEMail(String eMail) {
        this.e_mail = eMail;
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
