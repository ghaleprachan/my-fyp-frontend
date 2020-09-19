
package com.example.sajilothree.ModelsPackage.ProfileModelPackage.ProfileDetails;

import java.util.List;
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
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("UserType")
    @Expose
    private String userType;
    @SerializedName("Contacts")
    @Expose
    private List<Contact> contacts = null;
    @SerializedName("Emails")
    @Expose
    private List<Email> emails = null;
    @SerializedName("Addresses")
    @Expose
    private List<Address> addresses = null;
    @SerializedName("Favorites")
    @Expose
    private List<Favorite> favorites = null;
    @SerializedName("Recommendations")
    @Expose
    private Integer recommendations;

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
    }

    public Integer getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(Integer recommendations) {
        this.recommendations = recommendations;
    }

}
