package com.example.sajilothree.ActivitiesPackage.BookingProcessPackage.SelectedRequestPosition;


public class SelectedRequestPosition {
    public static Integer selectedPosition;
    public static boolean isCustomer;
    public static Integer bookingId;

    public static boolean setContents(Integer position, boolean value, Integer booking) {
        selectedPosition = position;
        isCustomer = value;
        bookingId = booking;
        return true;
    }
}
