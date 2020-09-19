package com.example.sajilothree.ActivitiesPackage.AddPackage;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.test.rule.ActivityTestRule;

import com.example.sajilothree.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class AddPostActivityUnitTest {

    @Rule
    public ActivityTestRule<AddPostActivity> addPostActivityActivityTestRule = new
            ActivityTestRule<>(AddPostActivity.class);
    private AddPostActivity addPostActivity;

    @Before
    public void setUp() {
        addPostActivity = addPostActivityActivityTestRule.getActivity();
    }

    @Test
    public void formValidationIsNotEmpty() {
        boolean test = true;
        boolean result = AddPostActivity.postFormValidation("About Post");
        assertEquals(test, result);
    }

    @Test
    public void formValidationEmpty() {
        boolean test = false;
        boolean result = AddPostActivity.postFormValidation("");
        assertEquals(test, result);
    }

    @Test
    public void testLaunch() {
        View myPostType = addPostActivity.findViewById(R.id.postType);
        assertNotNull(myPostType);



        View addBackBtn = addPostActivity.findViewById(R.id.addBack);
        View selectedDate = addPostActivity.findViewById(R.id.selectedDate);

        assertNotNull(addBackBtn);
        assertNotNull(selectedDate);

        View offerValidDate = addPostActivity.findViewById(R.id.offerValidDate);
        View spinnerCardView = addPostActivity.findViewById(R.id.cardView);
        View uploadImage = addPostActivity.findViewById(R.id.uploadImage);
        View uploadImageBtn = addPostActivity.findViewById(R.id.uploadImageBtn);
        View postDescription = addPostActivity.findViewById(R.id.offerDescription);
        View confirmPost = addPostActivity.findViewById(R.id.confirmPost);
        View loadingParent = addPostActivity.findViewById(R.id.loadingView);
        View loadingAnimation = addPostActivity.findViewById(R.id.loadingAnimation);
        View customToolbar = addPostActivity.findViewById(R.id.customToolbar);
        View bodyContent = addPostActivity.findViewById(R.id.scrollViewContent);
        View hideTitle = addPostActivity.findViewById(R.id.postProblemText);
        View parentLayout = addPostActivity.findViewById(R.id.parent);
        RelativeLayout relativeLayout = addPostActivity.findViewById(R.id.parent);

        assertNotNull(spinnerCardView);
        assertNotNull(offerValidDate);
        assertNotNull(uploadImage);
        assertNotNull(uploadImageBtn);
        assertNotNull(postDescription);
        assertNotNull(confirmPost);
        assertNotNull(loadingParent);
        assertNotNull(loadingAnimation);
        assertNotNull(customToolbar);
        assertNotNull(bodyContent);
        assertNotNull(hideTitle);
        assertNotNull(parentLayout);
        assertNotNull(relativeLayout);
    }

    @After
    public void tearDown() {
        addPostActivity = null;
    }

    @Test
    public void onCreate() {
    }

    @Test
    public void onActivityResult() {
    }
}