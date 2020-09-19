
package com.example.sajilothree.ModelsPackage.ProfileModelPackage.ProfileAbout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfession {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("ProfessionId")
    @Expose
    private Integer professionId;
    @SerializedName("UserProfessionId")
    @Expose
    private Integer userProfessionId;
    @SerializedName("ProfessionName")
    @Expose
    private String professionName;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Integer getProfessionId() {
        return professionId;
    }

    public void setProfessionId(Integer professionId) {
        this.professionId = professionId;
    }

    public Integer getUserProfessionId() {
        return userProfessionId;
    }

    public void setUserProfessionId(Integer userProfessionId) {
        this.userProfessionId = userProfessionId;
    }

    public String getProfessionName() {
        return professionName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }

}
