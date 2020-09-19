
package com.example.sajilothree.ModelsPackage.SearchModel.ServicesModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("ProfessionId")
    @Expose
    private Integer professionId;
    @SerializedName("ProfessionName")
    @Expose
    private String professionName;
    @SerializedName("ProfessionImage")
    @Expose
    private String professionImage;

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

    public String getProfessionName() {
        return professionName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }

    public String getProfessionImage() {
        return professionImage;
    }

    public void setProfessionImage(String professionImage) {
        this.professionImage = professionImage;
    }

}
