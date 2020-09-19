package com.example.sajilothree.ActivitiesPackage.ChatActivityPackage;

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

public class ChatSystemActivityTest {
    @Rule
    public ActivityTestRule<ChatSystemActivity> chatSystemActivityActivityTestRule = new
            ActivityTestRule<>(ChatSystemActivity.class);
    private ChatSystemActivity chatSystemActivity;

    @Before
    public void setUp() {
        chatSystemActivity = chatSystemActivityActivityTestRule.getActivity();
    }

    @After
    public void tearDown() {
        chatSystemActivity = null;
    }

    @Test
    public void chatValidationNotEmpty() {
        boolean test = true;
        boolean result = ChatSystemActivity.validateChat("hello");
        assertEquals(test, result);
    }

    @Test
    public void chatValidationEmpty() {
        boolean test = false;
        boolean result = ChatSystemActivity.validateChat("");
        assertEquals(test, result);
    }

    @Test
    public void testLaunch() {
        View writeMessage = chatSystemActivity.findViewById(R.id.writeMessage);
        View sendButton = chatSystemActivity.findViewById(R.id.sendButton);
        View messagesView = chatSystemActivity.findViewById(R.id.messages);

        assertNotNull(writeMessage);
        assertNotNull(sendButton);
        assertNotNull(messagesView);
    }

    @Test
    public void onCreate() {
    }
}