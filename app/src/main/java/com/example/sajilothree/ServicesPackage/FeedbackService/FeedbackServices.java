package com.example.sajilothree.ServicesPackage.FeedbackService;

import com.example.sajilothree.ModelsPackage.ProfileModelPackage.ProfileReviewsModel.Feedback;

import java.util.ArrayList;
import java.util.List;

public class FeedbackServices {
    private static List<Feedback> allFeedback = new ArrayList<>();

    public static void addFeedback(List<Feedback> feedback) {
        if (allFeedback.size() > 0) {
            allFeedback.clear();
            allFeedback.addAll(feedback);
        } else {
            allFeedback.addAll(feedback);
        }
    }

    public static ArrayList<Feedback> filterByRatings(int rating) {
        ArrayList<Feedback> filteredFeedback = new ArrayList<>();
        filteredFeedback.clear();
        for (int i = 0; i < allFeedback.size(); i++) {
            if (allFeedback.get(i).getRating() <= rating
                    && allFeedback.get(i).getRating() > (rating - 1)) {
                filteredFeedback.add(allFeedback.get(i));
            }
        }
        return filteredFeedback;
    }
}
