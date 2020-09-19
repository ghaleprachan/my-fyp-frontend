
package com.example.sajilothree.ModelsPackage.HomeModel.OfferModelPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contact {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("ContactNumber")
    @Expose
    private String contactNumber;
    @SerializedName("ContactType")
    @Expose
    private String contactType;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

}
