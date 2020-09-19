
package com.example.sajilothree.ModelsPackage.LogInUserModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LogInUserModel {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("tokenNumber")
    @Expose
    private String tokenNumber;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTokenNumber() {
        return tokenNumber;
    }

    public void setTokenNumber(String tokenNumber) {
        this.tokenNumber = tokenNumber;
    }

}
