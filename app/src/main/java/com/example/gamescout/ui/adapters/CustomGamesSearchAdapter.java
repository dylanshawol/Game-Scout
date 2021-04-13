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

public class CustomGamesSearchAdapter extends RecyclerView.Adapter<CustomGamesSearchAdapter.ViewHolder> {
    ArrayList<Game> gamesSearchList;
    Context context;
    Activity activity;

    public CustomGamesSearchAdapter(ArrayList<Game> gamesList, Context context) {
        this.gamesSearchList = gamesList;
        this.context = context;
    }

    public CustomGamesSearchAdapter(ArrayList<Game> gamesList, Context context, Activity activity) {
        this.gamesSearchList = gamesList;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_search_cardview_layout, parent, false);
        return new CustomGamesSearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Game game = this.gamesSearchList.get(position);

        holder.gameName.setText(game.getGameName());
        holder.gamePrice.setText("$" + game.getGameNormalPrice());

        holder.linkIcon.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(APIConst.STEAM_APP_ID_URL + game.getSteamAppID()));

            if (intent.resolveActivity(activity.getPackageManager()) != null) activity.startActivity(intent);
        });

        holder.addToWishListIcon.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);

            builder.setTitle("Add " + game.getGameName() + " to Wish List?")
                    // Yes
                    .setPositiveButton("YES", (dialogInterface, i) -> {
                        // Add Game to wish list
                        WishListDatabase db = new WishListDatabase(context);
                        db.addGame(new Game(game.getGameName(), game.getGameImage(), game.getGameNormalPrice(), game.getSteamAppID()));
                        db.close();

                        Navigation.findNavController(view).navigate(R.id.navigation_wish_list);
                    })
                    // No
                    .setNegativeButton("NO", ((dialogInterface, i) -> dialogInterface.dismiss()));

            builder.create().show();
        });

        holder.searchedGameCardView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();

            bundle.putString("gameName", game.getGameName());
            bundle.putString("gameImage", game.getGameImage());
            bundle.putString("gameNormalPrice", game.getGameNormalPrice());
            bundle.putString("steamAppID", game.getSteamAppID());

            Navigation.findNavController(view).navigate(R.id.searchedGameFragment, bundle);
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
        protected TextView gamePrice;
        protected ImageView linkIcon;
        protected ImageView addToWishListIcon;
        protected CardView searchedGameCardView;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gameName = itemView.findViewById(R.id.searchNameCV);
            gamePrice = itemView.findViewById(R.id.searchNormalPriceCV);
            linkIcon = itemView.findViewById(R.id.searchLinkBackgroundCV);
            addToWishListIcon = itemView.findViewById(R.id.searchAddBackgroundCV);
            searchedGameCardView = itemView.findViewById(R.id.searchedGameCardView);

        }
    }
}
