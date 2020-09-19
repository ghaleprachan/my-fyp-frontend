package com.example.sajilothree.ActivitiesPackage.BookingProcessPackage.RequestPackage;

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

public class RequestActivityTest {
    @Rule
    public ActivityTestRule<RequestActivity> requestActivityActivityTestRule = new
            ActivityTestRule<>(RequestActivity.class);
    private RequestActivity requestActivity;

    @Before
    public void setUp() {
        requestActivity = requestActivityActivityTestRule.getActivity();
    }

    @After
    public void tearDown() {
        requestActivity = null;
    }

    @Test
    public void testLaunch() {
        View customerName = requestActivity.findViewById(R.id.specialist_name);
        View serviceType = requestActivity.findViewById(R.id.service_type);
        View problemDescription = requestActivity.findViewById(R.id.problem_description);
        View expectedDate = requestActivity.findViewById(R.id.expected_date);
        View expectedTime = requestActivity.findViewById(R.id.expected_time);
        View customerImage = requestActivity.findViewById(R.id.userImage);
        View serviceProviderImage = requestActivity.findViewById(R.id.myImage);
        View sendDate = requestActivity.findViewById(R.id.sendDate);
        View greeting = requestActivity.findViewById(R.id.greeting);
        View headingText = requestActivity.findViewById(R.id.headingText);
        View backBtn = requestActivity.findViewById(R.id.backBill);
        View contentHeading = requestActivity.findViewById(R.id.contentHeading);
        View decline = requestActivity.findViewById(R.id.decline);
        View accept = requestActivity.findViewById(R.id.accept);
        View edit = requestActivity.findViewById(R.id.editDetails);
        View cancelBooking = requestActivity.findViewById(R.id.cancelBooking);
        View customerContent = requestActivity.findViewById(R.id.customerContent);
        View specialistContent = requestActivity.findViewById(R.id.specialistContent);
        View serviceAddress = requestActivity.findViewById(R.id.customer_address);
        View loadingAnimation = requestActivity.findViewById(R.id.loadingAnimation);

        assertNotNull(customerName);
        assertNotNull(serviceType);
        assertNotNull(problemDescription);
        assertNotNull(expectedDate);
        assertNotNull(expectedTime);
        assertNotNull(customerImage);
        assertNotNull(serviceProviderImage);
        assertNotNull(sendDate);
        assertNotNull(greeting);
        assertNotNull(headingText);
        assertNotNull(loadingAnimation);
        assertNotNull(backBtn);
        assertNotNull(contentHeading);
        assertNotNull(decline);
        assertNotNull(accept);
        assertNotNull(edit);
        assertNotNull(cancelBooking);
        assertNotNull(customerContent);
        assertNotNull(specialistContent);
        assertNotNull(serviceAddress);
    }

    @Test
    public void onCreate() {
    }
}