package com.example.gamescout.ui.wish_list;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamescout.R;
import com.example.gamescout.ui.adapters.CustomWishListAdapter;
import com.example.gamescout.ui.api.Game;
import com.example.gamescout.ui.database.WishListDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class WishListFragment extends Fragment {

    private WishListViewModel wishListViewModel;

    public static double totalPrice;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        wishListViewModel = new ViewModelProvider(this).get(WishListViewModel.class);

        View view = inflater.inflate(R.layout.fragment_wish_list, container, false);
        WishListDatabase db = new WishListDatabase(getContext());

        ArrayList<Game> wishListGames = db.getAllGames();

            ImageView wishListIcon = view.findViewById(R.id.wishListIcon);
            TextView wishListText = view.findViewById(R.id.wishListText);
            TextView totalPriceLabel = view.findViewById(R.id.totalPriceLabel);
            TextView totalPriceValue = view.findViewById(R.id.totalPriceValue);

        if (!wishListGames.isEmpty()) {
            wishListIcon.setImageDrawable(null);
            wishListText.setText("");

            if (wishListGames.size() > 0) {
                totalPriceLabel.setText("Total Price:");
                totalPriceValue.setText("$" + new DecimalFormat("#.##").format(totalPrice));
            }

            RecyclerView wishListRecycler = view.findViewById(R.id.wishListRecycler);

            wishListRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

            wishListRecycler.setAdapter(new CustomWishListAdapter(wishListGames, getContext(), getActivity()));
        } else {
            totalPriceLabel.setText("");
            totalPriceValue.setText("");

        }

        db.close();

        return view;
    }
}