package com.example.sajilothree.ActivitiesPackage.EditProfile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.ActivitiesPackage.CProfilePackage.CProfileActivity;
import com.example.sajilothree.ActivitiesPackage.EditProfile.AddNewDetailsForm.AddNewDetails;
import com.example.sajilothree.ActivitiesPackage.EditProfile.AddNewDetailsHolder.NewDetailsTypeHolder;
import com.example.sajilothree.ActivitiesPackage.SpProfilePackage.SpProfileActivity;
import com.example.sajilothree.AdapterPackages.EditProfileAdapters.EditAddressAdapter;
import com.example.sajilothree.AdapterPackages.EditProfileAdapters.EditEmailAdapter;
import com.example.sajilothree.AdapterPackages.EditProfileAdapters.EditContactAdapter;
import com.example.sajilothree.EncodeUserPackage.EncodeUser;
import com.example.sajilothree.ModelsPackage.RegisterNewUserModel.DistrictModelPackage.DistrictModel;
import com.example.sajilothree.ModelsPackage.UpdateProfile.UpdatableDetails.UpdateProfileDetailsModel;
import com.example.sajilothree.ModelsPackage.UpdateProfile.UpdateResponse.ProfileUpdateResponseModel;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.BasicUserDetailsHolder.BasicDetailsService;
import com.example.sajilothree.ServicesPackage.DistrictServicePackage.DistrictServices;
import com.example.sajilothree.ServicesPackage.UpdateProfile.UpdateProfileService;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;
import com.example.sajilothree.UserTypeHolder.UserTypes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {
    private TextView saveChanges, error;
    private ImageButton backBtn, addAddress, addContact, addEmail;
    private CircleImageView profileImage;
    private TextView addPhoto;
    private EditText name, bio;
    private RecyclerView addressView, contactsView, emailView;
    private Bitmap bitmap;
    private ImageView loading;
    private LinearLayout loadingParent;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_activity);
        userInterface();
//        This is when user cancels the update of profile
        onBackBtnClick();
