package com.example.sajilothree.ActivitiesPackage.ForgotPassword;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.ResetPasswordService.ResetPasswordService;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ResetOTPVerification extends AppCompatActivity {
    private Button verifyButton;
    private PinView pin;
    private TextView wrongNumber, resentPin;
    private ImageView loadingAnimation;
    private String verificationCodeBySystem;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_code_verification_activity_layout);
        userInterface();

        onResentClick();
        onWrongNumberClick();

        try {
            String phoneNumber = ResetPasswordService.resetDetails.get(0).getContacts().getContactNumber();
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            sendVerificationCodeToUser(phoneNumber);
            onVerifyClick();
        } catch (Exception ex) {
            Log.d("ResetOTP", Objects.requireNonNull(ex.getMessage()));
        }
    }

    private void sendVerificationCodeToUser(String phoneNumber) {
        Toast.makeText(this, "Processing", Toast.LENGTH_SHORT).show();
        loadingAnimation.setVisibility(View.VISIBLE);
        verifyButton.setVisibility(View.GONE);
        AnimationDrawable animationDrawable = (AnimationDrawable) loadingAnimation.getDrawable();
        animationDrawable.start();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60L,
                TimeUnit.SECONDS,
                this,
                mCallbacks
        );
    }

    private void loadingFinish() {
        loadingAnimation.setVisibility(View.GONE);
        verifyButton.setVisibility(View.VISIBLE);
        AnimationDrawable animationDrawable = (AnimationDrawable) loadingAnimation.getDrawable();
        animationDrawable.stop();
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        /*
         * This is executed when the code send is not the same phone that user is using
         * So this is should be done when when codes are different*/
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            loadingFinish();
            Toast.makeText(ResetOTPVerification.this, "Send", Toast.LENGTH_SHORT).show();
            verificationCodeBySystem = s;
        }

        @Override
        public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
            super.onCodeAutoRetrievalTimeOut(s);
            loadingFinish();
            Toast.makeText(ResetOTPVerification.this, "Otp Expired", Toast.LENGTH_SHORT).show();
        }

        /*
         * This method is executed when verification code is success
         * And perform the automatic tasks*/
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            loadingFinish();
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                Toast.makeText(ResetOTPVerification.this, "Success", Toast.LENGTH_SHORT).show();
                verifyCode(code);
                //signInUserByCredentials(phoneAuthCredential);
            }
            Toast.makeText(ResetOTPVerification.this, "Failed empty code", Toast.LENGTH_SHORT).show();
//            signInUserByCredentials(phoneAuthCredential);
        }

        /*
         * This method is executed when verification is failed*/
        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            loadingFinish();
            Toast.makeText(ResetOTPVerification.this, "Failed to verify " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    };

    private void verifyCode(String code) {
        Toast.makeText(this, "Failed to verify", Toast.LENGTH_SHORT).show();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, code);
        signInUserByCredentials(credential);
    }

    private void signInUserByCredentials(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).
                addOnCompleteListener(ResetOTPVerification.this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                        loadingFinish();
                        Intent intent = new Intent(this, EnterNewPasswordActivity.class);

                        /* * This flags are set to avoid issues that might occur when user press back
                         * btn after getting to another activity*/
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ResetOTPVerification.this, "Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void onVerifyClick() {
        verifyButton.setOnClickListener(v -> {
            try {
                String code = Objects.requireNonNull(pin.getText()).toString().trim();
                if ((code.isEmpty() || code.length() < 6)) {
                    pin.setError("Enter code...");
                    pin.requestFocus();
                } else {
                    String pinNumber = pin.getText().toString();
                    loadingAnimation.setVisibility(View.VISIBLE);
                    verifyButton.setVisibility(View.GONE);
                    AnimationDrawable animationDrawable = (AnimationDrawable) loadingAnimation.getDrawable();
                    animationDrawable.start();
                    verifyCode(pinNumber);
                }
            } catch (Exception ex) {
                Toast.makeText(this, "Exception: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onWrongNumberClick() {
        wrongNumber.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void onResentClick() {
        resentPin.setOnClickListener(v -> {
            Toast.makeText(this, "Resending...", Toast.LENGTH_SHORT).show();
        });
    }

    private void userInterface() {
        verifyButton = findViewById(R.id.verifyUser);
        pin = findViewById(R.id.pinView);
        wrongNumber = findViewById(R.id.wrongNumber);
        resentPin = findViewById(R.id.resendPin);
        loadingAnimation = findViewById(R.id.loadingAnimation);
    }
}
