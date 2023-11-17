package com.artiline.antoon.Database;

public class PrimaryKeyGenerator {
    private static int userPrimaryKey = 0;
    private static int comicsPrimaryKey = 0;

    public static synchronized int generateUserPrimaryKey() {
        return userPrimaryKey++;
    }

    public static synchronized int generateComicsPrimaryKey() {
        return comicsPrimaryKey++;
    }
}
