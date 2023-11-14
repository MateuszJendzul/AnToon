package com.artiline.antoon.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.artiline.antoon.R;
import com.artiline.antoon.Database.UserDAO;
import com.artiline.antoon.Database.UserRoomDB;
import com.artiline.antoon.Models.User;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private boolean hidePassword = false;
    private boolean showPassword = true;
    UserDAO userDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity_layout);
        Log.i(TAG, "onCreate: RegisterActivity");
        userDAO = UserRoomDB.getInstance(this).usersDAO();

        EditText registerActivityLayoutNameEditText = findViewById(R.id.register_activity_layout_name_edit_text_ID);
        EditText registerActivityLayoutPasswordEditText = findViewById(R.id.register_activity_layout_password_edit_text_ID);
        EditText registerActivityLayoutEmailEditText = findViewById(R.id.register_activity_layout_email_edit_text_ID);
        Button registerActivityLayoutBackButton = findViewById(R.id.register_activity_layout_back_button_ID);
        Button registerActivityLayoutLoginButton = findViewById(R.id.register_activity_layout_login_button_ID);
        Button registerActivityLayoutShowPasswordButton = findViewById(R.id.register_activity_layout_show_password_button_ID);

        registerActivityLayoutLoginButton.setOnClickListener(v -> {
            Log.i(TAG, "onClick: registerActivityLayoutLoginButton");
            String userName = registerActivityLayoutNameEditText.getText().toString();
            String userPassword = registerActivityLayoutPasswordEditText.getText().toString();
            String userEmail = registerActivityLayoutEmailEditText.getText().toString();

            if (!registerActivityLayoutNameEditText.getText().toString().isEmpty()
                    && !registerActivityLayoutPasswordEditText.getText().toString().isEmpty()
                    && !registerActivityLayoutEmailEditText.getText().toString().isEmpty()) {

                User newUser = new User(userName, userEmail, userPassword);
                userDAO.insert(newUser);
                Log.d(TAG, "Registered User: " + newUser.getName()
                        + " with ID of: " + newUser.getID());
                Intent registerActivityLayoutLoginButtonIntent = new Intent(
                        RegisterActivity.this, MainPageActivity.class);
                startActivity(registerActivityLayoutLoginButtonIntent);

            } else {
                Log.d(TAG, "empty fields");
                Toast.makeText(RegisterActivity.this, "Fill in all fields",
                        Toast.LENGTH_SHORT).show();
            }
        });

        registerActivityLayoutBackButton.setOnClickListener(view -> {
            Log.i(TAG, "onClick: registerActivityLayoutBackButton");
            Intent registerActivityLayoutBackButtonIntent = new Intent(
                    RegisterActivity.this, LoginRegisterActivity.class);
            startActivity(registerActivityLayoutBackButtonIntent);
        });

        registerActivityLayoutShowPasswordButton.setOnClickListener(view -> {
            Log.i(TAG, "onClick: registerActivityLayoutShowPasswordButton");
            // if password is hidden (default), set transformation to null (it will become visible)
            // if password is visible, set transformation with parameter of new transformation (hides it)
            if (hidePassword) {
                Log.d(TAG, "hidePassword");
                showPassword = true;
                hidePassword = false;
                registerActivityLayoutPasswordEditText.setTransformationMethod(new PasswordTransformationMethod());

            } else if (showPassword) {
                Log.d(TAG, "showPassword");
                hidePassword = true;
                registerActivityLayoutPasswordEditText.setTransformationMethod(null);
            }

        });

    }
}
