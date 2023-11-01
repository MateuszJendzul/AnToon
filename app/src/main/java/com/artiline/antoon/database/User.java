package com.artiline.antoon.database;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.artiline.antoon.exceptions.IDLessThanOneException;

import java.io.Serializable;

public class User implements Parcelable {
    private static final String TAG = "UsersDatabase";

    public User() {
        Log.i(TAG, "User No Args Constructor:");
    }

    public User(String name, String e_mail, String password) throws IDLessThanOneException {
        Log.i(TAG, "User: Constructor: " + "ID: " + getID() + " Name: " + getName() +
                " Password: " + getPassword() + " Email: " + getEMail());
        this.userID = currentListID;
        this.name = name;
        this.e_mail = e_mail;
        this.password = password;
        currentListID++;
    }

    // implementing parcelable interface
    // write and read in exactly same order
    public User(Parcel in) {
        userID = in.readInt();
        name = in.readString();
        e_mail = in.readString();
        password = in.readString();
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(userID);
        dest.writeString(name);
        dest.writeString(e_mail);
        dest.writeString(password);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @NonNull
    @Override
    public String toString() {
        return "ID: " + getID() + " Name: " + getName() + " Password: " + getPassword()
                + " Email: " + getEMail();
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
}
