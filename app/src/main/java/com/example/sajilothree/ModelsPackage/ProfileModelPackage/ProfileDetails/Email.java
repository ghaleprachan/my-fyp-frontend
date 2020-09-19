
package com.example.sajilothree.ModelsPackage.ProfileModelPackage.ProfileDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Email {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("Email1")
    @Expose
    private String email1;
    @SerializedName("EmailType")
    @Expose
    private String emailType;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmailType() {
        return emailType;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }

}
