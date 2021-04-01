package com.example.gamescout.ui.on_sale;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.gamescout.R;

public class OnSaleFragment extends Fragment {

    private OnSaleViewModel onSaleViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        onSaleViewModel =
                new ViewModelProvider(this).get(OnSaleViewModel.class);
        View root = inflater.inflate(R.layout.fragment_on_sale, container, false);



        return root;
    }
}