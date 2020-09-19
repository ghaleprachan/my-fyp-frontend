package com.example.sajilothree.ActivitiesPackage.BookingProcessPackage.BookingPackage;

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

public class BookingActivityTest {
    @Rule
    public ActivityTestRule<BookingActivity> bookingActivityActivityTestRule = new
            ActivityTestRule<>(BookingActivity.class);
    private BookingActivity bookingActivity;
    @Before
    public void setUp() {
        bookingActivity = bookingActivityActivityTestRule.getActivity();
    }

    @After
    public void tearDown() {
        bookingActivity = null;
    }
    @Test
    public void testLaunch() {
        View selectDate = bookingActivity.findViewById(R.id.expected_date);
        View selectTime = bookingActivity.findViewById(R.id.expected_time);
        View services = bookingActivity.findViewById(R.id.service_type);
        View addresses = bookingActivity.findViewById(R.id.service_address);
        View moreAddress = bookingActivity.findViewById(R.id.moreAddress);
        View bookNow = bookingActivity.findViewById(R.id.bookNow);
        View problem = bookingActivity.findViewById(R.id.problem_description);
        View backBtn = bookingActivity.findViewById(R.id.bookingBackBtn);
        View hideCardView = bookingActivity.findViewById(R.id.hideCardView);
        View loadingAnimation = bookingActivity.findViewById(R.id.loadingAnimation);
        View customerImage = bookingActivity.findViewById(R.id.myImage);
        View specialistImage = bookingActivity.findViewById(R.id.userImage);
        View customerName = bookingActivity.findViewById(R.id.greeting);
        View specialistName = bookingActivity.findViewById(R.id.specialist_name);


        assertNotNull(selectDate);
        assertNotNull(selectTime);
        assertNotNull(services);
        assertNotNull(addresses);
        assertNotNull(moreAddress);
        assertNotNull(bookNow);
        assertNotNull(problem);
        assertNotNull(backBtn);
        assertNotNull(hideCardView);
        assertNotNull(customerImage);
        assertNotNull(loadingAnimation);
        assertNotNull(specialistImage);
        assertNotNull(customerName);
        assertNotNull(specialistName);
    }

    @Test
    public void onCreate() {
    }
}