package com.example.gamescout.ui.api;

public class Game {
    private String gameName;
    private String gameNormalPrice;
    private String gameImage;
    private String metacriticScore;

    private String gameSalePrice;
    private String gameSavings;
    private String isOnSale;

    // Regular game
    public Game(String gameName, String gameNormalPrice, String gameImage) {
        this.gameName = gameName;
        this.gameNormalPrice = gameNormalPrice;
        this.gameImage = gameImage;
    }

    // On sale game
    public Game(String gameName, String gameNormalPrice, String gameImage, String metacriticScore, String gameSalePrice, String gameSavings) {
        this.gameName = gameName;
        this.gameNormalPrice = gameNormalPrice;
        this.gameImage = gameImage;
        this.metacriticScore = metacriticScore;
        this.gameSalePrice = gameSalePrice;
        this.gameSavings = gameSavings;
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
}
