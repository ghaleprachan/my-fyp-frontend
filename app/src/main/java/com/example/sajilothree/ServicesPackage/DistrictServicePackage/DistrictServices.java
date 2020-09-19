package com.example.sajilothree.ServicesPackage.DistrictServicePackage;

import com.example.sajilothree.ModelsPackage.RegisterNewUserModel.DistrictModelPackage.DistrictModel;
import com.example.sajilothree.ModelsPackage.RegisterNewUserModel.DistrictModelPackage.Municipality;

import java.util.ArrayList;

public class DistrictServices {
    public static ArrayList<DistrictModel> districtModels = new ArrayList<>();

    public static boolean addDistrict(DistrictModel model) {
        districtModels.clear();
        districtModels.add(model);
        return true;
    }

    public static ArrayList<String> filterDistricts() {
        ArrayList<String> districtName = new ArrayList<>();
        districtName.add("Select District");
        for (int i = 0; i < districtModels.get(0).getResult().size(); i++) {
            districtName.add(districtModels.get(0).getResult().get(i).getDistrictName());
        }
        return districtName;
    }

    private static ArrayList<Municipality> filterMunicipality(String district) {
        ArrayList<Municipality> municipalities = new ArrayList<>();
        for (int i = 0; i < districtModels.get(0).getResult().size(); i++) {
            if (districtModels.get(0).getResult().get(i).getDistrictName().equals(district)) {
                municipalities.addAll(districtModels.get(0).getResult().get(i).getMunicipalities());
            }
        }
        return municipalities;
    }

    public static ArrayList<String> getMunicipalityName(String district) {
        ArrayList<String> municipalityName = new ArrayList<>();
        municipalityName.add("Select Municipality");
        for (int i = 0; i < filterMunicipality(district).size(); i++) {
            municipalityName.add(filterMunicipality(district).get(i).getMunicipalityName());
        }
        return municipalityName;
    }

    public static int getDistrictId(String districtName) {
        int districtId = 0;
        for (int i = 0; i < districtModels.get(0).getResult().size(); i++) {
            if (districtModels.get(0).getResult().get(i).getDistrictName().equals(districtName)) {
                districtId = districtModels.get(0).getResult().get(i).getDistrictId();
            }
        }
        return districtId;
    }

    public static int getMunicipalityId(String districtName, String municipalityName) {
        int municipalityID = 0;
        for (int i = 0; i < filterMunicipality(districtName).size(); i++) {
            if (filterMunicipality(districtName).get(i).getMunicipalityName().equals(municipalityName)) {
                municipalityID = filterMunicipality(districtName).get(i).getMunicipalityId();
            }
        }
        return municipalityID;
    }
}
