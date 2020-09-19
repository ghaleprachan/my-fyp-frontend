package com.example.sajilothree.ServicesPackage.ChatSystemHeading;

import com.example.sajilothree.ModelsPackage.ChatHeadingModel.ChatHeadingModel;

import java.util.ArrayList;

public class ChatSystemHeadingServices {
    private static ArrayList<ChatHeadingModel> chatHeadings = new ArrayList<>();

    public static boolean addChatHeading(ChatHeadingModel headingModel) {
        chatHeadings.clear();
        chatHeadings.add(0, headingModel);
        return true;
    }


    //    It gets the last chat of two users finds out their seen status
    /*@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static ArrayList<Result> getLastChat(String participantOne, String participantTwo) {
        ArrayList<Result> chatBetween = new ArrayList<>();
        for (int i = 0; i < chatHeadings.get(0).getResult().size(); i++) {
            if (Objects.equals(
                    EncodeUser.enCodeUserId(chatHeadings.get(0).getResult().get(i).getParticipantOneId(), chatHeadings.get(0).getResult().get(i).getParticipantOneUsername()), participantOne)
                    || Objects.equals(EncodeUser.enCodeUserId(chatHeadings.get(0).getResult().get(i).getParticipantTwoId(), chatHeadings.get(0).getResult().get(i).getParticipantTwoUsername()), participantTwo) &&
                    Objects.equals(EncodeUser.enCodeUserId(chatHeadings.get(0).getResult().get(i).getParticipantOneId(), chatHeadings.get(0).getResult().get(i).getParticipantOneUsername()), participantTwo) ||
                    Objects.equals(EncodeUser.enCodeUserId(chatHeadings.get(0).getResult().get(i).getParticipantTwoId(), chatHeadings.get(0).getResult().get(i).getParticipantTwoUsername()), participantOne)) {
                chatBetween.add(chatHeadings.get(0).getResult().get(i));
            }
        }
        if (chatBetween.size() != 0) {
            return chatBetween;
        } else {
            return null;
        }
    }*/
}
