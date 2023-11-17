package com.artiline.antoon.Activities.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.artiline.antoon.Database.Comics.ComicsDAO;
import com.artiline.antoon.Database.Comics.ComicsRoomDB;
import com.artiline.antoon.Database.ComicsClickListener;
import com.artiline.antoon.Database.Models.Comics;
import com.artiline.antoon.Database.User.UserDAO;
import com.artiline.antoon.Database.User.UserRoomDB;
import com.artiline.antoon.R;

import java.util.List;

public class ComicsListAdapter extends RecyclerView.Adapter<ComicsViewHolder> {
    private static final String TAG = "ComicsListAdapter";

    ComicsDAO comicsDAO;
    List<Comics> comicsList;
    Context context;
    ComicsClickListener listener;

    public ComicsListAdapter(Context context, List<Comics> comicsList, ComicsClickListener listener) {
        Log.i(TAG, "ComicsListAdapter: ");
        this.context = context;
        this.comicsList = comicsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ComicsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        comicsDAO = ComicsRoomDB.getInstance(context).comicsDAO();
        return new ComicsViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.comics_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ComicsViewHolder holder, int position) {
        holder.comicsPicture.setImageResource(comicsDAO.getComicsByID(position).getComicsPicture());
        holder.chaptersCount.setText(comicsDAO.getComicsByID(position).getAtChapter());
        holder.comicsName.setText(comicsDAO.getComicsByID(position).getComicsName());

        holder.comicsCardView.setOnClickListener(v -> listener.onClick(
                comicsList.get(holder.getAdapterPosition())));

        holder.comicsCardView.setOnLongClickListener(v -> {
            listener.onLongClick(
                    comicsList.get(holder.getAdapterPosition()), holder.comicsCardView);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
