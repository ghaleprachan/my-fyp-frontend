package com.example.sajilothree.ActivitiesPackage.ForgotPassword;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.ModelsPackage.ResetPasswordModel.AfterUserVerify.ResetPasswordModel;
import com.example.sajilothree.MyAnimationsPackage.Techniques;
import com.example.sajilothree.MyAnimationsPackage.YoYo;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.ResetPasswordService.ResetPasswordService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.r0adkll.slidr.Slidr;

import org.json.JSONObject;

import java.util.Objects;

public class ResetPassword extends AppCompatActivity {
    private EditText username, phoneNumber;
    private Button changePassword;
    private ImageView loadingAnimation;
    private TextView errorMsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password_layout);
        Slidr.attach(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Forgot Password");
        userInterface();

        onSendNumberClick();
    }

    private void onSendNumberClick() {
        changePassword.setOnClickListener(v -> {
            if (verifyFields()) {
                validateUserAPICall();
            }
        });
    }



    private boolean verifyFields() {
        if (username.getText().toString().isEmpty()) {
            username.setError("Enter username!");
            username.requestFocus();
            return false;
        } else if (phoneNumber.getText().toString().isEmpty()) {
            phoneNumber.setError("Enter phone number!");
            phoneNumber.requestFocus();
            return false;
        } else if (phoneNumber.getText().toString().length() > 10 || phoneNumber.getText().toString().length() < 10) {
            phoneNumber.setError("Invalid phone number");
            phoneNumber.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    private void validateUserAPICall() {
        try {
            changePassword.setEnabled(false);
            changePassword.setVisibility(View.GONE);
            loadingAnimation.setVisibility(View.VISIBLE);
            AnimationDrawable animationDrawable = (AnimationDrawable) loadingAnimation.getDrawable();
            animationDrawable.start();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", username.getText().toString());
            jsonObject.put("phoneNumber", "+977" + phoneNumber.getText().toString());
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    BaseURL.verifyToResetPass,
                    jsonObject,
                    response -> {
                        changePassword.setEnabled(true);
                        changePassword.setVisibility(View.VISIBLE);
                        loadingAnimation.setVisibility(View.GONE);
                        animationDrawable.stop();
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            ResetPasswordModel resetPasswordModel = gson.fromJson(String.valueOf(response), ResetPasswordModel.class);
                            if (resetPasswordModel.getSuccess()) {
                                errorMsg.setVisibility(View.GONE);
                                if (ResetPasswordService.addResetDetails(resetPasswordModel.getResult())) {
                                    startActivity(new Intent(this, ResetOTPVerification.class));
                                    overridePendingTransition(R.anim.bottom_up, R.anim.nothing);
                                }
                            }
                        } catch (Exception ex) {
                            YoYo.with(Techniques.Shake).playOn(findViewById(R.id.errorMsg));
                            errorMsg.setVisibility(View.VISIBLE);
                        }
                    },
                    error -> {
                        changePassword.setEnabled(true);
                        changePassword.setVisibility(View.VISIBLE);
                        loadingAnimation.setVisibility(View.GONE);
                        animationDrawable.stop();
                        Toast.makeText(this, "Exception: " + error, Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);

        } catch (Exception ex) {
            Toast.makeText(this, "Exception: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    private void userInterface() {
        username = findViewById(R.id.username);
        phoneNumber = findViewById(R.id.phoneNumber);
        changePassword = findViewById(R.id.changePassword);
        loadingAnimation = findViewById(R.id.loadingAnimation);
        errorMsg = findViewById(R.id.errorMsg);
    }
}
