package com.artiline.antoon.Activities.Adapters;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.artiline.antoon.R;

public class ComicsViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "ComicsViewHolder";
    ImageView comicsPicture;
    CardView comicsCardView;
    TextView comicsName, chaptersCount;

    public ComicsViewHolder(@NonNull View itemView) {
        super(itemView);
        Log.i(TAG, "ComicsViewHolder: ");
        comicsPicture = itemView.findViewById(R.id.comics_picture_ID);
        comicsCardView = itemView.findViewById(R.id.comics_card_view_ID);
        comicsName = itemView.findViewById(R.id.comics_name_ID);
        chaptersCount = itemView.findViewById(R.id.chapters_count_ID);
    }
}
