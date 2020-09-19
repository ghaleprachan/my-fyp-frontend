package com.example.sajilothree.FragmentPackage.BookingPackage.FragmentsPackage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.AdapterPackages.BookingsFragmentPackage.FInalBookings.FinalBookingAdapter;
import com.example.sajilothree.ModelsPackage.BookingDetailsModel.FinalBookingModelPackage.FinalBookingModel;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.BookingServices.FinalBooking.FinalBookingServices;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.Objects;

public class Bookings extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.booking_view_pager, container, false);
    }

    private RecyclerView myBookingsView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userInterface(view);
        allBookingsAPICall();
    }

    private void userInterface(View view) {
        myBookingsView = view.findViewById(R.id.myBookings);
    }

    private void allBookingsAPICall() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tokenNumber", Username.username);
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    BaseURL.getAllFinalBookings,
                    jsonObject,
                    response -> {
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            FinalBookingModel bookingModel = gson.fromJson(String.valueOf(response), FinalBookingModel.class);
                            if (bookingModel.getSuccess()) {
                                if (FinalBookingServices.addAllBookings(bookingModel.getResult())) {
                                    setBookingView();
                                }
                            } else {
                                Toast.makeText(getContext(), "Something went wrong: " + response.toString(), Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception ex) {
                            Toast.makeText(getContext(), "Response Exception: " + response.toString(), Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        Toast.makeText(getContext(), "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Exception: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    private void setBookingView() {
        try {
            myBookingsView.setLayoutManager(new LinearLayoutManager(getContext()));
            FinalBookingAdapter finalBookingAdapter = new FinalBookingAdapter(getContext(), FinalBookingServices.finalBookings);
            myBookingsView.setAdapter(finalBookingAdapter);
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Recycler Exception: " + ex, Toast.LENGTH_SHORT).show();
        }
    }
}
