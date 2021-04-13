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

                    onSaleGamesList.add(new Game(gameName, gameNormalPrice, gameImage, steamAppID, metacriticScore, gameSalePrice, savings, steamRatingCount, steamRatingText));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            RecyclerView recyclerView = view.findViewById(R.id.onSaleRecycler);

            recyclerView.setAdapter(new CustomGamesOnSaleAdapter(onSaleGamesList, getContext()));

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        }, error -> System.out.println(error.getLocalizedMessage()));

        GameSingleton.getInstance(getContext()).getRequestQueue().add(r);

        return view;
    }

    public class CustomGamesOnSaleAdapter extends RecyclerView.Adapter<CustomGamesOnSaleAdapter.ViewHolder> {
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

            holder.addToWishListButton.setOnClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                builder.setTitle("Add " + game.getGameName() + " to Wish List?")
                        // Yes
                        .setPositiveButton("YES", (dialogInterface, i) -> {
                            // Add Game to wish list
                            Bundle extra = new Bundle();



                            dialogInterface.dismiss();
                        })
                        // No
                        .setNegativeButton("NO", ((dialogInterface, i) -> dialogInterface.dismiss()));

                builder.create().show();
            });

            holder.openInSteamButton.setOnClickListener(view -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(APIConst.STEAM_APP_ID_URL + game.getSteamAppID()));

                if (intent.resolveActivity(activity.getPackageManager()) != null) startActivity(intent);

            });

            holder.onSaleCardView.setOnClickListener(view -> {
                Bundle bundle = new Bundle();

                bundle.putString("gameName", game.getGameName());
                bundle.putString("gameImage", game.getGameImage());
                bundle.putString("gameNormalPrice", game.getGameNormalPrice());
                bundle.putString("gameSalePrice", game.getGameSalePrice());
                bundle.putString("metacriticScore", game.getMetacriticScore());
                bundle.putString("savings", game.getGameSavings());
                bundle.putString("steamAppID", game.getSteamAppID());
                bundle.putString("steamRatingCount", game.getSteamRatingCount());
                bundle.putString("steamRatingText", game.getSteamRatingText());

                Navigation.findNavController(view).navigate(R.id.onSaleGameFragment, bundle);
            });
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
            protected ImageView addToWishListButton;
            protected ImageView openInSteamButton;
            protected CardView onSaleCardView;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                gameName = itemView.findViewById(R.id.searchName);
                gameNormalPrice = itemView.findViewById(R.id.searchNormalPrice);
                gameOnSalePrice = itemView.findViewById(R.id.onSalePrice);
                addToWishListButton = itemView.findViewById(R.id.onSaleAddIcon);
                openInSteamButton = itemView.findViewById(R.id.searchLinkBackground);
                onSaleCardView = itemView.findViewById(R.id.onSaleCardView);
            }
        }
    }
}