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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.APIPackage.BaseUrlPackage.ChatHubUrlHolder;
import com.example.sajilothree.AdapterPackages.BookingsFragmentPackage.ChatSystem.ChatHeadingAdapter;
import com.example.sajilothree.ModelsPackage.ChatHeadingModel.ChatHeadingModel;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.ChatSystemHeading.ChatSystemHeadingServices;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

import java.util.Objects;

public class Messages extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.messages_view_pager, container, false);
    }

    private RecyclerView chatView;
    private HubConnection hubConnection;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userInterface(view);

        getLastMessageAPICall();
        buildHubConnection();
        getDataOnHubChange();
        getSeenFromHub();
    }

    private void getSeenFromHub() {
        try {
            hubConnection.on("GetLastUpdate", (participantOne, participantTwo) -> {
                if (participantOne.equals(Username.username) || participantTwo.equals(Username.username)) {
                    getLastMessageAPICall();
                }
            }, String.class, String.class);
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Exception = " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    private void buildHubConnection() {
        hubConnection = HubConnectionBuilder.create(ChatHubUrlHolder.chatHubApi).build();
        hubConnection.start();
    }

    private void getDataOnHubChange() {
        hubConnection.on("GetMessage", (senderId, receiverId, message) -> {
            if (receiverId.equals(Username.username) || senderId.equals(Username.username)) {
                getLastMessageAPICall();
            }
        }, String.class, String.class, String.class);
    }

    /*private void updateLastChage() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", null);
            ;
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Exception: " + ex, Toast.LENGTH_SHORT).show();
        }
    }*/
    private void getLastMessageAPICall() {
        try {
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    BaseURL.getChatHeadings + "?userId=" + Username.username,
                    response -> {
                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();
                        ChatHeadingModel headingModel = gson.fromJson(response, ChatHeadingModel.class);
                        if (headingModel.getSuccess()) {
                            addToChatAdapterView(headingModel);
                        } else {
                            Toast.makeText(getContext(), "Failed to get chat!", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> Toast.makeText(getContext(), "Error! " + error, Toast.LENGTH_SHORT).show()
            );
            RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    private void addToChatAdapterView(ChatHeadingModel chatHeadingModel) {
        if (ChatSystemHeadingServices.addChatHeading(chatHeadingModel)) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            chatView.setLayoutManager(layoutManager);
            ChatHeadingAdapter messagesAdapter = new ChatHeadingAdapter(getActivity(),
                    chatHeadingModel);
            chatView.setAdapter(messagesAdapter);
        }
    }

    private void userInterface(View view) {
        chatView = view.findViewById(R.id.chatView);
    }
}
