package com.example.sajilothree.FragmentPackage.NotificationPackage;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.APIPackage.BaseUrlPackage.ChatHubUrlHolder;
import com.example.sajilothree.AdapterPackages.NotificationAdapter.NotificationAdapter;
import com.example.sajilothree.AppCollection.App;
import com.example.sajilothree.MainActivity;
import com.example.sajilothree.ModelsPackage.Notification.NotificationModel;
import com.example.sajilothree.NotificationReceiver.NotificationReceiver;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.NotificationService.NotificationServices;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

public class NotificationFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notification_fragment, container, false);
    }

    private Toolbar toolbar;
    private HubConnection hubConnection;
    private RecyclerView bookingRequests;
    private NotificationManagerCompat notificationManager;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        notificationManager = NotificationManagerCompat.from(requireContext());
        userInterface(view);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle(getResources().getString(R.string.notification));

        buildHubConnection();
        getNotificationOnHunChange();

        getNotifications(false, "null", "null");
    }

    private void buildHubConnection() {
        hubConnection = HubConnectionBuilder.create(ChatHubUrlHolder.chatHubApi).build();
        hubConnection.start();
    }

    private void getNotificationOnHunChange() {
        hubConnection.on("Notify", (notificationId, header, content) ->
                getNotifications(true, header, content), Integer.class, String.class, String.class);
    }

    @Override
    public void onPause() {
        super.onPause();
        getNotificationOnHunChange();
    }

    private void getNotifications(boolean notify, String header, String content) {
        try {
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    BaseURL.getAllNotifications,
                    response -> {
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            NotificationModel getBooking = gson.fromJson(String.valueOf(response), NotificationModel.class);
                            if (getBooking.getSuccess()) {
                                if (NotificationServices.addAllNotification(getBooking.getResult())) {
                                    addNotificationInView();
                                    if (notify) {
                                        notificationShow(header, content);
                                    }
                                }
                            } else {
                                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(getContext(), "Failed to add to booking: " + ex, Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        Toast.makeText(getContext(), "Error Notification: " + error, Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Exception: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    private void addNotificationInView() {
        try {
            bookingRequests.setLayoutManager(new LinearLayoutManager(getContext()));
            NotificationAdapter notificationAdapter = new NotificationAdapter(getContext(), NotificationServices.bookingNotifications);
            bookingRequests.setAdapter(notificationAdapter);
        } catch (Exception ex) {
            Toast.makeText(getContext(), "View Exception: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    private void notificationShow(String header, String content) {
        try {
            Intent intent = new Intent(requireContext(), MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(requireContext(), 0, intent, 0);
            Intent broadCast = new Intent(requireContext(), NotificationReceiver.class);
            broadCast.putExtra("toastMessage", header);
            PendingIntent actionIntent = PendingIntent.getBroadcast(getContext(), 0,
                    broadCast, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification notification = new NotificationCompat.Builder(requireContext(), App.CHANNEL_2_ID)
                    .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                    .setContentTitle(header)
                    .setContentText(content)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_EVENT)
                    .setColor(Color.BLUE)
                    .setContentIntent(contentIntent)
                    .setAutoCancel(true)
                    .setOnlyAlertOnce(true)
                    .addAction(R.mipmap.ic_launcher_round, "", actionIntent)
                    .build();
            notificationManager.notify(1, notification);
        } catch (Exception ex) {
            Toast.makeText(getContext(), "New Notification " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    private void userInterface(View view) {
        toolbar = view.findViewById(R.id.bookRequestToolbar);
        bookingRequests = view.findViewById(R.id.requests);
        NotificationAdapter notificationAdapter = new NotificationAdapter(getContext(), NotificationServices.bookingNotifications);
        bookingRequests.setAdapter(notificationAdapter);
    }
}
