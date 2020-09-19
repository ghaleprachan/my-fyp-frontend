
package com.example.sajilothree.ModelsPackage.ChatSystemUserChatModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("Chat_id")
    @Expose
    private Integer chatId;
    @SerializedName("SenderId")
    @Expose
    private Integer senderId;
    @SerializedName("ReceiverId")
    @Expose
    private Integer receiverId;
    @SerializedName("SenderUsername")
    @Expose
    private String senderUsername;
    @SerializedName("SenderName")
    @Expose
    private String senderName;
    @SerializedName("ReceiverUsername")
    @Expose
    private String receiverUsername;
    @SerializedName("ReceiverName")
    @Expose
    private String receiverName;
    @SerializedName("SenderPhoto")
    @Expose
    private String senderPhoto;
    @SerializedName("ReceiverPhoto")
    @Expose
    private String receiverPhoto;
    @SerializedName("SendDate")
    @Expose
    private String sendDate;
    @SerializedName("Message")
    @Expose
    private String message;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getSenderPhoto() {
        return senderPhoto;
    }

    public void setSenderPhoto(String senderPhoto) {
        this.senderPhoto = senderPhoto;
    }

    public String getReceiverPhoto() {
        return receiverPhoto;
    }

    public void setReceiverPhoto(String receiverPhoto) {
        this.receiverPhoto = receiverPhoto;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
