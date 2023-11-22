package com.artiline.antoon.Activities.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.artiline.antoon.Database.Comics.ComicsDAO;
import com.artiline.antoon.Database.Comics.ComicsRoomDB;
import com.artiline.antoon.Database.ComicsClickListener;

import com.artiline.antoon.R;


public class ComicsListAdapter extends RecyclerView.Adapter<ComicsViewHolder> {
    private static final String TAG = "ComicsListAdapter";

    ComicsDAO comicsDAO;
    Context context;
    ComicsClickListener listener;

    public ComicsListAdapter(Context context, ComicsClickListener listener) {
        Log.i(TAG, "ComicsListAdapter: ");
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ComicsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: ");
        return new ComicsViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.comics_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ComicsViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: ");
        comicsDAO = ComicsRoomDB.getInstance(context).comicsDAO();
        holder.comicsPicture.setImageResource(comicsDAO.getComicsByID(position).getComicsPicture());
        holder.chaptersCount.setText(comicsDAO.getComicsByID(position).getAtChapter());
        holder.comicsName.setText(comicsDAO.getComicsByID(position).getComicsName());

        holder.comicsCardView.setOnClickListener(v -> listener.onClick(
                comicsDAO.getComicsByID(holder.getAdapterPosition())));

        holder.comicsCardView.setOnLongClickListener(v -> {
            listener.onLongClick(
                    comicsDAO.getComicsByID(holder.getAdapterPosition()), holder.comicsCardView);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: " + comicsDAO.getAll().size());
        return comicsDAO.getAll().size();
    }

}
