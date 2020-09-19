
package com.example.sajilothree.ModelsPackage.UpdateProfile.UpdatableDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Email {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("EmailId")
    @Expose
    private Integer emailId;
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

    public Integer getEmailId() {
        return emailId;
    }

    public void setEmailId(Integer emailId) {
        this.emailId = emailId;
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
