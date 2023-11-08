package com.artiline.antoon.database;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.artiline.antoon.exceptions.IDLessThanOneException;

public class User implements Parcelable {
    private static final String TAG = "UsersDatabase";

    public User() {
        Log.i(TAG, "User No Args Constructor:");
    }

    public User(String name, String e_mail, String password) throws IDLessThanOneException {
        Log.i(TAG, "User: Constructor: " + "ID: " + getID() + " Name: " + getName() +
                " Password: " + getPassword() + " Email: " + getEMail());
        this.user_ID = current_list_ID;
        this.name = name;
        this.email = e_mail;
        this.password = password;
        current_list_ID++;
    }

    // implementing parcelable interface
    // write and read in exactly same order
    public User(Parcel in) {
        user_ID = in.readInt();
        name = in.readString();
        email = in.readString();
        password = in.readString();
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(user_ID);
        dest.writeString(name);
        dest.writeString(email);
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

    private static int current_list_ID = 0;
    private int user_ID;
    private String name;
    private String email;
    private String password;

    public int getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }

    private void setID(int IDNumber) {
        current_list_ID = IDNumber;
    }

    private int getID() {
        return user_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEMail() {
        return email;
    }

    public void setEMail(String eMail) {
        this.email = eMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
