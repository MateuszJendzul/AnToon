package com.artiline.antoon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.artiline.antoon.R;
import com.artiline.antoon.database.UsersInterface;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_layout);
        Log.i(TAG, "onCreate: HomeActivity");

        TextView homeActivityLayoutWelcomeText = findViewById(R.id.home_activity_layout_welcome_text_ID);
        Button homeActivityLayoutLoginButton = findViewById(R.id.home_activity_layout_login_button_ID);

        String anToon = "AnToon";
        homeActivityLayoutWelcomeText.setText(anToon);


        homeActivityLayoutLoginButton.setOnClickListener(view -> {
            Log.i(TAG, "onClick: homeActivityLayoutLoginButton");
            Intent homeActivityLayoutLoginButtonIntent = new Intent(
                    this, LoginRegisterActivity.class);
            startActivity(homeActivityLayoutLoginButtonIntent);
        });
    }
}