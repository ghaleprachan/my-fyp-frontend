package com.example.sajilothree.ActivitiesPackage.FeedbackReply;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.ActivitiesPackage.HideKeyboard.HideKeyboard;
import com.example.sajilothree.AdapterPackages.ReplyAdapter.ReplyAdapter;
import com.example.sajilothree.ModelsPackage.ReplyModel.ReplyModel;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import org.json.JSONObject;

import java.util.Objects;

public class FeedbackReplyActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageView addReply;
    private EditText new_reply;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_reply_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Replies");

        userInterface();

        repliesApiCall();

        onAddClick();
    }

    private void onAddClick() {
        addReply.setOnClickListener(v -> {
            HideKeyboard.hideKeyboard(this);
            if (new_reply.getText().toString().isEmpty()) {
                new_reply.setError("Empty Review");
            } else {
                addReply.setClickable(false);
                addReviewAPICall();
            }
        });
    }

    private void addReviewAPICall() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("FeedbackId", getFeedbackId());
            jsonObject.put("FeedbackBy", Username.username);
            jsonObject.put("Reply", new_reply.getText());
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    BaseURL.addReplies,
                    jsonObject,
                    response -> {
                        try {
                            addReply.setClickable(true);
                            new_reply.setText(null);
                            repliesApiCall();
                        } catch (Exception ex) {
                            Toast.makeText(this, "Failed to add", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);

        } catch (Exception ex) {
            Toast.makeText(this, "Exception: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    private void userInterface() {
        recyclerView = findViewById(R.id.replyView);
        addReply = findViewById(R.id.addReply);
        new_reply = findViewById(R.id.new_reply);
    }

    private Integer getFeedbackId() {
        return getIntent().getIntExtra("FeedbackId", 0);
    }

    private void repliesApiCall() {
        try {
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    BaseURL.getReplies + getFeedbackId(),
                    response -> {
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            ReplyModel replyModel = gson.fromJson(response, ReplyModel.class);
                            if (replyModel.getSuccess()) {
                                fillTheReplies(replyModel);
                            }
                        } catch (Exception ex) {
                            Toast.makeText(this, "Response Ex: " + ex, Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show()
            );
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(this, "Exception: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    private void fillTheReplies(ReplyModel replyModel) {
        try {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            ReplyAdapter replyAdapter = new ReplyAdapter(this, replyModel);
            recyclerView.setAdapter(replyAdapter);
        } catch (Exception ex) {
            Toast.makeText(this, "Recycler Exception: " + ex, Toast.LENGTH_SHORT).show();
        }
    }
}
