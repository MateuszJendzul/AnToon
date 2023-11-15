package com.artiline.antoon.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.artiline.antoon.R;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    // declare layout objects
    TextView homeActivityLayoutWelcomeText;
    Button homeActivityLayoutLoginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_layout);
        Log.i(TAG, "onCreate: HomeActivity");

        // initialize layout objects
        homeActivityLayoutWelcomeText = findViewById(R.id.home_activity_layout_welcome_text_ID);
        homeActivityLayoutLoginButton = findViewById(R.id.home_activity_layout_login_button_ID);

        // displays text as application name
        String anToon = getString(R.string.app_name);
        homeActivityLayoutWelcomeText.setText(anToon);

        homeActivityLayoutLoginButton.setOnClickListener(view -> {
            Log.i(TAG, "onClick: homeActivityLayoutLoginButton");
            Intent homeActivityLayoutLoginButtonIntent = new Intent(
                    this, LoginRegisterActivity.class);
            startActivity(homeActivityLayoutLoginButtonIntent);
        });
    }
}