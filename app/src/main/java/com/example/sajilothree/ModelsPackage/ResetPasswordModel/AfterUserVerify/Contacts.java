
package com.example.sajilothree.ModelsPackage.ResetPasswordModel.AfterUserVerify;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contacts {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("ContactNumber")
    @Expose
    private String contactNumber;

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

}
