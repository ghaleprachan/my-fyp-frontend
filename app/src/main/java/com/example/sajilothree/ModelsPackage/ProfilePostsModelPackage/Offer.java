
package com.example.sajilothree.ModelsPackage.ProfilePostsModelPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Offer {

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
    @SerializedName("PostedDate")
    @Expose
    private String postedDate;
    @SerializedName("ValidDate")
    @Expose
    private String validDate;

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

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

}
