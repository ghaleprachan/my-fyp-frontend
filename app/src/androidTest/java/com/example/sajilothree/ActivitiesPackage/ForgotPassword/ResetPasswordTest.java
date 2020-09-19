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

public class ResetPasswordTest {
    @Rule
    public ActivityTestRule<ResetPassword> passwordActivityTestRule = new
            ActivityTestRule<>(ResetPassword.class);
    private ResetPassword password;

    @Before
    public void setUp() throws Exception {
        password = passwordActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        password = null;
    }

    @Test
    public void testLaunch() {
        View username = password.findViewById(R.id.username);
        View phoneNumber = password.findViewById(R.id.phoneNumber);
        View changePassword = password.findViewById(R.id.changePassword);
        View loadingAnimation = password.findViewById(R.id.loadingAnimation);
        View errorMsg = password.findViewById(R.id.errorMsg);


        assertNotNull(username);
        assertNotNull(phoneNumber);
        assertNotNull(changePassword);
        assertNotNull(errorMsg);
        assertNotNull(loadingAnimation);
    }

    @Test
    public void onCreate() {
    }
}