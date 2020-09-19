package com.example.sajilothree.ActivitiesPackage.DisplaySearchPackage;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.AdapterPackages.DisplaySearchAdapter.SearchAdapter;
import com.example.sajilothree.ModelsPackage.SearchResultPacakge.SearchResultModel;
import com.example.sajilothree.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.r0adkll.slidr.Slidr;

public class DisplaySearchActivity extends AppCompatActivity {
    private ImageButton backBtn, clearTxt, searchIcon;
    private EditText searchText;
    private ViewPager searchViewPager;
    private TabLayout searchTabs;
    private RecyclerView searchItemView;
    private ShimmerFrameLayout loading;
    private TextView noResult;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_search_layout);
        userInterface();
        Slidr.attach(this);
        onClearSearch();
        onSearchTextChange();
        onBackBtnPress();
        onSearchPressed();
        noResult.setVisibility(View.GONE);

    }

    public static boolean validateSearchItem(String searchItem) {
        return !searchItem.isEmpty();
    }

    private void searchAPiCall() {
        loading.setVisibility(View.VISIBLE);
        searchItemView.setVisibility(View.GONE);
        loading.startShimmer();
        RequestQueue requestQueue;
        StringRequest request = new StringRequest(
                Request.Method.GET,
                BaseURL.BaseURL + BaseURL.search + searchText.getText(),
                response -> {
                    loading.stopShimmer();
                    loading.setVisibility(View.GONE);
                    searchItemView.setVisibility(View.VISIBLE);
                    try {
                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();
                        SearchResultModel searchResults = gson.fromJson(response,
                                SearchResultModel.class);
                        addToView(searchResults);
                    } catch (Exception ex) {
                        Toast.makeText(DisplaySearchActivity.this, "Ex" + ex, Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    loading.setVisibility(View.GONE);
                    searchItemView.setVisibility(View.GONE);
                    Toast.makeText(DisplaySearchActivity.this, "Error " + error.toString(), Toast.LENGTH_SHORT).show();
                });
        requestQueue = Volley.newRequestQueue(DisplaySearchActivity.this);
        requestQueue.add(request);
    }

    private void addToView(SearchResultModel searchResults) {
        if (searchResults.getResult().size() == 0) {
            searchItemView.setVisibility(View.GONE);
        }
        RecyclerView.LayoutManager addressLayoutManager = new LinearLayoutManager(DisplaySearchActivity.this);
        searchItemView.setLayoutManager(addressLayoutManager);
        if (searchResults.getResult().size() == 0) {
            noResult.setVisibility(View.VISIBLE);
        } else {
            noResult.setVisibility(View.GONE);
        }
        SearchAdapter searchAdapter = new SearchAdapter(DisplaySearchActivity.this, searchResults);
        searchItemView.setAdapter(searchAdapter);
    }

    private void viewPagerSetUp() {
        /*SearchPagerAdapter sectionsPagerAdapter = new SearchPagerAdapter(this,
                getSupportFragmentManager());
        searchViewPager.setAdapter(sectionsPagerAdapter);
        searchTabs.setupWithViewPager(searchViewPager);
        searchViewPager.setOffscreenPageLimit(3);*/
    }

    private void onSearchPressed() {
        searchText.setOnEditorActionListener((v, actionId, event) -> {
            Toast.makeText(DisplaySearchActivity.this, "Searching...", Toast.LENGTH_SHORT).show();
            searchAPiCall();
            return false;
        });
    }

    private void onBackBtnPress() {
        backBtn.setOnClickListener(v -> onBackPressed());
    }

    private void onSearchTextChange() {
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchText.getText().toString().equals("")) {
                    clearTxt.setVisibility(View.GONE);
                    searchIcon.setVisibility(View.VISIBLE);
                } else {
                    clearTxt.setVisibility(View.VISIBLE);
                    searchIcon.setVisibility(View.GONE);
                }
                searchAPiCall();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void onClearSearch() {
        clearTxt.setOnClickListener(v -> searchText.setText(""));
    }

    private void userInterface() {
        backBtn = findViewById(R.id.searchBack);
        clearTxt = findViewById(R.id.clear);
        searchText = findViewById(R.id.searchHere);
        searchIcon = findViewById(R.id.searchIcon);
        searchItemView = findViewById(R.id.searchUsers);
        loading = findViewById(R.id.loadingSearched);
        noResult = findViewById(R.id.noResult);
    }
}
