
package com.example.sajilothree.ModelsPackage.BookingDetailsModel.NeededBookingDetails;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Customer {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("FullName")
    @Expose
    private String fullName;
    @SerializedName("UserProfileImage")
    @Expose
    private String UserProfileImage;
    @SerializedName("addresses")
    @Expose
    private List<Address> addresses = null;

    public String getUserProfileImage() {
        return UserProfileImage;
    }

    public void setUserProfileImage(String userProfileImage) {
        UserProfileImage = userProfileImage;
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

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

}
