package com.artiline.antoon.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    public static final String LOGIN_ACTIVITY_EXTRA = "loginActivityExtra";
    private boolean passwordVisible = false;
    private boolean passwordHidden = true;
    UserDAO userDAO;
    // declare SP as MainPageActivity (to replace boolean onLogged value)
    SharedPreferences loginActivitySP;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout);
        Log.i(TAG, "onCreate: LoginActivity");
        userDAO = UserRoomDB.getInstance(this).usersDAO();

        // initialize SP
        loginActivitySP = getSharedPreferences(LOGIN_ACTIVITY_EXTRA, Context.MODE_PRIVATE);

        EditText loginActivityLayoutNameEditText = findViewById(R.id.login_activity_layout_name_edit_text_ID);
        EditText loginActivityLayoutPasswordEditText = findViewById(R.id.login_activity_layout_password_edit_text_ID);
        Button loginActivityLayoutBackButton = findViewById(R.id.login_activity_layout_back_button_ID);
        Button loginActivityLayoutLoginButton = findViewById(R.id.login_activity_layout_login_button_ID);
        Button loginActivityLayoutShowPasswordButton = findViewById(R.id.login_activity_layout_show_password_button_ID);

        loginActivityLayoutLoginButton.setOnClickListener(v -> {
            Log.i(TAG, "onClick: loginActivityLayoutLoginButton");
            String userName = loginActivityLayoutNameEditText.getText().toString();
            String userPassword = loginActivityLayoutPasswordEditText.getText().toString();

            if (!loginActivityLayoutNameEditText.getText().toString().isEmpty()
                    && !loginActivityLayoutPasswordEditText.getText().toString().isEmpty()) {
                int loggedUserID = loggedUserID(userName, userPassword);

                if (userDAO.getAll().isEmpty()) {
                    Toast.makeText(this, "List of Users is empty!",
                            Toast.LENGTH_SHORT).show();

                } else if (loggedUserID >= 0) {
                    loginActivitySP.edit().putInt("loggedUserID", loggedUserID).apply();
                    Log.d(TAG, "loginActivitySP.edit().putInt(\"loggedUserID\") = " + loggedUserID);
                    Intent loginActivityLayoutLoginButtonIntent = new Intent(
                            getApplicationContext(), MainPageActivity.class);
                    startActivity(loginActivityLayoutLoginButtonIntent);
                } else {
                    Log.e(TAG, "loggedUserID not found! ID is less than 0");
                }

            } else {
                Toast.makeText(this, "Fill in all fields!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        loginActivityLayoutBackButton.setOnClickListener(view -> {
            Log.i(TAG, "onClick: loginActivityLayoutBackButton");
            Intent login_activity_layout_back_button_intent = new Intent(
                    this, LoginRegisterActivity.class);
            startActivity(login_activity_layout_back_button_intent);
        });

        loginActivityLayoutShowPasswordButton.setOnClickListener(view -> {
            Log.i(TAG, "onClick: loginActivityLayoutShowPasswordButton");
            // if password is hidden (default), set transformation to null (it will become visible)
            // if password is visible, set transformation with parameter of new transformation (hides it)
            if (passwordVisible) {
                Log.d(TAG, "hidePassword");
                passwordHidden = true;
                passwordVisible = false;
                loginActivityLayoutPasswordEditText.setTransformationMethod(new PasswordTransformationMethod());

            } else if (passwordHidden) {
                Log.d(TAG, "showPassword");
                passwordVisible = true;
                loginActivityLayoutPasswordEditText.setTransformationMethod(null);
            }

        });
    }

    private int loggedUserID(String userName, String userPassword) {
        Log.i(TAG, "loggedUser: ");
        for (int x = 0; x <= userDAO.getAll().size(); x++) {
            if (userName.equals(userDAO.getAll().get(x).getName())) {
                if (userPassword.equals(userDAO.getAll().get(x).getPassword())) {
                    Log.d(TAG, "loggedUser: returning user: " + userDAO.getAll().get(x).getName()
                            + " with ID of: " + userDAO.getAll().get(x).getID());
                    return userDAO.getAll().get(x).getID();
                } else {
                    Log.d(TAG, "loggedUser: Passwords doesn't match!");
                    Toast.makeText(LoginActivity.this, "Passwords doesn't match!",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.d(TAG, "loggedUser: User not found!");
                Toast.makeText(LoginActivity.this, "User not found!", Toast.LENGTH_SHORT).show();
            }
        }
        return -1;
    }
}
