package com.example.sajilothree.ActivitiesPackage.SpProfilePackage;

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

public class SpProfileActivityTest {
    @Rule
    public ActivityTestRule<SpProfileActivity> profileActivityActivityTestRule = new
            ActivityTestRule<>(SpProfileActivity.class);
    private SpProfileActivity profileActivity;

    @Before
    public void setUp() {
        profileActivity = profileActivityActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch() {
        View edit = profileActivity.findViewById(R.id.edit);
        View name = profileActivity.findViewById(R.id.name);
        View rating = profileActivity.findViewById(R.id.ratings);
        View ratingNumber = profileActivity.findViewById(R.id.ratingNumber);
        View loading = profileActivity.findViewById(R.id.profileHeadingLoading);
        View headingLayout = profileActivity.findViewById(R.id.profileHeadingContents);
        View profileImage = profileActivity.findViewById(R.id.c_photo);
        assertNotNull(edit);
        assertNotNull(name);
        assertNotNull(rating);
        assertNotNull(ratingNumber);
        assertNotNull(headingLayout);
        assertNotNull(loading);
        assertNotNull(profileImage);
    }

    @After
    public void tearDown() {
        profileActivity = null;
    }

    @Test
    public void onCreate() {
    }
}