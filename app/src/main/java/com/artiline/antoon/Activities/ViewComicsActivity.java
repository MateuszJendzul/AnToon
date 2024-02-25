package com.artiline.antoon.Activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.artiline.antoon.R;

public class ViewComicsActivity extends AppCompatActivity {
    private static final String TAG = "ViewComicsActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_comics_activity_layout);
    }
}
