package com.artiline.antoon.database;

import android.util.Log;

import java.util.ArrayList;

public class UsersDatabase {
    private static final String TAG = "UsersDatabase";
    private static ArrayList<User> usersList = new ArrayList<>();
    private static ArrayList<Integer> deletedUsersIDs = new ArrayList<>();

    public static ArrayList<User> getUsersList() {
        Log.i(TAG, "getUsersList:");
        return usersList;
    }

    public User createUser(String name, String eMail, String password) {
        Log.i(TAG, "createUser: " + name + " " + eMail + " " + password);
        return new User(name, eMail, password);
    }

    public void addUserToList(User user) {
        Log.i(TAG, "addUserToList: " + user);
        usersList.add(user);
    }

    public void removeUserFromList(int index) {
        Log.i(TAG, "removeUserFromList: " + "ID: " + usersList.get(index).getUserID() + " removed");
        usersList.remove(index);
    }
}
