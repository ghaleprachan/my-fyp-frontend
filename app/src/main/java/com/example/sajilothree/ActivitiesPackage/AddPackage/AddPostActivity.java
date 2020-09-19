package com.example.sajilothree.ActivitiesPackage.AddPackage;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.ModelsPackage.PostResponseModels.PostOfferResponseModel;
import com.example.sajilothree.ModelsPackage.PostResponseModels.PostProblemResponseModel;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.BasicUserDetailsHolder.BasicDetailsService;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;
import com.example.sajilothree.UserTypeHolder.UserTypes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Objects;


public class AddPostActivity extends AppCompatActivity {
    private ImageButton addBackBtn;
    private Spinner myPostType;
    private LinearLayout offerValidDate;
    private CardView spinnerCardView;

    private static final String TAG = "AddPostActivity";
    private EditText selectedDate, postDescription;
    private TextView confirmPost, hideTitle;
    private ImageView uploadImage;
    private Button uploadImageBtn;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    //    private final int IMG_REQUEST = 1;
    private Bitmap bitmap;
    private LinearLayout loadingParent;
    private ImageView loadingAnimation;
    private RelativeLayout customToolbar;
    private ScrollView bodyContent;
    private AnimationDrawable animationDrawable;
    private RelativeLayout parentLayout;

