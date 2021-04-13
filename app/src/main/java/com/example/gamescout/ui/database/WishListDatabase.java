package com.example.gamescout.ui.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.gamescout.ui.api.Game;

import java.util.ArrayList;
import java.util.Locale;

public class WishListDatabase extends SQLiteOpenHelper {
    // Database version
    public static final int DATABASE_VERSION = 1;

    // Database name
    public static final String DATABASE_NAME = "wishlist";

    // Table name
    public static final String TABLE_GAME_WISH_LIST = "game_wish_list";

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

    // Create Table Query
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

    // READ
    public Game getGame(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Game game = null;

        String[] columnNames = {COLUMN_ID, COLUMN_NAME, COLUMN_IMAGE, COLUMN_NORMAL_PRICE, COLUMN_SALE_PRICE,
                                COLUMN_SAVINGS, COLUMN_STEAM_APP_ID, COLUMN_STEAM_APP_RATING_TEXT,
                                COLUMN_STEAM_APP_REVIEW_COUNT, COLUMN_METACRITIC_SCORE};

        Cursor cursor = db.query(TABLE_GAME_WISH_LIST, columnNames, COLUMN_ID + "= ?",
                                 new String[]{String.valueOf(id)}, null, null, null);

        if (cursor.moveToFirst()) {
            game = new Game(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getString(8),
                cursor.getString(9)
            );
        }
        db.close();
        return game;
    }

    public ArrayList<Game> getAllGames() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_GAME_WISH_LIST, null);

        ArrayList<Game> games = new ArrayList<>();

        while (cursor.moveToNext()) {
            games.add(new Game(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9)
            ));
        }
        db.close();
        return games;
    }

    // DELETE
    public void deleteGame(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GAME_WISH_LIST, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
