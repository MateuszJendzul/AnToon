package com.artiline.antoon.Database.Adapters;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.artiline.antoon.R;

public class ComicsOnlyImageViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "ComicsOnlyImageViewHolder";
    CardView comicsCardViewImageOnly;
    ImageView comicsPicture;

    public ComicsOnlyImageViewHolder(@NonNull View itemView) {
        super(itemView);
        Log.i(TAG, "ComicsViewHolder: ");
        comicsPicture = itemView.findViewById(R.id.comics_picture_ID);
        comicsCardViewImageOnly = itemView.findViewById(R.id.comics_card_view_image_only_layout_ID);
    }
}