    private SlidrInterface slidrInterface;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post_activity_layout);
        slidrInterface = Slidr.attach(this);
        userInterface();
        hideUnnecessarilyViews();

        addDataOnSpinner();
        onBackClick();
        onSpinnerSelectionChange();
        onSelectDateClick();
        onDateSelected();
        onConfirmPostClick();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            onUploadImageClick();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void onUploadImageClick() {
        uploadImageBtn.setOnClickListener(v1 -> {
            selectImage();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void selectImage() {
        try {
            CropImage.activity().start(AddPostActivity.this);
        } catch (Exception ex) {
            Toast.makeText(this, "Failed: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
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
                uploadImage.setImageBitmap(bitmap);
                uploadImage.setVisibility(View.VISIBLE);

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

    public static boolean postFormValidation(String aboutPost) {
        return !aboutPost.isEmpty();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onConfirmPostClick() {
        confirmPost.setOnClickListener(v -> {
            if (postValidation()) {
                hideKeyboard();
                try {
                    if (myPostType.getSelectedItem().toString().equalsIgnoreCase("Post Offer") ||
                            myPostType.getSelectedItem().toString().equalsIgnoreCase("प्रस्ताव थप्नुहोस्")) {
                        postOfferAPI();
                    } else {
                        postProblemsAPI();
                    }
                } catch (Exception ex) {
                    System.out.println("Error " + ex);
                }
            } else {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(parentLayout.getWindowToken(), 0);
    }

    private void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.showSoftInput(postDescription, InputMethodManager.SHOW_FORCED);
    }

    private void showLoading() {
        loadingParent.setVisibility(View.VISIBLE);
        loadingAnimation.setVisibility(View.VISIBLE);
        animationDrawable = (AnimationDrawable) loadingAnimation.getDrawable();
        animationDrawable.start();
        customToolbar.setVisibility(View.GONE);
        bodyContent.setVisibility(View.GONE);
        slidrInterface.lock();
    }

    private void hideLoading() {
        loadingParent.setVisibility(View.GONE);
        animationDrawable.stop();
        customToolbar.setVisibility(View.VISIBLE);
        bodyContent.setVisibility(View.VISIBLE);
        slidrInterface.unlock();
    }

    private void postProblemsAPI() {
        try {
            showLoading();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ProblemDescription", postDescription.getText().toString());
            jsonObject.put("ProblemImage", bitmapToString(bitmap));
            jsonObject.put("PostedById", Username.username);
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    BaseURL.BaseURL + BaseURL.postProblems,
                    jsonObject,
                    response -> {
                        hideLoading();
                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();
                        PostProblemResponseModel responseModel = gson.fromJson(String.valueOf(response),
                                PostProblemResponseModel.class);
                        if (responseModel.getSuccess()) {
                            Toast.makeText(this, "Success " + response.toString(), Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } else {
                            showKeyboard();
                            Toast.makeText(this, "Something went wrong. Please try again!", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        showKeyboard();
                        hideLoading();
                        Toast.makeText(this, "Error " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
            );
            request.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 600000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 600000;
                }

                @Override
                public void retry(VolleyError error) {

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(this, "Exception" + ex, Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void postOfferAPI() {
        try {
            showLoading();
            JSONObject addPostData = new JSONObject();
            addPostData.put("OfferDescription", postDescription.getText().toString());
            addPostData.put("OfferImage", bitmapToString(bitmap));
            addPostData.put("OfferValidDate", selectedDate.getText().toString());
            addPostData.put("PostedById", Username.username);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    BaseURL.BaseURL + BaseURL.postOffers,
                    addPostData,
                    response -> {
                        hideLoading();
                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();
                        PostOfferResponseModel responseModel = gson.fromJson(String.valueOf(response),
                                PostOfferResponseModel.class);
                        if (responseModel.getSuccess()) {
                            Toast.makeText(this, "Success" + response, Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } else {
                            showKeyboard();
                            Toast.makeText(this, "Something went wrong. Please try again!", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        showKeyboard();
                        hideLoading();
                        Toast.makeText(this, "Error " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
            );
            request.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 600000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 600000;
                }

                @Override
                public void retry(VolleyError error) {

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean postValidation() {
        if (myPostType.getSelectedItem().toString().equalsIgnoreCase("Post Offer") ||
                myPostType.getSelectedItem().toString().equalsIgnoreCase("प्रस्ताव थप्नुहोस्")) {
            if (selectedDate.getText().toString().isEmpty()) {
                selectedDate.setError("Please Valid Date!");
                return false;
            } else if (postDescription.getText().toString().isEmpty()) {
                postDescription.setError("Add post description!");
                return false;
            } else {
                return true;
            }
        } else if (myPostType.getSelectedItem().toString().equalsIgnoreCase("Post Problem") ||
                myPostType.getSelectedItem().toString().equalsIgnoreCase("समस्या जोड्नुहोस्")) {
            if (postDescription.getText().toString().isEmpty()) {
                postDescription.setError("Add post description!");
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    private void hideUnnecessarilyViews() {
        try {
            if (BasicDetailsService.userDetailsModels.get(0)
                    .getResult().get(0).getUserType().equalsIgnoreCase(UserTypes.customer)) {
                myPostType.setVisibility(View.GONE);
                spinnerCardView.setVisibility(View.GONE);
                selectedDate.setVisibility(View.GONE);
                hideTitle.setVisibility(View.VISIBLE);
            } else {
                spinnerCardView.setVisibility(View.VISIBLE);
                myPostType.setVisibility(View.VISIBLE);
                selectedDate.setVisibility(View.VISIBLE);
                hideTitle.setVisibility(View.GONE);
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    private void onSelectDateClick() {
        selectedDate.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog pickerDialog = new DatePickerDialog(
                    AddPostActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    mDateSetListener,
                    year, month, day);
            pickerDialog.setTitle("SELECT DATE");

            Objects.requireNonNull(pickerDialog.getWindow()).setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT)
            );
            pickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            pickerDialog.show();
        });
    }

    private void onDateSelected() {
        mDateSetListener = (view, year, month, dayOfMonth) -> {
            month = month + 1;
            Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + dayOfMonth + "/" + year);
            String date = month + "/" + dayOfMonth + "/" + year;
            selectedDate.setText(date);
        };
    }


    private void onSpinnerSelectionChange() {
        myPostType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (myPostType.getSelectedItem().toString().equalsIgnoreCase("Post Problem") ||
                        myPostType.getSelectedItem().toString().equalsIgnoreCase("समस्या जोड्नुहोस्")) {
                    if (offerValidDate.getVisibility() == View.VISIBLE) {
                        offerValidDate.setVisibility(View.GONE);
                    }
                } else if (myPostType.getSelectedItem().toString().equalsIgnoreCase("Post Offer") ||
                        myPostType.getSelectedItem().toString().equalsIgnoreCase("प्रस्ताव थप्नुहोस्")) {
                    if (offerValidDate.getVisibility() == View.GONE) {
                        offerValidDate.setVisibility(View.VISIBLE);
                    }
                } else {
                    System.out.println("Error");
                    Toast.makeText(AddPostActivity.this, "Language issue!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void onBackClick() {
        addBackBtn.setOnClickListener(v -> onBackPressed());
    }

    private void userInterface() {
        myPostType = findViewById(R.id.postType);
        addBackBtn = findViewById(R.id.addBack);
        selectedDate = findViewById(R.id.selectedDate);
        offerValidDate = findViewById(R.id.offerValidDate);
        spinnerCardView = findViewById(R.id.cardView);
        uploadImage = findViewById(R.id.uploadImage);
        uploadImageBtn = findViewById(R.id.uploadImageBtn);
        postDescription = findViewById(R.id.offerDescription);
        confirmPost = findViewById(R.id.confirmPost);
        loadingParent = findViewById(R.id.loadingView);
        loadingAnimation = findViewById(R.id.loadingAnimation);
        customToolbar = findViewById(R.id.customToolbar);
        bodyContent = findViewById(R.id.scrollViewContent);
        hideTitle = findViewById(R.id.postProblemText);
        parentLayout = findViewById(R.id.parent);
    }

    private void addDataOnSpinner() {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.post_types, R.layout.spinner_item_text);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myPostType.setAdapter(arrayAdapter);
    }
}
