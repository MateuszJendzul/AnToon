package com.artiline.antoon.database;

import android.graphics.drawable.Drawable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.artiline.antoon.R;

public class AppBackground extends AppCompatActivity {
    ConstraintLayout homeActivityLayout = findViewById(R.id.home_activity_layout_ID);

    public void changeBackground(Drawable image) {
        homeActivityLayout.setBackground(image);
    }

    public void changeBackground(int color) {
        homeActivityLayout.setBackgroundColor(color);
    }
}
