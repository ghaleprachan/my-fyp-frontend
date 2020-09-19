package com.example.sajilothree.ServicesPackage.BookingHistory;

import com.example.sajilothree.ModelsPackage.BookingHistory.Result;

import java.util.ArrayList;
import java.util.List;

public class BookingHistoryService {
    public static ArrayList<Result> bookingHistory = new ArrayList<>();

    public static boolean addHistory(List<Result> results) {
        bookingHistory.clear();
        bookingHistory.addAll(results);
        return true;
    }

    public static Integer bookingId;

    public static boolean setBookingId(Integer id) {
        bookingId = id;
        return true;
    }

    public static Result getSelectedDetails() {
        Result result = new Result();
        for (int i = 0; i < bookingHistory.size(); i++) {
            if (bookingHistory.get(i).getBookingId().equals(bookingId)) {
                result = bookingHistory.get(i);
            }
        }
        return result;
    }
}
