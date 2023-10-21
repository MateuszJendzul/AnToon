package com.artiline.antoon;

import android.util.Log;

import java.util.Vector;

public class UsersDatabase {
    private static final String TAG = "UsersDatabase";
    User user_IR = new User();
    private static Vector<User> usersList;

    public void addUserToList(User user) {
        usersList.add(user.getID() + 1, user);
    }

    public User displayUser(int ID) {
        Log.i(TAG, "displayUser:");
        return usersList.get(ID);
    }
}
