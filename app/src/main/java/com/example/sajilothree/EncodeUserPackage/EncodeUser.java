package com.example.sajilothree.EncodeUserPackage;

import java.util.Base64;

public class EncodeUser {

    public static String enCodeUserId(int id, String username) {
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                return Base64.getEncoder()
                        .encodeToString((id + ":" + username).getBytes());
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }
}
