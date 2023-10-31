package com.artiline.antoon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.artiline.antoon.R;
import com.artiline.antoon.database.User;

public class MainPageActivity extends AppCompatActivity {
    private static final String TAG = "MainPageActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: MainPageActivity");

        TextView main_page_activity_layout_user_name_text = findViewById(R.id.main_page_activity_layout_user_name_text_ID);
        Button main_page_activity_layout_back_button = findViewById(R.id.main_page_activity_layout_back_button_ID);

        Intent main_page_activity_intent = getIntent();
        User registered_user = (User) main_page_activity_intent.getSerializableExtra(
                "register_activity_user_extra");
        User logged_user = (User) main_page_activity_intent.getSerializableExtra(
                "login_activity_user_extra");

        if (registered_user != null) {
            main_page_activity_layout_user_name_text.setText(registered_user.getName());
        } else if (logged_user != null) {
            main_page_activity_layout_user_name_text.setText(logged_user.getName());
        } else {
            Toast.makeText(MainPageActivity.this, "ERROR: User not found!", Toast.LENGTH_LONG).show();
        }

        main_page_activity_layout_back_button.setOnClickListener(view -> {
            Log.d(TAG, "onClick: main_page_activity_layout_back_button");
            Intent main_page_activity_layout_back_button_intent = new Intent(
                    MainPageActivity.this, LoginRegisterActivity.class);
            startActivity(main_page_activity_layout_back_button_intent);
        });
    }
}
