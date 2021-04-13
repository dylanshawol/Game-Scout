package com.example.gamescout.ui.adapters;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamescout.R;
import com.example.gamescout.ui.api.APIConst;
import com.example.gamescout.ui.api.Game;
import com.example.gamescout.ui.database.WishListDatabase;

import java.util.ArrayList;

public class CustomGamesOnSaleAdapter extends RecyclerView.Adapter<CustomGamesOnSaleAdapter.ViewHolder> {
    ArrayList<Game> gamesSearchList;
    Context context;
    Activity activity;

    public CustomGamesOnSaleAdapter(ArrayList<Game> gamesList, Context context, Activity activity) {
        this.gamesSearchList = gamesList;
        this.context = context;
        this.activity = activity;
    }

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
                        WishListDatabase db = new WishListDatabase(context);

                        db.addGame(new Game(game.getGameName(), game.getGameImage(), game.getGameNormalPrice(),
                                            game.getGameSalePrice(), game.getGameSavings(), game.getSteamAppID(),
                                            game.getSteamRatingText(), game.getSteamRatingCount(), game.getMetacriticScore()));

                        db.close();

                        Toast.makeText(context, "Game has been added to wish list!", Toast.LENGTH_SHORT).show();

                        dialogInterface.dismiss();

                        Navigation.findNavController(view).navigate(R.id.navigation_wish_list);
                    })
                    // No
                    .setNegativeButton("NO", ((dialogInterface, i) -> dialogInterface.dismiss()));

            builder.create().show();
        });

        holder.openInSteamButton.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(APIConst.STEAM_APP_ID_URL + game.getSteamAppID()));

            if (intent.resolveActivity(activity.getPackageManager()) != null) activity.startActivity(intent);
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
            gameName = itemView.findViewById(R.id.onSaleNameCV);
            gameNormalPrice = itemView.findViewById(R.id.onSaleNormalPriceCV);
            gameOnSalePrice = itemView.findViewById(R.id.onSalePriceCV);
            addToWishListButton = itemView.findViewById(R.id.onSaleAddIconCV);
            openInSteamButton = itemView.findViewById(R.id.onSaleLinkBackgroundCV);
            onSaleCardView = itemView.findViewById(R.id.onSaleCardView);
        }
    }
}
