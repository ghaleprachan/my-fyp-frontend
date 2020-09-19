package com.example.sajilothree.ActivitiesPackage.OPTActivityPackage;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.test.rule.ActivityTestRule;

import com.example.sajilothree.ActivitiesPackage.AddPackage.AddPostActivity;
import com.example.sajilothree.ActivitiesPackage.OPTActivityPackage.SwipeAdapter.CustomSwipeAdapter;
import com.example.sajilothree.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class OTPActivityTest {
    @Rule
    public ActivityTestRule<OTPActivity> otpActivityActivityTestRule = new
            ActivityTestRule<>(OTPActivity.class);
    private OTPActivity otpActivity;

    @Before
    public void setUp() {
        otpActivity = otpActivityActivityTestRule.getActivity();
    }

    @After
    public void tearDown() {
        otpActivity = null;
    }

    @Test
    public void validatePhone() {
        boolean test = true;
        boolean result = OTPActivity.phoneNumberValidation("9846356410");
        assertEquals(test, result);
    }

    @Test
    public void numberShort() {
        boolean test = false;
        boolean result = OTPActivity.phoneNumberValidation("9846");
        assertEquals(test, result);
    }

    @Test
    public void numberLong() {
        boolean test = false;
        boolean result = OTPActivity.phoneNumberValidation("984633564109846");
        assertEquals(test, result);
    }

    @Test
    public void validateUser() {
        boolean test = true;
        boolean result = OTPActivity.validateUser("prachan", "barpak");
        assertEquals(test, result);
    }

    @Test
    public void emptyUsername() {
        boolean test = false;
        boolean result = OTPActivity.validateUser("", "barpak");
        assertEquals(test, result);
    }

    @Test
    public void emptyPassword() {
        boolean test = false;
        boolean result = OTPActivity.validateUser("prachan", "");
        assertEquals(test, result);
    }

    @Test
    public void testLaunch() {
        View viewPager = otpActivity.findViewById(R.id.slideViewPager);
        View dotsLayout = otpActivity.findViewById(R.id.dotsContainer);
        View openSignIn = otpActivity.findViewById(R.id.signIn);
        View constraintLayout = otpActivity.findViewById(R.id.parent);
        View openRegister = otpActivity.findViewById(R.id.registerForm);
        View sendNumber = otpActivity.findViewById(R.id.sendNumber);
        View username = otpActivity.findViewById(R.id.userName);
        View password = otpActivity.findViewById(R.id.password);
        View phoneNumber = otpActivity.findViewById(R.id.phoneNumber);
        View progressBar = otpActivity.findViewById(R.id.progressBar);
        View logInBtn = otpActivity.findViewById(R.id.logInBtn);
        View hidePassword = otpActivity.findViewById(R.id.hidePassword);
        View showPassword = otpActivity.findViewById(R.id.showPassword);
        View shadow = otpActivity.findViewById(R.id.shadow);
        View shadowTwo = otpActivity.findViewById(R.id.shadowTwo);
        View forgotPassword = otpActivity.findViewById(R.id.forgotPassword);


        assertNotNull(viewPager);
        assertNotNull(dotsLayout);
        assertNotNull(openSignIn);
        assertNotNull(constraintLayout);
        assertNotNull(openRegister);
        assertNotNull(sendNumber);
        assertNotNull(username);
        assertNotNull(password);
        assertNotNull(phoneNumber);
        assertNotNull(progressBar);
        assertNotNull(logInBtn);
        assertNotNull(hidePassword);
        assertNotNull(showPassword);
        assertNotNull(shadow);
        assertNotNull(shadowTwo);
        assertNotNull(forgotPassword);
    }

    @Test
    public void onCreate() {
    }

    @Test
    public void dpToPx() {
    }

    @Test
    public void onStart() {
    }
}