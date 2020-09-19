package com.example.sajilothree.ActivitiesPackage.OPTActivityPackage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.ActivitiesPackage.ForgotPassword.ResetPassword;
import com.example.sajilothree.ActivitiesPackage.OPTActivityPackage.SwipeAdapter.CustomSwipeAdapter;
import com.example.sajilothree.ActivitiesPackage.RegisterActivityPackage.OtpCodeVerification.OtpCodeVerification;
import com.example.sajilothree.MainActivity;
import com.example.sajilothree.ModelsPackage.LogInUserModel.LogInUserModel;
import com.example.sajilothree.MyAnimationsPackage.Techniques;
import com.example.sajilothree.MyAnimationsPackage.YoYo;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.RegisterNewUserServices.RegisterNewUserServices;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class OTPActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private CustomSwipeAdapter adapter;
    private LinearLayout dotsLayout;
    private int custom_position = 0;
    private RelativeLayout slogan;

    private TextView openSignIn, openRegister, forgotPassword;
    private EditText phoneNumber, username, password;
    private Button sendNumber, logInBtn;
    private ConstraintLayout constraintLayout;
    private ImageView progressBar, hidePassword, showPassword;
    private View shadow, shadowTwo;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_layout);
        userInterface();
        logInBtn.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

//        this is for slider
        viewPager.setAdapter(adapter);
        setSlidingViewPager();
        prepareDots(custom_position++);
        onPageChange();
