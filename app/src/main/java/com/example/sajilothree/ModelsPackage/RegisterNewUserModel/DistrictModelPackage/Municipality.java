
package com.example.sajilothree.ModelsPackage.RegisterNewUserModel.DistrictModelPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Municipality {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("MunicipalityId")
    @Expose
    private Integer municipalityId;
    @SerializedName("MunicipalityName")
    @Expose
    private String municipalityName;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Integer getMunicipalityId() {
        return municipalityId;
    }

    public void setMunicipalityId(Integer municipalityId) {
        this.municipalityId = municipalityId;
    }

    public String getMunicipalityName() {
        return municipalityName;
    }

    public void setMunicipalityName(String municipalityName) {
        this.municipalityName = municipalityName;
    }

}
