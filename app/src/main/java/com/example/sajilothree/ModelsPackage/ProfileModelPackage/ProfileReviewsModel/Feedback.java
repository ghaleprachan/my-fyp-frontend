
package com.example.sajilothree.ModelsPackage.ProfileModelPackage.ProfileReviewsModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Feedback {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("FeedbackBy")
    @Expose
    private Integer feedbackBy;
    @SerializedName("FeedbaclTo")
    @Expose
    private Integer feedbaclTo;
    @SerializedName("Rating")
    @Expose
    private Double rating;
    @SerializedName("Feedback1")
    @Expose
    private String feedback1;
    @SerializedName("FeedbackId")
    @Expose
    private Integer feedbackId;
    @SerializedName("FullName")
    @Expose
    private String fullName;
    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("UserType")
    @Expose
    private String userType;
    @SerializedName("UserProfileImage")
    @Expose
    private Object userProfileImage;
    @SerializedName("FeedbackDate")
    @Expose
    private String feedbackDate;

    @SerializedName("ReplayCount")
    @Expose
    private Integer ReplayCount;

    public Integer getReplayCount() {
        return ReplayCount;
    }

    public void setReplayCount(Integer replayCount) {
        ReplayCount = replayCount;
    }

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Integer getFeedbackBy() {
        return feedbackBy;
    }

    public void setFeedbackBy(Integer feedbackBy) {
        this.feedbackBy = feedbackBy;
    }

    public Integer getFeedbaclTo() {
        return feedbaclTo;
    }

    public void setFeedbaclTo(Integer feedbaclTo) {
        this.feedbaclTo = feedbaclTo;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getFeedback1() {
        return feedback1;
    }

    public void setFeedback1(String feedback1) {
        this.feedback1 = feedback1;
    }

    public Integer getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Integer feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Object getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(Object userProfileImage) {
        this.userProfileImage = userProfileImage;
    }

    public String getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(String feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

}
