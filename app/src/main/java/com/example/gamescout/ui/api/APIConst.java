package com.example.gamescout.ui.api;

public class APIConst {
    public static final String API_URL_SEARCH = "https://www.cheapshark.com/api/1.0/games?title=";

    public static final String API_URL_DEALS = "https://www.cheapshark.com/api/1.0/deals";

    public static final String[] API_URL_DEALS_SORT_LIST = {
            "upperPrice", // Less than or equal
            "lowerPrice"  // Greater than or equal
    };
}
