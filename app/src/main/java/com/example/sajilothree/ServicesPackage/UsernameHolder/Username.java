package com.example.sajilothree.ServicesPackage.UsernameHolder;

import android.os.Build;

import androidx.annotation.RequiresApi;


public class Username {
    public static String username;
    public static String userType;

    public static boolean setUsername(String newUsername) {
        username = newUsername;
        return true;
    }

    private static String getUsername() {
        if (username.isEmpty()) {
            return null;
        } else {
            return username;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static boolean compareUsername(String userId) {
        if (getUsername() == null) {
            return false;
        } else return getUsername().equals(userId);
    }


    public static boolean setUserType(String type) {
        userType = type;
        return true;
    }
}
