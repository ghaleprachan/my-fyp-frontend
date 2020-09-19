
package com.example.sajilothree.ModelsPackage.SavedPostDetails;

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
    @SerializedName("OfferId")
    @Expose
    private Integer offerId;
    @SerializedName("ValidDate")
    @Expose
    private String validDate;
    @SerializedName("OfferImage")
    @Expose
    private Object offerImage;
    @SerializedName("PostedDate")
    @Expose
    private String postedDate;
    @SerializedName("OfferDescription")
    @Expose
    private String offerDescription;
    @SerializedName("UserType")
    @Expose
    private String userType;
    @SerializedName("PostedById")
    @Expose
    private Integer postedById;
    @SerializedName("PostedByUsername")
    @Expose
    private String postedByUsername;
    @SerializedName("PostedByImage")
    @Expose
    private Object postedByImage;
    @SerializedName("PostedByName")
    @Expose
    private String postedByName;
    @SerializedName("SaveDate")
    @Expose
    private String SaveDate;
    @SerializedName("Contacts")
    @Expose
    private List<Contact> contacts = null;

    public String getSaveDate() {
        return SaveDate;
    }

    public void setSaveDate(String saveDate) {
        SaveDate = saveDate;
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

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public Object getOfferImage() {
        return offerImage;
    }

    public void setOfferImage(Object offerImage) {
        this.offerImage = offerImage;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public void setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getPostedById() {
        return postedById;
    }

    public void setPostedById(Integer postedById) {
        this.postedById = postedById;
    }

    public String getPostedByUsername() {
        return postedByUsername;
    }

    public void setPostedByUsername(String postedByUsername) {
        this.postedByUsername = postedByUsername;
    }

    public Object getPostedByImage() {
        return postedByImage;
    }

    public void setPostedByImage(Object postedByImage) {
        this.postedByImage = postedByImage;
    }

    public String getPostedByName() {
        return postedByName;
    }

    public void setPostedByName(String postedByName) {
        this.postedByName = postedByName;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

}
