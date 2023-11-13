package com.artiline.antoon.activities.Database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.artiline.antoon.activities.Models.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insert(User user);

    @Query("SELECT * FROM User where ID = :ID")
    User getUserByID(long ID);

    @Query("SELECT * FROM User ORDER BY ID ASC")
    List<User> getAll();

    @Delete
    void deleteUserProfile(User user);
}
