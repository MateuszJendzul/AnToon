package com.artiline.antoon.activities;

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
import com.artiline.antoon.database.User;
import com.artiline.antoon.database.UsersDatabase;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    UsersDatabase usersDatabase = new UsersDatabase();
    private boolean password_visible = false;
    private boolean password_hidden = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        Log.d(TAG, "onCreate: LoginActivity");

        EditText login_activity_layout_name_edit_text = findViewById(R.id.login_activity_layout_name_edit_text_ID);
        EditText login_activity_layout_password_edit_text = findViewById(R.id.login_activity_layout_password_edit_text_ID);
        Button login_activity_layout_back_button = findViewById(R.id.login_activity_layout_back_button_ID);
        Button login_activity_layout_login_button = findViewById(R.id.login_activity_layout_login_button_ID);
        Button login_activity_layout_show_password_button = findViewById(R.id.login_activity_layout_show_password_button_ID);

        login_activity_layout_login_button.setOnClickListener(v -> {
            Log.d(TAG, "onClick: login_activity_layout_login_button");
            String user_name = login_activity_layout_name_edit_text.getText().toString();
            String user_password = login_activity_layout_password_edit_text.getText().toString();

            if (!login_activity_layout_name_edit_text.getText().toString().isEmpty()
                    && !login_activity_layout_password_edit_text.getText().toString().isEmpty()) {

                if (usersDatabase.getUsersList().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "List of Users is empty!",
                            Toast.LENGTH_SHORT).show();

                } else if (usersDatabase.getUsersList().size() > 0) {
                    Intent login_activity_layout_login_button_intent = new Intent(
                            LoginActivity.this, MainPageActivity.class);

                    User user = loggedUser(user_name, user_password);
                    if (user != null) {
                        login_activity_layout_login_button_intent.putExtra(
                                "loggedUser", user);
                        startActivity(login_activity_layout_login_button_intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "There is no such user!",
                                Toast.LENGTH_SHORT).show();
                    }
                }

            } else {
                Toast.makeText(LoginActivity.this, "Fill in all fields!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        login_activity_layout_back_button.setOnClickListener(view -> {
            Log.d(TAG, "onClick: login_activity_layout_back_button");
            Intent login_activity_layout_back_button_intent = new Intent(
                    LoginActivity.this, LoginRegisterActivity.class);
            startActivity(login_activity_layout_back_button_intent);
        });

        login_activity_layout_show_password_button.setOnClickListener(view -> {
            Log.d(TAG, "onClick: login_activity_layout_show_password_button");
            // if password is hidden (default), set transformation to null (it will become visible)
            // if password is visible, set transformation with parameter of new transformation (hides it)
            if (password_visible) {
                password_hidden = true;
                password_visible = false;
                login_activity_layout_password_edit_text.setTransformationMethod(new PasswordTransformationMethod());

            } else if (password_hidden) {
                password_visible = true;
                login_activity_layout_password_edit_text.setTransformationMethod(null);
            }

        });
    }

    public User loggedUser(String userName, String userPassword) {
        for (int x = 0; x < usersDatabase.getUsersList().size(); x++) {
            if (userName.equals(usersDatabase.getUsersList().get(x).getName())) {
                if (userPassword.equals(usersDatabase.getUsersList().get(x).getPassword())) {
                    return usersDatabase.getUsersList().get(x);
                }
            }
            //TODO change so app checks name and password separately
        }
        return null;
    }

}
