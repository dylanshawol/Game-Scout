package com.example.gamescout.ui.search;

import android.content.Intent;
import android.net.Uri;
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
import com.example.gamescout.ui.api.APIConst;
import com.example.gamescout.ui.api.Game;
import com.example.gamescout.ui.database.WishListDatabase;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchedGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchedGameFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchedGameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchedGameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchedGameFragment newInstance(String param1, String param2) {
        SearchedGameFragment fragment = new SearchedGameFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_searched_game, container, false);

        TextView searchedGameName = view.findViewById(R.id.searchedGameName);
        ImageView searchedGameImage = view.findViewById(R.id.searchedGameImage);
        TextView searchedGamePrice = view.findViewById(R.id.searchedGamePrice);
        Button addGameToWishList = view.findViewById(R.id.searchedGameButton);
        ImageView steamIcon = view.findViewById(R.id.searchedSteamIcon);

        if (getArguments() != null) {
            String gameName = getArguments().getString("gameName");
            String gameImage = getArguments().getString("gameImage");
            String gamePrice = getArguments().getString("gameNormalPrice");
            String steamAppID = getArguments().getString("steamAppID");

            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(gameName);
            searchedGameName.setText(gameName);
            searchedGamePrice.setText("$" + gamePrice);
            Picasso.get().load(gameImage).resize(960, 360).into(searchedGameImage);

            steamIcon.setOnClickListener(view1 -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(APIConst.STEAM_APP_ID_URL + getArguments().getString("steamAppID")));

                if (intent.resolveActivity(getActivity().getPackageManager()) != null) getActivity().startActivity(intent);
            });

            addGameToWishList.setOnClickListener(view1 -> {
                WishListDatabase db = new WishListDatabase(getContext());

                db.addGame(new Game(gameName, gameImage, gamePrice, steamAppID));

                db.close();

                Toast.makeText(getContext(), "Game added to wish list!", Toast.LENGTH_SHORT).show();

                Navigation.findNavController(view).navigate(R.id.navigation_wish_list);
            });
        }

        return view;
    }
}