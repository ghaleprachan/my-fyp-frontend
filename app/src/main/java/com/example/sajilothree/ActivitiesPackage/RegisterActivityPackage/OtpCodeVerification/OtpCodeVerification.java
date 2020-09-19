package com.example.sajilothree.ActivitiesPackage.RegisterActivityPackage.OtpCodeVerification;

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
import com.example.sajilothree.ActivitiesPackage.RegisterActivityPackage.SelectType.SelectUserTypeActivity;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.RegisterNewUserServices.RegisterNewUserServices;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class OtpCodeVerification extends AppCompatActivity {
    private Button verifyButton;
    private PinView pin;
    private TextView wrongNumber, resentPin;
    private ImageView loadingAnimation;
    private String verificationCodeBySystem;
    private PhoneAuthProvider.ForceResendingToken token;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_code_verification_activity_layout);
        userInterface();

        onResentClick();
        onWrongNumberClick();

        String phoneNumber = RegisterNewUserServices.contactNumber;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        sendVerificationCodeToUser(phoneNumber);
        onVerifyClick();
    }

    /*@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void sendVerificationCodeToUser(String phoneNumber) {
        try {
            // Construct data
            String apiKey = "apikey=" + "dMH1AQDfRtA-EZ39rtGYrX0ALQ8vdyv4hN76pvSnDQ";
            Random random = new Random();
            randomNumber = random.nextInt(999999);
            String message = "&message=" + "Your OTP code is here " + randomNumber;
            String sender = "&sender=" + "Sahayog";
            String numbers = "&numbers=" + phoneNumber;

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes(StandardCharsets.UTF_8));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuilder stringBuffer = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();

//            return stringBuffer.toString();
            Toast.makeText(this, "OTP Send Successfully..", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.d("TAG", Objects.requireNonNull(e.getMessage()));
            Toast.makeText(this, "Failed to send OTP " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onVerifyClick() {
        verifyButton.setOnClickListener(v -> {
            if (randomNumber == Integer.parseInt(Objects.requireNonNull(pin.getText()).toString())) {
                Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, SelectUserTypeActivity.class));
                overridePendingTransition(R.anim.bottom_up, R.anim.nothing);
            } else {
                pin.setError("Wrong OTP");
                pin.requestFocus();
            }
        });
    }*/

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
            Toast.makeText(OtpCodeVerification.this, "Send", Toast.LENGTH_SHORT).show();
            verificationCodeBySystem = s;
            token = forceResendingToken;
        }

        @Override
        public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
            super.onCodeAutoRetrievalTimeOut(s);
            loadingFinish();
            Toast.makeText(OtpCodeVerification.this, "Otp Expired", Toast.LENGTH_SHORT).show();
        }

        /*
         * This method is executed when verification code is success
         * And perform the automatic tasks*/
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            loadingFinish();
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                Toast.makeText(OtpCodeVerification.this, "Success", Toast.LENGTH_SHORT).show();
                verifyCode(code);
                //signInUserByCredentials(phoneAuthCredential);
            }
            Toast.makeText(OtpCodeVerification.this, "Failed empty code", Toast.LENGTH_SHORT).show();
//            signInUserByCredentials(phoneAuthCredential);
        }

        /*
         * This method is executed when verification is failed*/
        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            loadingFinish();
            Toast.makeText(OtpCodeVerification.this, "Failed to verify " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                addOnCompleteListener(OtpCodeVerification.this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                        loadingFinish();
                        Intent intent = new Intent(this, SelectUserTypeActivity.class);

                        /* * This flags are set to avoid issues that might occur when user press back
                         * btn after getting to another activity*/
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(OtpCodeVerification.this, "Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void onVerifyClick() {
        verifyButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, SelectUserTypeActivity.class);
            startActivity(intent);
            /*try {
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
            }*/
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
