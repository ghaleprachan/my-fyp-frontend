package com.example.sajilothree.ServicesPackage.DeleteByCheckHomeServices;

import com.example.sajilothree.ModelsPackage.HomeModel.OfferModelPackage.OfferModel;

import java.util.ArrayList;

public class OfferService {
    public static ArrayList<OfferModel> offerPromoModels = new ArrayList<>();

    public static void addPromo(OfferModel model) {
        offerPromoModels.add(model);
    }
}
