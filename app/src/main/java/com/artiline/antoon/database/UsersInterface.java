package com.artiline.antoon.database;

import android.util.Log;

import java.util.ArrayList;

@SuppressWarnings("FieldMayBeFinal")
public class UsersInterface {
    private static final String TAG = "UsersInterface";
    private static ArrayList<User> users_list = new ArrayList<>();
    private static ArrayList<Integer> deleted_users_ID = new ArrayList<>();

    public ArrayList<User> getUsersList() {
        Log.i(TAG, "getUsersList:");
        return users_list;
    }

    public ArrayList<Integer> getDeletedUsersID() {
        Log.i(TAG, "getDeletedUsersID:");
        return deleted_users_ID;
    }

    public User createUser(String name, String email, String password) {
        Log.i(TAG, "createUser: " + name + " " + email + " " + password);
        return new User(name, email, password);
    }

    public void addUserToList(User user) {
        Log.i(TAG, "addUserToList: " + user);
        users_list.add(user);
    }

    public void removeUserFromList(int index) {
        Log.i(TAG, "removeUserFromList: " + "ID: " + users_list.get(index).getUser_ID() + " removed");
        users_list.remove(index);
    }
}
