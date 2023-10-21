package com.artiline.antoon;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    UsersDatabase users_database_IR = new UsersDatabase();
    TextView text_1_text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Start");
        setContentView(R.layout.main_activity_layout);

        users_database_IR.addUserToList(users_database_IR.createUser(
                "Matt", "matis8571@gmail.com", "123"));

        text_1_text = findViewById(R.id.text_1_ID);
        String text_1_text_string = "" + users_database_IR.displayUser(1);
        text_1_text.setText(text_1_text_string);
    }
}