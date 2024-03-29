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
import com.artiline.antoon.Database.User.UserDAO;
import com.artiline.antoon.Database.User.UserRoomDB;
import com.artiline.antoon.Database.Models.User;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private static final String REGISTER_ACTIVITY_EXTRA = "registerActivityExtra";

    // declare and initialize variables
    private boolean hidePassword = false;
    private boolean showPassword = true;
    private static int ID = 1;

    // declare layout objects
    EditText registerActivityLayoutNameEditText, registerActivityLayoutPasswordEditText, registerActivityLayoutEmailEditText;
    Button registerActivityLayoutBackButton, registerActivityLayoutLoginButton, registerActivityLayoutShowPasswordButton;

    //declare instances
    UserDAO userDAO;
    SharedPreferences loginActivitySP, registerActivitySP, registerActivitySPReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity_layout);
        Log.i(TAG, "onCreate: RegisterActivity");

        // initialize instances
        userDAO = UserRoomDB.getInstance(this).usersDAO();
        loginActivitySP = getSharedPreferences(LoginActivity.LOGIN_ACTIVITY_EXTRA, Context.MODE_PRIVATE);
        registerActivitySP = getSharedPreferences(REGISTER_ACTIVITY_EXTRA, Context.MODE_PRIVATE);
        registerActivitySPReceiver = getApplicationContext().getSharedPreferences(
                REGISTER_ACTIVITY_EXTRA, Context.MODE_PRIVATE);

        // initialize layout objects
        registerActivityLayoutNameEditText = findViewById(R.id.register_activity_layout_name_edit_text_ID);
        registerActivityLayoutPasswordEditText = findViewById(R.id.register_activity_layout_password_edit_text_ID);
        registerActivityLayoutEmailEditText = findViewById(R.id.register_activity_layout_email_edit_text_ID);
        registerActivityLayoutBackButton = findViewById(R.id.register_activity_layout_back_button_ID);
        registerActivityLayoutLoginButton = findViewById(R.id.register_activity_layout_login_button_ID);
        registerActivityLayoutShowPasswordButton = findViewById(R.id.register_activity_layout_show_password_button_ID);

        registerActivityLayoutLoginButton.setOnClickListener(v -> {
            Log.i(TAG, "onClick: registerActivityLayoutLoginButton");
            // strings initialized with texts provided by user
            String userName = registerActivityLayoutNameEditText.getText().toString();
            String userPassword = registerActivityLayoutPasswordEditText.getText().toString();
            String userEmail = registerActivityLayoutEmailEditText.getText().toString();

            if (!registerActivityLayoutNameEditText.getText().toString().isEmpty()
                    && !registerActivityLayoutPasswordEditText.getText().toString().isEmpty()
                    && !registerActivityLayoutEmailEditText.getText().toString().isEmpty()) {

                ID = registerActivitySPReceiver.getInt("ID", 1);
                Log.d(TAG, "registerActivitySPReceiver.getInt(\"ID\" , 1)");

                // create User object using strings based on user input
                User newUser = new User();
                newUser.setUserID(ID);
                newUser.setName(userName);
                newUser.setPassword(userPassword);
                newUser.setEmail(userEmail);
                // uploads (inserts) newly created user object to DAO List of Users
                userDAO.insert(newUser);
                Log.i(TAG, "User: constructor: " + "ID: " + userDAO.getAll().get(ID).getUserID() +
                        " Name: " + userDAO.getAll().get(ID).getName() +
                        " Password: " + userDAO.getAll().get(ID).getPassword() +
                        " Email: " + userDAO.getAll().get(ID).getEmail());
                //TODO prevent user from creating new profile with same name as existing one

                // use SP to send int value representing ID of currently registered user
                loginActivitySP.edit().putInt("loggedUserID", userDAO.getAll().get(ID).getUserID()).apply();
                Log.d(TAG, "Registered User: " + newUser.getName()
                        + " with ID of: " + userDAO.getAll().get(ID).getUserID());
                // increment ID and put in into SP to later receive it and set it as new user ID
                ID++;
                Log.e(TAG, "!!!!!!!!ID = " + ID);
                registerActivitySP.edit().putInt("ID", ID).apply();
                // after creating user and sending its ID, starts main user page screen
                Intent registerActivityLayoutLoginButtonIntent = new Intent(
                        RegisterActivity.this, UserPageActivity.class);
                startActivity(registerActivityLayoutLoginButtonIntent);

            } else {
                Log.d(TAG, "empty fields");
                Toast.makeText(RegisterActivity.this, "Fill in all fields",
                        Toast.LENGTH_SHORT).show();
            }
        });

        registerActivityLayoutBackButton.setOnClickListener(view -> {
            Log.i(TAG, "onClick: registerActivityLayoutBackButton");
            finish();
        });

        // button allowing to show and hide typed password
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
