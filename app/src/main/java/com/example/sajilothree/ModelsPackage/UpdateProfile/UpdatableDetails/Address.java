
package com.example.sajilothree.ModelsPackage.UpdateProfile.UpdatableDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Address {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("AddressId")
    @Expose
    private Integer addressId;
    @SerializedName("AddressType")
    @Expose
    private String addressType;
    @SerializedName("DisctrictId")
    @Expose
    private Integer disctrictId;
    @SerializedName("DistrictName")
    @Expose
    private String districtName;
    @SerializedName("MunicipalityName")
    @Expose
    private String municipalityName;
    @SerializedName("MunicipalityId")
    @Expose
    private Integer municipalityId;
    @SerializedName("CurrentLocation")
    @Expose
    private String currentLocation;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public Integer getDisctrictId() {
        return disctrictId;
    }

    public void setDisctrictId(Integer disctrictId) {
        this.disctrictId = disctrictId;
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

    public Integer getMunicipalityId() {
        return municipalityId;
    }

    public void setMunicipalityId(Integer municipalityId) {
        this.municipalityId = municipalityId;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

}
