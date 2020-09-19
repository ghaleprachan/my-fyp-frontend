package com.example.sajilothree.ActivitiesPackage.BookingHistory;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
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
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.EncodeUserPackage.EncodeUser;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.BookingHistory.BookingHistoryService;
import com.example.sajilothree.ServicesPackage.BookingServices.BookingRequest.BookingRequestService;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.r0adkll.slidr.Slidr;

import java.util.Objects;

public class BookingHistoryDetails extends AppCompatActivity {
    private TextView totalCost, bookedDate, greetings, contentHeading, userName, status;
    private Button billDetails, cancelBooking, call, message;
    private CardView completeCard;
    private EditText serviceAddress, serviceDate, profession, problemDescription;
    private ImageView myImage, userImage;
    private RequestOptions options;
    private BottomSheetDialog bottomSheetDialog;
    private ImageView loadingAnimation;

    @SuppressLint({"SetTextI18n", "NewApi"})
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_booking_details_layout);
        Slidr.attach(this);
        userInterface();
        cancelBooking.setText(R.string.title_activity_delete);
        status.setText("PAID");
        status.setTextColor(getColor(R.color.green));
        cancelBooking.setBackgroundColor(getColor(R.color.red));

        fillAllDetails();
        onMoreBillDetailsClick();
        onCancelBooking();
        completeCard.setVisibility(View.GONE);
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
        status = findViewById(R.id.status);
        completeCard = findViewById(R.id.completeCard);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("SetTextI18n")
    private void fillAllDetails() {
        try {
            options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.profile_placeholder);
            bookedDate.setText("Booked on " + BookingRequestService.onlyDateDay(BookingHistoryService.getSelectedDetails().getBookingDate()));
            totalCost.setText("NPR " + BookingHistoryService.getSelectedDetails().getTotalCharge().toString());
            serviceAddress.setText(BookingHistoryService.getSelectedDetails().getCustomerAddress());
            profession.setText(BookingHistoryService.getSelectedDetails().getServiceType());
            problemDescription.setText(BookingHistoryService.getSelectedDetails().getProblemDescription());
            serviceDate.setText(BookingRequestService.dateTime(BookingHistoryService.getSelectedDetails().getServiceDate()));

            if (Objects.equals(EncodeUser.enCodeUserId(BookingHistoryService.getSelectedDetails().getCustomerId(),
                    BookingHistoryService.getSelectedDetails().getCustomerUserName()), Username.username)) {
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
        greetings.setText("Hi " + BookingHistoryService.getSelectedDetails().getCustomerName());
        Glide.with(this)
                .load(BaseURL.BaseURL + BookingHistoryService.getSelectedDetails().getCustomerImage())
                .apply(options)
                .into(myImage);
        Glide.with(this)
                .load(BaseURL.BaseURL + BookingHistoryService.getSelectedDetails().getSpecialistImage())
                .apply(options)
                .into(userImage);
        contentHeading.setText("Specialist");
        userName.setText(BookingHistoryService.getSelectedDetails().getSpecialistName());
    }

    @SuppressLint("SetTextI18n")
    private void isSpecialist() {
        greetings.setText("Hi " + BookingHistoryService.getSelectedDetails().getSpecialistName());
        Glide.with(this)
                .load(BaseURL.BaseURL + BookingHistoryService.getSelectedDetails().getSpecialistImage())
                .apply(options)
                .into(myImage);
        Glide.with(this)
                .load(BaseURL.BaseURL + BookingHistoryService.getSelectedDetails().getCustomerImage())
                .apply(options)
                .into(userImage);
        contentHeading.setText("Customer");
        userName.setText(BookingHistoryService.getSelectedDetails().getCustomerName());
    }

    @SuppressLint("SetTextI18n")
    private void onMoreBillDetailsClick() {
        billDetails.setOnClickListener(v -> {
            try {
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

                totalAmount.setText("NPR " + BookingHistoryService.getSelectedDetails().getTotalCharge().toString() + " Only");
                serviceCharge.setText("NPR " + BookingHistoryService.getSelectedDetails().getServiceCharge().toString());
                discount.setText("NPR " + BookingHistoryService.getSelectedDetails().getDiscount().toString() + "%");
                travellingCost.setText("NPR " + BookingHistoryService.getSelectedDetails().getTravellingCost().toString());
            } catch (Exception ex) {
                Toast.makeText(this, "Bottom Ex: " + ex, Toast.LENGTH_SHORT).show();
            }
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
            builder.setMessage("Are sure to delete booking?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                deleteBooking();
            });

            AlertDialog alert = builder.create();
            alert.show();
        } catch (Exception ex) {
            Toast.makeText(this, "Something Wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteBooking() {
        try {
            cancelBooking.setVisibility(View.GONE);
            loadingAnimation.setVisibility(View.VISIBLE);
            AnimationDrawable animationDrawable = (AnimationDrawable) loadingAnimation.getDrawable();
            animationDrawable.start();
            StringRequest request = new StringRequest(
                    Request.Method.DELETE,
                    BaseURL.deleteBookingHistory + BookingHistoryService.getSelectedDetails().getBookingId(),
                    response -> {
                        cancelBooking.setVisibility(View.VISIBLE);
                        loadingAnimation.setVisibility(View.GONE);
                        animationDrawable.stop();
                        try {
                            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(this, BookingHistory.class));
                            finish();
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
