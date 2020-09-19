package com.example.sajilothree.ActivitiesPackage.ForgotPassword;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.test.rule.ActivityTestRule;

import com.example.sajilothree.ActivitiesPackage.AddPackage.AddPostActivity;
import com.example.sajilothree.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResetOTPVerificationTest {

    @Rule
    public ActivityTestRule<ResetOTPVerification> otpVerificationActivityTestRule = new
            ActivityTestRule<>(ResetOTPVerification.class);
    private ResetOTPVerification otpVerification;

    @Before
    public void setUp() throws Exception {
        otpVerification = otpVerificationActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        otpVerification = null;
    }

    @Test
    public void testLaunch() {
        View verifyButton = otpVerification.findViewById(R.id.verifyUser);
        View pin = otpVerification.findViewById(R.id.pinView);
        View wrongNumber = otpVerification.findViewById(R.id.wrongNumber);
        View resentPin = otpVerification.findViewById(R.id.resendPin);
        View loadingAnimation = otpVerification.findViewById(R.id.loadingAnimation);


        assertNotNull(verifyButton);
        assertNotNull(pin);
        assertNotNull(wrongNumber);
        assertNotNull(resentPin);
        assertNotNull(loadingAnimation);
    }

    @Test
    public void onCreate() {
    }
}