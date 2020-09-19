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

public class EnterNewPasswordActivityTest {
    @Rule
    public ActivityTestRule<EnterNewPasswordActivity> passwordActivityActivityTestRule = new
            ActivityTestRule<>(EnterNewPasswordActivity.class);
    private EnterNewPasswordActivity passwordActivity;

    @Before
    public void setUp() {
        passwordActivity = passwordActivityActivityTestRule.getActivity();
    }

    @Test
    public void testPassword() {
        boolean test = true;
        boolean result = EnterNewPasswordActivity.validatePassword("password", "password");
        assertEquals(test, result);
    }

    @Test
    public void whenEmpty() {
        boolean test = false;
        boolean result = EnterNewPasswordActivity.validatePassword("", "");
        assertEquals(test, result);
    }

    @Test
    public void whenDontMatch() {
        boolean test = false;
        boolean result = EnterNewPasswordActivity.validatePassword("passwordone", "passwordTwo");
        assertEquals(test, result);
    }

    @Test
    public void testLaunch() {
        View password = passwordActivity.findViewById(R.id.password);
        View confirmPassword = passwordActivity.findViewById(R.id.confirmPassword);
        View changePassword = passwordActivity.findViewById(R.id.changePassword);
        View loadingAnimation = passwordActivity.findViewById(R.id.loadingAnimation);


        assertNotNull(confirmPassword);
        assertNotNull(changePassword);
        assertNotNull(password);
        assertNotNull(loadingAnimation);
    }

    @After
    public void tearDown() throws Exception {
        passwordActivity = null;
    }

    @Test
    public void onCreate() {
    }

    @Test
    public void verifyForm() {
    }
}