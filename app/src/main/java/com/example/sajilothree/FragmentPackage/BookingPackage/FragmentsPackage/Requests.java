package com.example.sajilothree.FragmentPackage.BookingPackage.FragmentsPackage;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.APIPackage.BaseUrlPackage.ChatHubUrlHolder;
import com.example.sajilothree.AdapterPackages.BookingsFragmentPackage.BookingRequest.RequestAdapter;
import com.example.sajilothree.AppCollection.App;
import com.example.sajilothree.MainActivity;
import com.example.sajilothree.ModelsPackage.BookingDetailsModel.RequestModel.BookingRequestModel;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.BookingServices.BookingRequest.BookingRequestService;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

import org.json.JSONObject;

import java.util.Objects;

public class Requests extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.request_fragment, container, false);
    }

    private HubConnection hubConnection;
    private RecyclerView bookingRequests;
    private NotificationManagerCompat notificationManager;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        notificationManager = NotificationManagerCompat.from(requireContext());
        userInterface(view);

        getBookingRequests(false);
        buildHubConnection();
        getBookingOnHubChange();
    }

    private void userInterface(View view) {
        bookingRequests = view.findViewById(R.id.bookingRequests);
    }

    private void buildHubConnection() {
        hubConnection = HubConnectionBuilder.create(ChatHubUrlHolder.chatHubApi).build();
        hubConnection.start();
    }

    private void getBookingOnHubChange() {
        hubConnection.on("Booking", (customerId, specialistId, bookingId) -> {
            if (customerId.equals(Username.username) || specialistId.equals(Username.username)) {
                getBookingRequests(true);
            }
        }, String.class, String.class, Integer.class);
    }

    @Override
    public void onPause() {
        super.onPause();
        getBookingOnHubChange();
    }

    private void notificationShow() {
        try {
            Notification notification = new NotificationCompat.Builder(requireContext(), App.CHANNEL_1_ID)
                    .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                    .setContentTitle("Booking Request")
                    .setContentText("You have new booking request. Please check it out.")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_EVENT)
                    .build();
            notificationManager.notify(1, notification);
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Failed: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    private void getBookingRequests(boolean notify) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tokenNumber", Username.username);
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    BaseURL.getAllBookingRequests,
                    jsonObject,
                    response -> {
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            BookingRequestModel requestModel = gson.fromJson(String.valueOf(response), BookingRequestModel.class);
                            if (requestModel.getSuccess()) {
                                if (BookingRequestService.addBookingList(requestModel.getResult())) {
                                    addDataToRequestView();
                                    if (notify) {
                                        notificationShow();
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            Toast.makeText(getContext(), "Parse Exception: " + ex, Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Exception: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    private void addDataToRequestView() {
        try {
            bookingRequests.setLayoutManager(new LinearLayoutManager(getContext()));
            RequestAdapter requestAdapter = new RequestAdapter(getContext(), BookingRequestService.bookingRequests);
            requestAdapter.notifyDataSetChanged();
            bookingRequests.setAdapter(requestAdapter);
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Seeding Exception: " + ex, Toast.LENGTH_SHORT).show();
        }
    }
}
