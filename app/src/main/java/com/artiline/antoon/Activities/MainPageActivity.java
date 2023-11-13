package com.artiline.antoon.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.artiline.antoon.R;
import com.artiline.antoon.database.AppFonts;
import com.artiline.antoon.database.CustomTypeFaceSpan;
import com.artiline.antoon.database.User;

public class MainPageActivity extends AppCompatActivity {
    private static final String TAG = "MainPageActivity";
    TextView main_page_activity_layout_user_name_text;
    Button main_page_activity_layout_menu_button;
    private static boolean loggedON = false;

    // string used in SharedPreferences and ParcelableExtra as name of extras
    public static final String MAIN_PAGE_ACTIVITY_EXTRA = "mainPageActivityExtra";
    // declare SP
    SharedPreferences mainPageActivitySP;
    SharedPreferences.Editor mainPageActivitySPEditor;
    SharedPreferences mainPageActivitySPReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_acivity_layout);
        Log.d(TAG, "onCreate: MainPageActivity");

        // initialize SP
        mainPageActivitySP = getSharedPreferences(MAIN_PAGE_ACTIVITY_EXTRA, Context.MODE_PRIVATE);
        mainPageActivitySPEditor = mainPageActivitySP.edit();
        mainPageActivitySPReceiver = getApplicationContext().getSharedPreferences(
                "appFontsPrefs", Context.MODE_PRIVATE);

        // get
        main_page_activity_layout_user_name_text = findViewById(R.id.main_page_activity_layout_user_name_text_ID);
        main_page_activity_layout_menu_button = findViewById(R.id.main_page_activity_layout_menu_button_ID);
        changeFont(AppFonts.appFontString);
        loggedON = true;
        putExtraOnLoggedOn(true);
        //TODO come up with a method to start application from MainPageActivity with saved user data
        //  from StartActivity

        User user = null;
        String main_page_activity_layout_user_name_text_string = null;
        Intent main_page_activity_intent = getIntent();

        // check if this activity has extra carried over from LoginActivity
        if (main_page_activity_intent.hasExtra(LoginActivity.LOGIN_ACTIVITY_EXTRA)) {
            user = main_page_activity_intent.getParcelableExtra(LoginActivity.LOGIN_ACTIVITY_EXTRA);

        } else if (main_page_activity_intent.hasExtra(RegisterActivity.REGISTER_ACTIVITY_EXTRA)) {
            user = main_page_activity_intent.getParcelableExtra(RegisterActivity.REGISTER_ACTIVITY_EXTRA);

        } else {
            Toast.makeText(this, "User not found!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Receiving user extra: EXTRA NOT FOUND");
        }

        main_page_activity_layout_user_name_text_string = user != null ? user.getName() : "null";
        main_page_activity_layout_user_name_text.setText(main_page_activity_layout_user_name_text_string);

        main_page_activity_layout_menu_button.setOnClickListener(mainPageActivityLayoutMenuButtonView -> {
            Log.d(TAG, "onClick: main_page_activity_layout_menu_button");
            PopupMenu popupMenu = new PopupMenu(MainPageActivity.this, mainPageActivityLayoutMenuButtonView);
            popupMenu.getMenuInflater().inflate(R.menu.user_pop_up_menu, popupMenu.getMenu());

            // used to set all menu item fonts by applyFontToMenuItem(MenuItem mi) method
            Menu userPopUpMenu = popupMenu.getMenu();
            for (int i = 0; i < userPopUpMenu.size(); i++) {
                // sets current menu item 'mi' value as menu item number 'i'
                // representing current loop iteration
                MenuItem mi = userPopUpMenu.getItem(i);
                // calls for method, passing current menu item 'mi'
                applyFontToMenuItem(mi);
            }

            popupMenu.setOnMenuItemClickListener(popupMenuItemView -> {
                Log.d(TAG, "onClick: popupMenu");
                int popupMenuItemID = popupMenuItemView.getItemId();

                if (popupMenuItemID == R.id.user_popup_menu_logout_ID) {
                    Log.d(TAG, "onClick: user_popup_menu_logout_ID");
                    loggedON = false;
                    putExtraOnLoggedOn(false);
                    Intent main_page_activity_layout_back_button_intent = new Intent(
                            MainPageActivity.this, LoginRegisterActivity.class);
                    startActivity(main_page_activity_layout_back_button_intent);

                } else if (popupMenuItemID == R.id.user_popup_menu_font_ID) {
                    Log.d(TAG, "onClick: user_popup_menu_font_ID");
                    PopupMenu changeFontMenu = new PopupMenu(MainPageActivity.this, mainPageActivityLayoutMenuButtonView);
                    changeFontMenu.getMenuInflater().inflate(R.menu.change_fonts_menu, changeFontMenu.getMenu());

                    // get menu layout and change fonts of menu items to represent described fonts
                    // number in fonts id represents it's index position
                    Menu fontsMenu = changeFontMenu.getMenu();
                    // menu item on index 0 changeFont0KalamRegularID
                    applyFontToMenuItem(fontsMenu.getItem(0), getResources().getFont(R.font.kalam_regular));
                    // menu item on index 1 changeFont1KomtxtbID
                    applyFontToMenuItem(fontsMenu.getItem(1), getResources().getFont(R.font.komtxtb_sans));

                    changeFontMenu.setOnMenuItemClickListener(changeFontMenuItem -> {
                        Log.d(TAG, "onClick: changeFontMenu");
                        int changeFontMenuID = changeFontMenuItem.getItemId();

                        if (changeFontMenuID == R.id.changeFont0KalamRegularID) {
                            Log.d(TAG, "onClick: changeFont0KalamRegularID");
                            AppFonts.appFontString = AppFonts.KALAM_FONT;
                            changeFont(AppFonts.appFontString);

                        } else if (changeFontMenuID == R.id.changeFont1KomtxtbID) {
                            Log.d(TAG, "onClick: changeFont1KomtxtbID");
                            AppFonts.appFontString = AppFonts.KOMTXTB_FONT;
                            changeFont(AppFonts.appFontString);
                        }

                        return false;
                    });
                    changeFontMenu.show();
                }
                return false;
            });
            popupMenu.show();
        });
    }

    void putExtraOnLoggedOn(boolean trueOrFalse) {
        mainPageActivitySPEditor.putBoolean("loggedON", trueOrFalse);
        mainPageActivitySPEditor.apply();
    }

    private void applyFontToMenuItem(MenuItem mi) {
        //TODO get to know how spannable string works
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        // sets 'mNewTitle' span based on custom span class
        // uses Typeface defined by currently used AppFonts.appFontString font string
        mNewTitle.setSpan(new CustomTypeFaceSpan("", getTypeface(), Color.BLACK), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // sets current menu item font as 'mNewTitle' span
        mi.setTitle(mNewTitle);
    }

    private void applyFontToMenuItem(MenuItem mi, Typeface typeface) {
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        // sets 'mNewTitle' span based on custom span class
        // uses passed typeFace parameter as font
        mNewTitle.setSpan(new CustomTypeFaceSpan("", typeface, Color.BLACK), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // sets current menu item font as 'mNewTitle' span
        mi.setTitle(mNewTitle);
    }

    /**
     * @param fontString
     */
    private void changeFont(String fontString) {
        Typeface typeface;

        if (fontString.equals(AppFonts.KALAM_FONT)) {
            typeface = getResources().getFont(R.font.kalam_regular);

        } else if (fontString.equals(AppFonts.KOMTXTB_FONT)) {
            typeface = getResources().getFont(R.font.komtxtb_sans);

        } else {
            Toast.makeText(this, "Font not found!", Toast.LENGTH_LONG).show();
            return;
        }

        mainPageActivitySPEditor.putString("font", fontString);
        mainPageActivitySPEditor.apply();

        //TODO make fonts changes for whole app, not only locally
        main_page_activity_layout_user_name_text.setTypeface(typeface);
        main_page_activity_layout_menu_button.setTypeface(typeface);
    }

    /**
     * Uses SharedPreferences to load font name (written by changeFont(String fontString) method)
     * and use it to compare font names to set return value of Typeface based on result.
     *
     * @return typeface based on currently set AppFonts.appFontString
     */
    private Typeface getTypeface() {
        String fontString = mainPageActivitySPReceiver.getString("font", AppFonts.appFontString);

        Typeface typeface = getResources().getFont(R.font.komtxtb_sans);

        if (fontString.equals(AppFonts.KALAM_FONT)) {
            typeface = getResources().getFont(R.font.kalam_regular);

        } else if (fontString.equals(AppFonts.KOMTXTB_FONT)) {
            typeface = getResources().getFont(R.font.komtxtb_sans);
        }

        return typeface;
    }
}
