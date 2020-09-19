package com.example.sajilothree.ActivitiesPackage.EditProfile.AddNewDetailsForm;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.ActivitiesPackage.EditProfile.AddNewDetailsHolder.NewDetailsTypeHolder;
import com.example.sajilothree.ActivitiesPackage.EditProfile.EditProfileActivity;
import com.example.sajilothree.ModelsPackage.RegisterNewUserModel.DistrictModelPackage.DistrictModel;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.DistrictServicePackage.DistrictServices;
import com.example.sajilothree.ServicesPackage.UpdateProfile.UpdateProfileService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.r0adkll.slidr.Slidr;

import java.util.Objects;

public class AddNewDetails extends AppCompatActivity {
    private Spinner districts, municipalities;
    private EditText currentLocation, phoneNumber, email;
    private ScrollView addressParent, contactParent, emailParent;
    private Button addAddress, addEmail, addContact;
    private ImageView backBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_details_layout);
        Slidr.attach(this);
        userInterface();
        try {
            setUpVisibility();
        } catch (Exception ex) {
            Log.d("ADDNEWONE", Objects.requireNonNull(ex.getMessage()));
        }

        if (DistrictServices.districtModels.size() != 0) {
            setDistricts();
        } else {
            AddressesAPICall();
        }

        onBackClick();

        onSelectedDistrictChange();

        onAddAddressClick();
        onAddContactClick();
        onAddEmailClick();

        try {
            settingCurrentLocation();
            settingUpPhoneNumber();
            settingUpEmail();
        } catch (Exception ex) {
            Log.d("ADDNEW", Objects.requireNonNull(ex.getMessage()));
        }

    }

    private void onBackClick() {
        backBtn.setOnClickListener(v -> onBackPressed());
    }

    private void userInterface() {
        districts = findViewById(R.id.district);
        municipalities = findViewById(R.id.municipality);
        currentLocation = findViewById(R.id.currentLocation);
        addressParent = findViewById(R.id.addressParent);
        contactParent = findViewById(R.id.contactsParent);
        emailParent = findViewById(R.id.emailsParent);
        addAddress = findViewById(R.id.addAddress);
        addEmail = findViewById(R.id.addEmail);
        addContact = findViewById(R.id.addContact);
        phoneNumber = findViewById(R.id.phoneNumber);
        email = findViewById(R.id.email);
        backBtn = findViewById(R.id.back);
    }

    private void settingUpEmail() {
        if (NewDetailsTypeHolder.position >= 0 && NewDetailsTypeHolder.addNew.equals("Email")) {
            email.setText(UpdateProfileService.newEmails.get(NewDetailsTypeHolder.position).getEmail1());
        } else {
            email.setText("");
        }
    }

    private void settingUpPhoneNumber() {
        if (NewDetailsTypeHolder.position >= 0 && NewDetailsTypeHolder.addNew.equals("Contact")) {
            phoneNumber.setText(UpdateProfileService.newContacts.get(NewDetailsTypeHolder.position).getContactNumber());
        } else {
            phoneNumber.setText("");
        }
    }

    private void settingCurrentLocation() {
        if (NewDetailsTypeHolder.position >= 0 && NewDetailsTypeHolder.addNew.equals("Address")) {
            currentLocation.setText(UpdateProfileService.newAddresses.get(NewDetailsTypeHolder.position).getCurrentLocation());
        } else {
            currentLocation.setText("");
        }
    }

    private void setUpVisibility() {
        if (NewDetailsTypeHolder.addNew.equals("Address")) {
            addressParent.setVisibility(View.VISIBLE);
            contactParent.setVisibility(View.GONE);
            emailParent.setVisibility(View.GONE);
        } else if (NewDetailsTypeHolder.addNew.equals("Contact")) {
            addressParent.setVisibility(View.GONE);
            contactParent.setVisibility(View.VISIBLE);
            emailParent.setVisibility(View.GONE);
        } else {
            addressParent.setVisibility(View.GONE);
            contactParent.setVisibility(View.GONE);
            emailParent.setVisibility(View.VISIBLE);
        }
    }

    private void onAddAddressClick() {
        addAddress.setOnClickListener(v -> {
            if (validateAddressForm()) {
                if (UpdateProfileService.addAddress(NewDetailsTypeHolder.position,
                        districts.getSelectedItem().toString(), municipalities.getSelectedItem().toString(),
                        currentLocation.getText().toString())) {
                    /*startActivity(new Intent(this, EditProfileActivity.class));
                    overridePendingTransition(0, 0);*/
                    onBackPressed();
                }
            }
        });
    }

    private boolean validateAddressForm() {
        if (districts.getSelectedItem().toString().equals("Select District*")) {
            Toast.makeText(this, "Select District!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (municipalities.getSelectedItem().toString().equals("Select Municipality*")) {
            Toast.makeText(this, "Select Municipality!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (currentLocation.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter current location!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void onAddContactClick() {
        addContact.setOnClickListener(v -> {
            if (contactValidation()) {
                if (UpdateProfileService.addContact(NewDetailsTypeHolder.position, phoneNumber.getText().toString())) {
                   /* startActivity(new Intent(this, EditProfileActivity.class));
                    overridePendingTransition(0, 0);*/
                    onBackPressed();
                }
            }
        });
    }

    private boolean contactValidation() {
        if (phoneNumber.getText().toString().isEmpty()) {
            phoneNumber.setError("Enter phone number!");
            return false;
        } else {
            return true;
        }
    }

    private void onAddEmailClick() {
        addEmail.setOnClickListener(v -> {
            if (emailValidation()) {
                if (UpdateProfileService.addEmails(NewDetailsTypeHolder.position, email.getText().toString())) {
                    /*startActivity(new Intent(this, EditProfileActivity.class));
                    overridePendingTransition(0, 0);*/
                    onBackPressed();
                }
            }
        });
    }

    private boolean emailValidation() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.getText().toString().isEmpty()) {
            email.setError("Enter email!");
            return false;
        } else if (!email.getText().toString().trim().matches(emailPattern)) {
            email.setError("Invalid format!");
            return false;
        } else {
            return true;
        }
    }

    private void AddressesAPICall() {
        try {
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    BaseURL.getAddresses,
                    response -> {
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            DistrictModel districtModel = gson.fromJson(response, DistrictModel.class);
                            if (DistrictServices.addDistrict(districtModel)) {
                                setDistricts();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(this, "Something went wrong " + ex, Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(this, "Exception " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    private void setDistricts() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, DistrictServices.filterDistricts()) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        districts.setAdapter(adapter);
        if (NewDetailsTypeHolder.position >= 0) {
            int spinnerPosition = adapter.getPosition(UpdateProfileService.newAddresses
                    .get(NewDetailsTypeHolder.position).getDistrictName());
            districts.setSelection(spinnerPosition);
        }
        setMunicipalities(districts.getSelectedItem().toString());
    }

    private void setMunicipalities(String selectedDistrict) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                DistrictServices.getMunicipalityName(selectedDistrict)) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        municipalities.setAdapter(adapter);
        if (NewDetailsTypeHolder.position >= 0) {
            int spinnerPosition = adapter.getPosition(UpdateProfileService.newAddresses
                    .get(NewDetailsTypeHolder.position).getMunicipalityName());
            municipalities.setSelection(spinnerPosition);
        }
    }

    private void onSelectedDistrictChange() {
        districts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setMunicipalities(districts.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setMunicipalities("Kathmandu");
            }
        });
    }
}
