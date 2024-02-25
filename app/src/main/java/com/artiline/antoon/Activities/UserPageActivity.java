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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.artiline.antoon.Database.Adapters.ComicsAdapter;
import com.artiline.antoon.Database.Comics.ComicsDAO;
import com.artiline.antoon.Database.Comics.ComicsRoomDB;
import com.artiline.antoon.Database.ComicsClickListener;
import com.artiline.antoon.Database.Models.Comics;
import com.artiline.antoon.Database.User.AddNewComics;
import com.artiline.antoon.Database.User.ViewComics;
import com.artiline.antoon.R;
import com.artiline.antoon.Database.User.UserDAO;
import com.artiline.antoon.Database.User.UserRoomDB;
import com.artiline.antoon.Database.AppFonts;
import com.artiline.antoon.Database.CustomTypeFaceSpan;
import com.artiline.antoon.Database.Models.User;

import java.util.List;

public class UserPageActivity extends AppCompatActivity {
    private static final String TAG = "UserPageActivity";

    // declare and initialize name strings
    public static final String MAIN_PAGE_ACTIVITY_EXTRA = "mainPageActivityExtra";
    public static final String LOGGED_ON_BOOL_EXTRA = "loggedOn";
    public static final String COMICS_ADD_CARD_CREATED = "comicsAddCardCreated";
    boolean comicsAddCardCreated;

    // declare layout objects
    TextView mainPageActivityLayoutUserNameText;
    Button mainPageActivityLayoutMenuButton, userPageActivityLayoutAddButton, userPageActivityLayoutDoubleArrowLeftButton,
            userPageActivityLayoutDoubleArrowRightButton;
    RecyclerView comicsRecycler;

