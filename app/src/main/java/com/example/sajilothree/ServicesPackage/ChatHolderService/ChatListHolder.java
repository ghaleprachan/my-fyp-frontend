package com.example.sajilothree.ServicesPackage.ChatHolderService;

import com.example.sajilothree.ModelsPackage.ChatSystemUserChatModels.Result;

import java.util.ArrayList;
import java.util.List;

public class ChatListHolder {
    public static ArrayList<Result> chatsModel = new ArrayList<>();

    public static boolean addOldChats(List<Result> oldChats) {
        chatsModel.clear();
        chatsModel.addAll(oldChats);
        return true;
    }

    public static boolean addChat(Result newChat) {
        chatsModel.add(newChat);
        return true;
    }
}
