package com.example.gamescout.ui.on_sale;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamescout.R;
import com.example.gamescout.ui.api.Game;
import com.example.gamescout.ui.database.WishListDatabase;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OnSaleGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OnSaleGameFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OnSaleGameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OnSaleGameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OnSaleGameFragment newInstance(String param1, String param2) {
        OnSaleGameFragment fragment = new OnSaleGameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    static String onSaleGameNameText;
    static String onSaleGameImageText;
    static String onSaleGameNormalPriceText;
    static String onSaleGameSalePriceText;
    static String steamAppID;
    static String metacriticScore;
    static String savings;
    static String steamRatingCount;
    static String steamRatingText;

    ImageView onSaleGameImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_on_sale_game, container, false);

        TextView onSaleGameName = view.findViewById(R.id.onSaleGameName);
        onSaleGameImage = view.findViewById(R.id.onSaleGameImage);
        TextView onSaleGameNormalPrice = view.findViewById(R.id.onSaleGameNormalPrice);
        TextView onSaleGameSalePrice = view.findViewById(R.id.onSaleGamePrice);
        TextView onSaleSteamRating = view.findViewById(R.id.steamRating);
        TextView onSaleSteamRatingCount = view.findViewById(R.id.numOfSteamRatings);


        ImageView onSaleGameMetacriticIcon = view.findViewById(R.id.metacriticIcon);
        TextView onSaleGameMetacriticLabel = view.findViewById(R.id.openOnMetacritic);
        ImageView onSaleGameMetacriticRatingBackground = view.findViewById(R.id.onSaleGameMetacriticRatingBackground);
        TextView onSaleGameMetacriticRating = view.findViewById(R.id.onSaleGameMetacriticRating);
        TextView onSaleGameMetacriticRatingLabel = view.findViewById(R.id.metacriticRatingLabel);

        Button addToWishList = view.findViewById(R.id.searchedGameButton);

        if (getArguments() != null) {
            onSaleGameNameText = getArguments().getString("gameName");
            onSaleGameImageText = getArguments().getString("gameImage");
            onSaleGameNormalPriceText = getArguments().getString("gameNormalPrice");
            onSaleGameSalePriceText = getArguments().getString("gameSalePrice");
            steamAppID = getArguments().getString("steamAppID");
            savings = getArguments().getString("savings");
            metacriticScore = getArguments().getString("metacriticScore");
            steamRatingCount = getArguments().getString("steamRatingCount");
            steamRatingText = getArguments().getString("steamRatingText");

            onSaleGameName.setText(onSaleGameNameText);
            Picasso.get().load(onSaleGameImageText).resize(960, 360).into(onSaleGameImage);
            onSaleGameNormalPrice.setText("$" + onSaleGameNormalPriceText);
            onSaleGameSalePrice.setText("$" + onSaleGameSalePriceText);
            onSaleSteamRating.setText(steamRatingText);
            onSaleSteamRatingCount.setText(steamRatingCount);

            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(onSaleGameNameText);


            int metacriticScoreNum = Integer.parseInt(metacriticScore);

                if (metacriticScoreNum != 0) {
                    if      (metacriticScoreNum >= 0 && metacriticScoreNum <= 49) onSaleGameMetacriticRatingBackground.setColorFilter(Color.parseColor("#CC0202"));
                    else if (metacriticScoreNum >= 50 && metacriticScoreNum <= 74) onSaleGameMetacriticRatingBackground.setColorFilter(Color.parseColor("#FF8300"));
                    else if (metacriticScoreNum >= 75 && metacriticScoreNum <= 100) onSaleGameMetacriticRatingBackground.setColorFilter(Color.parseColor("#4D9416"));
                } else {
                    onSaleGameMetacriticRating.setVisibility(View.GONE);
                    onSaleGameMetacriticRatingBackground.setVisibility(View.GONE);
                    onSaleGameMetacriticRatingLabel.setVisibility(View.GONE);
                    onSaleGameMetacriticIcon.setVisibility(View.GONE);
                    onSaleGameMetacriticLabel.setVisibility(View.GONE);
                }

                onSaleGameMetacriticRating.setText(String.valueOf(metacriticScoreNum));
        }

        addToWishList.setOnClickListener(view1 -> {
          // Add game to wish list/database
            WishListDatabase db = new WishListDatabase(getContext());

            db.addGame(new Game(onSaleGameNameText, onSaleGameImageText, onSaleGameNormalPriceText, onSaleGameSalePriceText, savings, steamAppID, steamRatingText, steamRatingCount, metacriticScore));

            db.close();

            Toast.makeText(getContext(), "Game added to wish list!", Toast.LENGTH_SHORT).show();

            Navigation.findNavController(view).navigate(R.id.navigation_wish_list);
        });

        return view;
    }
}