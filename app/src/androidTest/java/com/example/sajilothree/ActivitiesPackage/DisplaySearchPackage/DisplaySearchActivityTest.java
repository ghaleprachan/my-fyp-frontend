package com.example.sajilothree.ActivitiesPackage.DisplaySearchPackage;

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

public class DisplaySearchActivityTest {
    @Rule
    public ActivityTestRule<DisplaySearchActivity> displaySearchActivityActivityTestRule = new
            ActivityTestRule<>(DisplaySearchActivity.class);
    private DisplaySearchActivity displaySearchActivity;

    @Before
    public void setUp() {
        displaySearchActivity = displaySearchActivityActivityTestRule.getActivity();
    }

    @After
    public void tearDown() {
        displaySearchActivity = null;
    }

    @Test
    public void searchItemValidate() {
        boolean test = true;
        boolean result = DisplaySearchActivity.validateSearchItem("Plumber");
        assertEquals(test, result);
    }

    @Test
    public void searchItemValidateEmpty() {
        boolean test = false;
        boolean result = DisplaySearchActivity.validateSearchItem("");
        assertEquals(test, result);
    }

    @Test
    public void testLaunch() {
        View backBtn = displaySearchActivity.findViewById(R.id.searchBack);
        View clearTxt = displaySearchActivity.findViewById(R.id.clear);
        View searchText = displaySearchActivity.findViewById(R.id.searchHere);
        View searchIcon = displaySearchActivity.findViewById(R.id.searchIcon);
        View searchItemView = displaySearchActivity.findViewById(R.id.searchUsers);
        View loading = displaySearchActivity.findViewById(R.id.loadingSearched);


        assertNotNull(backBtn);
        assertNotNull(clearTxt);
        assertNotNull(searchText);
        assertNotNull(searchIcon);
        assertNotNull(searchItemView);
        assertNotNull(loading);
    }

    @Test
    public void onCreate() {
    }
}