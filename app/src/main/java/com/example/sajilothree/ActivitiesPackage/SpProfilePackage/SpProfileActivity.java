package com.example.sajilothree.ActivitiesPackage.SpProfilePackage;

import android.annotation.SuppressLint;
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
import com.example.sajilothree.ActivitiesPackage.CProfilePackage.CProfileActivity;
import com.example.sajilothree.ActivitiesPackage.EditProfile.EditProfileActivity;
import com.example.sajilothree.ActivitiesPackage.SpProfilePackage.ProfilePagerAdapterPackage.ProfilePagerAdapter;
import com.example.sajilothree.MainActivity;
import com.example.sajilothree.ModelsPackage.ProfileModelPackage.ProfileHeading.ProfileHeadingModel;
import com.example.sajilothree.ServicesPackage.UsernameHolder.OtherPersonUserId;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;
import com.example.sajilothree.VeryUserModel.VerifyUser;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sajilothree.R;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class SpProfileActivity extends AppCompatActivity {
    private static CircleImageView edit, profileImage;
    private TextView name, ratingNumber;
    private RatingBar rating;
    private ShimmerFrameLayout loading;
    private LinearLayout headingLayout;
    private String nameIs = null;
    private String headerBaseURL = BaseURL.BaseURL + BaseURL.headerURL;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sp_profile_activity_layout);
        userInterface();
        Toolbar profileToolbar = findViewById(R.id.cProfileToolbar);
        setSupportActionBar(profileToolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
//        This is all about view pager
        ProfilePagerAdapter sectionsPagerAdapter = new ProfilePagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        final TabLayout tabs = findViewById(R.id.profileTabs);
        tabs.setupWithViewPager(viewPager);

        verifyUser();
        //viewPager.setOffscreenPageLimit(4);
        profileDetailsAPiCall();
        onEditClick();
        onAppBarPositionChange();
    }

    //    verify the user and show only needed details to visitor
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void verifyUser() {
        try {
            if (VerifyUser.userVerification().equals(Username.username)) {
                edit.setVisibility(View.VISIBLE);
            } else {
                edit.setVisibility(View.GONE);
            }
        } catch (Exception ex) {
            edit.setVisibility(View.GONE);
        }
    }

    private void onAppBarPositionChange() {
        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.c_toolbar_layout);
        AppBarLayout appBarLayout = findViewById(R.id.c_app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    if (nameIs != null) {
                        collapsingToolbarLayout.setTitle(nameIs);
                        collapsingToolbarLayout.setCollapsedTitleGravity(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        collapsingToolbarLayout.setTitle(" ");
                        collapsingToolbarLayout.setCollapsedTitleGravity(View.TEXT_ALIGNMENT_CENTER);
                    }
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    private void userInterface() {
        edit = findViewById(R.id.edit);
        name = findViewById(R.id.name);
        rating = findViewById(R.id.ratings);
        ratingNumber = findViewById(R.id.ratingNumber);
        loading = findViewById(R.id.profileHeadingLoading);
        headingLayout = findViewById(R.id.profileHeadingContents);
        profileImage = findViewById(R.id.c_photo);
    }

    private void onEditClick() {
        edit.setOnClickListener(v -> {
            startActivity(new Intent(this, EditProfileActivity.class));
            overridePendingTransition(R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim);
            finish();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void profileDetailsAPiCall() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tokenNumber", VerifyUser.userVerification());

            loading.startShimmer();
            RequestQueue requestQueue;
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    headerBaseURL,
                    jsonObject,
                    response -> {
                        loading.stopShimmer();
                        loading.setVisibility(View.GONE);
                        headingLayout.setVisibility(View.VISIBLE);
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            ProfileHeadingModel profileHeading = gson.fromJson(String.valueOf(response),
                                    ProfileHeadingModel.class);
                            if (OtherPersonUserId.setUserFullName(profileHeading.getResult().getFullName())) {
                                fillUserHeading(profileHeading);
                            }
                        } catch (Exception ex) {
                            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        loading.stopShimmer();
                        loading.setVisibility(View.GONE);
                        headingLayout.setVisibility(View.GONE);
                        Toast.makeText(SpProfileActivity.this, "Error " + error.toString(), Toast.LENGTH_SHORT).show();
                    });
            requestQueue = Volley.newRequestQueue(SpProfileActivity.this);
            requestQueue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    private void fillUserHeading(ProfileHeadingModel profileHeading) {
        nameIs = profileHeading.getResult().getFullName();
        name.setText(profileHeading.getResult().getFullName());
        ratingNumber.setText(" /" + (profileHeading.getResult().getUserRating()));
        rating.setRating(Float.parseFloat(String.valueOf(profileHeading.getResult().getUserRating())));

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.profile_placeholder);
        Glide.with(this)
                .load(BaseURL.BaseURL +
                        profileHeading.getResult()
                                .getUserProfileImage())
                .apply(options)
                .into(profileImage);
    }
}

