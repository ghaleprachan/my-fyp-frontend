package com.example.sajilothree.ServicesPackage.NotificationService;

import com.example.sajilothree.ModelsPackage.Notification.Result;

import java.util.ArrayList;
import java.util.List;

public class NotificationServices {
    public static ArrayList<Result> bookingNotifications = new ArrayList<>();

    public static boolean addAllNotification(List<Result> result) {
        bookingNotifications.clear();
        bookingNotifications.addAll(result);
        return true;
    }
}
