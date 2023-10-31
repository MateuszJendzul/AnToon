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

public class LoginRegisterActivity extends AppCompatActivity {
    private static final String TAG = "LoginRegisterActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register_activity_layout);
        Log.d(TAG, "onCreate: LoginRegisterActivity");

        TextView login_register_layout_text = findViewById(R.id.login_register_activity_layout_text_ID);
        Button login_register_activity_layout_register_button = findViewById(R.id.login_register_activity_layout_register_button_ID);
        Button login_register_activity_layout_login_button = findViewById(R.id.login_register_activity_layout_login_button_ID);
        Button login_register_activity_layout_back_button = findViewById(R.id.login_register_layout_back_button_ID);

        String text_1_text_string = "Login into existing\nor register new profile";
        login_register_layout_text.setText(text_1_text_string);
        login_register_layout_text.setTextSize(26);

        login_register_activity_layout_register_button.setOnClickListener(view -> {
            Log.d(TAG, "onClick: login_register_activity_layout_register_button");
            Intent login_register_layout_register_button_intent = new Intent(
                    LoginRegisterActivity.this, RegisterActivity.class);
            startActivity(login_register_layout_register_button_intent);
        });

        login_register_activity_layout_login_button.setOnClickListener(view -> {
            Log.d(TAG, "onClick: login_register_activity_layout_login_button");
            Intent login_register_layout_login_button_intent = new Intent(
                    LoginRegisterActivity.this, LoginActivity.class);
            startActivity(login_register_layout_login_button_intent);
        });

        login_register_activity_layout_back_button.setOnClickListener(view -> {
            Log.d(TAG, "onClick: login_register_activity_layout_back_button");
            Intent login_register_layout_back_button_intent = new Intent(
                    LoginRegisterActivity.this, HomeActivity.class);
            startActivity(login_register_layout_back_button_intent);
        });
    }
}
