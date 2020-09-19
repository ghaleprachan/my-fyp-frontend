package com.example.sajilothree.ActivitiesPackage.UserSavedPosts;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.AdapterPackages.SavedPostsAdapter.SavedOfferDetailsAdapter;
import com.example.sajilothree.AdapterPackages.SavedPostsAdapter.SavedOfferGridAdapter;
import com.example.sajilothree.ModelsPackage.SavedPostDetails.SavedPostDetailsModel;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.UserSavedPosts.OfferDetailsService;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.r0adkll.slidr.Slidr;

import org.json.JSONObject;

import java.util.Objects;

public class SavedOfferGrid extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_offer_grid_layout);
        Slidr.attach(this);
        Toolbar toolbar = findViewById(R.id.savedToolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.saved_offers));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        userInterface();

        getAllThePostsAPI();
    }

    private void userInterface() {
        recyclerView = findViewById(R.id.savedPostGridView);
    }

    private void getAllThePostsAPI() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tokenNumber", Username.username);
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    BaseURL.getAllSavedPostsDetails,
                    jsonObject,
                    response -> {
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            SavedPostDetailsModel detailsModel = gson.fromJson(String.valueOf(response), SavedPostDetailsModel.class);
                            if (detailsModel.getSuccess()) {
                                if (OfferDetailsService.addDetails(detailsModel.getResult())) {
                                    addDetailsToView();
                                }
                            } else {
                                Toast.makeText(this, "Something went wrong please refresh page.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(this, "Parse Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(this, "Parse Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void addDetailsToView() {
        try {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            SavedOfferGridAdapter savedOfferGridAdapter = new SavedOfferGridAdapter(this, OfferDetailsService.offerDetails);
            recyclerView.setAdapter(savedOfferGridAdapter);
        } catch (Exception ex) {
            Toast.makeText(this, "Failed to add in view: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
