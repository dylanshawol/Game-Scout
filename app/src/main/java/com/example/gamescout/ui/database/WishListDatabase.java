package com.example.gamescout.ui.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.gamescout.ui.api.Game;

public class WishListDatabase extends SQLiteOpenHelper {
    // Database version
    public static final int DATABASE_VERSION = 1;

    // Database name
    public static final String DATABASE_NAME = "wishlist";

    // Table name
    public static final String TABLE_GAME_WISH_LIST = "gamewishlist";

    // Column Names
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_NORMAL_PRICE = "normal_price";
    public static final String COLUMN_SALE_PRICE = "sale_price";
    public static final String COLUMN_SAVINGS = "saving";
    public static final String COLUMN_STEAM_APP_ID = "steam_app_id";
    public static final String COLUMN_STEAM_APP_RATING_TEXT = "steam_app_rating_text";
    public static final String COLUMN_STEAM_APP_REVIEW_COUNT = "steam_app_review_count";
    public static final String COLUMN_METACRITIC_SCORE = "metacritic_score";

    public static final String CREATE_WISH_LIST_TABLE =
            "CREATE TABLE " + TABLE_GAME_WISH_LIST + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_IMAGE + " TEXT, " +
            COLUMN_NORMAL_PRICE + " TEXT, " +
            COLUMN_SALE_PRICE + " TEXT, " +
            COLUMN_SAVINGS + " TEXT, " +
            COLUMN_STEAM_APP_ID + " TEXT, " +
            COLUMN_STEAM_APP_RATING_TEXT + " TEXT, " +
            COLUMN_STEAM_APP_REVIEW_COUNT + " TEXT, " +
            COLUMN_METACRITIC_SCORE + " TEXT)";

    public WishListDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_WISH_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // CREATE
    public void addGame(Game game) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, game.getGameName());
        values.put(COLUMN_IMAGE, game.getGameImage());
        values.put(COLUMN_NORMAL_PRICE, game.getGameNormalPrice());
        values.put(COLUMN_SALE_PRICE, game.getGameSalePrice());
        values.put(COLUMN_SAVINGS, game.getGameSavings());
        values.put(COLUMN_STEAM_APP_ID, game.getSteamAppID());
        values.put(COLUMN_STEAM_APP_RATING_TEXT, game.getSteamRatingText());
        values.put(COLUMN_STEAM_APP_REVIEW_COUNT, game.getSteamRatingCount());
        values.put(COLUMN_METACRITIC_SCORE, game.getMetacriticScore());

        db.insert(TABLE_GAME_WISH_LIST, null, values);
        db.close();
    }


}
