package com.example.sajilothree.ActivitiesPackage.BookingProcessPackage.UpdateBooking;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.ActivitiesPackage.BookingProcessPackage.SelectedRequestPosition.SelectedRequestPosition;
import com.example.sajilothree.ActivitiesPackage.BookingProcessPackage.RequestPackage.RequestActivity;
import com.example.sajilothree.ActivitiesPackage.HideKeyboard.HideKeyboard;
import com.example.sajilothree.ModelsPackage.BookingDetailsModel.RequestModel.BookingRequestModel;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.BookingServices.BookingRequest.BookingRequestService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Objects;

public class UpdateBookingActivity extends AppCompatActivity {
    private RelativeLayout backBtn;
    private EditText serviceType, problemDescription, expectedDate, expectedTime, serviceAddress;
    private Button saveChanges;
    private int YEAR = 0;
    private int MONTH = 0;
    private int DATE = 0;
    private int HOUR = 0;
    private int MIN = -1;

    /*
     * For progress bar
     * After save button is clicked*/
    private CardView saveChangeCard;
    private ImageView loadingView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_bill_activity_layout);
        userInterface();
        onBackClick();
        try {
            fillContents();
        } catch (Exception ex) {
            Log.d("FillContents", Objects.requireNonNull(ex.getMessage()));
        }

        onDateClick();
        onTimeClick();
        onUpdateDetailsClick();
    }

    private void userInterface() {
        backBtn = findViewById(R.id.backEditBill);
        serviceType = findViewById(R.id.service_type);
        problemDescription = findViewById(R.id.problem_description);
        expectedDate = findViewById(R.id.expected_date);
        expectedTime = findViewById(R.id.expected_time);
        saveChanges = findViewById(R.id.updateDetails);
        saveChangeCard = findViewById(R.id.saveCard);
        loadingView = findViewById(R.id.progressBar);
        serviceAddress = findViewById(R.id.customer_address);
    }

    private void onUpdateDetailsClick() {
        saveChanges.setOnClickListener(v -> {
            if (formValidation()) {
                HideKeyboard.hideKeyboard(this);
                updateAPICall();
            }
        });
    }

    private boolean formValidation() {
        if (serviceType.getText().toString().isEmpty()) {
            serviceType.setError("Fill service type!");
            serviceType.requestFocus();
            return false;
        } else if (problemDescription.getText().toString().isEmpty()) {
            problemDescription.setError("Fill problem description!");
            serviceType.requestFocus();
            serviceType.setError(null);
            return false;
        } else if (expectedDate.getText().toString().isEmpty()) {
            expectedDate.setError("Select date!");
            expectedDate.requestFocus();
            problemDescription.setError(null);
            return false;
        } else if (expectedTime.getText().toString().isEmpty()) {
            expectedTime.setError("Select time!");
            expectedTime.requestFocus();
            expectedDate.setError(null);
            return false;
        } else if (serviceAddress.getText().toString().isEmpty()) {
            serviceAddress.setError("Add Address!");
            serviceAddress.requestFocus();
            expectedTime.setError(null);
            return false;
        } else {
            serviceAddress.setError(null);
            return true;
        }
    }

    /*Delete it afterward
    * This is done for form validation only*/
    public static boolean formValidationOne(String serviceTypeOne, String problemDescriptionOne, String expectedDateOne) {
        if (serviceTypeOne.isEmpty()) {
            return false;
        } else if (problemDescriptionOne.isEmpty()) {
            return false;
        } else return !expectedDateOne.isEmpty();
    }

    private void updateAPICall() {
        try {
            saveChangeCard.setVisibility(View.GONE);
            loadingView.setVisibility(View.VISIBLE);
            AnimationDrawable animationDrawable = (AnimationDrawable) loadingView.getDrawable();
            animationDrawable.start();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("BookingId", BookingRequestService.bookingRequests.get(SelectedRequestPosition.selectedPosition).getBookingId());
            jsonObject.put("ServiceType", serviceType.getText().toString());
            jsonObject.put("ProblemDescription", problemDescription.getText().toString());
            jsonObject.put("ServiceDate", expectedDate.getText().toString() + " " + expectedTime.getText().toString());
            jsonObject.put("ServiceAddress", serviceAddress.getText().toString());
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    BaseURL.updateBookings,
                    jsonObject,
                    response -> {
                        saveChangeCard.setVisibility(View.VISIBLE);
                        loadingView.setVisibility(View.GONE);
                        animationDrawable.stop();
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            BookingRequestModel requestModel = gson.fromJson(String.valueOf(response), BookingRequestModel.class);
                            if (requestModel.getSuccess()) {
                                if (BookingRequestService.updateBookingList(SelectedRequestPosition.selectedPosition, requestModel.getResult().get(0))) {
                                    startActivity(new Intent(this, RequestActivity.class));
                                    overridePendingTransition(R.anim.nav_default_pop_enter_anim, R.anim.nothing);
                                    finish();
                                }
                            }
                        } catch (Exception ex) {
                            Toast.makeText(this, "Response Exception: " + response.toString(), Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        saveChangeCard.setVisibility(View.VISIBLE);
                        loadingView.setVisibility(View.GONE);
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

    private void onDateClick() {
        expectedDate.setOnClickListener(v -> {
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
                        expectedDate.setText(charSequence);
                    }, YEAR, MONTH, DATE);
            datePickerDialog.show();
        });
    }

    private void onTimeClick() {
        expectedTime.setOnClickListener(v -> {
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
                expectedTime.setText(charSequence);
            }, HOUR, MIN, is24HourFormat);
            timePickerDialog.show();
        });
    }

    private void fillContents() {
        serviceType.setText(BookingRequestService.bookingRequests.get(SelectedRequestPosition.selectedPosition).getServiceType());
        problemDescription.setText(BookingRequestService.bookingRequests.get(SelectedRequestPosition.selectedPosition).getProblemDescription());
        serviceAddress.setText(BookingRequestService.bookingRequests.get(SelectedRequestPosition.selectedPosition).getServiceAddress());
        expectedDate.setText(BookingRequestService.dateConversion(BookingRequestService.bookingRequests.get(SelectedRequestPosition.selectedPosition).getServiceDate()));
        expectedTime.setText(BookingRequestService.timeConversion(BookingRequestService.bookingRequests.get(SelectedRequestPosition.selectedPosition).getServiceDate()));
    }

    private void onBackClick() {
        backBtn.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, RequestActivity.class));
        finish();
    }
}
