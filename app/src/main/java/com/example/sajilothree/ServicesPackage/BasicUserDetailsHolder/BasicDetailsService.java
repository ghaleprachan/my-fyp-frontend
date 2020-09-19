package com.example.sajilothree.ServicesPackage.BasicUserDetailsHolder;

import com.example.sajilothree.ModelsPackage.HomeModel.BasicUserDetails.BasicUserDetailsModel;

import java.util.ArrayList;

public class BasicDetailsService {
    public static ArrayList<BasicUserDetailsModel> userDetailsModels = new ArrayList<>(0);

    public static boolean addUserDetails(BasicUserDetailsModel model) {
        userDetailsModels.add(0, model);
        return true;
    }

    public static boolean addUpdatedName(String newName) {
        userDetailsModels.get(0).getResult().get(0).setFullName(newName);
        return true;
    }
}
