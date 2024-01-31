package com.artiline.antoon.Database.Models;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Comics")
public class Comics implements Serializable {
    private final static String TAG = "Comics";

    @PrimaryKey(autoGenerate = true)
    private int ID = 0;
    @ColumnInfo(name = "comicsName")
    private String comicsName = "comicsName";
    @ColumnInfo(name = "comicsPicture")
    private int comicsPicture = 0;
    @ColumnInfo(name = "rating")
    private int rating = 0;
    @ColumnInfo(name = "newestChapter")
    private int newestChapter = 0;
    @ColumnInfo(name = "atChapter")
    private int atChapter = 0;
    @ColumnInfo(name = "webLink")
    private String webLink = "webLink";

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getComicsName() {
        return comicsName;
    }

    public void setComicsName(String comicsName) {
        this.comicsName = comicsName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getNewestChapter() {
        return newestChapter;
    }

    public void setNewestChapter(int newestChapter) {
        this.newestChapter = newestChapter;
    }

    public int getAtChapter() {
        return atChapter;
    }

    public void setAtChapter(int atChapter) {
        this.atChapter = atChapter;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    public int getComicsPicture() {
        return comicsPicture;
    }

    public void setComicsPicture(int comicsPicture) {
        this.comicsPicture = comicsPicture;
    }

    public Comics() {
        Log.i(TAG, "Comics: no args constructor");
    }

    @NonNull
    @Override
    public String toString() {
        return "Comics{" +
                "ID=" + ID +
                ", comicsName='" + comicsName + '\'' +
                ", rating=" + rating +
                ", newestChapter=" + newestChapter +
                ", atChapter=" + atChapter +
                ", webLink='" + webLink + '\'' +
                '}';
    }
}
