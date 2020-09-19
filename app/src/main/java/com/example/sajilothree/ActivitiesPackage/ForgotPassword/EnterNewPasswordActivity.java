package com.example.sajilothree.ActivitiesPackage.ForgotPassword;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.MainActivity;
import com.example.sajilothree.ModelsPackage.ResetPasswordModel.PasswordChanged.PasswordChangeResponse;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.ResetPasswordService.ResetPasswordService;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.Objects;

public class EnterNewPasswordActivity extends AppCompatActivity {
    private EditText password, confirmPassword;
    private Button changePassword;
    private ImageView loadingAnimation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_new_password);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Set New Password");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        userInterface();
        onChangePasswordClick();
    }

    public static boolean validatePassword(String password, String confirmPassword) {
        if (password.isEmpty()) {
            return false;
        } else if (confirmPassword.isEmpty()) {
            return false;
        } else if (!password.equals(confirmPassword)) {
            return false;
        } else {
            return true;
        }
    }

    private void onChangePasswordClick() {
        changePassword.setOnClickListener(v -> {
            if (verifyForm()) {
                updatePasswordApiCall();
            }
        });
    }

    private void updatePasswordApiCall() {
        try {
            changePassword.setEnabled(false);
            changePassword.setVisibility(View.GONE);
            loadingAnimation.setVisibility(View.VISIBLE);
            AnimationDrawable animationDrawable = (AnimationDrawable) loadingAnimation.getDrawable();
            animationDrawable.start();

            JSONObject jsonObject = new JSONObject();
            if (ResetPasswordService.resetDetails.size() > 0) {
                jsonObject.put("userid", ResetPasswordService.resetDetails.get(0).getUserId());
            } else {
                jsonObject.put("userid", getIntent().getIntExtra("userId", 0));
            }
            jsonObject.put("password", password.getText().toString());

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.PUT,
                    BaseURL.resetPassword,
                    jsonObject,
                    response -> {
                        changePassword.setEnabled(true);
                        changePassword.setVisibility(View.VISIBLE);
                        loadingAnimation.setVisibility(View.GONE);
                        animationDrawable.stop();
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            PasswordChangeResponse changeResponse = gson.fromJson(String.valueOf(response), PasswordChangeResponse.class);
                            if (changeResponse.getSuccess()) {
                                if (Username.setUsername(changeResponse.getResult())) {
                                    startActivity(new Intent(this, MainActivity.class));
                                    overridePendingTransition(R.anim.nav_default_pop_enter_anim, R.anim.nothing);
                                    finish();
                                }
                            } else {
                                Toast.makeText(this, "Failed: " + response.toString(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(this, "Response Parse Exception: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        changePassword.setEnabled(true);
                        changePassword.setVisibility(View.VISIBLE);
                        loadingAnimation.setVisibility(View.GONE);
                        animationDrawable.stop();
                        Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);

        } catch (Exception ex) {
            Toast.makeText(this, "Exception: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public boolean verifyForm() {
        if (password.getText().toString().isEmpty()) {
            password.setError("Enter Password!");
            password.requestFocus();
            return false;
        } else if (confirmPassword.getText().toString().isEmpty()) {
            confirmPassword.setError("Enter Confirm Password!");
            confirmPassword.requestFocus();
            return false;
        } else if (!confirmPassword.getText().toString().equals(password.getText().toString())) {
            confirmPassword.setError("Password don't match");
            return false;
        } else {
            return true;
        }
    }

    private void userInterface() {
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        changePassword = findViewById(R.id.changePassword);
        loadingAnimation = findViewById(R.id.loadingAnimation);
    }
}
