package com.artiline.antoon;

import androidx.annotation.NonNull;

public class User {

    public User() {

    }

    public User(String name, String eMail, String password) {
        this.name = name;
        this.eMail = eMail;
        this.password = password;
        this.ID = ID;
        //TODO create list of deleted ID's and check newly created users for first available ID
        ID++;

    }

    private static int ID = 1;
    private String name;
    private String eMail;
    private String password;

    public void setID(int ID) {
        User.ID = ID;
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
