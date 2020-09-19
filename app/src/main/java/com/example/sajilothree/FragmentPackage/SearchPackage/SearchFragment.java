package com.example.sajilothree.FragmentPackage.SearchPackage;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.ActivitiesPackage.DisplaySearchPackage.DisplaySearchActivity;
import com.example.sajilothree.ActivitiesPackage.QrCodePackage.QrCodeActivity;
import com.example.sajilothree.AdapterPackages.SearchAdapters.RecommendedSPAdapter;
import com.example.sajilothree.AdapterPackages.SearchAdapters.ServicesAdapter;
import com.example.sajilothree.FragmentPackage.SearchPackage.CitySwipeAdapter.CitySwipeAdapter;
import com.example.sajilothree.ModelsPackage.SearchModel.CitiesModelPackage.CitiesModel;
import com.example.sajilothree.ModelsPackage.SearchModel.RecommendedSPModel.RecommendedSPModel;
import com.example.sajilothree.ModelsPackage.SearchModel.ServicesModel.ServicesModel;
import com.example.sajilothree.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.Objects;

public class SearchFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_fragment, container, false);
    }

    private EditText searchHere;
    private RecyclerView servicesView, recommendedServices;
    private ShimmerFrameLayout servicesLoading, recommendedServicesLoading;
    private ViewPager viewPager;
    private CitySwipeAdapter swipeAdapter;
    private int custom_position = 0;
    private LinearLayout dotsLayout;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userInterface(view);
        SearchClick();
        ServicesApiCall();

        CitiesAPICall();
        setSlidingViewPager();

        RecommendedServicesAPICall();

    }

    // recommended services api call
    private void RecommendedServicesAPICall() {
        try {
            recommendedServicesLoading.setVisibility(View.VISIBLE);
            recommendedServicesLoading.startShimmer();
            recommendedServices.setVisibility(View.GONE);
            StringRequest request = new StringRequest(Request.Method.GET, BaseURL.BaseURL + BaseURL.getRecommendedServices,
                    response -> {
                        recommendedServicesLoading.setVisibility(View.GONE);
                        recommendedServicesLoading.stopShimmer();
                        recommendedServices.setVisibility(View.VISIBLE);
                        try {
                            RecommendedSPModel spModel = new GsonBuilder().create().fromJson(response, RecommendedSPModel.class);
                            setUpRecommendedSpView(spModel);
                        } catch (Exception ex) {
                            Toast.makeText(getContext(), "Exception: " + ex, Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        recommendedServicesLoading.setVisibility(View.GONE);
                        recommendedServicesLoading.stopShimmer();
                        recommendedServices.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(), "Error " + error, Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
            requestQueue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // setting up adapter for recommended service providers
    private void setUpRecommendedSpView(RecommendedSPModel spModel) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        recommendedServices.setLayoutManager(layoutManager);
        RecommendedSPAdapter recommendedSPAdapter = new RecommendedSPAdapter(getContext(), spModel);
        recommendedServices.setAdapter(recommendedSPAdapter);
    }

    private void CitiesAPICall() {
        try {
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    BaseURL.BaseURL + BaseURL.getCities,
                    response -> {
                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();
                        CitiesModel model = gson.fromJson(response,
                                CitiesModel.class);
                        prepareDots(custom_position++, model.getResult().size());
                        onPageChange(model.getResult().size());
                        swipeAdapter = new CitySwipeAdapter(getContext(), model);
                        viewPager.setAdapter(swipeAdapter);
                    },
                    error -> Toast.makeText(getContext(), "Error" + error, Toast.LENGTH_SHORT).show()
            );
            RequestQueue requestQueue = Volley.newRequestQueue(
                    requireContext());
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Error " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    //    detecting the pager and changing the dots accordingly
    private void onPageChange(int numCities) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                prepareDots(position, numCities);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    //    setting up time for city change on search
    private void setSlidingViewPager() {
        Runnable mLoopingRunnable = new Runnable() {
            public void run() {
                try {
                    int currentItem = viewPager.getCurrentItem();
                    int totalItems = Objects.requireNonNull(viewPager.getAdapter()).getCount();
                    int nextItem = (currentItem + 1) % totalItems;
                    viewPager.setCurrentItem(nextItem, false);
                    viewPager.postDelayed(this, 3000);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(mLoopingRunnable, 2000);
    }

    //    These are for making circular dots on city slider
    private void prepareDots(int currentSlidePosition, int numCities) {
        try {

            if (dotsLayout.getChildCount() > 0) {
                dotsLayout.removeAllViews();
            }
            ImageView[] dots = new ImageView[numCities];

            for (int i = 0; i < numCities; i++) {
                dots[i] = new ImageView(getContext());
                if (i == currentSlidePosition) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(requireContext(),
                            R.drawable.active_dot_search));
                } else {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(requireContext(),
                            R.drawable.active_not_dot_search));
                }
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(4, 0, 4, 0);
                dotsLayout.addView(dots[i], layoutParams);
            }
        } catch (Exception ex) {
            Log.d("Not supported", Objects.requireNonNull(ex.getMessage()));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void ServicesApiCall() {
        servicesLoading.setVisibility(View.VISIBLE);
        servicesLoading.startShimmer();
        servicesView.setVisibility(View.GONE);
        RequestQueue requestQueue;
        StringRequest request = new StringRequest(
                Request.Method.GET,
                BaseURL.searchServices,
                response -> {
                    servicesLoading.setVisibility(View.GONE);
                    servicesLoading.stopShimmer();
                    servicesView.setVisibility(View.VISIBLE);
                    try {
                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();
                        ServicesModel availableServices = gson.fromJson(response,
                                ServicesModel.class);
                        addServicesToView(availableServices);
                    } catch (Exception ex) {
                        servicesLoading.setVisibility(View.GONE);
                        servicesView.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    servicesLoading.setVisibility(View.GONE);
                    servicesView.setVisibility(View.GONE);
                });
        requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(request);
    }

    // setting up adapter for services
    private void addServicesToView(ServicesModel availableServices) {
        LinearLayoutManager servicesLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        servicesView.setLayoutManager(servicesLayoutManager);
        ServicesAdapter servicesAdapter = new ServicesAdapter(getContext(), availableServices);
        servicesView.setAdapter(servicesAdapter);
    }

    // when search on top is clicked
    private void SearchClick() {
        searchHere.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Open", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getContext(), DisplaySearchActivity.class));
            requireActivity().overridePendingTransition(0, 0);
        });
    }

    private void userInterface(View view) {
        servicesView = view.findViewById(R.id.servicesView);
        searchHere = view.findViewById(R.id.searchHere);
        servicesLoading = view.findViewById(R.id.serviceLoading);
        viewPager = view.findViewById(R.id.slideCityViewPager);
        dotsLayout = view.findViewById(R.id.dotsContainer);
        recommendedServices = view.findViewById(R.id.recommendedServices);
        recommendedServicesLoading = view.findViewById(R.id.recommendedServicesLoading);
    }
}
