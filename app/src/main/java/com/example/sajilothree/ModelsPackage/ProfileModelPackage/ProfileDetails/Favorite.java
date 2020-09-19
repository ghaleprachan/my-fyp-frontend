
package com.example.sajilothree.ModelsPackage.ProfileModelPackage.ProfileDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Favorite {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("SPId")
    @Expose
    private Integer sPId;
    @SerializedName("FullName")
    @Expose
    private String fullName;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Integer getSPId() {
        return sPId;
    }

    public void setSPId(Integer sPId) {
        this.sPId = sPId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
