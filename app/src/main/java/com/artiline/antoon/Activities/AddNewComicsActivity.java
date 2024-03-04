package com.artiline.antoon.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.artiline.antoon.Database.Comics.ComicsRepository;
import com.artiline.antoon.Database.Models.Comics;
import com.artiline.antoon.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewComicsActivity extends AppCompatActivity {
    private static final String TAG = "AddNewComicsActivity";

    // declare layout objects
    Button addNewComicsActivityBackButtonID, addNewComicsActivityRegisterButtonID;
    ImageView addNewComicsActivityComicsImageViewID;
    EditText addNewComicsActivityURLEditTextID;

    //DEBUG
    TextView textView1;

    // declare instances
    ComicsRepository comicsRepository;

    // declare variables
    private String importedName, comicsNotFound, URLString;
    private int importedImage;

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
        //DEBUG
        textView1 = findViewById(R.id.textView1);

        // initialize instances
        comicsRepository = new ComicsRepository();

        // button to get back to the user page
        addNewComicsActivityBackButtonID.setOnClickListener(v -> {
            Intent addNewComicsActivityBackButtonIDIntent = new Intent(
                    AddNewComicsActivity.this, UserPageActivity.class);
            startActivity(addNewComicsActivityBackButtonIDIntent);
        });

        addNewComicsActivityRegisterButtonID.setOnClickListener(v -> {

            if (!addNewComicsActivityURLEditTextID.getText().toString().isEmpty()) {
                URLString = addNewComicsActivityURLEditTextID.getText().toString();

                comicsRepository.getComics(URLString, new Callback<Comics>() {
                    @Override
                    public void onResponse(Call<Comics> call, Response<Comics> response) {
                        Comics importedComics = response.body();
                        if (importedComics != null) {
                            setImportedImage(importedComics.getComicsPicture());

                        } else {
                            setImportedImage(R.drawable.comics_default_image);
                        }

                        if (importedComics != null) {
                            setImportedName(importedComics.getComicsName());
                            textView1.setText(getImportedName());

                        } else {
                            setComicsNotFound("Comics Not Found!");
                            setImportedName(getComicsNotFound());
                        }

                        // Populate your DAO object with the data from the comic object
                    }

                    @Override
                    public void onFailure(Call<Comics> call, Throwable t) {

                    }
                });
            }

        });


    }

    public int getImportedImage() {
        return importedImage;
    }

    public void setImportedImage(int importedImage) {
        this.importedImage = importedImage;
    }

    public String getImportedName() {
        return importedName;
    }

    public void setImportedName(String importedName) {
        this.importedName = importedName;
    }

    public String getURLString() {
        return URLString;
    }

    public void setURLString(String URLString) {
        this.URLString = URLString;
    }

    public String getComicsNotFound() {
        return comicsNotFound;
    }

    public void setComicsNotFound(String comicsNotFound) {
        this.comicsNotFound = comicsNotFound;
    }
}
