package com.artiline.antoon.Activities;

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
import com.artiline.antoon.Database.UserDAO;
import com.artiline.antoon.Database.UserRoomDB;
import com.artiline.antoon.Database.AppFonts;
import com.artiline.antoon.Database.CustomTypeFaceSpan;
import com.artiline.antoon.Models.User;

public class MainPageActivity extends AppCompatActivity {
    private static final String TAG = "MainPageActivity";
    // used in SharedPreferences and ParcelableExtra as name of extras
    public static final String MAIN_PAGE_ACTIVITY_EXTRA = "mainPageActivityExtra";
    public static final String LOGGED_ON_BOOL_EXTRA = "loggedOn";
    private static final String DEFAULT_FONT_EXTRA = "defaultFontExtra";
    TextView mainPageActivityLayoutUserNameText;
    Button mainPageActivityLayoutMenuButton;
    // declare SP
    SharedPreferences mainPageActivitySP, mainPageActivitySPReceiver, loginActivitySPReceiver;
    UserDAO userDAO;
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_acivity_layout);
        Log.i(TAG, "onCreate: MainPageActivity");
        userDAO = UserRoomDB.getInstance(this).usersDAO();

        // initialize SP
        mainPageActivitySP = getSharedPreferences(MAIN_PAGE_ACTIVITY_EXTRA, Context.MODE_PRIVATE);
        mainPageActivitySPReceiver = getApplicationContext().getSharedPreferences(
                MAIN_PAGE_ACTIVITY_EXTRA, Context.MODE_PRIVATE);
        loginActivitySPReceiver = getApplicationContext().getSharedPreferences(
                LoginActivity.LOGIN_ACTIVITY_EXTRA, Context.MODE_PRIVATE);

        // get SP
        mainPageActivityLayoutUserNameText = findViewById(R.id.main_page_activity_layout_user_name_text_ID);
        mainPageActivityLayoutMenuButton = findViewById(R.id.main_page_activity_layout_menu_button_ID);

        int loggedUserID = loginActivitySPReceiver.getInt("loggedUserID", 0);
        Log.d(TAG, "loginActivitySPReceiver.getInt(\"loggedUserID\") = " + loggedUserID);
        user = userDAO.getAll().get(loggedUserID);
        mainPageActivityLayoutUserNameText.setText(user.getName());

        String defaultFont = mainPageActivitySPReceiver.getString(DEFAULT_FONT_EXTRA, AppFonts.KOMTXTB_FONT);
        changeFont(defaultFont);
        mainPageActivitySP.edit().putBoolean(LOGGED_ON_BOOL_EXTRA, true).apply();

        mainPageActivityLayoutMenuButton.setOnClickListener(mainPageActivityLayoutMenuButtonView -> {
            Log.i(TAG, "onClick: main_page_activity_layout_menu_button");
            PopupMenu popupMenu = new PopupMenu(MainPageActivity.this, mainPageActivityLayoutMenuButtonView);
            popupMenu.getMenuInflater().inflate(R.menu.user_pop_up_menu, popupMenu.getMenu());

            // used to set all menu item fonts by applyFontToMenuItem(MenuItem mi) method
            Menu userPopUpMenu = popupMenu.getMenu();
            Log.d(TAG, "Menu userPopUpMenu: applying fonts to menu items");
            for (int i = 0; i < userPopUpMenu.size(); i++) {
                // sets current menu item 'mi' value as menu item number 'i'
                // representing current loop iteration
                MenuItem mi = userPopUpMenu.getItem(i);
                // calls for method, passing current menu item 'mi'
                applyFontToMenuItem(mi);
            }

            popupMenu.setOnMenuItemClickListener(popupMenuItemView -> {
                Log.i(TAG, "onClick: popupMenu");
                int popupMenuItemID = popupMenuItemView.getItemId();

                if (popupMenuItemID == R.id.user_popup_menu_logout_ID) {
                    Log.i(TAG, "onClick: userPopupMenuLogout");
                    // send loggedOn boolean value of false to SP
                    mainPageActivitySP.edit().putBoolean(LOGGED_ON_BOOL_EXTRA, false).apply();

                    Intent main_page_activity_layout_back_button_intent = new Intent(
                            MainPageActivity.this, LoginRegisterActivity.class);
                    startActivity(main_page_activity_layout_back_button_intent);

                } else if (popupMenuItemID == R.id.user_popup_menu_font_ID) {
                    Log.i(TAG, "onClick: userPopupMenuFont");
                    PopupMenu changeFontMenu = new PopupMenu(MainPageActivity.this, mainPageActivityLayoutMenuButtonView);
                    changeFontMenu.getMenuInflater().inflate(R.menu.change_fonts_menu, changeFontMenu.getMenu());

                    // get menu layout and change fonts of menu items to represent described fonts
                    // number in fonts id represents it's index position
                    Menu fontsMenu = changeFontMenu.getMenu();
                    Log.d(TAG, "PopupMenu changeFontMenu: applying fonts to fonts items");
                    // menu item on index 0 changeFont0KalamRegularID
                    applyFontToMenuItem(fontsMenu.getItem(0), getResources().getFont(R.font.kalam_regular));
                    // menu item on index 1 changeFont1KomtxtbID
                    applyFontToMenuItem(fontsMenu.getItem(1), getResources().getFont(R.font.komtxtb_sans));

                    changeFontMenu.setOnMenuItemClickListener(changeFontMenuItem -> {
                        Log.i(TAG, "onClick: changeFontMenu");
                        int changeFontMenuID = changeFontMenuItem.getItemId();

                        if (changeFontMenuID == R.id.changeFont0KalamRegularID) {
                            Log.i(TAG, "onClick: changeFont0KalamRegularID");
                            mainPageActivitySP.edit().putString(AppFonts.KALAM_FONT, AppFonts.KALAM_FONT).apply();
                            changeFont(AppFonts.KALAM_FONT);

                        } else if (changeFontMenuID == R.id.changeFont1KomtxtbID) {
                            Log.i(TAG, "onClick: changeFont1KomtxtbID");
                            mainPageActivitySP.edit().putString(AppFonts.KOMTXTB_FONT, AppFonts.KOMTXTB_FONT).apply();
                            changeFont(AppFonts.KOMTXTB_FONT);
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

    private void applyFontToMenuItem(MenuItem mi) {
        Log.i(TAG, "applyFontToMenuItem: ");
        //TODO get to know how spannable string works
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        // sets 'mNewTitle' span based on custom span class
        // uses Typeface defined by currently used AppFonts.appFontString font string
        mNewTitle.setSpan(new CustomTypeFaceSpan("", getTypeface(), Color.BLACK), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // sets current menu item font as 'mNewTitle' span
        mi.setTitle(mNewTitle);
    }

    private void applyFontToMenuItem(MenuItem mi, Typeface typeface) {
        Log.i(TAG, "applyFontToMenuItem: ");
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
        Log.i(TAG, "changeFont: ");
        Typeface typeface;

        if (fontString.equals(AppFonts.KALAM_FONT)) {
            Log.d(TAG, "fontString.equals(AppFonts.KALAM_FONT)");
            typeface = getResources().getFont(R.font.kalam_regular);

        } else if (fontString.equals(AppFonts.KOMTXTB_FONT)) {
            Log.d(TAG, "fontString.equals(AppFonts.KOMTXTB_FONT)");
            typeface = getResources().getFont(R.font.komtxtb_sans);

        } else {
            Log.w(TAG, "font now found!");
            Toast.makeText(this, "Font not found!", Toast.LENGTH_LONG).show();
            return;
        }

        //TODO make fonts changes for whole app, not only locally
        mainPageActivityLayoutUserNameText.setTypeface(typeface);
        mainPageActivityLayoutMenuButton.setTypeface(typeface);
    }

    /**
     * Uses SharedPreferences to load font name (written by changeFont(String fontString) method)
     * and use it to compare font names to set return value of Typeface based on result.
     *
     * @return typeface based on currently set AppFonts.appFontString
     */
    private Typeface getTypeface() {
        Log.i(TAG, "getTypeface: ");
        String fontString = mainPageActivitySPReceiver.getString(
                DEFAULT_FONT_EXTRA, AppFonts.KOMTXTB_FONT);

        Typeface typeface = getResources().getFont(R.font.komtxtb_sans);

        if (fontString.equals(AppFonts.KALAM_FONT)) {
            typeface = getResources().getFont(R.font.kalam_regular);

        } else if (fontString.equals(AppFonts.KOMTXTB_FONT)) {
            typeface = getResources().getFont(R.font.komtxtb_sans);
        }

        Log.d(TAG, "return typeface with: " + fontString + " font");
        return typeface;
    }


    SharedPreferences appFontsSPReceiver = getApplicationContext().getSharedPreferences(
            MainPageActivity.MAIN_PAGE_ACTIVITY_EXTRA, Context.MODE_PRIVATE);

}
