package com.example.sajilothree.ServicesPackage.ChatSystemReceiverID;

public class ChatSystemReceiverIdHolder {
    public static String receiverId;
    public static String otherName;

    public static String senderId;

    public static boolean addSenderId(String id) {
        senderId = id;
        return true;
    }

    public static boolean addReceiverId(String newReceiverId) {
        receiverId = newReceiverId;
        return true;
    }

    public static boolean addPersonName(String otherPersonName) {
        otherName = otherPersonName;
        return true;
    }
}
