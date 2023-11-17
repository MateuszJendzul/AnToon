package com.artiline.antoon.Database.User;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.artiline.antoon.Database.Models.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserRoomDB extends RoomDatabase {
    public abstract UserDAO usersDAO();
    private static UserRoomDB userRoomDB;
    private static final String DATABASE_NAME = "users";

    public static synchronized UserRoomDB getInstance(Context context) {
        if (userRoomDB == null) {
            userRoomDB = Room.databaseBuilder(context.getApplicationContext(), UserRoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return userRoomDB;
    }
}
