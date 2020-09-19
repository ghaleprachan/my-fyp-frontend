package com.example.sajilothree.ActivitiesPackage.BookingProcessPackage.FinalBookingDetailsPackage;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.ActivitiesPackage.BookingProcessPackage.SelectedRequestPosition.SelectedRequestPosition;
import com.example.sajilothree.EncodeUserPackage.EncodeUser;
import com.example.sajilothree.ModelsPackage.BookingDetailsModel.CancelBooking.CancelBookingResponse;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.BookingServices.BookingRequest.BookingRequestService;
import com.example.sajilothree.ServicesPackage.BookingServices.FinalBooking.FinalBookingServices;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.r0adkll.slidr.Slidr;

import java.util.Objects;

public class FinalBookingDetails extends AppCompatActivity {
    private TextView totalCost, bookedDate, greetings, contentHeading, userName;
    private Button billDetails, cancelBooking, call, message, bookingCompleted;
    private EditText serviceAddress, serviceDate, profession, problemDescription;
    private ImageView myImage, userImage;
    private RequestOptions options;
    private BottomSheetDialog bottomSheetDialog;
    private ImageView loadingAnimation;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_booking_details_layout);
        Slidr.attach(this);
        userInterface();

        fillAllDetails();
        onMoreBillDetailsClick();
        onCancelBooking();
        onBookingCompleted();
    }

    private void onBookingCompleted() {
        bookingCompleted.setOnClickListener(v -> {
            try {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
                builder.setTitle("Complete Booking");
                builder.setMessage("You want to make booking as completed?");
                builder.setPositiveButton("Yes", (dialog, which) -> completeBookingAPICall());

                AlertDialog alert = builder.create();
                alert.show();
            } catch (Exception ex) {
                Toast.makeText(this, "Something Wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void completeBookingAPICall() {
        try {
            StringRequest request = new StringRequest(
                    Request.Method.PUT,
                    BaseURL.completeBooking + FinalBookingServices.bookingId,
                    response -> {
                        try {
                            Toast.makeText(this, "Booking Completed", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } catch (Exception ex) {
                            Log.d("DebugResponse", Objects.requireNonNull(ex.getMessage()));
                        }
                    },
                    error -> {
                        Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show();
                    });
            Volley.newRequestQueue(this).add(request);
        } catch (Exception ex) {
            Log.d("Debug", Objects.requireNonNull(ex.getMessage()));
        }
    }

    private void userInterface() {
        totalCost = findViewById(R.id.totalCost);
        bookedDate = findViewById(R.id.bookingDate);
        billDetails = findViewById(R.id.fullBill);
        cancelBooking = findViewById(R.id.cancelBooking);
        call = findViewById(R.id.call);
        message = findViewById(R.id.message);
        serviceAddress = findViewById(R.id.service_address);
        serviceDate = findViewById(R.id.expected_date);
        profession = findViewById(R.id.profession);
        problemDescription = findViewById(R.id.problem_description);
        myImage = findViewById(R.id.myImage);
        userImage = findViewById(R.id.userImage);
        greetings = findViewById(R.id.greeting);
        contentHeading = findViewById(R.id.contentHeading);
        userName = findViewById(R.id.userName);
        loadingAnimation = findViewById(R.id.loadingAnimation);
        bookingCompleted = findViewById(R.id.bookingCompleted);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("SetTextI18n")
    private void fillAllDetails() {
        try {
            options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.profile_placeholder);
            bookedDate.setText("Booked on " + BookingRequestService.onlyDateDay(FinalBookingServices.getSelectedDetails().getBookingDate()));
            totalCost.setText("NPR " + FinalBookingServices.getSelectedDetails().getTotalCharge().toString());
            serviceAddress.setText(FinalBookingServices.getSelectedDetails().getCustomerAddress());
            profession.setText(FinalBookingServices.getSelectedDetails().getServiceType());
            problemDescription.setText(FinalBookingServices.getSelectedDetails().getProblemDescription());
            serviceDate.setText(BookingRequestService.dateTime(FinalBookingServices.getSelectedDetails().getServiceDate()));

            if (Objects.equals(EncodeUser.enCodeUserId(FinalBookingServices.getSelectedDetails().getCustomerId(),
                    FinalBookingServices.getSelectedDetails().getCustomerUserName()), Username.username)) {
                isCustomer();
            } else {
                isSpecialist();
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Exception: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void isCustomer() {
        greetings.setText("Hi " + FinalBookingServices.getSelectedDetails().getCustomerName());
        Glide.with(this)
                .load(BaseURL.BaseURL + FinalBookingServices.getSelectedDetails().getCustomerImage())
                .apply(options)
                .into(myImage);
        Glide.with(this)
                .load(BaseURL.BaseURL + FinalBookingServices.getSelectedDetails().getSpecialistImage())
                .apply(options)
                .into(userImage);
        contentHeading.setText("Specialist");
        userName.setText(FinalBookingServices.getSelectedDetails().getSpecialistName());
    }

    @SuppressLint("SetTextI18n")
    private void isSpecialist() {
        greetings.setText("Hi " + FinalBookingServices.getSelectedDetails().getSpecialistName());
        Glide.with(this)
                .load(BaseURL.BaseURL + FinalBookingServices.getSelectedDetails().getSpecialistImage())
                .apply(options)
                .into(myImage);
        Glide.with(this)
                .load(BaseURL.BaseURL + FinalBookingServices.getSelectedDetails().getCustomerImage())
                .apply(options)
                .into(userImage);
        contentHeading.setText("Customer");
        userName.setText(FinalBookingServices.getSelectedDetails().getCustomerName());
    }

    @SuppressLint("SetTextI18n")
    private void onMoreBillDetailsClick() {
        billDetails.setOnClickListener(v -> {
            LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            @SuppressLint("InflateParams") View modalBottomSheet = layoutInflater.inflate(R.layout.bill_all_details_sheet, null);
            bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(modalBottomSheet);
            bottomSheetDialog.setCanceledOnTouchOutside(true);
            bottomSheetDialog.setCancelable(true);
            bottomSheetDialog.show();

            TextView totalAmount = modalBottomSheet.findViewById(R.id.totalAmount);
            TextView serviceCharge = modalBottomSheet.findViewById(R.id.service_charge);
            TextView discount = modalBottomSheet.findViewById(R.id.discount);
            TextView travellingCost = modalBottomSheet.findViewById(R.id.traveling_cost);

            totalAmount.setText("NPR " + FinalBookingServices.getSelectedDetails().getTotalCharge().toString() + " Only");
            serviceCharge.setText("NPR " + FinalBookingServices.getSelectedDetails().getServiceCharge().toString());
            discount.setText("NPR " + FinalBookingServices.getSelectedDetails().getDiscount().toString() + "%");
            travellingCost.setText("NPR " + FinalBookingServices.getSelectedDetails().getTravellingCost().toString());
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
            builder.setTitle("Cancel Booking");
            builder.setMessage("Are sure to cancel booking?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                cancelBookingAPICall();
            });

            AlertDialog alert = builder.create();
            alert.show();
        } catch (Exception ex) {
            Toast.makeText(this, "Something Wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    private void cancelBookingAPICall() {
        try {
            cancelBooking.setVisibility(View.GONE);
            loadingAnimation.setVisibility(View.VISIBLE);
            AnimationDrawable animationDrawable = (AnimationDrawable) loadingAnimation.getDrawable();
            animationDrawable.start();
            StringRequest request = new StringRequest(
                    Request.Method.DELETE,
                    BaseURL.cancelBooking + FinalBookingServices.bookingId,
                    response -> {
                        cancelBooking.setVisibility(View.VISIBLE);
                        loadingAnimation.setVisibility(View.GONE);
                        animationDrawable.stop();
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            CancelBookingResponse bookingResponse = gson.fromJson(response, CancelBookingResponse.class);
                            if (bookingResponse.getSuccess()) {
                                if (FinalBookingServices.removeBooking(FinalBookingServices.bookingId)) {
                                    onBackPressed();
                                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception ex) {
                            Toast.makeText(this, "Parse Error: " + response, Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        cancelBooking.setVisibility(View.VISIBLE);
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
