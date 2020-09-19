package com.example.sajilothree.ActivitiesPackage.RegisterActivityPackage.RegisterForm;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import com.example.sajilothree.ActivitiesPackage.BookingProcessPackage.BookingPackage.BookingActivity;
import com.example.sajilothree.ActivitiesPackage.OPTActivityPackage.OTPActivity;
import com.example.sajilothree.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class RegisterFormTest {
    @Rule
    public ActivityTestRule<RegisterForm> registerFormActivityTestRule = new
            ActivityTestRule<>(RegisterForm.class);
    private RegisterForm registerForm;

    @Before
    public void setUp() {
        registerForm = registerFormActivityTestRule.getActivity();
    }

    @After
    public void tearDown() {
        registerForm = null;
    }

    @Test
    public void formValidation() {
        boolean test = true;
        boolean result = RegisterForm.formValidation("Prachan", "prachan@gmail.com",
                "9846356410", "Barpak, Gorkha", "Electrician", "prachan",
                "barpak1234");
        assertEquals(test, result);
    }

    @Test
    public void emptyFormValidation() {
        boolean test = false;
        boolean result = RegisterForm.formValidation("Prachan", "prachan@gmail.com",
                "9846356410", "", "", "prachan",
                "barpak1234");
        assertEquals(test, result);
    }

    @Test
    public void validateUser() {
        boolean test = true;
        boolean result = RegisterForm.validateUser("prachan", "prachan");
        assertEquals(test, result);
    }

    @Test
    public void emptyPassword() {
        boolean test = false;
        boolean result = RegisterForm.validateUser("", "barpak");
        assertEquals(test, result);
    }

    @Test
    public void invalidPassword() {
        boolean test = false;
        boolean result = RegisterForm.validateUser("barpakOne", "barpak");
        assertEquals(test, result);
    }

    @Test
    public void emptyConfirmPassword() {
        boolean test = false;
        boolean result = RegisterForm.validateUser("prachan", "");
        assertEquals(test, result);
    }

    @Test
    public void testLaunch() {
        View backButton = registerForm.findViewById(R.id.backBtnCustomerForm);
        View bounceOne = registerForm.findViewById(R.id.bounceOne);
        View bounceTwo = registerForm.findViewById(R.id.bounceTwo);
        View gender = registerForm.findViewById(R.id.gender);
        View districts = registerForm.findViewById(R.id.city);
        View municipalities = registerForm.findViewById(R.id.municipality);
        View back = registerForm.findViewById(R.id.back);
        View next = registerForm.findViewById(R.id.nextForm);
        View createAccount = registerForm.findViewById(R.id.createAccount);
        View fullName = registerForm.findViewById(R.id.fullName);
        View emailId = registerForm.findViewById(R.id.emailId);
        View currentLocation = registerForm.findViewById(R.id.preciseLocation);
        View districtError = registerForm.findViewById(R.id.districtError);
        View municipalityError = registerForm.findViewById(R.id.municipalityError);
        View username = registerForm.findViewById(R.id.username);
        View password = registerForm.findViewById(R.id.password);
        View confirmPassword = registerForm.findViewById(R.id.confirmPassword);
        View mItemSelected = registerForm.findViewById(R.id.selectedProfession);
        View selectedProfessionLayout = registerForm.findViewById(R.id.selectProfessionParent);
        View selectedProfessionShadow = registerForm.findViewById(R.id.selectProfessionShadow);
        View addProfession = registerForm.findViewById(R.id.addProfession);
        View addPhoto = registerForm.findViewById(R.id.addImage);
        View profileImage = registerForm.findViewById(R.id.profileImage);
        View progress = registerForm.findViewById(R.id.progressBar);


        assertNotNull(backButton);
        assertNotNull(bounceOne);
        assertNotNull(bounceTwo);
        assertNotNull(districts);
        assertNotNull(gender);
        assertNotNull(municipalities);
        assertNotNull(back);
        assertNotNull(next);
        assertNotNull(createAccount);
        assertNotNull(fullName);
        assertNotNull(currentLocation);
        assertNotNull(emailId);
        assertNotNull(districtError);
        assertNotNull(municipalityError);

        assertNotNull(username);
        assertNotNull(password);
        assertNotNull(confirmPassword);
        assertNotNull(mItemSelected);
        assertNotNull(selectedProfessionLayout);
        assertNotNull(selectedProfessionShadow);
        assertNotNull(addProfession);
        assertNotNull(addPhoto);
        assertNotNull(profileImage);
        assertNotNull(progress);
    }

    @Test
    public void onCreate() {
    }
}