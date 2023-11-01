package com.artiline.antoon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.artiline.antoon.R;
import com.artiline.antoon.database.User;

public class MainPageActivity extends AppCompatActivity {
    private static final String TAG = "MainPageActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_acivity_layout);
        Log.d(TAG, "onCreate: MainPageActivity");

        TextView main_page_activity_layout_user_name_text = findViewById(R.id.main_page_activity_layout_user_name_text_ID);
        Button main_page_activity_layout_back_button = findViewById(R.id.main_page_activity_layout_back_button_ID);

        User user = null;
        String main_page_activity_layout_user_name_text_string = null;
        Intent main_page_activity_intent = getIntent();

        if (main_page_activity_intent.hasExtra(LoginActivity.LOGIN_ACTIVITY_EXTRA)) {
            user = main_page_activity_intent.getParcelableExtra(LoginActivity.LOGIN_ACTIVITY_EXTRA);

        } else if (main_page_activity_intent.hasExtra(RegisterActivity.REGISTER_ACTIVITY_EXTRA)) {
            user = main_page_activity_intent.getParcelableExtra(RegisterActivity.REGISTER_ACTIVITY_EXTRA);
        }

        main_page_activity_layout_user_name_text_string = user != null ? user.getName() : "null";
        main_page_activity_layout_user_name_text.setText(main_page_activity_layout_user_name_text_string);

        main_page_activity_layout_back_button.setOnClickListener(view -> {
            Log.d(TAG, "onClick: main_page_activity_layout_back_button");
            Intent main_page_activity_layout_back_button_intent = new Intent(
                    MainPageActivity.this, LoginRegisterActivity.class);
            startActivity(main_page_activity_layout_back_button_intent);
        });
    }
}
