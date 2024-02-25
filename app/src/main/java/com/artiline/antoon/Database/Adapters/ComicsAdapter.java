package com.artiline.antoon.Database.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.artiline.antoon.Database.ComicsClickListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
    ComicsClickListener listener;

    public ComicsAdapter(List<Comics> comicsList, ComicsClickListener listener) {
        Log.i(TAG, "ComicsAdapter: ");
        this.comicsList = comicsList;
        this.listener = listener;
    }

    // Return different view types based on your condition
    @Override
    public int getItemViewType(int position) {
        // If position of currently processed (viewed) object equals size of comicsList
        // (position points to the last place on the list)
        if (position == (comicsList.size() - 1) && !comicsList.isEmpty()) {
            return VIEW_TYPE_IMAGE_ONLY;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: ");
        Comics currentComics = comicsList.get(position);

        if (holder instanceof ComicsViewHolder) {
            // Bind data to normal views
            comicsViewHolder = (ComicsViewHolder) holder;
            // Set image of currently processed (viewed) object using Picasso library
            // (added dependency to build.gradle scripts)
            Picasso.get().load(currentComics.getComicsPicture())
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.default_comics_pic)
                    .error(R.drawable.antoon_wallpaper)
                    .into(comicsViewHolder.comicsPicture);
            comicsViewHolder.atChapter.setText(String.valueOf(currentComics.getAtChapter()));
            comicsViewHolder.newestChapter.setText(String.valueOf(currentComics.getNewestChapter()));
            comicsViewHolder.comicsName.setText(currentComics.getComicsName());

            ((ComicsViewHolder) holder).comicsCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(comicsList.get(holder.getAdapterPosition()));
                }
            });

            ((ComicsViewHolder) holder).comicsCardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onLongClick(comicsList.get(holder.getAdapterPosition()), ((ComicsViewHolder) holder).comicsCardView);
                    return true;
                }
            });

        } else if (holder instanceof ComicsOnlyImageViewHolder) {
            // Bind data to views for image only
            comicsOnlyImageViewHolder = (ComicsOnlyImageViewHolder) holder;
            // Set image only data using Picasso library
            Picasso.get().load(currentComics.getComicsPicture())
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.default_comics_pic)
                    .error(R.drawable.antoon_wallpaper)
                    .into(comicsOnlyImageViewHolder.comicsPicture);

            ((ComicsOnlyImageViewHolder) holder).comicsCardViewImageOnly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(comicsList.get(holder.getAdapterPosition()));
                }
            });
        }


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: ");
        View view;
        // If getItemViewType() return value of currently processed (viewed) object equals
        // VIEW_TYPE_IMAGE_ONLY (which equals integer 1) inflate layout of comics_card_view_image_only_layout
        // which which results that this object will be displayed only be its image (as set in layout)
        if (viewType == VIEW_TYPE_IMAGE_ONLY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.comics_card_view_image_only_layout, parent, false);
            return new ComicsOnlyImageViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.comics_card_view_layout, parent, false);
            return new ComicsViewHolder(view);
        }
    }

    @Override
    public int getItemCount() {
        return comicsList.size();
    }
}
