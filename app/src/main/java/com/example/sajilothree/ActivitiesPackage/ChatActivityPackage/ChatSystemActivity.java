package com.example.sajilothree.ActivitiesPackage.ChatActivityPackage;

import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.APIPackage.BaseUrlPackage.ChatHubUrlHolder;
import com.example.sajilothree.AdapterPackages.UsetChatsAdapter.UserChatsAdapter;
import com.example.sajilothree.ModelsPackage.ChatSystemActivity.ChatSystemResponseModel;
import com.example.sajilothree.ModelsPackage.ChatSystemPutLastMessage.UpdateLastMessageModel;
import com.example.sajilothree.ModelsPackage.ChatSystemUserChatModels.ChatModel;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.ChatHolderService.ChatListHolder;
import com.example.sajilothree.ServicesPackage.ChatSystemReceiverID.ChatSystemReceiverIdHolder;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;
import com.r0adkll.slidr.Slidr;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class ChatSystemActivity extends AppCompatActivity {
    private EditText writeMessage;
    private ImageView sendButton;
    private RecyclerView messagesView;
    private HubConnection hubConnection;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_system_activity);
        ChatListHolder.chatsModel.clear();

        Toolbar chatSystemToolbar = findViewById(R.id.chatSystemToolbar);
        setSupportActionBar(chatSystemToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(ChatSystemReceiverIdHolder.otherName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Slidr.attach(this);
        userInterface();
        getMessagesFirst();
        buildHubConnection();
        getDataOnHubChange();

        sendToHub();
        try {
            if (!ChatSystemReceiverIdHolder.senderId.equals(Username.username)) {
                markChatRead();
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Exception: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean validateChat(String chat) {
        return !chat.isEmpty();
    }

    /*
     * It will update the database that the last send message is read
     * It will be notify to the hub which than will be send to the receiver site
     * The sender site will see the message is seen by the receiver*/
    private void markChatRead() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("participantOne", Username.username);
            jsonObject.put("participantTwo", ChatSystemReceiverIdHolder.receiverId);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT,
                    BaseURL.updateSeenChat,
                    jsonObject,
                    response -> {
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            UpdateLastMessageModel lastMessageModel = gson.fromJson(String.valueOf(response),
                                    UpdateLastMessageModel.class);
                            if (lastMessageModel.getSuccess()) {
                                hubConnection.start();
                                if (hubConnection.getConnectionState() == HubConnectionState.CONNECTED) {
                                    String participantOne = Username.username;
                                    String participantTwo = ChatSystemReceiverIdHolder.receiverId;
                                    hubConnection.send("UpdateLastMessage", participantOne, participantTwo);
                                }
                            } else {
                                Toast.makeText(this, "Start Chat", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(this, "While not chat: " + ex, Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        Toast.makeText(this, "Error For mark as read " + error, Toast.LENGTH_SHORT).show();
                    }
            );
            request.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 50000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 50000;
                }

                @Override
                public void retry(VolleyError error) {
                    Toast.makeText(ChatSystemActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(ChatSystemActivity.this);
            requestQueue.add(request);

        } catch (Exception ex) {
            Toast.makeText(this, "Exception for mark as read" + ex, Toast.LENGTH_SHORT).show();
            System.out.println(ex.toString());
        }
    }

    /*
     * This method will be activated when a new data will come to the hub
     * It will check if the receiver id is of given user
     * If it is of given user that it will again call the api for chat heading
     * Change it*/
    private void getDataOnHubChange() {
        hubConnection.on("GetMessage", (senderId, receiverId, message) -> {
            if (receiverId.equals(Username.username)) {
                getMessagesFirst();
            }
        }, String.class, String.class, String.class);
    }

    /*
     * This is to send the message into the database
     * This is a room for improvement, we directly can add data into the database into the same call
     * Return success message from the database*/
    private void sendToHub() {
        sendButton.setOnClickListener(v -> {
            if (!writeMessage.getText().toString().isEmpty()) {
                postMessageDB();
            }
        });
    }

    /*
     * This is to post the message into the database
     * Moreover it will check if the chat between user exists or not
     * And then it will add the message to the database with the the message in latest database
     * If the conversation between user doesn't exists it will start the conversation into the database*/
    private void postMessageDB() {
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("SenderId", Username.username);
            jsonBody.put("ReceiverId", ChatSystemReceiverIdHolder.receiverId);
            jsonBody.put("Message", writeMessage.getText().toString());

            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST,
                    BaseURL.postChat,
                    jsonBody,
                    response -> {
                        try {

                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            ChatSystemResponseModel responseModel = gson.fromJson(String.valueOf(response),
                                    ChatSystemResponseModel.class);
                            /*
                             * After data is added to the database it will send the notify message to the hub.*/
                            if (responseModel.getSuccess()) {
                                writeMessage.setText("");
                                if (ChatListHolder.addChat(responseModel.getResult().get(0))) {
                                    seedMessagesView();
                                    hubConnection.start();
                                    if (hubConnection.getConnectionState() == HubConnectionState.CONNECTED) {
                                        String hubSenderId = Username.username;
                                        String hubReceiverId = ChatSystemReceiverIdHolder.receiverId;
                                        String hubMessage = writeMessage.getText().toString();
                                        hubConnection.send("SendMessage", hubSenderId, hubReceiverId,
                                                hubMessage);
//                                    getMessagesFirst();
                                    }
                                }
                            } else {
                                Toast.makeText(this, "Failed to send message ", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(this, "Post message db: " + ex, Toast.LENGTH_SHORT).show();
                        }
                    }, error -> Toast.makeText(ChatSystemActivity.this, "Server Error!", Toast.LENGTH_SHORT).show());
            RequestQueue requestQueue;
            requestQueue = Volley.newRequestQueue(ChatSystemActivity.this);
            requestQueue.add(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*
     * This is to build the connection with hub*/
    private void buildHubConnection() {
        hubConnection = HubConnectionBuilder.create(ChatHubUrlHolder.chatHubApi).build();
        hubConnection.start();
    }

    /*
     * This is to get chat headings from database*/
    private void getMessagesFirst() {
        RequestQueue requestQueue;
        StringRequest request = new StringRequest(
                Request.Method.GET,
                BaseURL.getChat +
                        "?senderId=" + Username.username +
                        "&receiverId=" + ChatSystemReceiverIdHolder.receiverId,
                response -> {
                    try {
                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();
                        ChatModel newChats = gson.fromJson(response,
                                ChatModel.class);
                        if (newChats.getResult().size() > 0) {
                            if (ChatListHolder.addOldChats(newChats.getResult())) {
                                seedMessagesView();
                            }
                        } else {
                            Toast.makeText(this, "Start Conversation", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(ChatSystemActivity.this, "unknown error" + ex, Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(ChatSystemActivity.this, "Error", Toast.LENGTH_SHORT).show());
        requestQueue = Volley.newRequestQueue(ChatSystemActivity.this);
        requestQueue.add(request);
    }

    /*
     * Seeding the messages into view*/
    private void seedMessagesView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(ChatSystemActivity.this);
        layoutManager.setStackFromEnd(true);
        messagesView.setLayoutManager(layoutManager);
        messagesView.setLayoutManager(layoutManager);
        UserChatsAdapter recyclerAdapter = new UserChatsAdapter(ChatSystemActivity.this, ChatListHolder.chatsModel);
        messagesView.setAdapter(recyclerAdapter);
    }

    private void userInterface() {
        writeMessage = findViewById(R.id.writeMessage);
        sendButton = findViewById(R.id.sendButton);
        messagesView = findViewById(R.id.messages);
    }
}
