package com.example.sajilothree.ActivitiesPackage.BookingProcessPackage.RequestPackage;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.ActivitiesPackage.BookingProcessPackage.MakeBillPackage.MakeBill;
import com.example.sajilothree.ActivitiesPackage.BookingProcessPackage.SelectedRequestPosition.SelectedRequestPosition;
import com.example.sajilothree.ActivitiesPackage.BookingProcessPackage.UpdateBooking.UpdateBookingActivity;
import com.example.sajilothree.ModelsPackage.BookingDetailsModel.CancelBooking.CancelBookingResponse;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.BookingServices.BookingRequest.BookingRequestService;
import com.example.sajilothree.ServicesPackage.BookingServices.FinalBooking.FinalBookingServices;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class RequestActivity extends AppCompatActivity {
    private EditText serviceType, problemDescription, expectedDate, expectedTime;
    private TextView customerName, sendDate, greeting, headingText, contentHeading, serviceAddress;
    private CircleImageView customerImage, serviceProviderImage;
    private RelativeLayout backBtn;
    private Button decline, accept, edit, cancelBooking;
    private LinearLayout customerContent, specialistContent;
    private ImageView loadingAnimation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_activity_layout);
        userInterface();
        onBackBillClick();
        fillBookingDetails();
        onEditBooking();

        onCancelBooking();

        onAcceptClick();
        onDeclineClick();
    }

    private void userInterface() {
        customerName = findViewById(R.id.specialist_name);
        serviceType = findViewById(R.id.service_type);
        problemDescription = findViewById(R.id.problem_description);
        expectedDate = findViewById(R.id.expected_date);
        expectedTime = findViewById(R.id.expected_time);
        customerImage = findViewById(R.id.userImage);
        serviceProviderImage = findViewById(R.id.myImage);
        sendDate = findViewById(R.id.sendDate);
        greeting = findViewById(R.id.greeting);
        headingText = findViewById(R.id.headingText);
        backBtn = findViewById(R.id.backBill);
        contentHeading = findViewById(R.id.contentHeading);
        decline = findViewById(R.id.decline);
        accept = findViewById(R.id.accept);
        edit = findViewById(R.id.editDetails);
        cancelBooking = findViewById(R.id.cancelBooking);
        customerContent = findViewById(R.id.customerContent);
        specialistContent = findViewById(R.id.specialistContent);
        serviceAddress = findViewById(R.id.customer_address);
        loadingAnimation = findViewById(R.id.loadingAnimation);
    }

    private void onDeclineClick() {
        decline.setOnClickListener(v -> {
            cancelBookingAPICall();
        });
    }

    private void onAcceptClick() {
        accept.setOnClickListener(v -> {
            startActivity(new Intent(this, MakeBill.class));
            overridePendingTransition(R.anim.bottom_up, R.anim.nothing);
            finish();
        });
    }

    private void onCancelBooking() {
        cancelBooking.setOnClickListener(v -> {
            confirmAlert();
        });
    }

    private void confirmAlert() {
        try {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            builder.setTitle("Select Address");
            builder.setMessage("Are sure to cancel booking?");
            builder.setPositiveButton("Yes", (dialog, which) -> cancelBookingAPICall());

            AlertDialog alert = builder.create();
            alert.show();
        } catch (Exception ex) {
            Toast.makeText(this, "Something Wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    private void cancelBookingAPICall() {
        try {
            customerContent.setVisibility(View.GONE);
            loadingAnimation.setVisibility(View.VISIBLE);
            AnimationDrawable animationDrawable = (AnimationDrawable) loadingAnimation.getDrawable();
            animationDrawable.start();
            StringRequest request = new StringRequest(
                    Request.Method.DELETE,
                    BaseURL.cancelBooking + BookingRequestService.bookingRequests.get(selectedPosition()).getBookingId(),
                    response -> {
                        customerContent.setVisibility(View.VISIBLE);
                        loadingAnimation.setVisibility(View.GONE);
                        animationDrawable.stop();
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            CancelBookingResponse bookingResponse = gson.fromJson(response, CancelBookingResponse.class);
                            if (bookingResponse.getSuccess()) {
                                if (BookingRequestService.removeById(SelectedRequestPosition.bookingId)) {
                                    onBackPressed();
                                    finish();
                                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception ex) {
                            Toast.makeText(this, "Parse Error: " + response, Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        customerContent.setVisibility(View.VISIBLE);
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

    private void onBackBillClick() {
        backBtn.setOnClickListener(v -> onBackPressed());
    }

    @SuppressLint("SetTextI18n")
    private void fillBookingDetails() {
        /*
         * Common details between users*/
        serviceType.setText(BookingRequestService.bookingRequests.get(selectedPosition()).getServiceType());
        problemDescription.setText(BookingRequestService.bookingRequests.get(selectedPosition()).getProblemDescription());
        expectedDate.setText(BookingRequestService.dateConversion(BookingRequestService.bookingRequests.get(selectedPosition()).getServiceDate()));
        expectedTime.setText(BookingRequestService.timeConversion(BookingRequestService.bookingRequests.get(selectedPosition()).getServiceDate()));
        serviceAddress.setText(BookingRequestService.bookingRequests.get(selectedPosition()).getServiceAddress());
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.profile_placeholder);
        /*
         * This is to separate if user is customer or specialist*/
        if (isCustomer()) {
            greeting.setText("Hi " + BookingRequestService.bookingRequests.get(selectedPosition()).getCustomerName());
            headingText.setText("You have sent request");

            customerName.setText(BookingRequestService.bookingRequests.get(selectedPosition()).getSpecialistName());
            Glide.with(this)
                    .load(BaseURL.BaseURL + BookingRequestService.bookingRequests.get(selectedPosition()).getCustomerImage())
                    .apply(options).into(serviceProviderImage);
            Glide.with(this)
                    .load(BaseURL.BaseURL + BookingRequestService.bookingRequests.get(selectedPosition()).getSpecialistImage())
                    .apply(options).into(customerImage);
            contentHeading.setText("Request To");

            customerContent.setVisibility(View.VISIBLE);
            specialistContent.setVisibility(View.GONE);
        } else {
            greeting.setText("Hi " + BookingRequestService.bookingRequests.get(selectedPosition()).getSpecialistName());
            headingText.setText("You have new request");

            customerName.setText(BookingRequestService.bookingRequests.get(selectedPosition()).getCustomerName());
            Glide.with(this)
                    .load(BaseURL.BaseURL + BookingRequestService.bookingRequests.get(selectedPosition()).getSpecialistImage())
                    .apply(options).into(serviceProviderImage);
            Glide.with(this)
                    .load(BaseURL.BaseURL + BookingRequestService.bookingRequests.get(selectedPosition()).getCustomerImage())
                    .apply(options).into(customerImage);
            contentHeading.setText("Request By");

            specialistContent.setVisibility(View.VISIBLE);
            customerContent.setVisibility(View.GONE);
        }
        /*
         * This is send date*/
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = null;
            try {
                date = sdf.parse(BookingRequestService.bookingRequests.get(selectedPosition()).getBookingDate());
            } catch (Exception ex) {
                Toast.makeText(this, "Exception: " + ex, Toast.LENGTH_SHORT).show();
            }
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf2 = new SimpleDateFormat("MMMM dd, HH:mm a");
            assert date != null;
            String dateOne = sdf2.format(date);
            sendDate.setText(dateOne);
        } catch (Exception ex) {
            Toast.makeText(this, "Date Parse failed.", Toast.LENGTH_SHORT).show();
        }
    }

    /*
     * This is the selected position by user*/
    private Integer selectedPosition() {
        return SelectedRequestPosition.selectedPosition;
    }

    /*
     * This is done to separate if user is customer or specialist*/
    private boolean isCustomer() {
        return SelectedRequestPosition.isCustomer;
    }

    private void onEditBooking() {
        edit.setOnClickListener(v -> {
            Intent intent = new Intent(this, UpdateBookingActivity.class);
            /*intent.putExtra("selectedPosition", selectedPosition());
            intent.putExtra("isCustomer", "true");*/
            startActivity(intent);
            overridePendingTransition(R.anim.bottom_up, R.anim.nothing);
            finish();
        });
    }
}
