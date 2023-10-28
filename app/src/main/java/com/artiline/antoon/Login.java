package com.artiline.antoon;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    private static final String TAG = "ProfileSelect";
    private boolean password_visible = false;
    private boolean password_hidden = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        Log.d(TAG, "onCreate: ProfileSelect");

        EditText login_layout_name_edit_text = findViewById(R.id.login_layout_name_edit_text_ID);
        EditText login_layout_password_edit_text = findViewById(R.id.login_layout_password_edit_text_ID);
        Button login_layout_back_button = findViewById(R.id.login_layout_back_button_ID);
        Button login_layout_login_button = findViewById(R.id.login_layout_login_button_ID);
        Button login_layout_show_password_button = findViewById(R.id.login_layout_show_password_button_ID);

        login_layout_back_button.setOnClickListener(view -> {
            Log.d(TAG, "onClick: login_layout_back_button");
            Intent login_layout_back_button_intent = new Intent(
                    Login.this, MainActivity.class);
            startActivity(login_layout_back_button_intent);
        });

        login_layout_show_password_button.setOnClickListener(view -> {
            Log.d(TAG, "onClick: login_layout_show_password_button");
            // if password is hidden (default), set transformation to null (it will become visible)
            // if password is visible, set transformation with parameter of new transformation (hides it)
            if (password_visible) {
                password_hidden = true;
                password_visible = false;
                login_layout_password_edit_text.setTransformationMethod(new PasswordTransformationMethod());

            } else if (password_hidden) {
                password_visible = true;
                login_layout_password_edit_text.setTransformationMethod(null);
            }

        });
    }

}
