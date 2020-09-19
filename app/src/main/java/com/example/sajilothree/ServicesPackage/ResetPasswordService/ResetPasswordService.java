package com.example.sajilothree.ServicesPackage.ResetPasswordService;

import com.example.sajilothree.ModelsPackage.ResetPasswordModel.AfterUserVerify.Result;

import java.util.ArrayList;

public class ResetPasswordService {
    public static ArrayList<Result> resetDetails = new ArrayList<>(1);

    public static boolean addResetDetails(Result result) {
        resetDetails.clear();
        resetDetails.add(result);
        return true;
    }
}
