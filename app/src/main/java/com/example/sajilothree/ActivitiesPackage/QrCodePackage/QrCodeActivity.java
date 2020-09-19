package com.example.sajilothree.ActivitiesPackage.QrCodePackage;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.service.autofill.AutofillService;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.MainActivity;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;
import com.google.zxing.Result;
import com.r0adkll.slidr.Slidr;

import java.util.Objects;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private Menu menu;
    private ZXingScannerView mScannerView;
    private boolean flashState = false;
    private Button myQrCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_code_activity_layout);

        Toolbar toolbar = findViewById(R.id.qrCodeToolbar);
        setSupportActionBar(toolbar);
        userInterface();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
        Slidr.attach(this);

        ActivityCompat.requestPermissions(QrCodeActivity.this,
                new String[]{Manifest.permission.CAMERA},
                1);

        ViewGroup qrFrame = findViewById(R.id.qr_code);
        mScannerView = new ZXingScannerView(this);
        qrFrame.addView(mScannerView);
        onMyQrCodeClick();
    }

    private void onMyQrCodeClick() {
        myQrCode.setOnClickListener(v -> {
            startActivity(new Intent(QrCodeActivity.this, MyQrCode.class));
            overridePendingTransition(R.anim.bottom_up, R.anim.nothing);
        });
    }

    private void userInterface() {
        myQrCode = findViewById(R.id.myQrCodeBtn);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(QrCodeActivity.this, MainActivity.class));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        String username = result.getText();
        try {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            builder.setTitle("Complete Booking");
            builder.setMessage("You want to make booking as completed?");
            builder.setPositiveButton("Yes", (dialog, which) -> completeAPICall(username));

            AlertDialog alert = builder.create();
            alert.show();
        } catch (Exception ex) {
            Toast.makeText(this, "Something Wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    private void completeAPICall(String specialistToken) {
        try {
            StringRequest request = new StringRequest(Request.Method.PUT,
                    BaseURL.completeBookingByQR + Username.username + "&specialistToken=" + specialistToken,
                    response -> onBackPressed(),
                    error -> Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show());
            Volley.newRequestQueue(this).add(request);
        } catch (Exception ex) {
            Log.d("", Objects.requireNonNull(ex.getMessage()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.flashlight, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (!flashState) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_flash_off_black_24dp));
            Toast.makeText(getApplicationContext(), "Flashlight turned on", Toast.LENGTH_SHORT).show();
            flashState = true;
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_flash_on_black_24dp));
        } else {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_flash_on_black_24dp));
            Toast.makeText(getApplicationContext(), "Flashlight turned off", Toast.LENGTH_SHORT).show();
            mScannerView.setFlash(false);
            flashState = false;
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_flash_off_black_24dp));
        }
        return super.onOptionsItemSelected(item);
    }
}
