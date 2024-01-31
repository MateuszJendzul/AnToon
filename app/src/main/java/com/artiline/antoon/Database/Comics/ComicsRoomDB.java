package com.artiline.antoon.Database.Comics;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.artiline.antoon.Database.Models.Comics;

@Database(entities = {Comics.class}, version = 1, exportSchema = false)
public abstract class ComicsRoomDB extends RoomDatabase {
    public abstract ComicsDAO comicsDAO();
    private static ComicsRoomDB comicsRoomDB;
    private static final String DATABASE_NAME = "comics";

    public static synchronized ComicsRoomDB getInstance(Context context) {
        if (comicsRoomDB == null) {
            comicsRoomDB = Room.databaseBuilder(context.getApplicationContext(), ComicsRoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return comicsRoomDB;
    }
}
