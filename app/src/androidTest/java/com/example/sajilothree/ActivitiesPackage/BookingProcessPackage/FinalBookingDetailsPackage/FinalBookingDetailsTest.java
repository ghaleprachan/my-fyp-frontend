package com.example.sajilothree.ActivitiesPackage.BookingProcessPackage.FinalBookingDetailsPackage;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import com.example.sajilothree.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class FinalBookingDetailsTest {
    @Rule
    public ActivityTestRule<FinalBookingDetails> finalBookingDetailsActivityTestRule = new
            ActivityTestRule<>(FinalBookingDetails.class);
    private FinalBookingDetails finalBookingDetails;

    @Before
    public void setUp() {
        finalBookingDetails = finalBookingDetailsActivityTestRule.getActivity();
    }

    @After
    public void tearDown() {
        finalBookingDetails = null;
    }

    @Test
    public void testLaunch() {
        View totalCost = finalBookingDetails.findViewById(R.id.totalCost);
        View bookedDate = finalBookingDetails.findViewById(R.id.bookingDate);
        View billDetails = finalBookingDetails.findViewById(R.id.fullBill);
        View cancelBooking = finalBookingDetails.findViewById(R.id.cancelBooking);
        View  call = finalBookingDetails.findViewById(R.id.call);

        assertNotNull(totalCost);
        assertNotNull(bookedDate);
        assertNotNull(billDetails);
        assertNotNull(cancelBooking);
        assertNotNull(call);


        View  message = finalBookingDetails.findViewById(R.id.message);
        View  serviceAddress = finalBookingDetails.findViewById(R.id.service_address);
        View  serviceDate = finalBookingDetails.findViewById(R.id.expected_date);
        View profession = finalBookingDetails.findViewById(R.id.profession);
        View  problemDescription = finalBookingDetails.findViewById(R.id.problem_description);
        View  myImage = finalBookingDetails.findViewById(R.id.myImage);
        View userImage = finalBookingDetails.findViewById(R.id.userImage);
        View  greetings = finalBookingDetails.findViewById(R.id.greeting);
        View contentHeading = finalBookingDetails.findViewById(R.id.contentHeading);
        View  userName = finalBookingDetails.findViewById(R.id.userName);
        View  loadingAnimation = finalBookingDetails.findViewById(R.id.loadingAnimation);



        assertNotNull(message);
        assertNotNull(serviceAddress);
        assertNotNull(serviceDate);
        assertNotNull(profession);
        assertNotNull(problemDescription);
        assertNotNull(loadingAnimation);
        assertNotNull(myImage);
        assertNotNull(userImage);
        assertNotNull(greetings);
        assertNotNull(contentHeading);
        assertNotNull(userName);
    }

    @Test
    public void onCreate() {
    }
}