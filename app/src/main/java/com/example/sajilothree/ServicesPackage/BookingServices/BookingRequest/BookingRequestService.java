package com.example.sajilothree.ServicesPackage.BookingServices.BookingRequest;

import android.annotation.SuppressLint;

import com.example.sajilothree.ModelsPackage.BookingDetailsModel.RequestModel.Result;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingRequestService {
    public static ArrayList<Result> bookingRequests = new ArrayList<>();

    public static boolean addBookingList(List<Result> results) {
        bookingRequests.clear();
        bookingRequests.addAll(results);
        return true;
    }

    public static boolean updateBookingList(Integer position, Result result) {
        bookingRequests.add(position, result);
        return true;
    }

    public static boolean removeRequest(int position) {
        bookingRequests.remove(position);
        return true;
    }

    public static boolean removeById(Integer bookId) {
        for (int i = 0; i < bookingRequests.size(); i++) {
            if (bookingRequests.get(i).getBookingId().equals(bookId)) {
                bookingRequests.remove(i);
            }
        }
        return true;
    }

    public static String dateConversion(String stringDate) {
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date;
            try {
                date = sdf.parse(stringDate);
            } catch (Exception ex) {
                return null;
            }
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf2 = new SimpleDateFormat("MMM dd, yyyy");
            assert date != null;
            return sdf2.format(date);
        } catch (Exception ex) {
            return null;
        }
    }

    public static String chatSystemDate(String stringDate) {
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date;
            try {
                date = sdf.parse(stringDate);
            } catch (Exception ex) {
                return null;
            }
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf2 = new SimpleDateFormat("MMM dd, yyyy 'at' h:ss a");
            assert date != null;
            return sdf2.format(date);
        } catch (Exception ex) {
            return null;
        }
    }

    public static String timeConversion(String stringTime) {
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date time;
            try {
                time = sdf.parse(stringTime);
            } catch (Exception ex) {
                return null;
            }
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm a");
            assert time != null;
            return sdf2.format(time);
        } catch (Exception ex) {
            return null;
        }
    }

    public static String dateTime(String stringDate) {
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date;
            try {
                date = sdf.parse(stringDate);
            } catch (Exception ex) {
                return null;
            }
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE, dd MMM 'at' h:mm a");
            assert date != null;
            return sdf2.format(date);
        } catch (Exception ex) {
            return null;
        }
    }

    public static String onlyDateDay(String stringDate) {
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date;
            try {
                date = sdf.parse(stringDate);
            } catch (Exception ex) {
                return null;
            }
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf2 = new SimpleDateFormat("MMM dd");
            assert date != null;
            return sdf2.format(date);
        } catch (Exception ex) {
            return null;
        }
    }

    public static String myDateFormat(String stringDate, String dateFormat) {
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date;
            try {
                date = sdf.parse(stringDate);
            } catch (Exception ex) {
                return null;
            }
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf2 = new SimpleDateFormat(dateFormat);
            assert date != null;
            return sdf2.format(date);
        } catch (Exception ex) {
            return null;
        }
    }
}
