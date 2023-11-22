package com.artiline.antoon.Database;

public class PrimaryKeyGenerator {
    private static int ID = 0;

    public static synchronized int generateID() {
        return ID++;
    }
}
