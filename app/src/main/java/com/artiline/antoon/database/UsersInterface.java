package com.artiline.antoon.database;

import android.util.Log;

import java.util.ArrayList;

public class UsersInterface {
    private static final String TAG = "UsersInterface";
    private static ArrayList<User> usersList = new ArrayList<>();
    private static ArrayList<Integer> deletedUsersID = new ArrayList<>();

    public ArrayList<User> getUsersList() {
        Log.i(TAG, "getUsersList:");
        return usersList;
    }

    public ArrayList<Integer> getDeletedUsersID() {
        Log.i(TAG, "getDeletedUsersID:");
        return deletedUsersID;
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
