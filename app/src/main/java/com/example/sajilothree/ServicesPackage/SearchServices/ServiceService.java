package com.example.sajilothree.ServicesPackage.SearchServices;

import com.example.sajilothree.ModelsPackage.SearchModel.ServiceModel;

import java.util.ArrayList;

public class ServiceService {
    public static ArrayList<ServiceModel> allServices = new ArrayList<>();

    public static void addService(ServiceModel model) {
        allServices.add(model);
    }
}
