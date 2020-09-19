package com.example.sajilothree.AdapterPackages.BookingsFragmentPackage.ChatSystem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.ActivitiesPackage.ChatActivityPackage.ChatSystemActivity;
import com.example.sajilothree.EncodeUserPackage.EncodeUser;
import com.example.sajilothree.ModelsPackage.ChatHeadingModel.ChatHeadingModel;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.BookingServices.BookingRequest.BookingRequestService;
import com.example.sajilothree.ServicesPackage.ChatSystemReceiverID.ChatSystemReceiverIdHolder;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;

import java.util.Objects;

public class ChatHeadingAdapter extends RecyclerView.Adapter<ChatHeadingAdapter.MyViewHolder> {
    private Context context;
    private /*ArrayList<ChatHeadingModel>*/ ChatHeadingModel list;

    public ChatHeadingAdapter(Context context, ChatHeadingModel list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ChatHeadingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.messages_view_pager_design, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.date.setText(BookingRequestService.chatSystemDate(list.getResult().get(position).getSendDate()));

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.profile_placeholder);
        if (Objects.equals(EncodeUser.enCodeUserId(list.getResult().get(position).getParticipantOneId(),
                list.getResult().get(position).getParticipantOneUsername()), Username.username)) {
            Glide.with(context).
                    load(BaseURL.BaseURL + list.getResult().get(position).getParticipantTwoImage())
                    .apply(options).into(holder.userImage);
            holder.userName.setText(list.getResult().get(position).getParticipantTwoName());
        } else {
            Glide.with(context).
                    load(BaseURL.BaseURL + list.getResult().get(position).getParticipantOneImage())
                    .apply(options).into(holder.userImage);
            holder.userName.setText(list.getResult().get(position).getParticipantOneName());
        }
        holder.lastMessage.setText(list.getResult().get(position).getLastMessage1());
        if (!list.getResult().get(position).getSenderId().equals(Username.username)) {
            holder.sendOrNot.setVisibility(View.GONE);
            holder.senderName.setVisibility(View.VISIBLE);
            if (validateWithMe(position)) {
                holder.senderName.setText(list.getResult().get(position).getParticipantTwoName() + ": ");
            } else {
                holder.senderName.setText(list.getResult().get(position).getParticipantOneName() + ": ");
            }

            if (list.getResult().get(position).getSeen().equals("false")) {
                holder.newChats.setVisibility(View.VISIBLE);
                holder.lastMessage.setTypeface(null, Typeface.BOLD);
                holder.lastMessage.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            }

        } else {
            holder.senderName.setVisibility(View.VISIBLE);
            holder.sendOrNot.setVisibility(View.VISIBLE);
            holder.newChats.setVisibility(View.GONE);
            if (list.getResult().get(position).getSeen().equals("false")) {
                holder.sendOrNot.setText("Sent");
            } else {
                holder.sendOrNot.setText("Seen");
            }
        }
        onStartChat(holder.openChat, position);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean validateWithMe(int position) {
        return Objects.equals(EncodeUser.enCodeUserId(list.getResult().get(position).getParticipantOneId(),
                list.getResult().get(position).getParticipantOneUsername()), Username.username);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onStartChat(CardView openChat, int position) {
        openChat.setOnClickListener(v -> {
            if (validateWithMe(position)) {
                if (ChatSystemReceiverIdHolder.addReceiverId(EncodeUser.enCodeUserId(
                        list.getResult().get(position).getParticipantTwoId(),
                        list.getResult().get(position).getParticipantTwoUsername())) &&
                        ChatSystemReceiverIdHolder.addSenderId(list.getResult().get(position).getSenderId())) {
                    ChatSystemReceiverIdHolder.addPersonName(list.getResult().get(position).getParticipantTwoName());
                    startChatActivity();
                }
            } else {
                if (ChatSystemReceiverIdHolder.addReceiverId(EncodeUser.enCodeUserId(
                        list.getResult().get(position).getParticipantOneId(),
                        list.getResult().get(position).getParticipantOneUsername())) &&
                        ChatSystemReceiverIdHolder.addSenderId(list.getResult().get(position).getSenderId())) {
                    ChatSystemReceiverIdHolder.addPersonName(list.getResult().get(position).getParticipantOneName());
                    startChatActivity();
                }
            }
        });
    }

    private void startChatActivity() {
        context.startActivity(new Intent(context, ChatSystemActivity.class));
    }

    @Override
    public int getItemCount() {
        return list.getResult().size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView lastMessage, userName, senderName, sendOrNot;
        private ImageView userImage, newChats;
        private CardView openChat;
        private TextView date;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            lastMessage = itemView.findViewById(R.id.lastMessage);
            userImage = itemView.findViewById(R.id.userImage);
            userName = itemView.findViewById(R.id.userName);
            openChat = itemView.findViewById(R.id.startChatParent);
            newChats = itemView.findViewById(R.id.seenSign);
            senderName = itemView.findViewById(R.id.sender);
            sendOrNot = itemView.findViewById(R.id.seenOrNot);
            date = itemView.findViewById(R.id.date);
        }
    }
}
