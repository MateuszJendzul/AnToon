package com.artiline.antoon.Database.User;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.artiline.antoon.Database.Models.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    void insert(User user);

    @Query("SELECT * FROM User ORDER BY userID ASC")
    List<User> getAll();

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);
}
