package com.example.sajilothree.ServicesPackage.UsernameHolder;

import java.util.Base64;

public class OtherPersonUserId {
    public static String UserId;
    public static String otherUserName;

    public static boolean setUserId(String id) {
        UserId = id;
        return true;
    }

    public static boolean encode(int userId, String username) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            UserId = Base64.getEncoder()
                    .encodeToString((userId + ":" + username).getBytes());
        }
        return true;
    }

    public static boolean setUserFullName(String fullName) {
        otherUserName = fullName;
        return true;
    }
}