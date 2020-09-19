
package com.example.sajilothree.ModelsPackage.ResetPasswordModel.AfterUserVerify;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("Contacts")
    @Expose
    private Contacts contacts;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

}
