package com.artiline.antoon.Database.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.artiline.antoon.Database.Models.Comics;
import com.artiline.antoon.R;

import java.util.List;

public class ComicsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "ComicsAdapter";
    private static final int VIEW_TYPE_NORMAL = 0;
    private static final int VIEW_TYPE_IMAGE_ONLY = 1;

    List<Comics> comicsList;
    ComicsViewHolder comicsViewHolder;
    ComicsOnlyImageViewHolder comicsOnlyImageViewHolder;

    public ComicsAdapter(List<Comics> comicsList) {
        Log.i(TAG, "ComicsAdapter: ");
        this.comicsList = comicsList;
    }

    @Override
    public int getItemViewType(int position) {
        // Return different view types based on your condition
        if (position == (comicsList.size() - 1)) {
            return VIEW_TYPE_IMAGE_ONLY;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: ");
        Comics currentComics = comicsList.get(position);
        Context context = holder.itemView.getContext();

        // Retrieve dimensions from dimens.xml
        int targetWidth = (int) context.getResources().getDimension(R.dimen.comicsCardViewWidth);
        int targetHeight = (int) context.getResources().getDimension(R.dimen.comicsCardViewHeight);
        int targetOnlyImageHeight = (int) context.getResources().getDimension(R.dimen.comicsCardOnlyImageViewHeight);

        if (holder instanceof ComicsViewHolder) {
            // Bind data to normal views
            comicsViewHolder = (ComicsViewHolder) holder;
            Picasso.get().load(currentComics.getComicsPicture())
                    .resize(targetWidth, targetHeight)
                    .centerCrop()
                    .placeholder(R.drawable.default_comics_pic)
                    .error(R.drawable.antoon_wallpaper)
                    .into(comicsViewHolder.comicsPicture);
            comicsViewHolder.atChapter.setText(String.valueOf(currentComics.getAtChapter()));
            comicsViewHolder.newestChapter.setText(String.valueOf(currentComics.getNewestChapter()));
            comicsViewHolder.comicsName.setText(currentComics.getComicsName());

        } else if (holder instanceof ComicsOnlyImageViewHolder) {
            // Bind data to views for image only
            comicsOnlyImageViewHolder = (ComicsOnlyImageViewHolder) holder;
            // Set image only data using Picasso library
            Picasso.get().load(currentComics.getComicsPicture())
                    .resize(targetWidth, targetOnlyImageHeight)
                    .centerCrop()
                    .placeholder(R.drawable.default_comics_pic)
                    .error(R.drawable.antoon_wallpaper)
                    .into(comicsOnlyImageViewHolder.comicsPicture);
        }
    }

    @NonNull
    @Override
    public ComicsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: ");
        View view;
        if (viewType == VIEW_TYPE_IMAGE_ONLY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.comics_card_view_image_only, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.comics_card_view, parent, false);
        }
        return new ComicsViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return comicsList.size();
    }
}
