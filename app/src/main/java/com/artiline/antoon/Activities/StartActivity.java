package com.artiline.antoon.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.artiline.antoon.database.User;

public class StartActivity extends AppCompatActivity {
    private static final String TAG = "StartActivity";
    // declare SP
    SharedPreferences startActivitySPReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: StartActivity");

        // initialize SP
        startActivitySPReceiver = getApplicationContext().getSharedPreferences(
                MainPageActivity.MAIN_PAGE_ACTIVITY_EXTRA, Context.MODE_PRIVATE);

        // get bool value from SP
        boolean loggedON = startActivitySPReceiver.getBoolean(
                "loggedON", false);

        Intent mainOrHomeActivityIntent;
        // check for MainPageActivity bool value based on fact if user did log in (true) or out (false)
        // if user did logout application will start from HomeActivity as by default
        if (loggedON) {
            mainOrHomeActivityIntent = new Intent(this, MainPageActivity.class);
            startActivity(mainOrHomeActivityIntent);

        } else {
            mainOrHomeActivityIntent = new Intent(this, HomeActivity.class);
            startActivity(mainOrHomeActivityIntent);
        }
    }
}
