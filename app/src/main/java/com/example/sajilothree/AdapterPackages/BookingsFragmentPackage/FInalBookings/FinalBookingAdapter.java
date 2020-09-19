package com.example.sajilothree.AdapterPackages.BookingsFragmentPackage.FInalBookings;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.ActivitiesPackage.BookingProcessPackage.FinalBookingDetailsPackage.FinalBookingDetails;
import com.example.sajilothree.EncodeUserPackage.EncodeUser;
import com.example.sajilothree.ModelsPackage.BookingDetailsModel.FinalBookingModelPackage.Result;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.BookingServices.BookingRequest.BookingRequestService;
import com.example.sajilothree.ServicesPackage.BookingServices.FinalBooking.FinalBookingServices;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;

import java.util.ArrayList;
import java.util.Objects;

public class FinalBookingAdapter extends RecyclerView.Adapter<FinalBookingAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<Result> finalBookings;

    public FinalBookingAdapter(Context mContext, ArrayList<Result> finalBookings) {
        this.mContext = mContext;
        this.finalBookings = finalBookings;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.final_booking_layout_design, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.profile_placeholder);
        String[] district = finalBookings.get(position).getCustomerAddress().split(" ", 2);
        holder.address.setText(district[0]);
        holder.bookDate.setText("Booked on " + BookingRequestService.onlyDateDay(finalBookings.get(position).getBookingDate()));
        holder.serviceDate.setText(BookingRequestService.dateTime(finalBookings.get(position).getServiceDate()));
        if (Objects.equals(EncodeUser.enCodeUserId(finalBookings.get(position).getCustomerId(),
                finalBookings.get(position).getCustomerUserName()), Username.username)) {
            Glide.with(mContext)
                    .load(BaseURL.BaseURL + finalBookings.get(position).getSpecialistImage())
                    .apply(options)
                    .into(holder.userImage);
            holder.userName.setText(finalBookings.get(position).getSpecialistName());
        } else {
            Glide.with(mContext)
                    .load(BaseURL.BaseURL + finalBookings.get(position).getCustomerImage())
                    .apply(options)
                    .into(holder.userImage);
            holder.userName.setText(finalBookings.get(position).getCustomerName());
        }
        onParentCardClick(holder.parentCardView, position);
    }

    private void onParentCardClick(CardView parentCardView, int position) {
        parentCardView.setOnClickListener(v -> {
            try {

                if (FinalBookingServices.setBookingId(finalBookings.get(position).getBookingId())) {
                    mContext.startActivity(new Intent(mContext, FinalBookingDetails.class));
                    ((AppCompatActivity) mContext).overridePendingTransition(R.anim.bottom_up, R.anim.nothing);
                }
            } catch (Exception ex) {
                Toast.makeText(mContext, "Deleted Item", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return finalBookings.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView userImage;
        private CardView parentCardView;
        private TextView userName;
        private TextView serviceDate;
        private TextView address;
        private TextView bookDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            parentCardView = itemView.findViewById(R.id.parentCardView);
            userImage = itemView.findViewById(R.id.userPhoto);
            userName = itemView.findViewById(R.id.userName);
            serviceDate = itemView.findViewById(R.id.serviceDate);
            address = itemView.findViewById(R.id.address);
            bookDate = itemView.findViewById(R.id.bookedDate);
        }
    }
}
