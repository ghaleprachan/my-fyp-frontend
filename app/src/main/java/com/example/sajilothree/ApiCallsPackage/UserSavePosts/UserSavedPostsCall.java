package com.example.sajilothree.ApiCallsPackage.UserSavePosts;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.ModelsPackage.SavedPostIdList.SavedPostIdModel;
import com.example.sajilothree.ServicesPackage.UserSavedPosts.SavedIdList;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;


public class UserSavedPostsCall {
    public static void callSaveOfferId(Context context) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tokenNumber", Username.username);
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    BaseURL.getUserSavedOffersId,
                    jsonObject,
                    response -> {
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            SavedPostIdModel savedPostIdModel = gson.fromJson(String.valueOf(response), SavedPostIdModel.class);
                            if (savedPostIdModel.getSuccess()) {
                                SavedIdList.addList(savedPostIdModel.getResult());
                            } else {
                                Toast.makeText(context, "Response Failed: " + savedPostIdModel.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(context, "Failed to parse: " + ex, Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> Toast.makeText(context, "Error: " + error, Toast.LENGTH_SHORT).show()
            );
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(context, "Failed to get saved " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