//        This is when user confirms to update this her profile
        onSaveChangesClick();
        if (DistrictServices.districtModels.size() == 0) {
            AddressesAPICall();
        }
        if (UpdateProfileService.editProfileDetails.size() == 0) {
            EditableDetailsAPICall();
        } else {
            profileDetailsSeeding();
        }

        onAddPhotoClick();

        onAddAddressClick();
        onAddContactClick();
        onAddEmailClick();
    }

    private void onAddContactClick() {
        addContact.setOnClickListener(v -> {
            if (NewDetailsTypeHolder.setAddNew("Contact") && NewDetailsTypeHolder.addNewPosition(-1)) {
                startActivity(new Intent(this, AddNewDetails.class));
                overridePendingTransition(R.anim.bottom_up, R.anim.nothing);
            }
        });
    }

    private void onAddAddressClick() {
        addAddress.setOnClickListener(v -> {
            if (NewDetailsTypeHolder.setAddNew("Address") && NewDetailsTypeHolder.addNewPosition(-1)) {
                startActivity(new Intent(this, AddNewDetails.class));
                overridePendingTransition(R.anim.bottom_up, R.anim.nothing);
            }
        });
    }

    private void onAddEmailClick() {
        addEmail.setOnClickListener(v -> {
            if (NewDetailsTypeHolder.setAddNew("Email") && NewDetailsTypeHolder.addNewPosition(-1)) {
                startActivity(new Intent(this, AddNewDetails.class));
                overridePendingTransition(R.anim.bottom_up, R.anim.nothing);
            }
        });
    }

    private void onAddPhotoClick() {
        addPhoto.setOnClickListener(v -> {
            CropImage.activity().start(EditProfileActivity.this);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                assert result != null;
                Uri mImageUri = result.getUri();
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mImageUri);
                profileImage.setImageBitmap(bitmap);
                if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception e = result.getError();
                    Toast.makeText(this, "Possible error is: " + e, Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Exception: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    private String bitmapToString(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] imgBytes = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(imgBytes, Base64.DEFAULT);
        } else {
            return null;
        }
    }

    private void userInterface() {
        backBtn = findViewById(R.id.editBackBtn);
        saveChanges = findViewById(R.id.saveChanges);
        profileImage = findViewById(R.id.editProfileImage);
        name = findViewById(R.id.editFullName);
        addressView = findViewById(R.id.addressesView);
        contactsView = findViewById(R.id.contactsView);
        emailView = findViewById(R.id.emailsView);
        addPhoto = findViewById(R.id.editAddProfilePhoto);
        addAddress = findViewById(R.id.addNewAddress);
        addContact = findViewById(R.id.addNewContact);
        addEmail = findViewById(R.id.addNewEmail);
        loading = findViewById(R.id.loadingUpdate);
        loadingParent = findViewById(R.id.loadingParent);
        bio = findViewById(R.id.bio);
        error = findViewById(R.id.errorMsg);
    }

    private void EditableDetailsAPICall() {
        try {
            loadingParent.setVisibility(View.VISIBLE);
            AnimationDrawable animation = (AnimationDrawable) loading.getDrawable();
            animation.start();
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    BaseURL.getEditDetails + "?userId=" + Username.username,//"?userId=ODQ6cHJhY2hhbmdoYWxl", //+ Username.username,
                    response -> {
                        animation.stop();
                        loadingParent.setVisibility(View.GONE);
                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();
                        try {
                            UpdateProfileDetailsModel profileService = gson.fromJson(response, UpdateProfileDetailsModel.class);
                            if (UpdateProfileService.editProfileDetails.size() == 0) {
                                if (UpdateProfileService.addDetails(profileService)) {
                                    UpdateProfileService.addAllAddress(UpdateProfileService.editProfileDetails.get(0).getResult()
                                            .get(0).getAddresses());
                                    UpdateProfileService.addAllContact(UpdateProfileService.editProfileDetails.get(0).getResult()
                                            .get(0).getContacts());
                                    UpdateProfileService.addAllEmails(UpdateProfileService.editProfileDetails.get(0).getResult()
                                            .get(0).getEmails());
                                    profileDetailsSeeding();
                                }
                            } else {
                                profileDetailsSeeding();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(this, "Failed to parse: " + ex, Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        animation.stop();
                        loadingParent.setVisibility(View.GONE);
                        Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(this, "Exception: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    private void profileDetailsSeeding() {
        try {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.profile_placeholder);
            Glide.with(this)
                    .load(BaseURL.BaseURL + UpdateProfileService.editProfileDetails.get(0).getResult().get(0).getUserProfileImage())
                    .apply(options)
                    .into(profileImage);
            name.setText(UpdateProfileService.editProfileDetails.get(0).getResult().get(0).getFullName());

            EditAddressAdapter editAddressAdapter = new EditAddressAdapter(this,
                    UpdateProfileService.newAddresses);
            addressView.setLayoutManager(new LinearLayoutManager(this));
            editAddressAdapter.notifyDataSetChanged();
            editAddressAdapter.notifyItemChanged(NewDetailsTypeHolder.position);

            addressView.setAdapter(editAddressAdapter);

            contactsView.setLayoutManager(new LinearLayoutManager(this));
            EditContactAdapter editPhoneNumber = new EditContactAdapter(this, UpdateProfileService.newContacts);
            editAddressAdapter.notifyDataSetChanged();
            contactsView.setAdapter(editPhoneNumber);

            emailView.setLayoutManager(new LinearLayoutManager(this));
            EditEmailAdapter editEmailAdapter = new EditEmailAdapter(this, UpdateProfileService.newEmails);
            editAddressAdapter.notifyDataSetChanged();
            emailView.setAdapter(editEmailAdapter);
        } catch (Exception ex) {
            Toast.makeText(this, "Something went wrong: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean validateDetails(String name, String contact, String email) {
        if (name.isEmpty()) {
            return false;
        } else if (contact.isEmpty()) {
            return false;
        } else if (contact.length() < 10) {
            return false;
        } else if (contact.length() > 10) {
            return false;
        } else if (email.isEmpty()) {
            return false;
        } else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
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
                            DistrictServices.addDistrict(districtModel);
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onSaveChangesClick() {
        saveChanges.setOnClickListener(v -> {
            if (formValidations()) {
                saveChangesApiCall();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private boolean formValidations() {
        if (name.getText().toString().isEmpty()) {
            name.requestFocus();
            name.setError("Enter name");
            return false;
        } else if (UpdateProfileService.newAddresses.size() == 0) {
            error.requestFocus();
            error.setText("Addresses missing!");
            return false;
        } else if (UpdateProfileService.newContacts.size() == 0) {
            error.requestFocus();
            error.setText("Contact Missing!");
            return false;
        } else {
            return true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void saveChangesApiCall() {
        try {
            loadingParent.setVisibility(View.VISIBLE);
            AnimationDrawable animation = (AnimationDrawable) loading.getDrawable();
            animation.start();
            saveChanges.setVisibility(View.GONE);

            JSONArray jsonArrayAddress = new JSONArray();
            for (int i = 0; i < UpdateProfileService.newAddresses.size(); i++) {
                JSONObject jsonObjectAddress = new JSONObject();
                try {
                    jsonObjectAddress.put("AddressId", UpdateProfileService.newAddresses.get(i).getAddressId().toString());
                    jsonObjectAddress.put("DisctrictId", DistrictServices.getDistrictId(UpdateProfileService.newAddresses.get(i).getDistrictName()));
                    jsonObjectAddress.put("MunicipalityId", DistrictServices.getMunicipalityId(UpdateProfileService.newAddresses.get(i).getDistrictName(),
                            UpdateProfileService.newAddresses.get(i).getMunicipalityName()));
                    jsonObjectAddress.put("CurrentLocation", UpdateProfileService.newAddresses.get(i).getCurrentLocation());
                    jsonObjectAddress.put("AddressType", "Home");
                } catch (Exception ex) {
                    Toast.makeText(this, "Exception adding address: " + ex, Toast.LENGTH_SHORT).show();
                }
                jsonArrayAddress.put(jsonObjectAddress);
            }

            JSONArray jsonArrayContacts = new JSONArray();
            for (int i = 0; i < UpdateProfileService.newContacts.size(); i++) {
                JSONObject jsonObjectContacts = new JSONObject();
                try {
                    jsonObjectContacts.put("ContactId", UpdateProfileService.newContacts.get(i).getContactId());
                    jsonObjectContacts.put("ContactNumber", UpdateProfileService.newContacts.get(i).getContactNumber());
                    jsonObjectContacts.put("ContactType", "Mobile");
                } catch (Exception ex) {
                    Toast.makeText(this, "Contacts Exception: " + ex, Toast.LENGTH_SHORT).show();
                }
                jsonArrayContacts.put(jsonObjectContacts);
            }

            JSONArray jsonArrayEmails = new JSONArray();
            for (int i = 0; i < UpdateProfileService.newEmails.size(); i++) {
                JSONObject jsonObjectEmails = new JSONObject();
                try {
                    jsonObjectEmails.put("EmailId", UpdateProfileService.newEmails.get(i).getEmailId());
                    jsonObjectEmails.put("Email1", UpdateProfileService.newEmails.get(i).getEmail1());
                    jsonObjectEmails.put("EmailType", "Mail");
                } catch (Exception ex) {
                    Toast.makeText(this, "Emails Exception: " + ex, Toast.LENGTH_SHORT).show();
                }
                jsonArrayEmails.put(jsonObjectEmails);
            }

            JSONObject jsonObjectAll = new JSONObject();

            jsonObjectAll.put("FullName", name.getText().toString());
            jsonObjectAll.put("Gender", "Male");
            jsonObjectAll.put("UserProfileImage", bitmapToString(bitmap));
            jsonObjectAll.put("AboutUser", bio.getText().toString());
            jsonObjectAll.put("Contacts", jsonArrayContacts);
            jsonObjectAll.put("Emails", jsonArrayEmails);
            jsonObjectAll.put("Addresses", jsonArrayAddress);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.PUT,
                    BaseURL.updateProfile + "?userId=" + Username.username,// "?userId=ODQ6cHJhY2hhbmdoYWxl", //+ Username.username,
                    jsonObjectAll,
                    response -> {
                        saveChanges.setVisibility(View.VISIBLE);
                        animation.stop();
                        loadingParent.setVisibility(View.GONE);
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            ProfileUpdateResponseModel responseModel = gson.fromJson(String.valueOf(response), ProfileUpdateResponseModel.class);
                            if (responseModel.getSuccess()) {
                                Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                if (BasicDetailsService.userDetailsModels.get(0).getResult().get(0).getUserType()
                                        .equals(UserTypes.customer) &&
                                        BasicDetailsService.addUpdatedName(name.getText().toString())) {
                                    clearOldDetails();
                                    startActivity(new Intent(this, CProfileActivity.class));
                                    overridePendingTransition(R.anim.nav_default_pop_enter_anim, R.anim.nothing);
                                    finish();
                                } else if (BasicDetailsService.userDetailsModels.get(0).getResult().get(0).getUserType()
                                        .equals(UserTypes.sp) &&
                                        BasicDetailsService.addUpdatedName(name.getText().toString())) {
                                    clearOldDetails();
                                    startActivity(new Intent(this, SpProfileActivity.class));
                                    overridePendingTransition(R.anim.nav_default_pop_enter_anim, R.anim.nothing);
                                    finish();
                                }
                            } else {
                                Toast.makeText(this, "Failed try again!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(this, "Json Exception: " + ex, Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        animation.stop();
                        saveChanges.setVisibility(View.VISIBLE);
                        loadingParent.setVisibility(View.GONE);
                        Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
            );
            request.setRetryPolicy(new DefaultRetryPolicy(
                    900000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(this, "Exception: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    private void clearOldDetails() {
        try {
            UpdateProfileService.editProfileDetails.clear();
            UpdateProfileService.newEmails.clear();
            UpdateProfileService.newContacts.clear();
            UpdateProfileService.newAddresses.clear();
        } catch (Exception ex) {
            Toast.makeText(this, "Something went wrong: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        clearOldDetails();
//            onBackPressed();
        if (BasicDetailsService.userDetailsModels.get(0).getResult().get(0).getUserType()
                .equals(UserTypes.customer)) {
            startActivity(new Intent(this, CProfileActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, SpProfileActivity.class));
            finish();
        }
    }

    private void onBackBtnClick() {
        backBtn.setOnClickListener(v -> {
            clearOldDetails();
//            onBackPressed();
            if (BasicDetailsService.userDetailsModels.get(0).getResult().get(0).getUserType()
                    .equals(UserTypes.customer)) {
                startActivity(new Intent(this, CProfileActivity.class));
                finish();
            } else {
                startActivity(new Intent(this, SpProfileActivity.class));
                finish();
            }
        });
    }
}
