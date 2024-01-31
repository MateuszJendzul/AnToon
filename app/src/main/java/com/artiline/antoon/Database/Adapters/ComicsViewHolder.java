package com.artiline.antoon.Database.Adapters;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.artiline.antoon.R;

public class ComicsViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "ComicsViewHolder";
    ImageView comicsPicture;
    TextView comicsName, newestChapter, atChapter;

    public ComicsViewHolder(@NonNull View itemView) {
        super(itemView);
        Log.i(TAG, "ComicsViewHolder: ");
        comicsPicture = itemView.findViewById(R.id.comics_picture_ID);
        comicsName = itemView.findViewById(R.id.comics_name_ID);
        newestChapter = itemView.findViewById(R.id.newest_chapter_ID);
        atChapter = itemView.findViewById(R.id.at_chapter_ID);
    }
}
