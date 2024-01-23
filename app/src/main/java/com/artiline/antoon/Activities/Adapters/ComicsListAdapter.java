package com.artiline.antoon.Activities.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.artiline.antoon.Database.Models.Comics;
import com.artiline.antoon.R;

import java.util.List;

public class ComicsListAdapter extends RecyclerView.Adapter<ComicsViewHolder> {
    private static final String TAG = "ComicsListAdapter";
    Context context;
    List<Comics> comicsList;

    public ComicsListAdapter(Context context, List<Comics> comicsList) {
        Log.i(TAG, "ComicsListAdapter: ");
        this.context = context;
        this.comicsList = comicsList;
    }

    @NonNull
    @Override
    public ComicsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: ");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comics_card_view, parent, false);
        return new ComicsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicsViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: ");
        Comics currentComics = comicsList.get(position);
        // Bind your data to the CardView elements here
        holder.comicsPicture.setImageResource(currentComics.getComicsPicture());
        holder.chaptersCount.setText(currentComics.getAtChapter());
        holder.comicsName.setText(currentComics.getComicsName());

    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: ");
        return comicsList.size();
    }
}
