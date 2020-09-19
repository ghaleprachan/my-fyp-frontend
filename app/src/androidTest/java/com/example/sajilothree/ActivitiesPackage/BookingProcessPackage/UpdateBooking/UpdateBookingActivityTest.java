package com.example.sajilothree.ActivitiesPackage.BookingProcessPackage.UpdateBooking;

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

public class UpdateBookingActivityTest {
    @Rule
    public ActivityTestRule<UpdateBookingActivity> updateBookingActivityActivityTestRule = new
            ActivityTestRule<>(UpdateBookingActivity.class);
    private UpdateBookingActivity bookingActivity;

    @Before
    public void setUp() {
        bookingActivity = updateBookingActivityActivityTestRule.getActivity();
    }

    @After
    public void tearDown() {
        bookingActivity = null;
    }

    @Test
    public void formValidation() {
        boolean result = true;
        boolean actual = UpdateBookingActivity.formValidationOne("plumber",
                "I have a broken bed", "25-June-2019");
        assertEquals(result, actual);
    }

    @Test
    public void testLaunch() {
        View backBtn = bookingActivity.findViewById(R.id.backEditBill);
        View serviceType = bookingActivity.findViewById(R.id.service_type);
        View problemDescription = bookingActivity.findViewById(R.id.problem_description);
        View expectedDate = bookingActivity.findViewById(R.id.expected_date);
        View expectedTime = bookingActivity.findViewById(R.id.expected_time);
        View saveChanges = bookingActivity.findViewById(R.id.updateDetails);
        View saveChangeCard = bookingActivity.findViewById(R.id.saveCard);
        View loadingView = bookingActivity.findViewById(R.id.progressBar);
        View serviceAddress = bookingActivity.findViewById(R.id.customer_address);


        assertNotNull(backBtn);
        assertNotNull(serviceType);
        assertNotNull(problemDescription);
        assertNotNull(expectedDate);
        assertNotNull(expectedTime);
        assertNotNull(saveChanges);
        assertNotNull(loadingView);
        assertNotNull(saveChangeCard);
        assertNotNull(serviceAddress);
    }

    @Test
    public void onCreate() {
    }

    @Test
    public void onBackPressed() {
    }
}