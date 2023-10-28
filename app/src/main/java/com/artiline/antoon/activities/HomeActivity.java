package com.artiline.antoon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.artiline.antoon.R;
import com.artiline.antoon.database.UsersDatabase;

public class HomeActivity extends AppCompatActivity {
    UsersDatabase users_database = new UsersDatabase();
    private static final String TAG = "HomeActivity";
    private static boolean adminCreated = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_layout);
        Log.d(TAG, "onCreate: HomeActivity");

        TextView home_activity_layout_welcome_text = findViewById(R.id.home_activity_layout_welcome_text_ID);
        Button home_activity_layout_login_button = findViewById(R.id.home_activity_layout_login_button_ID);

        String text_1_text_string = "AnToon";
        home_activity_layout_welcome_text.setText(text_1_text_string);
        home_activity_layout_welcome_text.setTextSize(42);

        if (!adminCreated) {
            createAdmin();
            adminCreated = true;
        }

        home_activity_layout_login_button.setOnClickListener(view -> {
            Log.d(TAG, "onClick: home_activity_layout_login_button");
            Intent home_activity_layout_login_button_intent = new Intent(HomeActivity.this,
                    LoginRegisterActivity.class);
            startActivity(home_activity_layout_login_button_intent);
        });

    }

    private void createAdmin() {
        users_database.addUserToList(users_database.createUser(
                "Admin", "admin@admin.com", "admin"));
    }

}