package com.example.sajilothree.ActivitiesPackage.BookingProcessPackage.SelectedRequestPosition;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SelectedRequestPositionTest {
    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void setContents() {
        Integer selectedPosition = 12;
        boolean isCustomer = false;
        Integer bookingId = 20;

        SelectedRequestPosition.setContents(12, false, 20);

        assertEquals(selectedPosition, SelectedRequestPosition.selectedPosition);
        assertEquals(isCustomer, SelectedRequestPosition.isCustomer);
        assertEquals(bookingId, SelectedRequestPosition.bookingId);
    }
}