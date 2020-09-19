package com.example.sajilothree.ActivitiesPackage.BookingHistory;

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

public class BookingHistoryDetailsTest {
    @Rule
    public ActivityTestRule<BookingHistoryDetails> bookingHistoryDetailsActivityTestRule = new
            ActivityTestRule<>(BookingHistoryDetails.class);
    private BookingHistoryDetails historyDetails;

    @Before
    public void setUp() throws Exception {
        historyDetails = bookingHistoryDetailsActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        historyDetails = null;
    }

    @Test
    public void testLaunch() {
        View totalCost = historyDetails.findViewById(R.id.totalCost);
        View bookedDate = historyDetails.findViewById(R.id.bookingDate);
        View billDetails = historyDetails.findViewById(R.id.fullBill);
        View cancelBooking = historyDetails.findViewById(R.id.cancelBooking);
        View call = historyDetails.findViewById(R.id.call);
        View message = historyDetails.findViewById(R.id.message);
        View serviceAddress = historyDetails.findViewById(R.id.service_address);
        View serviceDate = historyDetails.findViewById(R.id.expected_date);
        View profession = historyDetails.findViewById(R.id.profession);
        View problemDescription = historyDetails.findViewById(R.id.problem_description);
        View myImage = historyDetails.findViewById(R.id.myImage);
        View userImage = historyDetails.findViewById(R.id.userImage);
        View greetings = historyDetails.findViewById(R.id.greeting);
        View contentHeading = historyDetails.findViewById(R.id.contentHeading);
        View userName = historyDetails.findViewById(R.id.userName);
        View loadingAnimation = historyDetails.findViewById(R.id.loadingAnimation);
        View status = historyDetails.findViewById(R.id.status);


        assertNotNull(totalCost);
        assertNotNull(bookedDate);
        assertNotNull(billDetails);
        assertNotNull(cancelBooking);
        assertNotNull(call);
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
        assertNotNull(status);
    }

    @Test
    public void onCreate() {
    }
}