package com.artiline.antoon;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.artiline.antoon.database.UsersDatabase;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static boolean adminCreated = false;
    UsersDatabase users_database_IR = new UsersDatabase();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        Log.d(TAG, "onCreate: Start");

        TextView main_activity_layout_welcome_text = findViewById(R.id.main_activity_layout_welcome_text_ID);
        Button main_activity_layout_login_button = findViewById(R.id.main_activity_layout_login_button_ID);

        String text_1_text_string = "AnToon";
        main_activity_layout_welcome_text.setText(text_1_text_string);
        main_activity_layout_welcome_text.setTextSize(42);

        if (!adminCreated) {
            createAdmin();
            adminCreated = true;
        }

        main_activity_layout_login_button.setOnClickListener(view -> {
            Log.d(TAG, "onClick: main_activity_layout_login_button");
            Intent main_activity_layout_login_button_intent = new Intent(MainActivity.this, Login.class);
            startActivity(main_activity_layout_login_button_intent);
        });

    }

    private void createAdmin() {
        users_database_IR.addUserToList(users_database_IR.createUser(
                "Admin", "admin@admin.com", "admin"));
    }

}