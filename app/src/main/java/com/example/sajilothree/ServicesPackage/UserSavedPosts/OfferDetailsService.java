package com.example.sajilothree.ServicesPackage.UserSavedPosts;

import com.example.sajilothree.ModelsPackage.SavedPostDetails.Result;

import java.util.ArrayList;
import java.util.List;

public class OfferDetailsService {
    public static ArrayList<Result> offerDetails = new ArrayList<>();

    public static boolean addDetails(List<Result> offers) {
        offerDetails.clear();
        offerDetails.addAll(offers);
        return true;
    }
}
