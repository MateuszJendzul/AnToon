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
import com.artiline.antoon.database.UsersInterface;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    public static final String REGISTER_ACTIVITY_EXTRA = "register_activity_extra";
    private boolean password_visible = false;
    private boolean password_hidden = true;
    UsersInterface users_database = new UsersInterface();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity_layout);
        Log.d(TAG, "onCreate: RegisterActivity");

        EditText register_activity_layout_name_edit_text = findViewById(R.id.register_activity_layout_name_edit_text_ID);
        EditText register_activity_layout_password_edit_text = findViewById(R.id.register_activity_layout_password_edit_text_ID);
        EditText register_activity_layout_email_edit_text = findViewById(R.id.register_activity_layout_email_edit_text_ID);
        Button register_activity_layout_back_button = findViewById(R.id.register_activity_layout_back_button_ID);
        Button register_activity_layout_login_button = findViewById(R.id.register_activity_layout_login_button_ID);
        Button register_activity_layout_show_password_button = findViewById(R.id.register_activity_layout_show_password_button_ID);

        register_activity_layout_login_button.setOnClickListener(v -> {
            Log.d(TAG, "onClick: register_activity_layout_login_button");
            String user_name = register_activity_layout_name_edit_text.getText().toString();
            String user_password = register_activity_layout_password_edit_text.getText().toString();
            String user_email = register_activity_layout_email_edit_text.getText().toString();

            if (!register_activity_layout_name_edit_text.getText().toString().isEmpty()
                    && !register_activity_layout_password_edit_text.getText().toString().isEmpty()
                    && !register_activity_layout_email_edit_text.getText().toString().isEmpty()) {
                User register_activity_user = new User(user_name, user_email, user_password);
                users_database.addUserToList(register_activity_user);
                Intent register_activity_layout_login_button_intent = new Intent(
                        RegisterActivity.this, MainPageActivity.class);
                register_activity_layout_login_button_intent.putExtra(
                        REGISTER_ACTIVITY_EXTRA, register_activity_user);
                startActivity(register_activity_layout_login_button_intent);

            } else {
                Toast.makeText(RegisterActivity.this, "Fill in all fields",
                        Toast.LENGTH_SHORT).show();
            }
        });

        register_activity_layout_back_button.setOnClickListener(view -> {
            Log.d(TAG, "onClick: register_activity_layout_back_button");
            Intent register_activity_layout_back_button_intent = new Intent(
                    RegisterActivity.this, LoginRegisterActivity.class);
            startActivity(register_activity_layout_back_button_intent);
        });

        register_activity_layout_show_password_button.setOnClickListener(view -> {
            Log.d(TAG, "onClick: register_activity_layout_show_password_button");
            // if password is hidden (default), set transformation to null (it will become visible)
            // if password is visible, set transformation with parameter of new transformation (hides it)
            if (password_visible) {
                password_hidden = true;
                password_visible = false;
                register_activity_layout_password_edit_text.setTransformationMethod(new PasswordTransformationMethod());

            } else if (password_hidden) {
                password_visible = true;
                register_activity_layout_password_edit_text.setTransformationMethod(null);
            }

        });

    }
}
