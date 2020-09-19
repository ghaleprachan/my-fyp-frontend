package com.example.sajilothree.ServicesPackage.UserSavedPosts;

import android.util.Log;
import android.widget.Toast;

import com.example.sajilothree.ModelsPackage.SavedPostIdList.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SavedIdList {
    private static ArrayList<Result> savedIdList = new ArrayList<>();

    public static void addList(List<Result> idList) {
        savedIdList.clear();
        savedIdList.addAll(idList);
    }

    public static boolean checkPostIsSaved(int postId) {
        boolean status = false;
        try {
            for (int i = 0; i < savedIdList.size(); i++) {
                if (savedIdList.get(i).getOfferId() == postId) {
                    status = true;
                }
            }
        } catch (Exception ex) {
            Log.d("NoSaved", Objects.requireNonNull(ex.getMessage()));
        }
        return status;
    }
}
