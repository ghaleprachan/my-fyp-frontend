package com.example.sajilothree.ActivitiesPackage.AddReview;

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

public class AddReviewActivityTest {

    @Rule
    public ActivityTestRule<AddReviewActivity> addReviewActivityActivityTestRule = new
            ActivityTestRule<>(AddReviewActivity.class);
    private AddReviewActivity addReviewActivity;


    @Before
    public void setUp() {
        addReviewActivity = addReviewActivityActivityTestRule.getActivity();
    }

    @After
    public void tearDown() {
        addReviewActivity = null;
    }

    @Test
    public void reviewValidation() {
        boolean test = true;
        boolean result = AddReviewActivity.reviewValidation("he is good", 3.5f);
        assertEquals(result, test);
    }

    @Test
    public void reviewValidationOne() {
        boolean test = false;
        boolean result = AddReviewActivity.reviewValidation("he is good", 0.0f);
        assertEquals(result, test);
    }

    @Test
    public void testLaunch() {
        View backBtn = addReviewActivity.findViewById(R.id.addBack);
        View postReview = addReviewActivity.findViewById(R.id.postReview);
        View rating = addReviewActivity.findViewById(R.id.ratings);
        View feedback = addReviewActivity.findViewById(R.id.feedbackText);
        View feedbackContent = addReviewActivity.findViewById(R.id.postView);
        View loadingContent = addReviewActivity.findViewById(R.id.loadingView);
        View loading = addReviewActivity.findViewById(R.id.animationImage);

        assertNotNull(backBtn);
        assertNotNull(postReview);
        assertNotNull(rating);
        assertNotNull(feedback);
        assertNotNull(feedbackContent);
        assertNotNull(loadingContent);
        assertNotNull(loading);
    }

    @Test
    public void onCreate() {
    }
}