
package com.example.sajilothree.ModelsPackage.BookingDetailsModel.NeededBookingDetails;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceProvider {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("FullName")
    @Expose
    private String fullName;

    public String getUserProfileImage() {
        return UserProfileImage;
    }

    public void setUserProfileImage(String userProfileImage) {
        UserProfileImage = userProfileImage;
    }

    @SerializedName("UserProfileImage")
    @Expose
    private String UserProfileImage;
    @SerializedName("professions")
    @Expose
    private List<Profession> professions = null;
    @SerializedName("timeTable")
    @Expose
    private List<Object> timeTable = null;

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

    public List<Profession> getProfessions() {
        return professions;
    }

    public void setProfessions(List<Profession> professions) {
        this.professions = professions;
    }

    public List<Object> getTimeTable() {
        return timeTable;
    }

    public void setTimeTable(List<Object> timeTable) {
        this.timeTable = timeTable;
    }

}
