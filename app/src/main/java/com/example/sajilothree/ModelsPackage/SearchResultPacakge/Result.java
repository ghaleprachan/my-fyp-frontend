
package com.example.sajilothree.ModelsPackage.SearchResultPacakge;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("FullName")
    @Expose
    private String fullName;
    @SerializedName("Username")
    @Expose
    private String userName;
    @SerializedName("UserProfileImage")
    @Expose
    private String userProfileImage;
    @SerializedName("UserRating")
    @Expose
    private Double userRating;
    @SerializedName("UserType")
    @Expose
    private String userType;
    @SerializedName("ProfessionName")
    @Expose
    private String professionName;
    public void setUserName(String $id) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(String userProfileImage) {
        this.userProfileImage = userProfileImage;
    }

    public Double getUserRating() {
        return userRating;
    }

    public void setUserRating(Double userRating) {
        this.userRating = userRating;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getProfessionName() {
        return professionName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }

}
