package com.example.sajilothree.ActivitiesPackage.EditProfile.AddNewDetailsForm;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import com.example.sajilothree.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class AddNewDetailsTest {
    @Rule
    public ActivityTestRule<AddNewDetails> addNewDetailsActivityTestRule = new
            ActivityTestRule<>(AddNewDetails.class);
    private AddNewDetails addNewDetails;

    @Before
    public void setUp() {
        addNewDetails = addNewDetailsActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch() {
        View districts = addNewDetails.findViewById(R.id.district);
        View municipalities = addNewDetails.findViewById(R.id.municipality);
        View currentLocation = addNewDetails.findViewById(R.id.currentLocation);
        View addressParent = addNewDetails.findViewById(R.id.addressParent);
        View contactParent = addNewDetails.findViewById(R.id.contactsParent);
        View emailParent = addNewDetails.findViewById(R.id.emailsParent);
        View addAddress = addNewDetails.findViewById(R.id.addAddress);
        View addEmail = addNewDetails.findViewById(R.id.addEmail);
        View addContact = addNewDetails.findViewById(R.id.addContact);
        View phoneNumber = addNewDetails.findViewById(R.id.phoneNumber);
        View email = addNewDetails.findViewById(R.id.email);
        View backBtn = addNewDetails.findViewById(R.id.back);


        assertNotNull(districts);
        assertNotNull(municipalities);
        assertNotNull(currentLocation);
        assertNotNull(addressParent);
        assertNotNull(contactParent);
        assertNotNull(emailParent);
        assertNotNull(addAddress);
        assertNotNull(addEmail);
        assertNotNull(addContact);
        assertNotNull(phoneNumber);
        assertNotNull(email);
        assertNotNull(backBtn);
    }

    @After
    public void tearDown() {
        addNewDetails = null;
    }

    @Test
    public void onCreate() {
    }
}