package com.example.sajilothree.ActivitiesPackage.SettingPackage.EnterOldPassword;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
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
import com.example.sajilothree.ActivitiesPackage.ForgotPassword.EnterNewPasswordActivity;
import com.example.sajilothree.ModelsPackage.ChagePassword.ChangePasswordResponse;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.Objects;

public class ValidateOldPassword extends AppCompatActivity {
    private TextView password, errorMessage;
    private Button confirmPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.validate_old_password_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Old Password");
        userInterface();
        password.requestFocus();
        confirmPassword.setEnabled(false);
        passwordTextWatcher();

        onConfirmPasswordClick();
    }

    private void passwordTextWatcher() {
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (password.getText().toString().length() == 0 || password.getText().toString().isEmpty()) {
                    confirmPassword.setEnabled(false);
                } else {
                    confirmPassword.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void onConfirmPasswordClick() {
        confirmPassword.setOnClickListener(v -> {
            if (validateForm()) {
                validatePasswordApiCall();
            }
        });
    }

    private void validatePasswordApiCall() {
        try {
            errorMessage.setVisibility(View.GONE);
            confirmPassword.setEnabled(false);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", Username.username);
            jsonObject.put("password", password.getText().toString());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, BaseURL.changePassword, jsonObject,
                    response -> {
                        confirmPassword.setEnabled(true);
                        try {
                            ChangePasswordResponse passwordResponse = new GsonBuilder().create().fromJson(String.valueOf(response),
                                    ChangePasswordResponse.class);
                            if (passwordResponse.getSuccess()) {
                                Intent intent = new Intent(this, EnterNewPasswordActivity.class);
                                intent.putExtra("userId", passwordResponse.getResult());
                                startActivity(intent);
                                finish();
                            } else {
                                errorMessage.setVisibility(View.VISIBLE);
                            }
                        } catch (Exception ex) {
                            errorMessage.setVisibility(View.VISIBLE);
                        }
                    },
                    error -> {
                        confirmPassword.setEnabled(true);
                        Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show();
                    });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(this, "Exception: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateForm() {
        if (password.getText().toString().isEmpty()) {
            password.requestFocus();
            password.setError("Please enter password!");
            return false;
        } else {
            return true;
        }
    }

    private void userInterface() {
        password = findViewById(R.id.oldPassword);
        errorMessage = findViewById(R.id.errorMsg);
        confirmPassword = findViewById(R.id.confirmPassword);
    }
}
