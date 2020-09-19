package com.example.sajilothree.ActivitiesPackage.BookingProcessPackage.MakeBillPackage;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import com.example.sajilothree.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class MakeBillUnitTest {

    @Rule
    public ActivityTestRule<MakeBill> makeBillActivityTestRule = new ActivityTestRule<>(MakeBill.class);
    private MakeBill makeBill;

    @Before
    public void setUp() {
        makeBill = makeBillActivityTestRule.getActivity();

    }

    @After
    public void tearDown() {
        makeBill = null;
    }

    @Test
    public void testLaunch() {
        View totalCost = makeBill.findViewById(R.id.totalCost);
        View serviceCharge = makeBill.findViewById(R.id.service_charge);
        View discount = makeBill.findViewById(R.id.discount);
        View travellingCost = makeBill.findViewById(R.id.traveling_cost);
        View createBill = makeBill.findViewById(R.id.confirm_booking);
        View myImage = makeBill.findViewById(R.id.myImage);

        assertNotNull(totalCost);
        assertNotNull(serviceCharge);
        assertNotNull(discount);
        assertNotNull(travellingCost);
        assertNotNull(createBill);
        assertNotNull(myImage);
    }

    @Test
    public void calculateTotalCost() {
        double serviceCharge = 100.0f;
        double discount = 10.0f;
        double travellingCost = 20.0f;

        String expected = "110.0";

        String actual = makeBill.calculateTotalCost(serviceCharge, discount, travellingCost);

        assertEquals(expected, actual);
    }
}