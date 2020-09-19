package com.example.sajilothree.ActivitiesPackage.FullImageViewPackage;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.R;
import com.r0adkll.slidr.Slidr;

public class FullImageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image_activity);
        Slidr.attach(this);

        LinearLayout backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> onBackPressed());

        ImageView selectedImage = findViewById(R.id.selectedImage);

        String imageUrl = getIntent().getStringExtra("imageUrl");

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.profile_placeholder);
        Glide.with(this)
                .load(BaseURL.BaseURL + imageUrl)
                .apply(options)
                .into(selectedImage);
    }
}
