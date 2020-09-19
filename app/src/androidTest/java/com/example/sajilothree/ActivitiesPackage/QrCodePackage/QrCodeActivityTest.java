package com.example.sajilothree.ActivitiesPackage.QrCodePackage;

import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.test.rule.ActivityTestRule;

import com.example.sajilothree.ActivitiesPackage.AddPackage.AddPostActivity;
import com.example.sajilothree.R;

import org.junit.Rule;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class QrCodeActivityTest {
    @Rule
    public ActivityTestRule<QrCodeActivity> qrCodeActivityActivityTestRule = new
            ActivityTestRule<>(QrCodeActivity.class);
    private QrCodeActivity qrCodeActivity;

    @Test
    public void onCreate() {
        qrCodeActivity = qrCodeActivityActivityTestRule.getActivity();
    }

    @Test
    public void onRequestPermissionsResult() {
        qrCodeActivity = null;
    }

    @Test
    public void testLaunch() {
        try {
            View myQrCode = qrCodeActivity.findViewById(R.id.myQrCodeBtn);
            assertNotNull(myQrCode);
        } catch (Exception ex) {
            Log.d("TEST", Objects.requireNonNull(ex.getMessage()));
        }
    }

    @Test
    public void onResume() {
    }

    @Test
    public void onPause() {
    }

    @Test
    public void handleResult() {
    }

    @Test
    public void onCreateOptionsMenu() {
    }

    @Test
    public void onOptionsItemSelected() {
    }
}