package com.example.sajilothree.AdapterPackages.UsetChatsAdapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.ActivitiesPackage.ChatActivityPackage.ChatSystemActivity;
import com.example.sajilothree.EncodeUserPackage.EncodeUser;
import com.example.sajilothree.ModelsPackage.ChatSystemUserChatModels.Result;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.BookingServices.BookingRequest.BookingRequestService;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserChatsAdapter extends RecyclerView.Adapter<UserChatsAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<Result> newChats;

    public UserChatsAdapter(Context mContext, ArrayList<Result> newChats) {
        this.mContext = mContext;
        this.newChats = newChats;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.chat_system_activity_design, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (Objects.equals(EncodeUser.enCodeUserId(newChats.get(position).getSenderId(),
                newChats.get(position).getSenderUsername()), Username.username)) {
            holder.myMessage.setText(newChats.get(position).getMessage());
            holder.myDate.setText(BookingRequestService.myDateFormat(newChats.get(position).getSendDate(),
                    "MMM dd, hh:mm a"));
            holder.myChats.setVisibility(View.VISIBLE);
            holder.otherChats.setVisibility(View.GONE);
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background);
            Glide.with(mContext).load(BaseURL.BaseURL + newChats.get(position).getSenderPhoto())
                    .apply(options).into(holder.myPhoto);

            holder.myPhoto.setVisibility(View.VISIBLE);
            holder.otherPhoto.setVisibility(View.GONE);

        } else {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background);
            Glide.with(mContext).load(BaseURL.BaseURL + newChats.get(position).getSenderPhoto())
                    .apply(options).into(holder.otherPhoto);

            holder.otherMessage.setText(newChats.get(position).getMessage());
            holder.otherDate.setText(BookingRequestService.myDateFormat(newChats.get(position).getSendDate(),
                    "MMM dd, hh:mm a"));
            holder.myChats.setVisibility(View.GONE);
            holder.otherChats.setVisibility(View.VISIBLE);

            holder.myPhoto.setVisibility(View.GONE);
            holder.otherPhoto.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return newChats.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView otherPhoto, myPhoto;
        private TextView myMessage, otherMessage, myDate, otherDate;
        private LinearLayout myChats, otherChats;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            otherPhoto = itemView.findViewById(R.id.otherPhoto);
            myPhoto = itemView.findViewById(R.id.myPhoto);
            myMessage = itemView.findViewById(R.id.myMessage);
            otherMessage = itemView.findViewById(R.id.otherMessage);
            myChats = itemView.findViewById(R.id.me);
            otherChats = itemView.findViewById(R.id.other);
            myDate = itemView.findViewById(R.id.myDate);
            otherDate = itemView.findViewById(R.id.otherDate);
        }
    }
}
