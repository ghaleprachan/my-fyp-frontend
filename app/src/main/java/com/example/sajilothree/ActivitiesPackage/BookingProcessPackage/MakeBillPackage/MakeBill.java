package com.example.sajilothree.ActivitiesPackage.BookingProcessPackage.MakeBillPackage;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.ActivitiesPackage.BookingProcessPackage.SelectedRequestPosition.SelectedRequestPosition;
import com.example.sajilothree.ModelsPackage.BillPackage.BillResponseModel;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.BookingServices.BookingRequest.BookingRequestService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

public class MakeBill extends AppCompatActivity {
    private RelativeLayout backBill;
    private EditText serviceCharge, discount, travellingCost;
    private TextView totalCost;
    private Button confirmBooking;
    private ImageView loadingAnimation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_bill_activity);
        userInterface();
        onBackBillClick();

        onServiceChargeChange();
        onDiscountChange();
        onTravelingCostChange();
        onConfirmBookingClick();
    }

    private void userInterface() {
        backBill = findViewById(R.id.backBill);
        serviceCharge = findViewById(R.id.service_charge);
        discount = findViewById(R.id.discount);
        travellingCost = findViewById(R.id.traveling_cost);
        totalCost = findViewById(R.id.totalCost);
        confirmBooking = findViewById(R.id.confirm_booking);
        loadingAnimation = findViewById(R.id.loadingAnimation);
    }

    private void onConfirmBookingClick() {
        confirmBooking.setOnClickListener(v -> {
            if (formValidation()) {
                addBillAPICall();
            }
        });
    }

    private void addBillAPICall() {
        try {
            loadingAnimation.setVisibility(View.VISIBLE);
            confirmBooking.setVisibility(View.GONE);
            AnimationDrawable animationDrawable = (AnimationDrawable) loadingAnimation.getDrawable();
            animationDrawable.start();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("BookingId", SelectedRequestPosition.bookingId);
            jsonObject.put("ServiceCharge", serviceCharge.getText().toString());
            jsonObject.put("TravellingCost", travellingCost.getText().toString());
            jsonObject.put("Discount", discount.getText().toString());
            jsonObject.put("TotalCharge", totalCost.getText().toString());
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    BaseURL.postBill,
                    jsonObject,
                    response -> {
                        loadingAnimation.setVisibility(View.GONE);
                        confirmBooking.setVisibility(View.VISIBLE);
                        animationDrawable.stop();
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            BillResponseModel billResponseModel = gson.fromJson(String.valueOf(response), BillResponseModel.class);
                            if (billResponseModel.getSuccess()) {
                                if (BookingRequestService.removeById(SelectedRequestPosition.bookingId)) {
                                    Toast.makeText(this, "Success to add", Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                }
                            } else {
                                Toast.makeText(this, "Something went wrong" + response.toString(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(this, "Response Exception: " + response.toString(), Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        loadingAnimation.setVisibility(View.GONE);
                        confirmBooking.setVisibility(View.VISIBLE);
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

    private boolean formValidation() {
        if (serviceCharge.getText().toString().isEmpty()) {
            serviceCharge.requestFocus();
            serviceCharge.setError("Add Service Charge!");
            return false;
        } else if (discount.getError() != null) {
            discount.requestFocus();
            serviceCharge.setError(null);
            discount.setError("Invalid discount!");
            return false;
        } else {
            return true;
        }
    }

    private String totalCostCalculate() {
        try {
            if (getServiceCharge() == 0.0f) {
                serviceCharge.setError("Add service charge!");
                serviceCharge.requestFocus();
                return "0.0";
            } else if (getDiscount() == 0.0f) {
                return Double.toString(getServiceCharge() + getTravelingCost());
            } else {
                /*double totalCharge = (getServiceCharge() * ((100.0f - getDiscount()) / 100.0f)) + getTravelingCost();
                return Double.toString(totalCharge);*/
                return calculateTotalCost(getServiceCharge(), getDiscount(), getTravelingCost());
            }
        } catch (Exception ex) {
            return "0.0";
        }
    }

    public String calculateTotalCost(double serviceCharge, double discount, double travellingCost) {
        double totalCharge = (serviceCharge * ((100.0f - discount) / 100.0f)) + travellingCost;
        return Double.toString(totalCharge);
    }

    private double getServiceCharge() {
        try {
            if (serviceCharge.getText().toString().isEmpty()) {
                return 0.0f;
            } else {
                return Double.parseDouble(serviceCharge.getText().toString());
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Get Service Charge " + ex, Toast.LENGTH_SHORT).show();
            return 0.0f;
        }
    }

    private double getDiscount() {
        try {
            if (discount.getText().toString().isEmpty()) {
                return 0.0f;
            } else {
                if (Integer.parseInt(discount.getText().toString()) > 100 || Integer.parseInt(discount.getText().toString()) < 0) {
                    discount.setError("Invalid discount!");
                    discount.requestFocus();
                    return 0.0f;
                } else {
                    return Double.parseDouble(discount.getText().toString());
                }
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Get Discount: " + ex, Toast.LENGTH_SHORT).show();
            return 0.0f;
        }
    }

    private double getTravelingCost() {
        try {
            if (travellingCost.getText().toString().isEmpty()) {
                return 0.0f;
            } else {
                return Double.parseDouble(travellingCost.getText().toString());
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Get Travelling: " + ex, Toast.LENGTH_SHORT).show();
            return 0.0f;
        }
    }

    private void onServiceChargeChange() {
        serviceCharge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                totalCost.setText(totalCostCalculate());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void onDiscountChange() {
        discount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                totalCost.setText(totalCostCalculate());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void onTravelingCostChange() {
        travellingCost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                totalCost.setText(totalCostCalculate());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void onBackBillClick() {
        backBill.setOnClickListener(v -> onBackPressed());
    }
}
