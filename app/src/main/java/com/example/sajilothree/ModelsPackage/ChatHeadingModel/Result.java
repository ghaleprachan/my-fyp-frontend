
package com.example.sajilothree.ModelsPackage.ChatHeadingModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("LastChatId")
    @Expose
    private Integer lastChatId;
    @SerializedName("ParticipantOneId")
    @Expose
    private Integer participantOneId;
    @SerializedName("ParticipantOneUsername")
    @Expose
    private String participantOneUsername;
    @SerializedName("ParticipantOneName")
    @Expose
    private String participantOneName;
    @SerializedName("ParticipantOneImage")
    @Expose
    private String participantOneImage;
    @SerializedName("ParticipantTwoId")
    @Expose
    private Integer participantTwoId;
    @SerializedName("ParticipantTwoUsername")
    @Expose
    private String participantTwoUsername;
    @SerializedName("ParticipantTwoName")
    @Expose
    private String participantTwoName;
    @SerializedName("ParticipantTwoImage")
    @Expose
    private String participantTwoImage;
    @SerializedName("SendDate")
    @Expose
    private String sendDate;
    @SerializedName("LastMessage1")
    @Expose
    private String lastMessage1;
    @SerializedName("Seen")
    @Expose
    private String seen;
    @SerializedName("SenderId")
    @Expose
    private String senderId;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Integer getLastChatId() {
        return lastChatId;
    }

    public void setLastChatId(Integer lastChatId) {
        this.lastChatId = lastChatId;
    }

    public Integer getParticipantOneId() {
        return participantOneId;
    }

    public void setParticipantOneId(Integer participantOneId) {
        this.participantOneId = participantOneId;
    }

    public String getParticipantOneUsername() {
        return participantOneUsername;
    }

    public void setParticipantOneUsername(String participantOneUsername) {
        this.participantOneUsername = participantOneUsername;
    }

    public String getParticipantOneName() {
        return participantOneName;
    }

    public void setParticipantOneName(String participantOneName) {
        this.participantOneName = participantOneName;
    }

    public String getParticipantOneImage() {
        return participantOneImage;
    }

    public void setParticipantOneImage(String participantOneImage) {
        this.participantOneImage = participantOneImage;
    }

    public Integer getParticipantTwoId() {
        return participantTwoId;
    }

    public void setParticipantTwoId(Integer participantTwoId) {
        this.participantTwoId = participantTwoId;
    }

    public String getParticipantTwoUsername() {
        return participantTwoUsername;
    }

    public void setParticipantTwoUsername(String participantTwoUsername) {
        this.participantTwoUsername = participantTwoUsername;
    }

    public String getParticipantTwoName() {
        return participantTwoName;
    }

    public void setParticipantTwoName(String participantTwoName) {
        this.participantTwoName = participantTwoName;
    }

    public String getParticipantTwoImage() {
        return participantTwoImage;
    }

    public void setParticipantTwoImage(String participantTwoImage) {
        this.participantTwoImage = participantTwoImage;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getLastMessage1() {
        return lastMessage1;
    }

    public void setLastMessage1(String lastMessage1) {
        this.lastMessage1 = lastMessage1;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

}