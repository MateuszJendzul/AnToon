package com.artiline.antoon.activities.Database;

import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.artiline.antoon.activities.MainPageActivity;

/**
 * @noinspection FieldMayBeFinal
 */
public class AppFonts extends AppCompatActivity {
    private static final String TAG = "AppFonts";

    // define new font here, to use it later
    // (as one of available fonts) to compare it with string parameter of changeFont(font)
    // method which will use it to change selected fonts of displayed texts.
    public static final String KOMTXTB_FONT = "komtxtb_sans";
    public static final String KALAM_FONT = "kalam_regular";
    public static String appFontString = KOMTXTB_FONT;
}
