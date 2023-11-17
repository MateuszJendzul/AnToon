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

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.artiline.antoon.R;
import com.artiline.antoon.Database.User.UserDAO;
import com.artiline.antoon.Database.User.UserRoomDB;
import com.artiline.antoon.Database.AppFonts;
import com.artiline.antoon.Database.CustomTypeFaceSpan;
import com.artiline.antoon.Database.Models.User;

public class MainPageActivity extends AppCompatActivity {
    private static final String TAG = "MainPageActivity";

    // declare and initialize name strings
    public static final String MAIN_PAGE_ACTIVITY_EXTRA = "mainPageActivityExtra";
    public static final String LOGGED_ON_BOOL_EXTRA = "loggedOn";

    // declare layout objects
    TextView mainPageActivityLayoutUserNameText;
    Button mainPageActivityLayoutMenuButton;
    RecyclerView recyclerView;

    // declare instances
    SharedPreferences mainPageActivitySP, mainPageActivitySPReceiver, loginActivitySPReceiver;
    UserDAO userDAO;
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_acivity_layout);
        Log.i(TAG, "onCreate: MainPageActivity");

        // initialize instances
        userDAO = UserRoomDB.getInstance(this).usersDAO();
        mainPageActivitySP = getSharedPreferences(MAIN_PAGE_ACTIVITY_EXTRA, Context.MODE_PRIVATE);
        mainPageActivitySPReceiver = getApplicationContext().getSharedPreferences(
                MAIN_PAGE_ACTIVITY_EXTRA, Context.MODE_PRIVATE);
        loginActivitySPReceiver = getApplicationContext().getSharedPreferences(
                LoginActivity.LOGIN_ACTIVITY_EXTRA, Context.MODE_PRIVATE);

        // initialize layout objects
        mainPageActivityLayoutUserNameText = findViewById(R.id.main_page_activity_layout_user_name_text_ID);
        mainPageActivityLayoutMenuButton = findViewById(R.id.main_page_activity_layout_menu_button_ID);
        recyclerView = findViewById(R.id.comicsCardViewID);

        int loggedUserID = loginActivitySPReceiver.getInt("loggedUserID", -1);
        Log.d(TAG, "getInt(\"loggedUserID\", -1): " + loggedUserID);
        // set current user object as object from DAO
        user = userDAO.getUserByID(loggedUserID);
        mainPageActivityLayoutUserNameText.setText(user.getName());

        // load previously selected (or default) font
        changeFont(user.getFont());
        mainPageActivitySP.edit().putBoolean(LOGGED_ON_BOOL_EXTRA, true).apply();
        Log.d(TAG, "putBoolean(LOGGED_ON_BOOL_EXTRA, true)");

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
                    // set loggedOn value to false, used in StartActivity to launch activity
                    mainPageActivitySP.edit().putBoolean(LOGGED_ON_BOOL_EXTRA, false).apply();
                    Log.d(TAG, "putBoolean(LOGGED_ON_BOOL_EXTRA, false)");

                    Intent main_page_activity_layout_back_button_intent = new Intent(
                            MainPageActivity.this, LoginRegisterActivity.class);
                    startActivity(main_page_activity_layout_back_button_intent);

                } else if (popupMenuItemID == R.id.user_popup_menu_font_ID) {
                    Log.i(TAG, "onClick: userPopupMenuFont");
                    // create new PopupMenu menu object to inflate list of available fonts
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

                        if (changeFontMenuID == R.id.changeFontInd0KalamRegularID) {
                            Log.i(TAG, "onClick: changeFontInd0KalamRegularID");
                            // change currently saved font of this user
                            user.setFont(AppFonts.KALAM_FONT);

                        } else if (changeFontMenuID == R.id.changeFontInd1KomtxtbID) {
                            Log.i(TAG, "onClick: changeFontInd1KomtxtbID");
                            user.setFont(AppFonts.KOMTXTB_FONT);
                        }

                        // updateUser DAO method used to apply all changes made for current user
                        userDAO.updateUser(user);
                        changeFont(user.getFont());
                        Log.d(TAG, "User: " + user.getName() + " font changed to: " + user.getFont());
                        return false;
                    });
                    changeFontMenu.show();
                }
                return false;
            });
            popupMenu.show();
        });

        // overrides default phone back button
        // before closing activity, sends boolean SP value telling StartActivity that user logged out
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Log.i(TAG, "handleOnBackPressed: ");
                // set loggedOn value to false, used in StartActivity to launch activity
                mainPageActivitySP.edit().putBoolean(LOGGED_ON_BOOL_EXTRA, false).apply();
                Log.d(TAG, "putBoolean(LOGGED_ON_BOOL_EXTRA, false)");
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    /**
     * Changes typeface (font) of selected menu item.
     * Uses typeface attached to current user object.
     *
     * @param mi menu item e.g. MenuItem mi = userPopUpMenu.getItem(i); where 'i' stands for index
     */
    private void applyFontToMenuItem(MenuItem mi) {
        Log.i(TAG, "applyFontToMenuItem: ");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        // sets 'mNewTitle' span based on custom span class
        // uses Typeface defined by currently used AppFonts.appFontString font string
        mNewTitle.setSpan(new CustomTypeFaceSpan("", getTypeface(), Color.BLACK), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // sets current menu item font as 'mNewTitle' span
        mi.setTitle(mNewTitle);
    }

    /**
     * Change typeface (font) of selected menu item.
     * Uses typeface provided as parameter.
     *
     * @param mi       menu item e.g. MenuItem mi = userPopUpMenu.getItem(i); where 'i' stands for index
     * @param typeface of designated font
     */
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
     * Changes fonts of designated elements using provided String variable, it is used to compare it to
     * existing fonts. When String matches font name of one of the font names from collection,
     * changes fonts of these elements to display texts with matched font.
     *
     * @param fontString name of the font to be displayed
     */
    private void changeFont(String fontString) {
        Log.i(TAG, "changeFont: font = " + fontString);
        Typeface typeface;

        switch (fontString) {
            case "Default":
                Log.d(TAG, "fontString.equals(\"Default\")");
                typeface = getResources().getFont(R.font.komtxtb_sans);
                break;

            case AppFonts.KALAM_FONT:
                Log.d(TAG, "fontString.equals(AppFonts.KALAM_FONT)");
                typeface = getResources().getFont(R.font.kalam_regular);
                break;

            case AppFonts.KOMTXTB_FONT:
                Log.d(TAG, "fontString.equals(AppFonts.KOMTXTB_FONT)");
                typeface = getResources().getFont(R.font.komtxtb_sans);
                break;

            default:
                Log.w(TAG, "font now found!");
                Toast.makeText(this, "Font not found!", Toast.LENGTH_LONG).show();
                return;
        }

        //TODO make fonts changes for whole app, not only locally
        mainPageActivityLayoutUserNameText.setTypeface(typeface);
    }

    /**
     * Loads font String attached to User object and compares it to existing font names.
     * If match is found, initiates Typeface instance with matched found and returns it.
     *
     * @return returns typeface based on font String attached to User object.
     */
    private Typeface getTypeface() {
        Log.i(TAG, "getTypeface: ");
        Typeface typeface = getResources().getFont(R.font.komtxtb_sans);

        if (user.getFont().equals(AppFonts.KALAM_FONT)) {
            typeface = getResources().getFont(R.font.kalam_regular);

        } else if (user.getFont().equals(AppFonts.KOMTXTB_FONT)) {
            typeface = getResources().getFont(R.font.komtxtb_sans);

        } else {
            Log.e(TAG, "Font attached to " + user.getName() +
                    " doesn't match with any existing font names");
        }

        Log.d(TAG, "return typeface with: " + user.getFont() + " font");
        return typeface;
    }
}
