package com.example.sajilothree.ServicesPackage.ServiceProviderListShare;

import com.example.sajilothree.ModelsPackage.SPListSharePropertyModel.ShareModel;

import java.util.ArrayList;

public class SpListShareService {
    public static ArrayList<ShareModel> sharedPro = new ArrayList<>(1);

    public static boolean addProperties(ShareModel model) {
        sharedPro.add(0, model);
        return true;
    }
}