//        these are for log in functions
        onSignInClick();
        onRegisterClick();
        onLogInButtonClick();
        onSendPhoneNumberClick();
        onHideShowPassword();
        phoneNumber.setText("9846356410");

        onKeyBoardOpen();

        onForgotPasswordClick();
    }

    private void onForgotPasswordClick() {
        forgotPassword.setOnClickListener(v -> {
            startActivity(new Intent(OTPActivity.this, ResetPassword.class));
            overridePendingTransition(R.anim.nav_default_pop_enter_anim, R.anim.nothing);
        });
    }

    public static boolean phoneNumberValidation(String phoneNumber) {
        if (phoneNumber.isEmpty()) {
            return false;
        } else if (phoneNumber.length() < 10) {
            return false;
        } else return phoneNumber.length() <= 10;
    }

    public static boolean validateUser(String username, String password) {
        if (username.isEmpty()) {
            return false;
        } else return !password.isEmpty();
    }

    private void onSendPhoneNumberClick() {
        sendNumber.setOnClickListener(v -> {
            if (phoneNumber.getText().toString().isEmpty()) {
                YoYo.with(Techniques.Shake).playOn(findViewById(R.id.phoneCard));
                phoneNumber.setError("Enter Number!");
                phoneNumber.requestFocus();
            } else if (phoneNumber.getText().toString().length() < 10 ||
                    phoneNumber.getText().toString().length() > 10) {
                YoYo.with(Techniques.Shake).playOn(findViewById(R.id.phoneCard));
                phoneNumber.setError("Invalid Phone Number!");
                phoneNumber.requestFocus();
            } else {
                if (RegisterNewUserServices.setContact(fullPhoneNumber())) {
                    Intent intent = new Intent(OTPActivity.this, OtpCodeVerification.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.bottom_up, R.anim.nothing);
                }
            }
        });
    }

    private String fullPhoneNumber() {
        String code = "+977";
        String number = phoneNumber.getText().toString();
        return code + number;
    }

    private void onKeyBoardOpen() {
        constraintLayout.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            int heightDiff = constraintLayout.getRootView().getHeight() - constraintLayout.getHeight();
            if (heightDiff > dpToPx(OTPActivity.this, 200)) {
                slogan.setVisibility(View.GONE);
                shadow.setVisibility(View.GONE);
                shadowTwo.setVisibility(View.GONE);
            } else {
                shadow.setVisibility(View.VISIBLE);
                slogan.setVisibility(View.VISIBLE);
                shadowTwo.setVisibility(View.VISIBLE);
            }
        });
    }

    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    private void onHideShowPassword() {
        showPassword.setOnClickListener(v -> {
            password.setTransformationMethod(new PasswordTransformationMethod());
            showPassword.setVisibility(View.GONE);
            hidePassword.setVisibility(View.VISIBLE);
        });
        hidePassword.setOnClickListener(v1 -> {
            password.setTransformationMethod(new HideReturnsTransformationMethod());
            showPassword.setVisibility(View.VISIBLE);
            hidePassword.setVisibility(View.GONE);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseAuth.getInstance().signOut();
        }
    }

    private void onLogInButtonClick() {
        logInBtn.setOnClickListener(v -> {
            TextFieldValidation();
        });
    }

    private void TextFieldValidation() {
        if (username.getText().toString().isEmpty()) {
            YoYo.with(Techniques.Shake).playOn(findViewById(R.id.usernameCard));
            username.setError("Username Empty!");
        } else if (password.getText().toString().isEmpty()) {
            YoYo.with(Techniques.Shake).playOn(findViewById(R.id.passwordCard));
            password.setError("Password Empty!");
        } else {
            APICall();
        }
    }

    private void APICall() {
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("username", username.getText().toString());
            jsonBody.put("password", password.getText().toString());
            logInBtn.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            AnimationDrawable animation = (AnimationDrawable) progressBar.getDrawable();
            animation.start();
            JsonObjectRequest jsonObject = new JsonObjectRequest(
                    Request.Method.POST,
                    BaseURL.validateUser,
                    jsonBody,
                    response -> {
                        if (response != null) {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            LogInUserModel profileHeading = gson.fromJson(String.valueOf(response),
                                    LogInUserModel.class);
                            if (profileHeading.getSuccess()) {
//                                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                                if (Username.setUsername(profileHeading.getTokenNumber())) {
                                    progressBar.setVisibility(View.GONE);
                                    logInBtn.setVisibility(View.VISIBLE);
                                    Intent activity = new Intent(OTPActivity.this, MainActivity.class);
                                    startActivity(activity);
//                                    basicUserDetailsAPICall();
                                } else {
                                    logInBtn.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                logInBtn.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                YoYo.with(Techniques.Shake).playOn(findViewById(R.id.usernameCard));
                                YoYo.with(Techniques.Shake).playOn(findViewById(R.id.passwordCard));
                                username.setError("User not found!");
                                password.setError("User not found!");
                            }
                        } else {
                            Toast.makeText(OTPActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        logInBtn.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(OTPActivity.this, "Server Error! " + error, Toast.LENGTH_SHORT).show();
                    });

            RequestQueue requestQueue;
            requestQueue = Volley.newRequestQueue(OTPActivity.this);
            requestQueue.add(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onRegisterClick() {
        openRegister.setOnClickListener(v -> {
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(OTPActivity.this, R.layout.otp_activity);
            ChangeBounds transition = new ChangeBounds();
            transition.setInterpolator(new AnticipateInterpolator(1.0f));
            transition.setDuration(500);
            TransitionManager.beginDelayedTransition(constraintLayout, transition);
            constraintSet.applyTo(constraintLayout);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onSignInClick() {
        openSignIn.setOnClickListener(v -> {
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(OTPActivity.this, R.layout.sign_in_layout);
            ChangeBounds transition = new ChangeBounds();
            transition.setInterpolator(new AnticipateInterpolator(1.0f));
            transition.setDuration(500);
            TransitionManager.beginDelayedTransition(constraintLayout, transition);
            constraintSet.applyTo(constraintLayout);
        });
    }

    private void onPageChange() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                prepareDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setSlidingViewPager() {
        Runnable mLoopingRunnable = new Runnable() {
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                int totalItems = Objects.requireNonNull(viewPager.getAdapter()).getCount();
                int nextItem = (currentItem + 1) % totalItems;
                viewPager.setCurrentItem(nextItem, false);
                viewPager.postDelayed(this, 2000);
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(mLoopingRunnable, 2000);
    }

    private void prepareDots(int currentSlidePosition) {
        if (dotsLayout.getChildCount() > 0) {
            dotsLayout.removeAllViews();
        }
        ImageView[] dots = new ImageView[4];

        for (int i = 0; i < 4; i++) {
            dots[i] = new ImageView(this);
            if (i == currentSlidePosition) {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active_dot));
            } else {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active_not_dot));
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(4, 0, 4, 0);
            dotsLayout.addView(dots[i], layoutParams);
        }
    }

    private void userInterface() {
        viewPager = findViewById(R.id.slideViewPager);
        adapter = new CustomSwipeAdapter(OTPActivity.this);
        dotsLayout = findViewById(R.id.dotsContainer);
        openSignIn = findViewById(R.id.signIn);
        constraintLayout = findViewById(R.id.parent);
        openRegister = findViewById(R.id.registerForm);
        sendNumber = findViewById(R.id.sendNumber);
        username = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        phoneNumber = findViewById(R.id.phoneNumber);
        progressBar = findViewById(R.id.progressBar);
        logInBtn = findViewById(R.id.logInBtn);
        hidePassword = findViewById(R.id.hidePassword);
        showPassword = findViewById(R.id.showPassword);
        slogan = findViewById(R.id.slogan);
        shadow = findViewById(R.id.shadow);
        shadowTwo = findViewById(R.id.shadowTwo);
        forgotPassword = findViewById(R.id.forgotPassword);
    }
}
