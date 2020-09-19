package com.example.sajilothree.ActivitiesPackage.UserFavorites;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.AdapterPackages.MyFavorites.MyFavoritesAdapter;
import com.example.sajilothree.ModelsPackage.UserFavoriteModels.FavoritesDetails.MyFavoritesDetails;
import com.example.sajilothree.ModelsPackage.UserFavoriteModels.FavoritesDetails.Result;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.r0adkll.slidr.Slidr;

import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

public class MyFavorites extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_favorites_layout);
        Slidr.attach(this);
        Toolbar toolbar = findViewById(R.id.fav_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.my_favorites));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        userInterface();

        favoritesApiCall();

        onRefresh();
    }

    private void onRefresh() {
        refresh.setOnRefreshListener(() -> {
            refresh.setColorSchemeResources(
                    R.color.colorAccent,
                    R.color.blue,
                    R.color.colorPrimary
            );
            favoritesApiCall();
        });
    }

    private void userInterface() {
        recyclerView = findViewById(R.id.favourites);
        refresh = findViewById(R.id.swipeRefresh);
    }

    private void favoritesApiCall() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tokenNumber", Username.username);
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    BaseURL.getUserFavDetails,
                    jsonObject,
                    response -> {
                        refresh.setRefreshing(false);
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            MyFavoritesDetails myFavorites = gson.fromJson(String.valueOf(response), MyFavoritesDetails.class);
                            if (myFavorites.getSuccess()) {
                                setDataToView(myFavorites.getResult());
                            }
                        } catch (Exception ex) {
                            Toast.makeText(this, "Response Parse Exception: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }, error -> {
                refresh.setRefreshing(false);
                Toast.makeText(this, "Failed to get request.", Toast.LENGTH_SHORT).show();
            }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);

        } catch (Exception ex) {
            Toast.makeText(this, "Exception: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setDataToView(List<Result> result) {
        try {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            MyFavoritesAdapter myFavoritesAdapter = new MyFavoritesAdapter(this, result);
            recyclerView.setAdapter(myFavoritesAdapter);
        } catch (Exception ex) {
            Toast.makeText(this, "Fill View Exception: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
