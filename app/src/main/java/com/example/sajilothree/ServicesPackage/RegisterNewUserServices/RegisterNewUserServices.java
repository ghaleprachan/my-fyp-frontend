package com.example.sajilothree.ServicesPackage.RegisterNewUserServices;

import com.example.sajilothree.ModelsPackage.RegisterNewUserModel.RegisterprofessionModel.RegisterProfessionModel;
import com.example.sajilothree.ModelsPackage.RegisterNewUserModel.RegisterprofessionModel.Result;

import java.util.ArrayList;

public class RegisterNewUserServices {
    public static String UserType;
    public static String contactNumber;

    public static boolean setContact(String contact) {
        contactNumber = contact;
        return true;
    }

    public static boolean setUserType(String type) {
        UserType = type;
        return true;
    }

    private static ArrayList<RegisterProfessionModel> userProfessions = new ArrayList<>();

    public static boolean setUserProfession(RegisterProfessionModel profession) {
        userProfessions.clear();
        userProfessions.add(profession);
        return true;
    }

    public static String[] professionList() {
        String[] professions = new String[userProfessions.get(0).getResult().size()];
        for (int i = 0; i < userProfessions.get(0).getResult().size(); i++) {
            professions[i] = userProfessions.get(0).getResult().get(i).getProfessionName();
        }
        return professions;
    }

    public static ArrayList<Integer> getProfessionId(ArrayList<Integer> list) {
        ArrayList<Integer> selectedProfessionId = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            selectedProfessionId.add(userProfessions.get(0).getResult().get(list.get(i)).getProfessionId());
        }
        return selectedProfessionId;
    }
}
