package com.artiline.antoon.Database.Comics;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.artiline.antoon.Database.Models.Comics;

import java.util.List;

@Dao
public interface ComicsDAO {
    @Insert
    void insert(Comics comics);

    @Query("SELECT * FROM Comics WHERE ID =:ID")
    Comics getComicsByID(int ID);

    @Query("SELECT * FROM Comics ORDER BY ID ASC")
    List<Comics> getAll();

    @Update
    void updateComics(Comics comics);

    @Delete
    void deleteComics(Comics comics);
}
