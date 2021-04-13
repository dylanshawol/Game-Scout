package com.example.gamescout.ui.wish_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.gamescout.R;

public class WishListFragment extends Fragment {

    private WishListViewModel wishListViewModel;

    public static final int UPDATE = 1;
    public static final int CREATE = 2;

    public static final String ACTION_TYPE = "action_type";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        wishListViewModel =
                new ViewModelProvider(this).get(WishListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_wish_list, container, false);


        return root;
    }
}