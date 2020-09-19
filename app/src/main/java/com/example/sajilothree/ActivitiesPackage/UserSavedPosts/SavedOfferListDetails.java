package com.example.sajilothree.ActivitiesPackage.UserSavedPosts;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sajilothree.AdapterPackages.SavedPostsAdapter.SavedOfferDetailsAdapter;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.UserSavedPosts.OfferDetailsService;
import com.r0adkll.slidr.Slidr;

import java.util.Objects;

public class SavedOfferListDetails extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_offer_list_layout);
        setContentView(R.layout.saved_offer_grid_layout);
        Slidr.attach(this);
        Toolbar toolbar = findViewById(R.id.savedToolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.saved_offers));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        userInterface();

        loadDetailsView();
    }

    public Integer selectedPosition() {
        return getIntent().getIntExtra("position", 0);
    }

    private void loadDetailsView() {
        try {
            Toast.makeText(this, selectedPosition().toString(), Toast.LENGTH_SHORT).show();
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            SavedOfferDetailsAdapter savedOfferGridAdapter = new SavedOfferDetailsAdapter(this, OfferDetailsService.offerDetails);
            recyclerView.setAdapter(savedOfferGridAdapter);

//            recyclerView.smoothScrollToPosition(selectedPosition());
            recyclerView.scrollToPosition(selectedPosition());

        } catch (Exception ex) {
            Toast.makeText(this, "Exception: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void userInterface() {
        recyclerView = findViewById(R.id.savedPostGridView);
    }

}
