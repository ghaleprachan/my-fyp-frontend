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

public class BookingHistoryTest {
    @Rule
    public ActivityTestRule<BookingHistory> bookingHistoryActivityTestRule = new
            ActivityTestRule<>(BookingHistory.class);
    private BookingHistory bookingHistory;

    @Before
    public void setUp() throws Exception {
        bookingHistory = bookingHistoryActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        bookingHistory = null;
    }

    @Test
    public void testLaunch() {
        View recyclerView = bookingHistory.findViewById(R.id.recentBooking);
        View refresh = bookingHistory.findViewById(R.id.swipeRefresh);


        assertNotNull(recyclerView);
        assertNotNull(refresh);
    }


    @Test
    public void onCreate() {
    }

    @Test
    public void onBackPressed() {
    }
}