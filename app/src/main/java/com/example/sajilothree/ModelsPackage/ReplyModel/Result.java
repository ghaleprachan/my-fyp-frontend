
package com.example.sajilothree.ModelsPackage.ReplyModel;

import com.android.volley.toolbox.StringRequest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("ReplyId")
    @Expose
    private Integer replyId;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("FullName")
    @Expose
    private String fullName;
    @SerializedName("UserProfileImage")
    @Expose
    private Object userProfileImage;
    @SerializedName("ReplyDate")
    @Expose
    private String replyDate;
    @SerializedName("Reply")
    @Expose
    private String reply;
    @SerializedName("FeedbackId")
    @Expose
    private Integer feedbackId;

    @SerializedName("UserType")
    @Expose
    private String UserType;

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Object getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(Object userProfileImage) {
        this.userProfileImage = userProfileImage;
    }

    public String getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(String replyDate) {
        this.replyDate = replyDate;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Integer getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Integer feedbackId) {
        this.feedbackId = feedbackId;
    }

}
