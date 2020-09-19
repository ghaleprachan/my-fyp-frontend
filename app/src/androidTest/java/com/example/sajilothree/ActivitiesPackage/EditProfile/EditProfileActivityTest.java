package com.example.sajilothree.ActivitiesPackage.EditProfile;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import com.example.sajilothree.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class EditProfileActivityTest {
    @Rule
    public ActivityTestRule<EditProfileActivity> addPostActivityActivityTestRule = new
            ActivityTestRule<>(EditProfileActivity.class);
    private EditProfileActivity addPostActivity;

    @Before
    public void setUp() {
        addPostActivity = addPostActivityActivityTestRule.getActivity();
    }

    @After
    public void tearDown() {
        addPostActivity = null;
    }

    @Test
    public void formValidation() {
        boolean test = true;
        boolean result = EditProfileActivity.validateDetails("Prachan",
                "9846356410", "ghaleprachan@gmail.com");
        assertEquals(test, result);
    }

    @Test
    public void emailValidation() {
        boolean test = false;
        boolean result = EditProfileActivity.validateDetails("Prachan",
                "9846356410", "ghaleprachan");
        assertEquals(test, result);
    }

    @Test
    public void phoneValidation() {
        boolean test = false;
        boolean result = EditProfileActivity.validateDetails("Prachan",
                "984635641011", "ghaleprachan@gmail.com");
        assertEquals(test, result);
    }
    @Test
    public void onCreate() {
    }

    @Test
    public void onActivityResult() {
    }

    @Test
    public void onBackPressed() {
    }

    @Test
    public void testLaunch() {
        View backBtn = addPostActivity.findViewById(R.id.editBackBtn);
        View saveChanges = addPostActivity.findViewById(R.id.saveChanges);
        View profileImage = addPostActivity.findViewById(R.id.editProfileImage);
        View name = addPostActivity.findViewById(R.id.editFullName);
        View addressView = addPostActivity.findViewById(R.id.addressesView);
        View contactsView = addPostActivity.findViewById(R.id.contactsView);
        View emailView = addPostActivity.findViewById(R.id.emailsView);
        View addPhoto = addPostActivity.findViewById(R.id.editAddProfilePhoto);
        View addAddress = addPostActivity.findViewById(R.id.addNewAddress);
        View addContact = addPostActivity.findViewById(R.id.addNewContact);
        View addEmail = addPostActivity.findViewById(R.id.addNewEmail);
        View loading = addPostActivity.findViewById(R.id.loadingUpdate);
        View loadingParent = addPostActivity.findViewById(R.id.loadingParent);


        assertNotNull(backBtn);
        assertNotNull(saveChanges);
        assertNotNull(profileImage);
        assertNotNull(name);
        assertNotNull(addressView);
        assertNotNull(contactsView);
        assertNotNull(emailView);
        assertNotNull(addPhoto);
        assertNotNull(addAddress);
        assertNotNull(loadingParent);
        assertNotNull(addContact);
        assertNotNull(addEmail);
        assertNotNull(loading);
    }
}