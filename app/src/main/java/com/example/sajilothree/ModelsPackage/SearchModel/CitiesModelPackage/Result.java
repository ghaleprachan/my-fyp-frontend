
package com.example.sajilothree.ModelsPackage.SearchModel.CitiesModelPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("DistrictId")
    @Expose
    private Integer districtId;
    @SerializedName("DistrictName")
    @Expose
    private String districtName;
    @SerializedName("DisctrictImage")
    @Expose
    private String disctrictImage;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDisctrictImage() {
        return disctrictImage;
    }

    public void setDisctrictImage(String disctrictImage) {
        this.disctrictImage = disctrictImage;
    }

}
