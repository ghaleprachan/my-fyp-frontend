package com.example.sajilothree.ServicesPackage.BookingServices.FinalBooking;


import com.example.sajilothree.ModelsPackage.BookingDetailsModel.FinalBookingModelPackage.Result;

import java.util.ArrayList;
import java.util.List;

public class FinalBookingServices {
    public static ArrayList<Result> finalBookings = new ArrayList<>();

    public static boolean addAllBookings(List<Result> resultList) {
        finalBookings.clear();
        finalBookings.addAll(resultList);
        return true;
    }

    public static Integer bookingId;

    public static boolean setBookingId(Integer id) {
        bookingId = id;
        return true;
    }

    public static Result getSelectedDetails() {
        Result result = new Result();
        for (int i = 0; i < finalBookings.size(); i++) {
            if (finalBookings.get(i).getBookingId().equals(bookingId)) {
                result = finalBookings.get(i);
            }
        }
        return result;
    }

    public static boolean removeBooking(Integer bookingId) {
        for (int i = 0; i < finalBookings.size(); i++) {
            if (finalBookings.get(i).getBookingId().equals(bookingId)) {
                finalBookings.remove(i);
            }
        }
        return true;
    }
}
