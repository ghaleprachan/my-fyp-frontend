package com.example.sajilothree.AdapterPackages.NotificationAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sajilothree.ModelsPackage.Notification.Result;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.BookingServices.BookingDetailsProfile.BookingDetailsServices;
import com.example.sajilothree.ServicesPackage.BookingServices.BookingRequest.BookingRequestService;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Result> notifications;

    public NotificationAdapter(Context context, ArrayList<Result> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.notification_fragment_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.notificationHeading.setText(notifications.get(position).getNotificationType());
        holder.notificationDesc.setText(notifications.get(position).getNotificationText());
        holder.date.setText(BookingRequestService.chatSystemDate(notifications.get(position).getNotificationDate()));
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ExpandableTextView notificationDesc;
        private TextView notificationHeading, date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationDesc = itemView.findViewById(R.id.expand_text_view);
            notificationHeading = itemView.findViewById(R.id.notificationHeading);
            date = itemView.findViewById(R.id.date);
        }
    }
}
