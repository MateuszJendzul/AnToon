package com.artiline.antoon.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.artiline.antoon.R;
import com.artiline.antoon.database.UsersInterface;

public class HomeActivity extends AppCompatActivity {
    UsersInterface users_database = new UsersInterface();
    private static final String TAG = "HomeActivity";
    private static boolean admin_created = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_layout);
        Log.d(TAG, "onCreate: HomeActivity");

        TextView home_activity_layout_welcome_text = findViewById(R.id.home_activity_layout_welcome_text_ID);
        Button home_activity_layout_login_button = findViewById(R.id.home_activity_layout_login_button_ID);

        String text_1_text_string = "AnToon";
        home_activity_layout_welcome_text.setText(text_1_text_string);

        if (!admin_created) {
            createAdmin();
            admin_created = true;
        }

        home_activity_layout_login_button.setOnClickListener(view -> {
            Log.d(TAG, "onClick: home_activity_layout_login_button");
            Intent home_activity_layout_login_button_intent = new Intent(
                    this, LoginRegisterActivity.class);
            startActivity(home_activity_layout_login_button_intent);
        });

    }

    private void createAdmin() {
        users_database.addUserToList(users_database.createUser(
                "admin", "admin@admin.com", "admin"));
    }
}