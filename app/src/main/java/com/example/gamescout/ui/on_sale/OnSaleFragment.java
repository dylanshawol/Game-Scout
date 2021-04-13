package com.example.gamescout.ui.on_sale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.gamescout.R;
import com.example.gamescout.ui.adapters.CustomGamesOnSaleAdapter;
import com.example.gamescout.ui.api.APIConst;
import com.example.gamescout.ui.api.Game;
import com.example.gamescout.ui.api.GameSingleton;


import org.json.JSONException;

import java.util.ArrayList;

public class OnSaleFragment extends Fragment {

    private OnSaleViewModel onSaleViewModel;
    private Activity activity;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        onSaleViewModel = new ViewModelProvider(this).get(OnSaleViewModel.class);
        View view = inflater.inflate(R.layout.fragment_on_sale, container, false);

        activity = getActivity();

        ArrayList<Game> onSaleGamesList = new ArrayList<>();

        String url = APIConst.API_URL_DEALS;

        JsonArrayRequest r = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            String gameName;
            String gameImage;
            String steamAppID;
            String gameNormalPrice;
            String gameSalePrice;
            String savings;
            String metacriticScore;
            String steamRatingCount;
            String steamRatingText;

            for (int i = 0; i < response.length(); i++) {
                try {
                    gameName = response.getJSONObject(i).getString(APIConst.NAME_KEY);
                    gameImage = response.getJSONObject(i).getString(APIConst.IMAGE_KEY);
                    steamAppID = response.getJSONObject(i).getString(APIConst.STEAM_APP_ID_KEY);
                    gameNormalPrice = response.getJSONObject(i).getString(APIConst.NORMAL_PRICE_KEY);
                    gameSalePrice = response.getJSONObject(i).getString(APIConst.SALE_PRICE_KEY);
                    savings = response.getJSONObject(i).getString(APIConst.SAVINGS_KEY);
                    metacriticScore = response.getJSONObject(i).getString(APIConst.METACRITIC_SCORE_KEY);
                    steamRatingCount = response.getJSONObject(i).getString(APIConst.STEAM_RATING_COUNT_KEY);
                    steamRatingText = response.getJSONObject(i).getString(APIConst.STEAM_RATING_TEXT_KEY);

                    onSaleGamesList.add(new Game(gameName, gameImage, gameNormalPrice, gameSalePrice, savings, steamAppID, steamRatingText, steamRatingCount, metacriticScore));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            RecyclerView recyclerView = view.findViewById(R.id.onSaleRecycler);

            recyclerView.setAdapter(new CustomGamesOnSaleAdapter(onSaleGamesList, getContext(), getActivity()));

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        }, error -> System.out.println(error.getLocalizedMessage()));

        GameSingleton.getInstance(getContext()).getRequestQueue().add(r);

        return view;
    }
}