package com.example.sajilothree.ActivitiesPackage.SplashScreenPackage;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sajilothree.ActivitiesPackage.OPTActivityPackage.OTPActivity;
import com.example.sajilothree.R;

import java.util.Locale;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeLanguage();
        setContentView(R.layout.splash_screen_activity);

        ImageView imageView = findViewById(R.id.animationImage);
        AnimationDrawable animation = (AnimationDrawable) imageView.getDrawable();
        animation.start();

        activityChange();
    }

    private void activityChange() {
        new Handler().postDelayed(() -> {
            Intent mainIntent = new Intent(SplashScreen.this, OTPActivity.class);
            startActivity(mainIntent);
            finish();
        }, 2500);
    }

    private void changeLanguage() {
        SharedPreferences settings = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        if (settings.getString("My_Lang", "").equals("en")) {
            Locale locale = new Locale("en");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
            settings.edit().putString("My_Lang", "en").apply();
        } else if (settings.getString("My_Lang", "").equals("ne")) {
            Locale locale = new Locale("ne");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
            settings.edit().putString("My_Lang", "ne").apply();
        } else {
            Locale locale = new Locale("en");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
            settings.edit().putString("My_Lang", "en").apply();
        }
    }
}
