
package com.example.sajilothree.ModelsPackage.SavedPostIdList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("SavedId")
    @Expose
    private Integer savedId;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("OfferId")
    @Expose
    private Integer offerId;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Integer getSavedId() {
        return savedId;
    }

    public void setSavedId(Integer savedId) {
        this.savedId = savedId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }

}
