package com.artiline.antoon.Database;

public class PrimaryKeyGenerator {
    private static long primaryKey = 0;

    public static synchronized long generatePrimaryKey(){
        return primaryKey++;
    }
}
