package com.example.sajilothree.ActivitiesPackage.ServiceProvidersPackage;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.AdapterPackages.SpListAdapter.SpListAdapter;
import com.example.sajilothree.ModelsPackage.SPListSharePropertyModel.SPListAPIModel.SpListModel;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.ServiceProviderListShare.SpListShareService;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.r0adkll.slidr.Slidr;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ServiceProvidersList extends AppCompatActivity {
    private RecyclerView serviceProviderView;
    private ShimmerFrameLayout loading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_providers_list_layout);
        Toolbar toolbar = findViewById(R.id.professionToolbar);
        setSupportActionBar(toolbar);
        // This sets the share properties between activities
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(SpListShareService.sharedPro.get(0).getHeading());
        }
        ImageView professionImage = findViewById(R.id.professionImage);
        Glide.with(this).load(SpListShareService.sharedPro.get(0).getProfessionImage())
                .into(professionImage);
        // This is to display the back button on top
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
        Slidr.attach(this);
        userInterface();

        ServiceProviderListApiCall();
    }

    private void ServiceProviderListApiCall() {
        try {
            loading.setVisibility(View.VISIBLE);
            loading.startShimmer();
            serviceProviderView.setVisibility(View.GONE);
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    BaseURL.BaseURL + BaseURL.getSPList + "?professionType="
                            + SpListShareService.sharedPro.get(0).getHeading(),
                    response -> {
                        loading.stopShimmer();
                        loading.setVisibility(View.GONE);
                        serviceProviderView.setVisibility(View.VISIBLE);
                        Toast.makeText(this, "Response", Toast.LENGTH_SHORT).show();
                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();
                        SpListModel spListModel = gson.fromJson(response, SpListModel.class);
                        setUpSPListView(spListModel);
                    },
                    error -> {
                        loading.stopShimmer();
                        loading.setVisibility(View.GONE);
                        serviceProviderView.setVisibility(View.VISIBLE);
                        Toast.makeText(this, "Error " + error, Toast.LENGTH_SHORT).show();
                    });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(this, "Exception: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    //Setting up the views for service providers list recycler view
    private void setUpSPListView(SpListModel spListModel) {
        serviceProviderView.setLayoutManager(new LinearLayoutManager(this));
        SpListAdapter spListAdapter = new SpListAdapter(this, spListModel);
        serviceProviderView.setAdapter(spListAdapter);
    }

    private void userInterface() {
        serviceProviderView = findViewById(R.id.serviceProviderList);
        loading = findViewById(R.id.sp_listLoading);
    }
}
