package com.example.sajilothree.ActivitiesPackage.RegisterActivityPackage.SelectType;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sajilothree.ActivitiesPackage.RegisterActivityPackage.RegisterForm.RegisterForm;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.RegisterNewUserServices.RegisterNewUserServices;
import com.r0adkll.slidr.Slidr;

public class SelectUserTypeActivity extends AppCompatActivity {
    private ImageView backBtn;
    private TextView typeCustomer, typeServiceProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_user_type_activity_layout);
        Slidr.attach(this);
        userInterface();
        onBackClick();
        onCustomerSelected();
        onServiceProviderSelected();
    }

    private void onServiceProviderSelected() {
        typeServiceProvider.setOnClickListener(v -> {
            if (RegisterNewUserServices.setUserType("Service Provider")) {
                startActivity(new Intent(this, RegisterForm.class));
                overridePendingTransition(R.anim.bottom_up, R.anim.nothing);
            }
        });
    }

    private void onCustomerSelected() {
        typeCustomer.setOnClickListener(v -> {
            if (RegisterNewUserServices.setUserType("Customer")) {
                startActivity(new Intent(this, RegisterForm.class));
                overridePendingTransition(R.anim.bottom_up, R.anim.nothing);
            }
        });
    }

    private void onBackClick() {
        backBtn.setOnClickListener(v -> onBackPressed());
    }

    private void userInterface() {
        typeCustomer = findViewById(R.id.customer);
        typeServiceProvider = findViewById(R.id.serviceProvider);
        backBtn = findViewById(R.id.backBtn);
    }
}
