package com.example.gamescout.ui.api;

public class Game {
    private String gameName;
    private String gameNormalPrice;
    private String gameImage;
    private String metacriticScore;
    private String steamAppID;
    private String steamRatingText;
    private String steamRatingCount;

    private String gameSalePrice;
    private String gameSavings;



    // Regular game
    public Game(String gameName, String gameNormalPrice, String gameImage, String steamAppID) {
        this.gameName = gameName;
        this.gameNormalPrice = gameNormalPrice;
        this.gameImage = gameImage;
        this.steamAppID = steamAppID;
    }

    // On sale game
    public Game(String gameName, String gameNormalPrice, String gameImage, String steamAppID,
                String metacriticScore, String gameSalePrice, String gameSavings, String steamRatingCount, String steamRatingText) {
        this.gameName = gameName;
        this.gameNormalPrice = gameNormalPrice;
        this.gameImage = gameImage;
        this.steamAppID = steamAppID;
        this.metacriticScore = metacriticScore;
        this.gameSalePrice = gameSalePrice;
        this.gameSavings = gameSavings;
        this.steamRatingCount = steamRatingCount;
        this.steamRatingText = steamRatingText;
    }

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
}
