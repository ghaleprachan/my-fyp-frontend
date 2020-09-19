package com.example.sajilothree.FragmentPackage.HomePackage;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.ActivitiesPackage.BookingHistory.BookingHistory;
import com.example.sajilothree.ActivitiesPackage.CProfilePackage.CProfileActivity;
import com.example.sajilothree.ActivitiesPackage.QrCodePackage.QrCodeActivity;
import com.example.sajilothree.ActivitiesPackage.UserFavorites.MyFavorites;
import com.example.sajilothree.ActivitiesPackage.UserSavedPosts.SavedOfferGrid;
import com.example.sajilothree.FragmentPackage.HomePackage.SectionPagerAdapter.SectionsPagerAdapter;
import com.example.sajilothree.ActivitiesPackage.SettingPackage.Setting;
import com.example.sajilothree.ActivitiesPackage.SpProfilePackage.SpProfileActivity;
import com.example.sajilothree.ModelsPackage.HomeModel.BasicUserDetails.BasicUserDetailsModel;
import com.example.sajilothree.R;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;

import com.example.sajilothree.ServicesPackage.BasicUserDetailsHolder.BasicDetailsService;
import com.example.sajilothree.UserTypeHolder.UserTypes;
import com.example.sajilothree.ServicesPackage.UsernameHolder.OtherPersonUserId;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class DashboardFragment extends Fragment implements
        NavigationView.OnNavigationItemSelectedListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment_drawer, container, false);
    }

    private TextView name, id;
    private ImageView goProfile, userProfileImage;
    private ViewPager viewPager;
    private TabLayout tabs;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        userInterface(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        }

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getContext(),
                getChildFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        navigationView.setNavigationItemSelectedListener(this);
        toggle.syncState();
        int size = navigationView.getMenu().size();
        for (int i = 0; i < size; i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
        }
        setHasOptionsMenu(true);
        onNavProfileClick();
        basicUserDetailsAPICall();
    }

    private void basicUserDetailsAPICall() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tokenNumber", Username.username);
            RequestQueue requestQueue;
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    BaseURL.BaseURL + BaseURL.basicUserDetails,
                    jsonObject,
                    response -> {
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            BasicUserDetailsModel profileHeading = gson.fromJson(String.valueOf(response),
                                    BasicUserDetailsModel.class);
                            if (BasicDetailsService.addUserDetails(profileHeading)) {
                                populatingDrawer();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> Toast.makeText(getContext(), "Error " + error.toString(), Toast.LENGTH_SHORT).show());
            requestQueue = Volley.newRequestQueue(requireActivity());
            requestQueue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void populatingDrawer() {
        try {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.profile_placeholder);
            Glide.with(this)
                    .load(BaseURL.BaseURL + BasicDetailsService.userDetailsModels.get(0).getResult().get(0).getUserProfileImage())
                    .apply(options).into(userProfileImage);
            name.setText(BasicDetailsService.userDetailsModels.get(0).getResult().get(0).getFullName());
            id.setText(BasicDetailsService.userDetailsModels.get(0).getResult().get(0).getUsername());
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Exception: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    private void onNavProfileClick() {
        goProfile.setOnClickListener(v -> {
            if (OtherPersonUserId.setUserId(Username.username)) {
                activityChange();
                drawer.closeDrawer(navigationView);
            }
        });
    }

    private void userInterface(View view) {
        viewPager = view.findViewById(R.id.dashboard_view_pager);
        tabs = view.findViewById(R.id.tabs);
        drawer = view.findViewById(R.id.drawer_layout);
        navigationView = view.findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        goProfile = headerView.findViewById(R.id.navProfile);
        name = headerView.findViewById(R.id.name);
        id = headerView.findViewById(R.id.username);
        userProfileImage = headerView.findViewById(R.id.imageView);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home_option_menu, menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.filter_posts:
                Toast.makeText(getContext(), "Show filter options", Toast.LENGTH_SHORT).show();
                break;
            case R.id.qr_code:
                startActivity(new Intent(getContext(), QrCodeActivity.class));
                requireActivity().overridePendingTransition(R.anim.nav_default_pop_enter_anim,
                        R.anim.nav_default_pop_exit_anim);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_profile:
                //byte[] byteArray = Base64.getDecoder(Username.username.getBytes());
                if (OtherPersonUserId.setUserId(Username.username)) {
                    activityChange();
                }
                break;
            case R.id.nav_setting:
                startActivity(new Intent(getContext(), Setting.class));
                requireActivity().overridePendingTransition(R.anim.nav_default_pop_enter_anim,
                        R.anim.nav_default_pop_exit_anim);
                break;
            case R.id.nav_saved_offers:
                startActivity(new Intent(getContext(), SavedOfferGrid.class));
                requireActivity().overridePendingTransition(R.anim.nav_default_pop_enter_anim,
                        R.anim.nav_default_pop_exit_anim);
                break;
            case R.id.nav_favorites:
                startActivity(new Intent(getContext(), MyFavorites.class));
                requireActivity().overridePendingTransition(R.anim.nav_default_pop_enter_anim,
                        R.anim.nav_default_pop_exit_anim);
                break;
            case R.id.nav_booking_history:
                startActivity(new Intent(getContext(), BookingHistory.class));
                requireActivity().overridePendingTransition(R.anim.nav_default_pop_enter_anim,
                        R.anim.nav_default_pop_exit_anim);
                break;
        }
        drawer.closeDrawer(navigationView);
        return false;
    }

    private void activityChange() {
        try {
            if (BasicDetailsService.userDetailsModels.get(0).getResult().get(0)
                    .getUserType().equals(UserTypes.customer)) {
                startActivity(new Intent(getContext(), CProfileActivity.class));
                requireActivity().overridePendingTransition(R.anim.nav_default_pop_enter_anim,
                        R.anim.nav_default_pop_exit_anim);
            } else if (BasicDetailsService.userDetailsModels.get(0).getResult().get(0)
                    .getUserType().equals(UserTypes.sp)) {
                startActivity(new Intent(getContext(), SpProfileActivity.class));
                requireActivity().overridePendingTransition(R.anim.nav_default_pop_enter_anim,
                        R.anim.nav_default_pop_exit_anim);
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Sorry, username not found!", Toast.LENGTH_SHORT).show();
        }
    }
}
