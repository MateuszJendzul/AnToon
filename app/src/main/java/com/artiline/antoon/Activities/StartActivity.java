package com.artiline.antoon.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.artiline.antoon.Database.UserDAO;
import com.artiline.antoon.Database.UserRoomDB;
import com.artiline.antoon.Models.User;

public class StartActivity extends AppCompatActivity {
    private static final String TAG = "StartActivity";
    private static final String START_ACTIVITY_EXTRA = "startActivityExtra";
    // declare SP
    SharedPreferences startActivitySP, startActivitySPReceiver, startActivityMainPageSPReceiver;
    UserDAO userDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: StartActivity");
        userDAO = UserRoomDB.getInstance(this).usersDAO();
        startActivitySP = getSharedPreferences(START_ACTIVITY_EXTRA, Context.MODE_PRIVATE);
        startActivitySPReceiver = getApplicationContext().getSharedPreferences(
                START_ACTIVITY_EXTRA, Context.MODE_PRIVATE);

        boolean adminCreated = startActivitySPReceiver.getBoolean("adminCreated", false);
        // when there is none, creates admin profile with default values
        if (!adminCreated) {
            createAdmin();
            startActivitySP.edit().putBoolean("adminCreated", true).apply();
        }

        // initialize SP
        startActivityMainPageSPReceiver = getApplicationContext().getSharedPreferences(
                MainPageActivity.MAIN_PAGE_ACTIVITY_EXTRA, Context.MODE_PRIVATE);
        // get bool value from SP
        boolean loggedON = startActivityMainPageSPReceiver.getBoolean(MainPageActivity.LOGGED_ON_BOOL_EXTRA, false);
        Log.d(TAG, "getting boolean loggedON from startActivitySPReceiverMain boolean: " + loggedON);

        Intent startActivityIntent;

        // check for MainPageActivity bool value based on fact if user did log in (true) or out (false)
        // if user did logout application will start from HomeActivity as by default
        if (loggedON) {
            Log.d(TAG, "loggedON: true");
            startActivityIntent = new Intent(this, MainPageActivity.class);
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
        Log.i(TAG, "createAdmin: admin created");
        User admin = new User("admin", "admin@admin.com", "admin");
        userDAO.insert(admin);
    }
}
