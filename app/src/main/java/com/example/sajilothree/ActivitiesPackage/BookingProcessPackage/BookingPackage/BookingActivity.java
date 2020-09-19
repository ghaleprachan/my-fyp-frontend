package com.example.sajilothree.ActivitiesPackage.BookingProcessPackage.BookingPackage;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.APIPackage.BaseUrlPackage.ChatHubUrlHolder;
import com.example.sajilothree.ActivitiesPackage.HideKeyboard.HideKeyboard;
import com.example.sajilothree.EncodeUserPackage.EncodeUser;
import com.example.sajilothree.ModelsPackage.BookingDetailsModel.BookingResponseModel.BookingResponse;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.BookingServices.BookingDetailsProfile.BookingDetailsServices;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Objects;

public class BookingActivity extends AppCompatActivity {
    private EditText selectTime, selectDate, addresses, problem; // These show the selected day to the user
    private EditText services;
    private int YEAR = 0;
    private int MONTH = 0;
    private int DATE = 0;
    private int HOUR = 0;
    private int MIN = -1;
    private LinearLayout backBtn;
    private CardView hideCardView;
    private ImageView moreAddress, loadingAnimation, customerImage, specialistImage;
    private TextView customerName, specialistName;
    private Button bookNow;
    private HubConnection hubConnection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_activity_layout);
        userInterface();
        onBackClick();
        fillUpdDetails();
        onSelectDate();
        onSelectTime();
        onServiceClick();
        onAddressClick();
        onBookNow();

        buildHubConnection();
    }

    private void buildHubConnection() {
        hubConnection = HubConnectionBuilder.create(ChatHubUrlHolder.chatHubApi).build();
        hubConnection.start();
    }

    private void userInterface() {
        selectDate = findViewById(R.id.expected_date);
        selectTime = findViewById(R.id.expected_time);
        services = findViewById(R.id.service_type);

        addresses = findViewById(R.id.service_address);
        moreAddress = findViewById(R.id.moreAddress);
        bookNow = findViewById(R.id.bookNow);
        problem = findViewById(R.id.problem_description);
        backBtn = findViewById(R.id.bookingBackBtn);

        hideCardView = findViewById(R.id.hideCardView);
        loadingAnimation = findViewById(R.id.loadingAnimation);

        customerImage = findViewById(R.id.myImage);
        specialistImage = findViewById(R.id.userImage);
        customerName = findViewById(R.id.greeting);
        specialistName = findViewById(R.id.specialist_name);
    }

    @SuppressLint("SetTextI18n")
    private void fillUpdDetails() {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.profile_placeholder);
        Glide.with(this)
                .load(BaseURL.BaseURL + BookingDetailsServices.bookingDetails.get(0).getServiceProvider().get(0).getUserProfileImage())
                .apply(options).into(specialistImage);
        Glide.with(this)
                .load(BaseURL.BaseURL + BookingDetailsServices.bookingDetails.get(0).getCustomer().get(0).getUserProfileImage())
                .apply(options).into(customerImage);
        customerName.setText("Hi " + BookingDetailsServices.bookingDetails.get(0).getCustomer().get(0).getFullName());
        specialistName.setText(BookingDetailsServices.bookingDetails.get(0).getServiceProvider().get(0).getFullName());
    }

    private void onBackClick() {
        backBtn.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    /*
     * The below contents are for selecting date of form one
     * The date shown in View and stored in background are different*/
    private void onSelectDate() {
        selectDate.setOnClickListener(v -> {
            HideKeyboard.hideKeyboard(this);
            /*
             * This is to show the selected date next time
             * To avoid reset of date the if condition is applied*/
            if (YEAR == 0 || MONTH == 0 || DATE == 0) {
                Calendar calendar = Calendar.getInstance();
                YEAR = calendar.get(Calendar.YEAR);
                MONTH = calendar.get(Calendar.MONTH);
                DATE = calendar.get(Calendar.DATE);
            }
            DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(this),
                    (datePicker, year, month, date) -> {
                        // This store the type of date that is to send to the api
                        YEAR = year;
                        MONTH = month;
                        DATE = date;
                        // Format Datetime show in format (Tuesday-Apr 20 2020)
                        Calendar newCalendar = Calendar.getInstance();
                        newCalendar.set(Calendar.YEAR, year);
                        newCalendar.set(Calendar.MONTH, month);
                        newCalendar.set(Calendar.DATE, date);
                        CharSequence charSequence = DateFormat.format("MMM d, yyyy", newCalendar);
                        selectDate.setText(charSequence);
                    }, YEAR, MONTH, DATE);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        });
    }

    private void onSelectTime() {
        selectTime.setOnClickListener(v -> {
            HideKeyboard.hideKeyboard(this);
            /*
             * This is to show the selected date next time
             * To avoid reset of date the if condition is applied*/
            if (HOUR == 0 && MIN == -1) {
                Calendar calendar = Calendar.getInstance();
                HOUR = calendar.get(Calendar.HOUR);
                MIN = calendar.get(Calendar.MINUTE);
            }
            boolean is24HourFormat = DateFormat.is24HourFormat(this);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (timePicker, hour, minute) -> {
                HOUR = hour;
                MIN = minute;
                // Time custom format
                Calendar newCalendar = Calendar.getInstance();
                newCalendar.set(Calendar.HOUR, hour);
                newCalendar.set(Calendar.MINUTE, minute);
                CharSequence charSequence = DateFormat.format("hh:mm a", newCalendar);
                selectTime.setText(charSequence);
            }, HOUR, MIN, is24HourFormat);
            timePickerDialog.show();
        });
    }

    private void onServiceClick() {
        services.setText(BookingDetailsServices.getServices().get(0));
        services.setOnClickListener(v -> {
            try {
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1);
                for (int i = 0; i < BookingDetailsServices.getServices().size(); i++) {
                    arrayAdapter.add(BookingDetailsServices.getServices().get(i));
                }
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
                builder.setTitle("Select Service");
                if (arrayAdapter.getCount() == 0) {
                    builder.setMessage("No Services Available.");
                } else {
                    builder.setAdapter(arrayAdapter, (dialog, item) ->
                            services.setText(arrayAdapter.getItem(item)));
                }
                AlertDialog alert = builder.create();
                alert.show();
            } catch (Exception ex) {
                Toast.makeText(this, "Something Wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onAddressClick() {
        addresses.setText(BookingDetailsServices.getAddresses());
        moreAddress.setOnClickListener(v -> {
            try {
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1);
                for (int i = 0; i < BookingDetailsServices.bookingDetails.get(0).getCustomer().get(0).getAddresses().size(); i++) {
                    arrayAdapter.add(BookingDetailsServices.bookingDetails.get(0).getCustomer().get(0).getAddresses().get(i).getDistrictName() + " - " +
                            BookingDetailsServices.bookingDetails.get(0).getCustomer().get(0).getAddresses().get(i).getMunicipalityName() + ", " +
                            BookingDetailsServices.bookingDetails.get(0).getCustomer().get(0).getAddresses().get(i).getCurrentLocation());
                }
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
                builder.setTitle("Select Address");
                if (arrayAdapter.getCount() == 0) {
                    builder.setMessage("No Addresses Available.");
                } else {
                    builder.setAdapter(arrayAdapter, (dialog, item) ->
                            addresses.setText(arrayAdapter.getItem(item)));
                }
                AlertDialog alert = builder.create();
                alert.show();
            } catch (Exception ex) {
                Toast.makeText(this, "Something Wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean formValidation() {
        if (services.getText().toString().isEmpty()) {
            services.setError("Select service!");
            services.requestFocus();
            return false;
        } else if (problem.getText().toString().isEmpty()) {
            problem.setError("Write problem!");
            problem.requestFocus();
            services.setError(null);
            return false;
        } else if (addresses.getText().toString().isEmpty()) {
            addresses.setError("Write address!");
            addresses.requestFocus();
            problem.setError(null);
            return false;
        } else if (selectDate.getText().toString().isEmpty()) {
            selectDate.setError("Select date!");
            selectDate.requestFocus();
            addresses.setError(null);
            return false;
        } else if (selectTime.getText().toString().isEmpty()) {
            selectTime.setError("Select time!");
            selectTime.requestFocus();
            selectDate.setError(null);
            return false;
        } else {
            selectTime.setError(null);
            return true;
        }
    }

    private void onBookNow() {
        bookNow.setOnClickListener(v -> {
            HideKeyboard.hideKeyboard(this);
            if (formValidation()) {
                bookToAPI();
            }
        });
    }

    private void bookToAPI() {
        try {
            hideCardView.setVisibility(View.GONE);
            loadingAnimation.setVisibility(View.VISIBLE);
            AnimationDrawable animationDrawable = (AnimationDrawable) loadingAnimation.getDrawable();
            animationDrawable.start();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ServiceProvider", BookingDetailsServices.bookingDetails.get(0).getServiceProvider().get(0).getUserId());
            jsonObject.put("Customer", BookingDetailsServices.bookingDetails.get(0).getCustomer().get(0).getUserId());
            jsonObject.put("ServiceType", services.getText().toString());
            jsonObject.put("ServiceDate", selectDate.getText().toString() + " " + selectTime.getText().toString());
            jsonObject.put("CustomerAddress", addresses.getText().toString());
            jsonObject.put("ProblemDescription", problem.getText().toString());
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    BaseURL.startBooking,
                    jsonObject,
                    response -> {
                        hideCardView.setVisibility(View.VISIBLE);
                        loadingAnimation.setVisibility(View.GONE);
                        animationDrawable.stop();
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            BookingResponse bookingResponse = gson.fromJson(String.valueOf(response), BookingResponse.class);
                            if (bookingResponse.getSuccess()) {
                                hubConnection.start();
                                if (hubConnection.getConnectionState() == HubConnectionState.CONNECTED) {
                                    String customerId = EncodeUser.enCodeUserId(
                                            bookingResponse.getResult().get(0).getCustomerId(),
                                            bookingResponse.getResult().get(0).getCustomerUsername());
                                    String specialistId = EncodeUser.enCodeUserId(
                                            bookingResponse.getResult().get(0).getSpecialistId(),
                                            bookingResponse.getResult().get(0).getSpecialistUsername());
                                    Integer bookingId = bookingResponse.getResult().get(0).getBookingId();
                                    hubConnection.send("BookingNotification", customerId, specialistId,
                                            bookingId);
                                }
                                onBackPressed();
                            } else {
                                Toast.makeText(this, "Something went wrong: " + bookingResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(this, "Failed: " + ex, Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        hideCardView.setVisibility(View.VISIBLE);
                        loadingAnimation.setVisibility(View.GONE);
                        animationDrawable.stop();
                        Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(this, "Exception: " + ex, Toast.LENGTH_SHORT).show();
        }
    }
}
