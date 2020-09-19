
package com.example.sajilothree.ModelsPackage.UserFavoriteModels.FavoritesDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("FavoriteId")
    @Expose
    private Integer favoriteId;
    @SerializedName("CustomerId")
    @Expose
    private Integer customerId;
    @SerializedName("SPId")
    @Expose
    private Integer sPId;
    @SerializedName("UserProfileImage")
    @Expose
    private Object userProfileImage;
    @SerializedName("UserRating")
    @Expose
    private Double userRating;
    @SerializedName("FullName")
    @Expose
    private String fullName;
    @SerializedName("AddedDate")
    @Expose
    private String addedDate;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("Username")
    @Expose
    private String username;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Integer getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(Integer favoriteId) {
        this.favoriteId = favoriteId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getSPId() {
        return sPId;
    }

    public void setSPId(Integer sPId) {
        this.sPId = sPId;
    }

    public Object getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(Object userProfileImage) {
        this.userProfileImage = userProfileImage;
    }

    public Double getUserRating() {
        return userRating;
    }

    public void setUserRating(Double userRating) {
        this.userRating = userRating;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
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

}
