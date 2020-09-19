package com.example.sajilothree.ActivitiesPackage.UserSavedPosts;

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

public class SavedOfferGridTest {
    @Rule
    public ActivityTestRule<SavedOfferGrid> savedOfferGridActivityTestRule = new
            ActivityTestRule<>(SavedOfferGrid.class);
    private SavedOfferGrid savedOfferGrid;

    @Before
    public void setUp() {
        savedOfferGrid = savedOfferGridActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch() {
        View recyclerView = savedOfferGrid.findViewById(R.id.savedPostGridView);

        assertNotNull(recyclerView);
    }

    @After
    public void tearDown() {
        savedOfferGrid = null;
    }

    @Test
    public void onCreate() {
    }
}