package com.example.sajilothree.ActivitiesPackage.SettingPackage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sajilothree.ActivitiesPackage.SettingPackage.EnterOldPassword.ValidateOldPassword;
import com.example.sajilothree.ActivitiesPackage.SettingPackage.SwitchProfile.SwitchProfileActivity;
import com.example.sajilothree.R;
import com.r0adkll.slidr.Slidr;

import java.util.Locale;
import java.util.Objects;

public class Setting extends AppCompatActivity {
    private RelativeLayout changeLanguage;
    private TextView selectedLanguage;
    private LinearLayout changePassword, switchProfile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.setting_layout);
        userInterface();

        Toolbar toolbar = findViewById(R.id.settingToolbar);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
        Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.setting));
        Slidr.attach(this);
        onLanguageChange();

        setSelectedLanguage();
        onChangePasswordClick();
        onSwitchProfileClick();
    }

    private void onSwitchProfileClick() {
        switchProfile.setOnClickListener(v -> {
            startActivity(new Intent(this, SwitchProfileActivity.class));
            overridePendingTransition(R.anim.nav_default_pop_enter_anim, R.anim.nothing);
        });
    }

    private void onChangePasswordClick() {
        changePassword.setOnClickListener(v -> {
            startActivity(new Intent(this, ValidateOldPassword.class));
            overridePendingTransition(R.anim.nav_default_pop_enter_anim, R.anim.nothing);
        });
    }

    @SuppressLint("SetTextI18n")
    private void setSelectedLanguage() {
        SharedPreferences pref = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = pref.getString("My_Lang", "");
        if (language.equals("en")) {
            selectedLanguage.setText("English");
        } else if (language.equals("ne")) {
            selectedLanguage.setText("नेपाली");
        } else {
            selectedLanguage.setText("English");
        }
    }

    private void onLanguageChange() {
        changeLanguage.setOnClickListener(v -> showLanguageChangeDialog());
    }

    private void userInterface() {
        selectedLanguage = findViewById(R.id.language);
        changeLanguage = findViewById(R.id.changeLanguage);
        changePassword = findViewById(R.id.changePassword);
        switchProfile = findViewById(R.id.switchProfile);
    }

    private void showLanguageChangeDialog() {
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Setting.this,
                android.R.layout.simple_list_item_1);
        arrayAdapter.add("English");
        arrayAdapter.add("नेपाली");

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Setting.this);
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.setTitle(R.string.change_language_here);
        builder.setIcon(R.drawable.ic_language_black_24dp);

        builder.setAdapter(arrayAdapter, (dialog, item) -> {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
                Toast.makeText(this, "Action can't be performed!", Toast.LENGTH_SHORT).show();
            } else {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(Setting.this);
                builder1.setNegativeButton("Cancel", (dialog1, which) -> dialog.dismiss());
                builder1.setTitle("Alert!");
                builder1.setMessage("App will restart to change the language successfully!");
                builder1.setPositiveButton("Ok", (dialog1, which) -> {
                    if (item == 0) {
                        setLocate("en");
                        recreate();
                        restartActivity();
                    } else {
                        setLocate("ne");
                        recreate();
                        restartActivity();
                    }
                });
                android.app.AlertDialog alert = builder1.create();
                alert.show();
                dialog.dismiss();
            }
        });
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    private void restartActivity() {
        Intent i = getBaseContext().getPackageManager().
                getLaunchIntentForPackage(getBaseContext().getPackageName());
        assert i != null;
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        overridePendingTransition(0, 0);
        finish();
    }

    private void setLocate(String lang) {
        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        Configuration config = new Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    public void loadLocale() {
        SharedPreferences pref = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = pref.getString("My_Lang", "");
        setLocate(language);
    }
    //    We can lock and unlock slide from slider.lock or unlock
}
