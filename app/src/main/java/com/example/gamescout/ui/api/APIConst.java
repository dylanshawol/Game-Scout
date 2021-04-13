package com.example.gamescout.ui.api;

public class APIConst {
    public static final String API_URL_SEARCH = "https://www.cheapshark.com/api/1.0/games?title=";
    public static final String API_URL_DEALS = "https://www.cheapshark.com/api/1.0/deals?storeID=1&sortBy=Reviews&onSale=1";
    public static final String STEAM_APP_ID_URL = "http://store.steampowered.com/app/";

    // JSON Deal Keys
    public static final String NAME_KEY = "title";
    public static final String NORMAL_PRICE_KEY = "normalPrice";
    public static final String IMAGE_KEY = "thumb";
    public static final String METACRITIC_SCORE_KEY = "metacriticScore";
    public static final String SALE_PRICE_KEY = "salePrice";
    public static final String SAVINGS_KEY = "savings";
    public static final String STEAM_APP_ID_KEY = "steamAppID";
    public static final String STEAM_RATING_COUNT_KEY = "steamRatingCount";
    public static final String STEAM_RATING_TEXT_KEY = "steamRatingText";


    // JSON Search Keys
    public static final String SEARCH_NAME_KEY = "external";
    public static final String SEARCH_PRICE_KEY = "cheapest";


}
