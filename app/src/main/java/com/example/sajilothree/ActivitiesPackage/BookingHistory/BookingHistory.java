package com.example.sajilothree.ActivitiesPackage.BookingHistory;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.AdapterPackages.BookingHistoryAdapter.BookingHistoryAdapter;
import com.example.sajilothree.MainActivity;
import com.example.sajilothree.ModelsPackage.BookingHistory.BookingHistoryModel;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.BookingHistory.BookingHistoryService;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.r0adkll.slidr.Slidr;

import org.json.JSONObject;

import java.util.Objects;

public class BookingHistory extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_history_activity);
        Slidr.attach(this);
        Toolbar toolbar = findViewById(R.id.fav_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.previous_bookings));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        userInterface();

        recentBookingAPICall();
        onRefreshPage();
    }

    private void onRefreshPage() {
        refresh.setOnRefreshListener(this::recentBookingAPICall);
    }

    private void userInterface() {
        recyclerView = findViewById(R.id.recentBooking);
        refresh = findViewById(R.id.swipeRefresh);
    }

    private void recentBookingAPICall() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tokenNumber", Username.username);
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    BaseURL.getBookingHistory,
                    jsonObject,
                    response -> {
                        refresh.setRefreshing(false);
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            BookingHistoryModel finalBookingModel = gson.fromJson(String.valueOf(response), BookingHistoryModel.class);
                            if (finalBookingModel.getSuccess()) {
                                if (BookingHistoryService.addHistory(finalBookingModel.getResult())) {
                                    fillUpHistoryContent();
                                }
                            }
                        } catch (Exception ex) {
                            Toast.makeText(this, "Response Parse Error: " + ex, Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        refresh.setRefreshing(false);
                        Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(this, "Exception: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void fillUpHistoryContent() {
        try {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            BookingHistoryAdapter bookingHistoryAdapter = new BookingHistoryAdapter(this, BookingHistoryService.bookingHistory);
            recyclerView.setAdapter(bookingHistoryAdapter);
        } catch (Exception ex) {
            Toast.makeText(this, "Not history found: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
