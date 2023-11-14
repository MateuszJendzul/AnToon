package com.artiline.antoon.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

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

        TextView loginRegisterLayoutText = findViewById(R.id.login_register_activity_layout_text_ID);
        Button loginRegisterActivityLayoutRegisterButton = findViewById(R.id.login_register_activity_layout_register_button_ID);
        Button loginRegisterActivityLayoutLoginButton = findViewById(R.id.login_register_activity_layout_login_button_ID);
        Button loginRegisterActivityLayoutBackButton = findViewById(R.id.login_register_layout_back_button_ID);

        String orString = "OR";
        loginRegisterLayoutText.setText(orString);
        loginRegisterLayoutText.setTextSize(26);

        loginRegisterActivityLayoutRegisterButton.setOnClickListener(view -> {
            Log.d(TAG, "onClick: loginRegisterActivityLayoutRegisterButton");
            Intent loginRegisterLayoutRegisterButtonIntent = new Intent(
                    LoginRegisterActivity.this, RegisterActivity.class);
            startActivity(loginRegisterLayoutRegisterButtonIntent);
        });

        loginRegisterActivityLayoutLoginButton.setOnClickListener(view -> {
            Log.d(TAG, "onClick: loginRegisterActivityLayoutLoginButton");
            Intent loginRegisterLayoutLoginButtonIntent = new Intent(
                    LoginRegisterActivity.this, LoginActivity.class);
            startActivity(loginRegisterLayoutLoginButtonIntent);
        });

        loginRegisterActivityLayoutBackButton.setOnClickListener(view -> {
            Log.d(TAG, "onClick: loginRegisterActivityLayoutBackButton");
            Intent loginRegisterLayoutBackButtonIntent = new Intent(
                    LoginRegisterActivity.this, HomeActivity.class);
            startActivity(loginRegisterLayoutBackButtonIntent);
        });
    }
}
