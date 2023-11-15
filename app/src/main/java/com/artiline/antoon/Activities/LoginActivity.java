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

    // declare and initialize name strings
    public static final String LOGIN_ACTIVITY_EXTRA = "loginActivityExtra";

    // declare and initialize variables
    private boolean passwordVisible = false;
    private boolean passwordHidden = true;

    // declare layout objects
    EditText loginActivityLayoutNameEditText, loginActivityLayoutPasswordEditText;
    Button loginActivityLayoutBackButton, loginActivityLayoutLoginButton, loginActivityLayoutShowPasswordButton;

    //declare instances
    UserDAO userDAO;
    SharedPreferences loginActivitySP;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout);
        Log.i(TAG, "onCreate: LoginActivity");

        // initialize instances
        userDAO = UserRoomDB.getInstance(this).usersDAO();
        loginActivitySP = getSharedPreferences(LOGIN_ACTIVITY_EXTRA, Context.MODE_PRIVATE);

        // initialize layout objects
        loginActivityLayoutNameEditText = findViewById(R.id.login_activity_layout_name_edit_text_ID);
        loginActivityLayoutPasswordEditText = findViewById(R.id.login_activity_layout_password_edit_text_ID);
        loginActivityLayoutBackButton = findViewById(R.id.login_activity_layout_back_button_ID);
        loginActivityLayoutLoginButton = findViewById(R.id.login_activity_layout_login_button_ID);
        loginActivityLayoutShowPasswordButton = findViewById(R.id.login_activity_layout_show_password_button_ID);

        loginActivityLayoutLoginButton.setOnClickListener(v -> {
            Log.i(TAG, "onClick: loginActivityLayoutLoginButton");
            String userName = loginActivityLayoutNameEditText.getText().toString();
            String userPassword = loginActivityLayoutPasswordEditText.getText().toString();

            // if all fields are filled
            if (!loginActivityLayoutNameEditText.getText().toString().isEmpty()
                    && !loginActivityLayoutPasswordEditText.getText().toString().isEmpty()) {
                int loggedUserID = findUserID(userName, userPassword);
                // use SP to send int value representing ID of currently logged user
                loginActivitySP.edit().putInt("loggedUserID", loggedUserID).apply();

                // should never occur, default "admin" user is created on first application launch
                if (userDAO.getAll().isEmpty()) {
                    Toast.makeText(this, "List of Users is empty!",
                            Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Empty list, Check for Admin profile!");

                    // if int equals (or is bigger than) 0, launch intent and change page to MainPageActivity
                } else if (loggedUserID >= 0) {
                    Log.d(TAG, "loggedUserID = " + loggedUserID + " launching MainPageActivity");
                    Intent loginActivityLayoutLoginButtonIntent = new Intent(
                            LoginActivity.this, MainPageActivity.class);
                    startActivity(loginActivityLayoutLoginButtonIntent);

                } else {
                    Log.e(TAG, "loggedUserID not found! ID is less than 0");
                    Log.e(TAG, "Possibly loggedUserID() method returned -1 when no matching user was found");
                }

            } else {
                Toast.makeText(this, "Fill in all fields!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        loginActivityLayoutBackButton.setOnClickListener(view -> {
            Log.i(TAG, "onClick: loginActivityLayoutBackButton");
            finish();
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

    /**
     * Returns ID of user when provided parameters are successfully matched with ID of User object from List of Users from DAO.
     *
     * @param userName     user name to compare
     * @param userPassword user password to compare
     * @return returns ID of matched User object
     */
    private int findUserID(String userName, String userPassword) {
        Log.i(TAG, "loggedUser: ");
        for (int x = 0; x < userDAO.getAll().size(); x++) {
            if (userName.equals(userDAO.getUserByID(x).getName())) {
                if (userPassword.equals(userDAO.getUserByID(x).getPassword())) {
                    Log.d(TAG, "Returning user: " + userDAO.getUserByID(x).getName()
                            + " with ID of: " + userDAO.getUserByID(x).getID());
                    return userDAO.getUserByID(x).getID();
                } else {
                    Log.d(TAG, "Passwords doesn't match!");
                    Toast.makeText(LoginActivity.this, "Passwords doesn't match!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
        Log.d(TAG, "User name not found!");
        Toast.makeText(LoginActivity.this, "User name not found!!",
                Toast.LENGTH_SHORT).show();
        return -1;
    }
}
