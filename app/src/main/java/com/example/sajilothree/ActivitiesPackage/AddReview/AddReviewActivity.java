package com.example.sajilothree.ActivitiesPackage.AddReview;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.APIPackage.BaseUrlPackage.ChatHubUrlHolder;
import com.example.sajilothree.ModelsPackage.FeedbackPosted.FeedbackPostedModel;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.ChatSystemReceiverID.ChatSystemReceiverIdHolder;
import com.example.sajilothree.ServicesPackage.UsernameHolder.OtherPersonUserId;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;

import org.json.JSONObject;

public class AddReviewActivity extends AppCompatActivity {
    private ImageButton backBtn;
    private TextView postReview;
    private RatingBar rating;
    private EditText feedback;
    private NestedScrollView feedbackContent;
    private LinearLayout loadingContent;
    private ImageView loading;
    private HubConnection hubConnection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_review_layout);
//        Slidr.attach(this);
        userInterface();
        onBackBtnClick();
        onPostReviewClick();

        buildHubConnection();
    }

    private void buildHubConnection() {
        hubConnection = HubConnectionBuilder.create(ChatHubUrlHolder.chatHubApi).build();
        hubConnection.start();
    }

    private void onPostReviewClick() {
        postReview.setOnClickListener(v -> {
            if (fieldValidation()) {
                postToDbApi();
            }
        });
    }

    public static boolean reviewValidation(String review, float rating) {
        if (review.isEmpty()) {
            return false;
        } else return !(rating <= 0.0f);
    }

    private boolean fieldValidation() {
        if (rating.getRating() == 0.0) {
            Toast.makeText(this, "Add rating!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (feedback.getText().toString().isEmpty()) {
            Toast.makeText(this, "Write feedback!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void postToDbApi() {
        try {
            feedbackContent.setVisibility(View.GONE);
            loadingContent.setVisibility(View.VISIBLE);
            AnimationDrawable animation = (AnimationDrawable) loading.getDrawable();
            animation.start();
            postReview.setVisibility(View.GONE);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("FeedbackBy", Username.username);
            jsonObject.put("FeedbackTo", OtherPersonUserId.UserId);
            jsonObject.put("Feedback", feedback.getText().toString());
            jsonObject.put("Rating", rating.getRating());

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    BaseURL.postFeedback,
                    jsonObject,
                    response -> {
                        feedbackContent.setVisibility(View.VISIBLE);
                        loadingContent.setVisibility(View.GONE);
                        animation.stop();
                        postReview.setVisibility(View.VISIBLE);
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            FeedbackPostedModel postedModel = gson.fromJson(String.valueOf(response), FeedbackPostedModel.class);
                            if (postedModel.getSuccess()) {
                                hubConnection.start();
                                if (hubConnection.getConnectionState() == HubConnectionState.CONNECTED) {
                                    String feedBackTo = OtherPersonUserId.UserId;
                                    String feedbackBy = Username.username;
                                    hubConnection.send("UpdateFeedback", feedBackTo, feedbackBy);
                                }
                                onBackPressed();
                            } else {
                                Toast.makeText(this, "Failed to post" + postedModel.getResult(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(this, "Exception Inside: " + ex, Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        feedbackContent.setVisibility(View.VISIBLE);
                        loadingContent.setVisibility(View.GONE);
                        animation.stop();
                        postReview.setVisibility(View.VISIBLE);
                        Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);


        } catch (Exception ex) {
            Toast.makeText(this, "Exception: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    private void onBackBtnClick() {
        backBtn.setOnClickListener(v -> onBackPressed());
    }

    private void userInterface() {
        backBtn = findViewById(R.id.addBack);
        postReview = findViewById(R.id.postReview);
        rating = findViewById(R.id.ratings);
        feedback = findViewById(R.id.feedbackText);

        feedbackContent = findViewById(R.id.postView);
        loadingContent = findViewById(R.id.loadingView);
        loading = findViewById(R.id.animationImage);
    }
}
