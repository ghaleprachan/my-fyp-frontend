
package com.example.sajilothree.ModelsPackage.ProfileModelPackage.ProfileDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("DistrictName")
    @Expose
    private String districtName;
    @SerializedName("MunicipalityName")
    @Expose
    private String municipalityName;
    @SerializedName("CurrentLocation")
    @Expose
    private String currentLocation;
    @SerializedName("AddressType")
    @Expose
    private String addressType;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getMunicipalityName() {
        return municipalityName;
    }

    public void setMunicipalityName(String municipalityName) {
        this.municipalityName = municipalityName;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

}
