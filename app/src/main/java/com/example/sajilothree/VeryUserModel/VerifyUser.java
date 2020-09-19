package com.example.sajilothree.VeryUserModel;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.sajilothree.ServicesPackage.UsernameHolder.OtherPersonUserId;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;

public class VerifyUser {

    //    This verify if the user is visiting his profile or others.
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String userVerification() {
        try {
            if (Username.compareUsername(OtherPersonUserId.UserId)) {
                return Username.username;
            } else {
                return OtherPersonUserId.UserId;
            }
        } catch (Exception ex) {
            return Username.username;
        }
    }
}
