package com.example.gamescout.ui.api;

public class APIConst {
    public static final String API_URL_SEARCH = "https://www.cheapshark.com/api/1.0/games?title=";

    public static final String API_URL_DEALS = "https://www.cheapshark.com/api/1.0/deals";

    public static final String[] API_URL_DEALS_SORT_LIST = {
            "upperPrice", // Less than or equal
            "lowerPrice"  // Greater than or equal
    };

    // JSON Deal Keys
    public static final String NAME_KEY = "internalName";
    public static final String NORMAL_PRICE_KEY = "normalPrice";
    public static final String IMAGE_KEY = "thumb";
    public static final String METACRITIC_SCORE_KEY = "metacriticScore";
    public static final String SALE_PRICE_KEY = "salePrice";
    public static final String SAVINGS_KEY = "savings";
    public static final String IS_ON_SALE_KEY = "isOnSale";


    // JSON Search Keys
    public static final String SEARCH_NAME_KEY = "external";
    public static final String SEARCH_PRICE_KEY = "cheapest";


}
