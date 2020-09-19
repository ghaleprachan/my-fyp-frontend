package com.example.sajilothree.ActivitiesPackage.CProfilePackage;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.test.rule.ActivityTestRule;

import com.example.sajilothree.ActivitiesPackage.AddPackage.AddPostActivity;
import com.example.sajilothree.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class CProfileActivityTest {
    @Rule
    public ActivityTestRule<CProfileActivity> activityActivityTestRule = new
            ActivityTestRule<>(CProfileActivity.class);
    private CProfileActivity profileActivity;

    @Before
    public void setUp() {
        profileActivity = activityActivityTestRule.getActivity();
    }

    @After
    public void tearDown() {
        profileActivity = null;
    }

    @Test
    public void testLaunch() {
        View edit = profileActivity.findViewById(R.id.edit);
        View name = profileActivity.findViewById(R.id.name);
        View loading = profileActivity.findViewById(R.id.profileHeadingLoading);
        View headingLayout = profileActivity.findViewById(R.id.profileHeadingContents);
        View profileImage = profileActivity.findViewById(R.id.c_photo);

        assertNotNull(edit);
        assertNotNull(name);
        assertNotNull(loading);
        assertNotNull(headingLayout);
        assertNotNull(profileImage);
    }

    @Test
    public void onCreate() {
    }
}