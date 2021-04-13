package com.example.gamescout.ui.api;

import android.os.Parcel;
import android.os.Parcelable;

public class Game implements Parcelable {
    private int id;
    private String gameName;
    private String gameImage;
    private String gameNormalPrice;
    private String gameSalePrice;
    private String gameSavings;
    private String steamAppID;
    private String steamRatingText;
    private String steamRatingCount;
    private String metacriticScore;


    // Regular game
    public Game(String gameName, String gameImage, String gameNormalPrice, String steamAppID) {
        this.gameName = gameName;
        this.gameImage = gameImage;
        this.gameNormalPrice = gameNormalPrice;
        this.steamAppID = steamAppID;
    }

    // On sale game
    public Game(String gameName, String gameImage, String gameNormalPrice, String gameSalePrice, String gameSavings, String steamAppID,
                String steamRatingText, String steamRatingCount, String metacriticScore) {
        this.gameName = gameName;
        this.gameImage = gameImage;
        this.gameNormalPrice = gameNormalPrice;
        this.gameSalePrice = gameSalePrice;
        this.gameSavings = gameSavings;
        this.steamAppID = steamAppID;
        this.steamRatingText = steamRatingText;
        this.steamRatingCount = steamRatingCount;
        this.metacriticScore = metacriticScore;
    }

    // DB read game
    public Game(int id, String gameName, String gameImage, String gameNormalPrice, String gameSalePrice, String gameSavings, String steamAppID,
                String steamRatingText, String steamRatingCount, String metacriticScore) {
        this.id = id;
        this.gameName = gameName;
        this.gameImage = gameImage;
        this.gameNormalPrice = gameNormalPrice;
        this.gameSalePrice = gameSalePrice;
        this.gameSavings = gameSavings;
        this.steamAppID = steamAppID;
        this.steamRatingText = steamRatingText;
        this.steamRatingCount = steamRatingCount;
        this.metacriticScore = metacriticScore;
    }

    public Game(Parcel in) {
        id = in.readInt();
        gameName = in.readString();
        gameImage = in.readString();
        gameNormalPrice = in.readString();
        gameSalePrice = in.readString();
        gameSavings = in.readString();
        steamAppID = in.readString();
        steamRatingText = in.readString();
        steamRatingCount = in.readString();
        metacriticScore = in.readString();
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(gameName);
        parcel.writeString(gameImage);
        parcel.writeString(gameNormalPrice);
        parcel.writeString(gameSalePrice);
        parcel.writeString(gameSavings);
        parcel.writeString(steamAppID);
        parcel.writeString(steamRatingText);
        parcel.writeString(steamRatingCount);
        parcel.writeString(metacriticScore);
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel parcel) { return new Game(parcel); }

        @Override
        public Game[] newArray(int i) { return new Game[i]; }
    };

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameNormalPrice() {
        return gameNormalPrice;
    }

    public void setGameNormalPrice(String gameNormalPrice) {
        this.gameNormalPrice = gameNormalPrice;
    }

    public String getGameImage() {
        return gameImage;
    }

    public void setGameImage(String gameImage) {
        this.gameImage = gameImage;
    }

    public String getMetacriticScore() {
        return metacriticScore;
    }

    public void setMetacriticScore(String metacriticScore) {
        this.metacriticScore = metacriticScore;
    }

    public String getGameSalePrice() {
        return gameSalePrice;
    }

    public void setGameSalePrice(String gameSalePrice) {
        this.gameSalePrice = gameSalePrice;
    }

    public String getGameSavings() {
        return gameSavings;
    }

    public void setGameSavings(String gameSavings) {
        this.gameSavings = gameSavings;
    }

    public String getSteamAppID() {
        return steamAppID;
    }

    public void setSteamAppID(String steamAppID) {
        this.steamAppID = steamAppID;
    }

    public String getSteamRatingText() {
        return steamRatingText;
    }

    public void setSteamRatingText(String steamRatingText) {
        this.steamRatingText = steamRatingText;
    }

    public String getSteamRatingCount() {
        return steamRatingCount;
    }

    public void setSteamRatingCount(String steamRatingCount) {
        this.steamRatingCount = steamRatingCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
