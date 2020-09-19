package com.example.sajilothree.ApiCallsPackage.UserFavorites;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.ModelsPackage.UserFavoriteModels.AddToFavResponse.AddToFavResponse;
import com.example.sajilothree.ModelsPackage.UserFavoriteModels.FavUserId.IsUserFavorite;
import com.example.sajilothree.ServicesPackage.UsernameHolder.OtherPersonUserId;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

public class AddUserToFavorites {
    public static boolean addToFavorites(Context context) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("favoriteBy", Username.username);
            jsonObject.put("favoriteTo", OtherPersonUserId.UserId);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    BaseURL.addUserToFav,
                    jsonObject,
                    response -> {
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            AddToFavResponse addToFavResponse = gson.fromJson(String.valueOf(response), AddToFavResponse.class);
                            if (addToFavResponse.getSuccess()) {
                                Toast.makeText(context, addToFavResponse.getResult(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Failed to add: " + addToFavResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(context, "Parse Exception: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        Toast.makeText(context, "Error to add: " + error, Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(context, "Exception: " + ex, Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public static void getUserFavId(Context context, ImageView isFav, ImageView notFav) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("favoriteBy", Username.username);
            jsonObject.put("favoriteTo", OtherPersonUserId.UserId);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    BaseURL.getUserFavId,
                    jsonObject,
                    response -> {
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            IsUserFavorite favIdList = gson.fromJson(String.valueOf(response), IsUserFavorite.class);
                            if (favIdList.getSuccess()) {
                                isFav.setVisibility(View.VISIBLE);
                                notFav.setVisibility(View.GONE);
                            } else {
                                isFav.setVisibility(View.GONE);
                                notFav.setVisibility(View.VISIBLE);
                            }
                        } catch (Exception ex) {
                            Toast.makeText(context, "Parse Exception: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> Toast.makeText(context, "Error to add: " + error, Toast.LENGTH_SHORT).show()
            );
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(context, "Exception: " + ex, Toast.LENGTH_SHORT).show();
        }
    }
}
