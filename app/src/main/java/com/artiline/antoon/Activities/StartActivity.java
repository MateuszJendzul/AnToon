package com.artiline.antoon.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.artiline.antoon.Database.User.UserDAO;
import com.artiline.antoon.Database.User.UserRoomDB;
import com.artiline.antoon.Database.Models.User;

public class StartActivity extends AppCompatActivity {
    private static final String TAG = "StartActivity";

    // declare and initialize name strings
    private static final String START_ACTIVITY_EXTRA = "startActivityExtra";

    // declare instances
    SharedPreferences startActivitySP, startActivitySPReceiver, startActivityMainPageSPReceiver;
    UserDAO userDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: StartActivity");

        // initialize instances
        userDAO = UserRoomDB.getInstance(this).usersDAO();
        startActivitySP = getSharedPreferences(START_ACTIVITY_EXTRA, Context.MODE_PRIVATE);
        startActivitySPReceiver = getApplicationContext().getSharedPreferences(
                START_ACTIVITY_EXTRA, Context.MODE_PRIVATE);
        startActivityMainPageSPReceiver = getApplicationContext().getSharedPreferences(
                UserPageActivity.MAIN_PAGE_ACTIVITY_EXTRA, Context.MODE_PRIVATE);

        // used to limit admin profile creation to one per application
        boolean adminCreated = startActivitySPReceiver.getBoolean("adminCreated", false);

        // when there is none, creates admin profile with default values
        if (!adminCreated) {
            createAdmin();
            startActivitySP.edit().putBoolean("adminCreated", true).apply();
        }

        // get bool value from SP
        boolean loggedON = startActivityMainPageSPReceiver.getBoolean(UserPageActivity.LOGGED_ON_BOOL_EXTRA, false);
        Log.d(TAG, "getting boolean loggedON from startActivitySPReceiverMain boolean: " + loggedON);

        Intent startActivityIntent;

        // check for UserPageActivity bool value based on fact if user did log in (true) or out (false)
        // if user did logout application will start from HomeActivity as by default
        if (loggedON) {
            Log.d(TAG, "loggedON: true");
            startActivityIntent = new Intent(this, UserPageActivity.class);
            startActivity(startActivityIntent);

        } else {
            Log.d(TAG, "loggedON: false");
            startActivityIntent = new Intent(this, HomeActivity.class);
            startActivity(startActivityIntent);
        }
    }

    /**
     * Creates admin profile with fixed variables properties, such as: name, email and password.
     */
    private void createAdmin() {
        Log.i(TAG, "createAdmin:");
        User admin = new User();
        admin.setUserID(0);
        admin.setName("admin");
        admin.setEmail("admin@email");
        admin.setPassword("admin");
        userDAO.insert(admin);
    }
}
