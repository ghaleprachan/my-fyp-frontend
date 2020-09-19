package com.example.sajilothree.ActivitiesPackage.RegisterActivityPackage.RegisterForm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.CustomViewPackage.BounceScrollView;
import com.example.sajilothree.MainActivity;
import com.example.sajilothree.ModelsPackage.RegisterNewUserModel.DistrictModelPackage.DistrictModel;
import com.example.sajilothree.ModelsPackage.RegisterNewUserModel.RegisterprofessionModel.RegisterProfessionModel;
import com.example.sajilothree.ModelsPackage.UserRegistrationResponse.UserRegistrationResponseModel;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.DistrictServicePackage.DistrictServices;
import com.example.sajilothree.ServicesPackage.RegisterNewUserServices.RegisterNewUserServices;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.r0adkll.slidr.Slidr;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterForm extends AppCompatActivity {
    private ImageView backButton, addProfession;
    private BounceScrollView bounceOne, bounceTwo;
    private Spinner gender, districts, municipalities;
    private TextView back, next, addPhoto;
    private Button createAccount;
    private EditText fullName, emailId,
            currentLocation, districtError, municipalityError, username, password, confirmPassword;
    private EditText mItemSelected;

    private LinearLayout selectedProfessionLayout;
    private View selectedProfessionShadow;

    private Bitmap bitmap;
    private CircleImageView profileImage;

    private ImageView progress;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_register_form_layout);
        userInterface();
        onBackClicked();
        setGenders();
        fullName.requestFocus();
        AddressesAPICall();
        onSelectedDistrictChange();
        onNextFormClick();
        onCreateAccountClick();
        onCreateAccount();

        GetProfessionApiCall();
        onAddProfessionClick();
        onAddImageClick();
    }

    private void setUpVisibility() {
        if (RegisterNewUserServices.UserType.equals("Service Provider")) {
            selectedProfessionLayout.setVisibility(View.VISIBLE);
            selectedProfessionShadow.setVisibility(View.VISIBLE);
        } else if (RegisterNewUserServices.UserType.equals("Customer")) {
            selectedProfessionLayout.setVisibility(View.GONE);
            selectedProfessionShadow.setVisibility(View.GONE);
        } else {
            selectedProfessionShadow.setVisibility(View.GONE);
            selectedProfessionLayout.setVisibility(View.GONE);
        }
    }

    public static boolean formValidation(String name, String email, String phoneNumber,
                                         String address, String profession, String username, String password) {
        if (name.isEmpty()) {
            return false;
        } else if (email.isEmpty()) {
            return false;
        } else if (phoneNumber.isEmpty()) {
            return false;
        } else if (address.isEmpty()) {
            return false;
        } else if (profession.isEmpty()) {
            return false;
        } else if (username.isEmpty()) {
            return false;
        } else if (password.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean validateUser(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            return false;
        } else {
            return true;
        }
    }

    //    Lets create a method, which will be called when press the textview
    private void onAddImageClick() {
        addPhoto.setOnClickListener(v -> {
            CropImage.activity().start(RegisterForm.this);
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

    String[] listItems; // This is all the list of professions that wil come from api
    boolean[] checkedItems; // This is the list of selected items
    ArrayList<Integer> mUserItems = new ArrayList<>(); // this is the position for selected items
//    The mUser can be send to the service class which can be used to filter the selected professions

    ArrayList<String> selectedProfessionName = new ArrayList<>();

    private void onAddProfessionClick() {
        addProfession.setOnClickListener(v -> {
            try {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(RegisterForm.this);
                mBuilder.setTitle("Select your professions");
                mBuilder.setIcon(R.drawable.ic_play_for_work_black_24dp);
                mBuilder.setMultiChoiceItems(listItems, checkedItems, (dialogInterface, position, isChecked) -> {
                    if (isChecked) {
                        mUserItems.add(position);
                    } else {
                        mUserItems.remove((Integer.valueOf(position)));
                    }
                });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", (dialogInterface, which) -> {
                    StringBuilder item = new StringBuilder();
                    for (int i = 0; i < mUserItems.size(); i++) {
                        item.append(listItems[mUserItems.get(i)]);
                        if (i != mUserItems.size() - 1) {
                            item.append(", ");
                        }
                    }
                    mItemSelected.setText(item.toString());
                });
                mBuilder.setNegativeButton("Dismiss", (dialogInterface, i) -> dialogInterface.dismiss());
                mBuilder.setNeutralButton("Clear", (dialogInterface, which) -> {
                    for (int i = 0; i < checkedItems.length; i++) {
                        checkedItems[i] = false;
                        mUserItems.clear();
                        mItemSelected.setText("");
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            } catch (Exception ex) {
                Toast.makeText(this, "Exception : " + ex, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void GetProfessionApiCall() {
        try {
            selectedProfessionLayout.setVisibility(View.GONE);
            selectedProfessionShadow.setVisibility(View.GONE);
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    BaseURL.getProfessions,
                    response -> {
                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();
                        RegisterProfessionModel professionModel = gson.fromJson(response, RegisterProfessionModel.class);
                        if (professionModel.getSuccess()) {
                            if (RegisterNewUserServices.setUserProfession(professionModel)) {
                                /*selectedProfessionLayout.setVisibility(View.VISIBLE);
                                selectedProfessionShadow.setVisibility(View.VISIBLE);*/
                                setUpVisibility();
                                listItems = RegisterNewUserServices.professionList();
                                checkedItems = new boolean[listItems.length];
                            }
                        }
                    },
                    error -> {
                        Toast.makeText(this, "Error : " + error, Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(this, "Exception : " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    //Thi is for form validation
    private boolean formValidation() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (RegisterNewUserServices.UserType.equals("Customer")) {
            if (fullName.getText().toString().isEmpty()) {
                fullName.setError("Please enter your name!");
                fullName.requestFocus();
                return false;
            } else if (!emailId.getText().toString().isEmpty() &&
                    !emailId.getText().toString().trim().matches(emailPattern)) {
                emailId.setError("Invalid email format!");
                emailId.requestFocus();
                fullName.setError(null);
                return false;
            } else if (districts.getSelectedItem().toString().equals("Select District*")) {
                districtError.setError("Please select district!");
                districtError.requestFocus();
                emailId.setError(null);
                return false;
            } else if (municipalities.getSelectedItem().toString().equals("Select Municipality")) {
                municipalityError.setError("Please select municipality!");
                municipalityError.requestFocus();
                districtError.setError(null);
                return false;
            } else if (currentLocation.getText().toString().isEmpty()) {
                currentLocation.setError("Please enter your current location!");
                currentLocation.requestFocus();
                municipalityError.setError(null);
                return false;
            } else {
                currentLocation.setError(null);
                return true;
            }
        } else {
            if (fullName.getText().toString().isEmpty()) {
                fullName.setError("Please enter your name!");
                fullName.requestFocus();
                return false;
            } else if (!emailId.getText().toString().isEmpty() &&
                    !emailId.getText().toString().trim().matches(emailPattern)) {
                emailId.setError("Invalid email format!");
                emailId.requestFocus();
                fullName.setError(null);
                return false;
            } else if (districts.getSelectedItem().toString().equals("Select District")) {
                districtError.setError("Please select district!");
                districtError.requestFocus();
                return false;
            } else if (municipalities.getSelectedItem().toString().equals("Select Municipality")) {
                municipalityError.setError("Please select municipality!");
                municipalityError.requestFocus();
                districtError.setError(null);
                return false;
            } else if (currentLocation.getText().toString().isEmpty()) {
                currentLocation.setError("Please enter your current location!");
                currentLocation.requestFocus();
                municipalityError.setError(null);
                return false;
            } else if (mItemSelected.getText().toString().isEmpty()) {
                mItemSelected.setError("Select profession!");
                mItemSelected.requestFocus();
                currentLocation.setError(null);
                return false;
            } else {
                mItemSelected.setError(null);
                return true;
            }
        }

    }

    private void onCreateAccount() {
        createAccount.setOnClickListener(v -> {
            if (validateFormTwo()) {
                RegisterUserApi();
            }
        });
    }

    // validating username password and other
    @SuppressLint("SetTextI18n")
    private boolean validateFormTwo() {
        if (username.getText().toString().isEmpty() || username.getText().toString().contains(":")
                || username.getText().toString().contains(" ") || username.getText().toString().length() < 8) {
            username.setError("Please enter username!");
            return false;
        } else if (password.getText().toString().isEmpty() || password.getText().toString().length() < 8) {
            password.setError("Please enter password!");
            return false;
        } else if (confirmPassword.getText().toString().isEmpty()) {
            confirmPassword.setError("Please enter confirm password!");
            return false;
        } else if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
            confirmPassword.setError("Confirm password do not match with password!");
            return false;
        } else if (username.getText().toString().contains(":")) {
            username.setError("Username can't have : !");
            return false;
        } else {
            return true;
        }
    }

    // posting the user details into the database to register them
    private void RegisterUserApi() {
        try {
            AnimationDrawable animation = (AnimationDrawable) progress.getDrawable();
            animation.start();
            progress.setVisibility(View.VISIBLE);
            createAccount.setVisibility(View.GONE);
            for (int i = 0; i < mUserItems.size(); i++) {
                selectedProfessionName.add(listItems[mUserItems.get(i)]);
            }

            JSONArray jsonProfessions = new JSONArray();
            ArrayList<Integer> finalSelectedProfessionId = new ArrayList<>(RegisterNewUserServices.getProfessionId(mUserItems));

            for (int i = 0; i < finalSelectedProfessionId.size(); i++) {
                JSONObject jsonObjectProfession = new JSONObject();
                try {
                    jsonObjectProfession.put("ProfessionId", finalSelectedProfessionId.get(i).toString());
                } catch (Exception ex) {
                    Toast.makeText(this, "Ex " + ex, Toast.LENGTH_SHORT).show();
                }
                jsonProfessions.put(jsonObjectProfession);
            }
            JSONObject jsonObjectAddresses = new JSONObject();
            jsonObjectAddresses.put("DisctrictId", DistrictServices.getDistrictId(districts.getSelectedItem().toString()));
            jsonObjectAddresses.put("MunicipalityId", DistrictServices.getMunicipalityId(districts.getSelectedItem().toString(),
                    municipalities.getSelectedItem().toString()));
            jsonObjectAddresses.put("CurrentLocation", currentLocation.getText().toString());
            jsonObjectAddresses.put("AddressType", "Home");

            JSONObject jsonObjectContact = new JSONObject();
            jsonObjectContact.put("ContactNumber", RegisterNewUserServices.contactNumber);
            jsonObjectContact.put("ContactType", "Mobile");

            JSONObject jsonObjectEmails = new JSONObject();
            jsonObjectEmails.put("Email1", emailId.getText().toString());
            jsonObjectEmails.put("EmailType", "Personal");

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("UserName", username.getText().toString());
            jsonObject.put("Password", password.getText().toString());
            jsonObject.put("FullName", fullName.getText().toString());
            jsonObject.put("Gender", gender.getSelectedItem().toString());
            jsonObject.put("UserType", RegisterNewUserServices.UserType);
            jsonObject.put("UserProfileImage", bitmapToString(bitmap));
            jsonObject.put("Addresses", jsonObjectAddresses);
            jsonObject.put("Contacts", jsonObjectContact);
            jsonObject.put("Emails", jsonObjectEmails);
            jsonObject.put("UserProfession", jsonProfessions);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    BaseURL.registerNewUser,
                    jsonObject,
                    response -> {
                        try {
                            animation.stop();
                            progress.setVisibility(View.GONE);
                            createAccount.setVisibility(View.VISIBLE);
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            UserRegistrationResponseModel responseModel = gson.fromJson(String.valueOf(response), UserRegistrationResponseModel.class);
                            Toast.makeText(this, "Account Created!", Toast.LENGTH_SHORT).show();
                            if (responseModel.getSuccess()) {
                                if (Username.setUsername(responseModel.getTokenNumber()) &&
                                        Username.setUserType(RegisterNewUserServices.UserType)) {
                                    startActivity(new Intent(this, MainActivity.class));
                                    overridePendingTransition(R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim);
                                }
                            } else {
                                Toast.makeText(this, "Failed to post: " + responseModel.getTokenNumber(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            animation.stop();
                            progress.setVisibility(View.GONE);
                            createAccount.setVisibility(View.VISIBLE);
                            Toast.makeText(this, "Exception Here: " + ex, Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        animation.stop();
                        progress.setVisibility(View.GONE);
                        createAccount.setVisibility(View.VISIBLE);
                        Toast.makeText(this, "Error posting user: " + error, Toast.LENGTH_SHORT).show();
                    }
            );
            request.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 500000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 500000;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        } catch (Exception ex) {
            System.out.println("Exception is: " + ex);
            Toast.makeText(this, "Exception: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    private void onNextFormClick() {
        next.setOnClickListener(v -> {
            if (formValidation()) {
                username.requestFocus();
                bounceOne.setVisibility(View.GONE);
                next.setVisibility(View.GONE);
                back.setVisibility(View.VISIBLE);
                bounceTwo.setVisibility(View.VISIBLE);
                Toast.makeText(this, Integer.toString(DistrictServices.getMunicipalityId(districts.getSelectedItem().toString(),
                        municipalities.getSelectedItem().toString())), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onCreateAccountClick() {
        back.setOnClickListener(v -> {
            bounceTwo.setVisibility(View.GONE);
            bounceOne.setVisibility(View.VISIBLE);
            next.setVisibility(View.VISIBLE);
            back.setVisibility(View.GONE);
        });
    }

    private void AddressesAPICall() {
        try {
            back.setVisibility(View.GONE);
            bounceOne.setVisibility(View.GONE);
            bounceTwo.setVisibility(View.GONE);
            next.setVisibility(View.GONE);
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    BaseURL.getAddresses,
                    response -> {
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            DistrictModel districtModel = gson.fromJson(response, DistrictModel.class);
                            if (DistrictServices.addDistrict(districtModel)) {
                                bounceOne.setVisibility(View.VISIBLE);
                                next.setVisibility(View.VISIBLE);
                                back.setVisibility(View.GONE);
                                bounceTwo.setVisibility(View.GONE);
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

    private void setGenders() {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.gender, R.layout.spinner_item_text);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(arrayAdapter);
    }

    private void onBackClicked() {
        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void userInterface() {
        backButton = findViewById(R.id.backBtnCustomerForm);
        bounceOne = findViewById(R.id.bounceOne);
        bounceTwo = findViewById(R.id.bounceTwo);
        gender = findViewById(R.id.gender);
        districts = findViewById(R.id.city);
        municipalities = findViewById(R.id.municipality);
        back = findViewById(R.id.back);
        next = findViewById(R.id.nextForm);
        createAccount = findViewById(R.id.createAccount);
        fullName = findViewById(R.id.fullName);
        emailId = findViewById(R.id.emailId);
        currentLocation = findViewById(R.id.preciseLocation);
        districtError = findViewById(R.id.districtError);
        municipalityError = findViewById(R.id.municipalityError);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        mItemSelected = findViewById(R.id.selectedProfession);
        selectedProfessionLayout = findViewById(R.id.selectProfessionParent);
        selectedProfessionShadow = findViewById(R.id.selectProfessionShadow);
        addProfession = findViewById(R.id.addProfession);
        addPhoto = findViewById(R.id.addImage);
        profileImage = findViewById(R.id.profileImage);
        progress = findViewById(R.id.progressBar);
    }
}