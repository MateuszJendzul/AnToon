package com.artiline.antoon.Database.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.artiline.antoon.Database.Models.Comics;
import com.artiline.antoon.R;

import java.util.List;

public class ComicsAdapter extends RecyclerView.Adapter<ComicsViewHolder> {
    private static final String TAG = "ComicsAdapter";
    List<Comics> comicsList;

    public ComicsAdapter(List<Comics> comicsList) {
        Log.i(TAG, "ComicsAdapter: ");
        this.comicsList = comicsList;
    }

    @Override
    public void onBindViewHolder(@NonNull ComicsViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: ");
        Comics currentComics = comicsList.get(position);
        Context context = holder.itemView.getContext();

        // Retrieve dimensions from dimens.xml
        int targetWidth = (int) context.getResources().getDimension(R.dimen.comicsCardViewWidth);
        int targetHeight = (int) context.getResources().getDimension(R.dimen.comicsCardViewHeight);

        // Bind your data to the CardView elements here
        Picasso.get().load(currentComics.getComicsPicture())
                .resize(targetWidth, targetHeight)
                .centerCrop()
                .placeholder(R.drawable.default_comics_pic)
                .error(R.drawable.antoon_wallpaper)
                .into(holder.comicsPicture);
        holder.atChapter.setText(String.valueOf(currentComics.getAtChapter()));
        holder.newestChapter.setText(String.valueOf(currentComics.getNewestChapter()));
        holder.comicsName.setText(currentComics.getComicsName());
    }

    @NonNull
    @Override
    public ComicsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: ");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comics_card_view, parent, false);
        return new ComicsViewHolder(view);
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: ");
        return comicsList.size();
    }
}
