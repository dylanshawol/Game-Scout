package com.example.gamescout.ui.on_sale;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.gamescout.R;
import com.example.gamescout.ui.api.APIConst;
import com.example.gamescout.ui.api.Game;
import com.example.gamescout.ui.api.GameSingleton;


import org.json.JSONException;

import java.util.ArrayList;

public class OnSaleFragment extends Fragment {

    private OnSaleViewModel onSaleViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        onSaleViewModel =
                new ViewModelProvider(this).get(OnSaleViewModel.class);
        View root = inflater.inflate(R.layout.fragment_on_sale, container, false);

        ArrayList<Game> onSaleGamesList = new ArrayList<>();

        String url = APIConst.API_URL_DEALS;

        JsonArrayRequest r = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            String gameName;
            String gameNormalPrice;
            String gameSalePrice;
            String savings;
            String metacriticScore;

            for (int i = 0; i < response.length(); i++) {
                try {
                    gameName = response.getJSONObject(i).getString(APIConst.NAME_KEY);
                    gameNormalPrice = response.getJSONObject(i).getString(APIConst.NORMAL_PRICE_KEY);
                    gameSalePrice = response.getJSONObject(i).getString(APIConst.SALE_PRICE_KEY);
                    savings = response.getJSONObject(i).getString(APIConst.SAVINGS_KEY);
                    metacriticScore = response.getJSONObject(i).getString(APIConst.METACRITIC_SCORE_KEY);

                    onSaleGamesList.add(new Game(gameName, gameNormalPrice, "", metacriticScore, gameSalePrice, savings));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            RecyclerView recyclerView = root.findViewById(R.id.onSaleRecycler);

            recyclerView.setAdapter(new CustomGamesOnSaleAdapter(onSaleGamesList, getContext()));

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        }, error -> System.out.println(error.getLocalizedMessage()));

        GameSingleton.getInstance(getContext()).getRequestQueue().add(r);

        return root;
    }

    public static class CustomGamesOnSaleAdapter extends RecyclerView.Adapter<CustomGamesOnSaleAdapter.ViewHolder> {
        ArrayList<Game> gamesSearchList;
        Context context;

        public CustomGamesOnSaleAdapter(ArrayList<Game> gamesList, Context context) {
            this.gamesSearchList = gamesList;
            this.context = context;
        }

        @NonNull
        @Override
        public CustomGamesOnSaleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_on_sale_cardview_layout, parent, false);
            return new CustomGamesOnSaleAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomGamesOnSaleAdapter.ViewHolder holder, int position) {
            Game game = this.gamesSearchList.get(position);

            holder.gameName.setText(game.getGameName());
            holder.gameNormalPrice.setText("$" + game.getGameNormalPrice());
            holder.gameOnSalePrice.setText("$" + game.getGameSalePrice());

        }

        @Override
        public int getItemCount() {
            if (gamesSearchList == null) {
                return -1;
            }
            return gamesSearchList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            protected TextView gameName;
            protected TextView gameNormalPrice;
            protected TextView gameOnSalePrice;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                gameName = itemView.findViewById(R.id.onSaleName);
                gameNormalPrice = itemView.findViewById(R.id.onSaleNormalPrice);
                gameOnSalePrice = itemView.findViewById(R.id.onSalePrice);

            }
        }
    }
}