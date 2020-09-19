
package com.example.sajilothree.ModelsPackage.BookingDetailsModel.NeededBookingDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profession {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("ProfessionId")
    @Expose
    private Integer professionId;
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

    public String getProfessionName() {
        return professionName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }

}
