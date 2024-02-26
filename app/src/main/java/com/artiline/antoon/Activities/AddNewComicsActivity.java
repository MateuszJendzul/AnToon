package com.artiline.antoon.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.artiline.antoon.R;

public class AddNewComicsActivity extends AppCompatActivity {
    private static final String TAG = "AddNewComicsActivity";

    // declare layout objects
    Button addNewComicsActivityBackButtonID, addNewComicsActivityRegisterButtonID;
    ImageView addNewComicsActivityComicsImageViewID;
    EditText addNewComicsActivityURLEditTextID;

    // declare variables
    private String importedImage, importedName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_comics_activity_layout);
        Log.i(TAG, "onCreate: AddNewComicsActivity");

        // initialize layout objects
        addNewComicsActivityBackButtonID = findViewById(R.id.add_new_comics_activity_back_button_ID);
        addNewComicsActivityRegisterButtonID = findViewById(R.id.add_new_comics_activity_register_button_ID);
        addNewComicsActivityComicsImageViewID = findViewById(R.id.add_new_comics_activity_comics_image_view_ID);
        addNewComicsActivityURLEditTextID = findViewById(R.id.add_new_comics_activity_URL_edit_text_ID);

        // button to get back to the user page
        addNewComicsActivityBackButtonID.setOnClickListener(v -> {
            Intent addNewComicsActivityBackButtonIDIntent = new Intent(
                    AddNewComicsActivity.this, UserPageActivity.class);
            startActivity(addNewComicsActivityBackButtonIDIntent);
        });

        addNewComicsActivityRegisterButtonID.setOnClickListener(v -> {
            String URL = addNewComicsActivityURLEditTextID.getText().toString();

            if (!addNewComicsActivityURLEditTextID.getText().toString().isEmpty()) {
                
            }

        });


    }

    public String getImportedImage() {
        return importedImage;
    }

    public void setImportedImage(String importedImage) {
        this.importedImage = importedImage;
    }

    public String getImportedName() {
        return importedName;
    }

    public void setImportedName(String importedName) {
        this.importedName = importedName;
    }
}
