package com.example.sajilothree;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.example.sajilothree.ActivitiesPackage.AddPackage.AddPostActivity;
import com.example.sajilothree.ActivitiesPackage.OPTActivityPackage.OTPActivity;
import com.example.sajilothree.ApiCallsPackage.UserFavorites.AddUserToFavorites;
import com.example.sajilothree.AppCollection.App;
import com.example.sajilothree.FragmentPackage.NotificationPackage.NotificationFragment;
import com.example.sajilothree.FragmentPackage.BookingPackage.BookingFragment;
import com.example.sajilothree.FragmentPackage.HomePackage.DashboardFragment;
import com.example.sajilothree.FragmentPackage.SearchPackage.SearchFragment;
import com.example.sajilothree.NotificationReceiver.NotificationReceiver;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jediburrell.customfab.FloatingActionButton;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Fragment active, homeFragment, searchFragment, notificationFragment, bookingFragment, profileFragment;
    private FragmentManager fragmentManager;
    private FloatingActionButton addPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        userInterface();
        placeFragment();
        addFragments();

        addPosts();
        onBottomNavItemClick();
        if (savedInstanceState != null) {
            savedInstanceState.clone();
        }
    }

    private void addPosts() {
        addPosts.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddPostActivity.class));
            Objects.requireNonNull(MainActivity.this).overridePendingTransition(R.anim.bottom_up,
                    R.anim.nothing);
        });
    }

    private void placeFragment() {
        homeFragment = new DashboardFragment();
        searchFragment = new SearchFragment();
        notificationFragment = new NotificationFragment();
        bookingFragment = new BookingFragment();
        fragmentManager = getSupportFragmentManager();
        active = homeFragment;
    }

    private void addFragments() {
        fragmentManager.beginTransaction().add(R.id.frameLayout, searchFragment, "search").hide(searchFragment).commit();
        fragmentManager.beginTransaction().add(R.id.frameLayout, notificationFragment, "notification").hide(notificationFragment).commit();
        fragmentManager.beginTransaction().add(R.id.frameLayout, bookingFragment, "booking").hide(bookingFragment).commit();
        fragmentManager.beginTransaction().add(R.id.frameLayout, active, "home").commit();
    }

    private void onBottomNavItemClick() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    fragmentManager.beginTransaction().hide(active).show(homeFragment).commit();
                    active = homeFragment;
                    return true;
                case R.id.search:
                    fragmentManager.beginTransaction().hide(active).show(searchFragment).commit();
                    active = searchFragment;
                    return true;
                case R.id.notification:
                    fragmentManager.beginTransaction().hide(active).show(notificationFragment).commit();
                    active = notificationFragment;
                    return true;
                case R.id.bookings:
                    fragmentManager.beginTransaction().hide(active).show(bookingFragment).commit();
                    active = bookingFragment;
                    return true;
            }
            return false;
        });
    }

    private void userInterface() {
        bottomNavigationView = findViewById(R.id.bottomNavView);
        addPosts = findViewById(R.id.addPosts);
    }
}