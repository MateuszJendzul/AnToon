package com.artiline.antoon.Database;

import androidx.cardview.widget.CardView;

import com.artiline.antoon.Database.Models.Comics;

public interface ComicsClickListener {
    void onClick(Comics comics);

    void onLongClick(Comics comics, CardView cardView);
}
