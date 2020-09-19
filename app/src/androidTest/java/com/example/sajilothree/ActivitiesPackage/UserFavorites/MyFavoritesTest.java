package com.example.sajilothree.ActivitiesPackage.UserFavorites;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.test.rule.ActivityTestRule;

import com.example.sajilothree.ActivitiesPackage.AddPackage.AddPostActivity;
import com.example.sajilothree.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyFavoritesTest {
    @Rule
    public ActivityTestRule<MyFavorites> favoritesActivityTestRule = new
            ActivityTestRule<>(MyFavorites.class);
    private MyFavorites myFavorites;

    @Before
    public void setUp() {
        myFavorites = favoritesActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch() {
        View recyclerView = myFavorites.findViewById(R.id.favourites);
        View refresh = myFavorites.findViewById(R.id.swipeRefresh);
        
        assertNotNull(recyclerView);
        assertNotNull(refresh);
    }

    @After
    public void tearDown() {
        myFavorites = null;
    }

    @Test
    public void onCreate() {

    }
}