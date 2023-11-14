package com.artiline.antoon.Database;

public class PrimaryKeyGenerator {
    private static int primaryKey = 0;

    public static synchronized int generatePrimaryKey(){
        return primaryKey++;
    }
}
