package com.example.gamescout.ui.search;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.SearchView;
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
import com.example.gamescout.ui.database.WishListDatabase;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        SearchView searchView = view.findViewById(R.id.searchView);

        searchView.setIconifiedByDefault(false);

        searchView.setQueryHint("Search for a game");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
//                Navigation.findNavController(view).navigate(R.id.searchedGameFragment);

                ArrayList<Game> searchedGamesList = new ArrayList<>();

                try {
                    String searchURL = APIConst.API_URL_SEARCH + URLEncoder.encode(s, String.valueOf(StandardCharsets.UTF_8));

                    JsonArrayRequest r = new JsonArrayRequest(Request.Method.GET, searchURL, null, response -> {
                            String gameName;
                            String gameNormalPrice;
                            String gameImage;
                            String steamAppID;

                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    gameName = response.getJSONObject(i).getString(APIConst.SEARCH_NAME_KEY);
                                    gameNormalPrice = response.getJSONObject(i).getString(APIConst.SEARCH_PRICE_KEY);
                                    gameImage = response.getJSONObject(i).getString(APIConst.IMAGE_KEY);
                                    steamAppID = response.getJSONObject(i).getString(APIConst.STEAM_APP_ID_KEY);

                                    searchedGamesList.add(new Game(gameName, gameNormalPrice, gameImage, steamAppID));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        RecyclerView recyclerView = view.findViewById(R.id.searchRecycler);

                        recyclerView.setAdapter(new CustomGamesSearchAdapter(searchedGamesList, getContext()));

                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                    }, error -> System.out.println(error.getLocalizedMessage()));

                    GameSingleton.getInstance(getContext()).getRequestQueue().add(r);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) { return false; }
        });

        return view;

    }


    public static class CustomGamesSearchAdapter extends RecyclerView.Adapter<CustomGamesSearchAdapter.ViewHolder> {
        ArrayList<Game> gamesSearchList;
        Context context;

        public CustomGamesSearchAdapter(ArrayList<Game> gamesList, Context context) {
            this.gamesSearchList = gamesList;
            this.context = context;
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

            });

            holder.addToWishListIcon.setOnClickListener(view -> {
                WishListDatabase db = new WishListDatabase(context);
                db.addGame(new Game(game.getGameName(), game.getGameNormalPrice(), game.getGameImage(), game.getSteamAppID()));
                db.close();
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


            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                gameName = itemView.findViewById(R.id.searchName);
                gamePrice = itemView.findViewById(R.id.searchNormalPrice);
                linkIcon = itemView.findViewById(R.id.searchLinkBackground);
                addToWishListIcon = itemView.findViewById(R.id.searchAddBackground
                );
            }
        }
    }
}