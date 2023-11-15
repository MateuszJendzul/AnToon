package com.artiline.antoon.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.artiline.antoon.Models.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insert(User user);

    @Query("SELECT * FROM User where ID = :ID")
    User getUserByID(long ID);

    @Query("SELECT * FROM User ORDER BY ID ASC")
    List<User> getAll();

    @Update
    void updateUser(User user);

    @Delete
    void deleteUserProfile(User user);
}