    // declare instances
    SharedPreferences mainPageActivitySP, mainPageActivitySPReceiver, loginActivitySPReceiver;
    ComicsAdapter comicsAdapter;
    UserDAO userDAO;
    ComicsDAO comicsDAO;
    List<Comics> comicsList;
    LinearLayoutManager comicsLayoutManager;
    Comics comicsTempObject;
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_page_acivity_layout);
        Log.i(TAG, "onCreate: UserPageActivity");

        // initialize instances
        userDAO = UserRoomDB.getInstance(this).usersDAO();
        comicsDAO = ComicsRoomDB.getInstance(this).comicsDAO();
        mainPageActivitySP = getSharedPreferences(MAIN_PAGE_ACTIVITY_EXTRA, Context.MODE_PRIVATE);
        mainPageActivitySPReceiver = getApplicationContext().getSharedPreferences(
                MAIN_PAGE_ACTIVITY_EXTRA, Context.MODE_PRIVATE);
        loginActivitySPReceiver = getApplicationContext().getSharedPreferences(
                LoginActivity.LOGIN_ACTIVITY_EXTRA, Context.MODE_PRIVATE);

        // initialize layout objects
        mainPageActivityLayoutUserNameText = findViewById(R.id.user_page_activity_layout_user_name_text_ID);
        mainPageActivityLayoutMenuButton = findViewById(R.id.user_page_activity_layout_menu_button_ID);
        userPageActivityLayoutAddButton = findViewById(R.id.user_page_activity_layout_add_button_ID);
        userPageActivityLayoutDoubleArrowLeftButton = findViewById(R.id.user_page_activity_layout_double_arrow_left_button_ID);
        userPageActivityLayoutDoubleArrowRightButton = findViewById(R.id.user_page_activity_layout_double_arrow_right_button_ID);
        comicsRecycler = findViewById(R.id.user_page_activity_layout_comics_recycler_ID);

        // initialize variables
        comicsAddCardCreated = mainPageActivitySPReceiver.getBoolean(COMICS_ADD_CARD_CREATED, false);
        int loggedUserID = loginActivitySPReceiver.getInt("loggedUserID", -1);

        // load logged user data
        Log.d(TAG, "LOGGED USER ID: " + loggedUserID);
        // set current user object as object from DAO
        user = userDAO.getAll().get(loggedUserID);
        mainPageActivityLayoutUserNameText.setText(user.getName());

        // load previously selected (or default) user font
        changeFont(user.getFont());
        mainPageActivitySP.edit().putBoolean(LOGGED_ON_BOOL_EXTRA, true).apply();
        Log.d(TAG, "putBoolean(LOGGED_ON_BOOL_EXTRA, true)");

        // update user comics list
        updateComicsViewRecycler();
        // add GGGGGGGGGG
        createComicsAddCard();
        // GGGGGGGGGGGGGG
        overrideBackButton();

        //TODO 
        mainPageActivityLayoutMenuButton.setOnClickListener(mainPageActivityLayoutMenuButtonView -> {
            Log.i(TAG, "onClick: mainPageActivityLayoutMenuButton");
            PopupMenu popupMenu = new PopupMenu(UserPageActivity.this, mainPageActivityLayoutMenuButtonView);
            popupMenu.getMenuInflater().inflate(R.menu.user_pop_up_menu, popupMenu.getMenu());
            Menu userPopUpMenu = popupMenu.getMenu();
            applyFontToAllMenuItems(userPopUpMenu);

            popupMenu.setOnMenuItemClickListener(popupMenuItemView -> {
                Log.i(TAG, "onClick: popupMenu");
                int popupMenuItemID = popupMenuItemView.getItemId();

                if (popupMenuItemID == R.id.user_popup_menu_logout_ID) {
                    Log.i(TAG, "onClick: userPopupMenuLogout");
                    // set loggedOn value to false, used in StartActivity to launch activity
                    mainPageActivitySP.edit().putBoolean(LOGGED_ON_BOOL_EXTRA, false).apply();
                    Log.d(TAG, "putBoolean(LOGGED_ON_BOOL_EXTRA, false)");

                    Intent main_page_activity_layout_back_button_intent = new Intent(
                            UserPageActivity.this, LoginRegisterActivity.class);
                    startActivity(main_page_activity_layout_back_button_intent);

                } else if (popupMenuItemID == R.id.user_popup_menu_font_ID) {
                    Log.i(TAG, "onClick: userPopupMenuFont");
                    // create new PopupMenu menu object to inflate list of available fonts
                    PopupMenu changeFontMenu = new PopupMenu(UserPageActivity.this, mainPageActivityLayoutMenuButtonView);
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

        userPageActivityLayoutAddButton.setOnClickListener(v -> {
            Log.i(TAG, "onClick: userPageActivityLayoutAddButton");
            createNewComics();
        });

        userPageActivityLayoutDoubleArrowLeftButton.setOnClickListener(v -> {
            Log.i(TAG, "onClick: userPageActivityLayoutDoubleArrowLeftButton"
                    + "\nScrolling to the start of the list");
            scrollToFirst();
        });

        userPageActivityLayoutDoubleArrowRightButton.setOnClickListener(v -> {
            Log.i(TAG, "onClick: userPageActivityLayoutDoubleArrowRightButton"
                    + "\nScrolling to the end of the list");
            scrollToLast();
        });
    }

    // TODO
    private void createNewComics() {
        Comics newComics = new Comics();
        newComics.setComicsName("New Comics");
        newComics.setComicsPicture(R.drawable.default_comics_pic_1);
        newComics.setWebLink("webLink");
        newComics.setRating(0);
        newComics.setAtChapter(0);
        newComics.setNewestChapter(0);
        Log.i(TAG, "COMIC: " + newComics.getComicsName());
        comicsDAO.insert(newComics);
        updateComicsViewRecycler();
    }

    // TODO
    private void createComicsAddCard() {
        if (!comicsAddCardCreated) {
            Comics newComics = new Comics();
            newComics.setComicsPicture(R.drawable.add_image);
            comicsDAO.insert(newComics);
            updateComicsViewRecycler();
            mainPageActivitySP.edit().putBoolean(COMICS_ADD_CARD_CREATED, true).apply();
        }
    }

    /**
     * Jumps to first object of the list displayed in the Recycler view.
     */
    private void scrollToFirst() {
        comicsRecycler.scrollToPosition(0);
    }

    /**
     * Jumps to last object of the list displayed in the Recycler view.
     */
    private void scrollToLast() {
        comicsRecycler.scrollToPosition(comicsAdapter.getItemCount() - 1);
    }

    /**
     * Changes position of "ADD" card of user comics list, to ensure that its always displayed as
     * last on the list view on the recycler.
     * Checks if ("ADD" card ID should always be set to '1') "ADD" card ID is present on the list,
     * deletes it, and then adds it again.
     */
    private void updateComicsAddCardPosition() {
        Log.i(TAG, "updateComicsAddCardPosition: ");

        if (comicsList.size() > 0) {
            if (comicsList.get(comicsList.size() - 1).getID() != 1) {
                for (int i = 0; i < comicsList.size() - 1; i++) {
                    // DAO PrimaryKey auto generation starts from index 1
                    Log.d(TAG, "Searching for comics ID: 1");
                    if (comicsList.get(i).getID() == 1) {
                        Log.d(TAG, "Comics ID: 1 FOUND");
                        comicsTempObject = comicsList.get(i);
                        comicsList.remove(i);
                        comicsList.add(comicsTempObject);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Used to update comicsRecycler RecyclerView.
     * Loads objects from the DAO comics list and assigns it to the comicsList,
     * creates new comicsAdapter adapter instance using ComicsAdapter.class default constructor,
     * creates new comicsLayoutManager layout instance with set properties,
     * uses created comicsAdapter to set adapter of comicsRecycler,
     * uses created comicsLayoutManager to set layout of comicsRecycler.
     */
    private void updateComicsViewRecycler() {
        Log.i(TAG, "updateComicsViewRecycler: ");

        comicsList = comicsDAO.getAll();
        comicsAdapter = new ComicsAdapter(comicsList, comicsClickListener);

        comicsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        comicsRecycler.setLayoutManager(comicsLayoutManager);
        comicsRecycler.setAdapter(comicsAdapter);
        updateComicsAddCardPosition();
    }

    /**
     * Used to change fonts of all items of the provided menu.
     * Iterates thru all menu items of the menu list and applies new font to these using
     * applyFontToMenuItem(menuItem) function.
     *
     * @param menu menu of which fonts will change
     */
    private void applyFontToAllMenuItems(Menu menu) {
        MenuItem mi;
        for (int i = 0; i < menu.size(); i++) {
            // reassigns current menu item 'mi' with next item from the 'menu' list
            mi = menu.getItem(i);
            // calls for method, passing current menu item 'mi' as argument
            applyFontToMenuItem(mi);
        }
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

    /**
     * Overrides default phone back button,
     * before closing activity, sends boolean SP value telling StartActivity that user logged out
     */
    private void overrideBackButton() {
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

    // TODO
    private final ComicsClickListener comicsClickListener = new ComicsClickListener() {
        @Override
        public void onClick(Comics comics) {
            Log.d(TAG, "onClick: comicsClickListener");
            Intent comicsClickListenerIntent;

            Toast.makeText(UserPageActivity.this, "CLICK", Toast.LENGTH_SHORT).show();
//            if (comicsList.get(comicsList.size() - 1).getID() == 1) {
//                comicsClickListenerIntent = new Intent(UserPageActivity.this, AddNewComics.class);
//                startActivity(comicsClickListenerIntent);
//
//            } else {
//                comicsClickListenerIntent = new Intent(UserPageActivity.this, ViewComics.class);
//                startActivity(comicsClickListenerIntent);
//            }
        }

        @Override
        public void onLongClick(Comics comics, CardView cardView) {
            Log.d(TAG, "onLongClick: comicsClickListener");
            showPopUp(comics, cardView);
        }
    };

    /**
     * Creates new PopupMenu to display it after user long clicks on item in comicsRecycler RecyclerView.
     * If user selects one of options:
     * - delete: to delete selected comics from DAO.
     *
     * @param cardView pass CardView layout to be displayed.
     */
    private void showPopUp(Comics comics, CardView cardView) {
        Log.d(TAG, "showPopUp");
        PopupMenu popupMenu = new PopupMenu(UserPageActivity.this, cardView);
        popupMenu.getMenuInflater().inflate(R.menu.popup_comics_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.popup_comics_menu_delete_comics_ID) {
                comicsDAO.deleteComics(comics);
                updateComicsViewRecycler();
                return true;

            } else {
                return false;
            }
        });

        // call show function to summon selected menu
        popupMenu.show();
    }
}