package com.example.sajilothree.ServicesPackage.DeleteByCheckHomeServices;

import com.example.sajilothree.ModelsPackage.HomeModel.ProblemsModel;

import java.util.ArrayList;

public class ProblemsService {
    public static ArrayList<ProblemsModel> problemsModels = new ArrayList<>();

    public static void addPromo(ProblemsModel model) {
        problemsModels.add(model);
    }
}
