package com.example.sajilothree.ActivitiesPackage.DisplaySearchPackage.FragmentsPacakges;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sajilothree.R;

public class AllSearchResult extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.all_search_result, container, false);
    }

    private RecyclerView allSearches;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userInterface(view);

        RecyclerView.LayoutManager addressLayoutManager = new LinearLayoutManager(getContext());
        allSearches.setLayoutManager(addressLayoutManager);

    }

    private void userInterface(View view) {
        allSearches = view.findViewById(R.id.allSearchView);
    }
}
