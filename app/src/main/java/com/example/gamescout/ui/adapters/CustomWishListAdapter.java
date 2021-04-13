package com.example.gamescout.ui.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.example.gamescout.ui.wish_list.WishListFragment;

import java.util.ArrayList;


public class CustomWishListAdapter extends RecyclerView.Adapter<CustomWishListAdapter.ViewHolder> {
    ArrayList<Game> wishListGamesList;
    Context context;
    Activity activity;

    public CustomWishListAdapter(ArrayList<Game> gamesList, Context context, Activity activity) {
        this.wishListGamesList = gamesList;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CustomWishListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wish_list_cardview_layout, parent, false);
        return new CustomWishListAdapter.ViewHolder(view);
    }

    double totalPrice = 0;

    @Override
    public void onBindViewHolder(@NonNull CustomWishListAdapter.ViewHolder holder, int position) {
        Game game = this.wishListGamesList.get(position);

        holder.gameName.setText(game.getGameName());
        holder.gameNormalPrice.setText("$" + game.getGameNormalPrice());


        if (game.getGameSalePrice() == null) {
            holder.wishListPrice.setText("$" + game.getGameNormalPrice());
            holder.gameNormalPrice.setText("");

            totalPrice += Double.parseDouble(game.getGameNormalPrice());
        } else {
            holder.wishListPrice.setText("$" + game.getGameSalePrice());
            holder.wishListPrice.setPadding(15, 0, 0, 0);

            totalPrice += Double.parseDouble(game.getGameSalePrice());

        }

        WishListFragment.totalPrice = totalPrice;

        holder.deleteButton.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);

            builder.setTitle("Delete " + game.getGameName() + " from Wish List?")
                    // Yes
                    .setPositiveButton("YES", (dialogInterface, i) -> {
                        // Delete the game from the wish list
                        WishListDatabase db = new WishListDatabase(context);

                        db.deleteGame(game.getId());

                        if (!wishListGamesList.isEmpty()) {
                            wishListGamesList.remove(position);
                        }

                        notifyItemRemoved(position);

                        if (game.getGameSalePrice() == null) {
                            WishListFragment.totalPrice -= Double.parseDouble(game.getGameNormalPrice());
                        } else {
                            WishListFragment.totalPrice -= Double.parseDouble(game.getGameSalePrice());
                        }

                        db.close();

                        Toast.makeText(context, game.getGameName() + " has been deleted.", Toast.LENGTH_SHORT).show();

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

    }

    @Override
    public int getItemCount() {
        if (wishListGamesList == null) {
            return -1;
        }
        return wishListGamesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView gameName;
        protected TextView gameNormalPrice;
        protected TextView wishListPrice;
        protected ImageView deleteButton;
        protected ImageView openInSteamButton;
        protected CardView wishListCardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gameName = itemView.findViewById(R.id.wishListNameCV);
            gameNormalPrice = itemView.findViewById(R.id.wishListNormalPriceCV);
            wishListPrice = itemView.findViewById(R.id.wishListPriceCV);
            deleteButton = itemView.findViewById(R.id.wishListAddButtonBackgroundCV);
            openInSteamButton = itemView.findViewById(R.id.wishListLinkBackgroundCV);
            wishListCardView = itemView.findViewById(R.id.wishListCardView);
        }
    }
}
