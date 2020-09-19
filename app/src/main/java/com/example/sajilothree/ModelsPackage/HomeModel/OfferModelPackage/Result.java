
package com.example.sajilothree.ModelsPackage.HomeModel.OfferModelPackage;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("offerId")
    @Expose
    private Integer offerId;
    @SerializedName("OfferDescription")
    @Expose
    private String offerDescription;
    @SerializedName("OfferImage")
    @Expose
    private String offerImage;
    @SerializedName("ValidDate")
    @Expose
    private String validDate;
    @SerializedName("PostedDate")
    @Expose
    private String postedDate;
    @SerializedName("UserType")
    @Expose
    private String userType;
    @SerializedName("postedById")
    @Expose
    private Integer postedById;
    @SerializedName("postedByUsername")
    @Expose
    private String postedByUsername;
    @SerializedName("postedBy")
    @Expose
    private String postedBy;
    @SerializedName("userPhoto")
    @Expose
    private String userPhoto;
    @SerializedName("Contacts")
    @Expose
    private List<Contact> contacts = null;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public void setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
    }

    public String getOfferImage() {
        return offerImage;
    }

    public void setOfferImage(String offerImage) {
        this.offerImage = offerImage;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
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

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

}
