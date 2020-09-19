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

public class SavedOfferListDetailsTest {
    @Rule
    public ActivityTestRule<SavedOfferListDetails> savedOfferListDetailsActivityTestRule = new
            ActivityTestRule<>(SavedOfferListDetails.class);
    private SavedOfferListDetails savedOfferListDetails;

    @Before
    public void setUp() {
        savedOfferListDetails = savedOfferListDetailsActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch() {
        View recyclerView = savedOfferListDetails.findViewById(R.id.savedPostGridView);
        assertNotNull(recyclerView);
    }

    @After
    public void tearDown() {
        savedOfferListDetails = null;
    }

    @Test
    public void onCreate() {
    }

    @Test
    public void selectedPosition() {
    }
}