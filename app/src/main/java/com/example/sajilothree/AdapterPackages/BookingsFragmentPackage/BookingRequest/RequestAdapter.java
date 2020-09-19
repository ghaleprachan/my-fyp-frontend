package com.example.sajilothree.AdapterPackages.BookingsFragmentPackage.BookingRequest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.ActivitiesPackage.BookingProcessPackage.SelectedRequestPosition.SelectedRequestPosition;
import com.example.sajilothree.ActivitiesPackage.BookingProcessPackage.RequestPackage.RequestActivity;
import com.example.sajilothree.EncodeUserPackage.EncodeUser;
import com.example.sajilothree.ModelsPackage.BookingDetailsModel.RequestModel.Result;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.BookingServices.BookingRequest.BookingRequestService;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.MyViewHolder> {
    private Context context;
    private List<Result> bookingRequests;

    public RequestAdapter(Context context, List<Result> bookingRequests) {
        this.context = context;
        this.bookingRequests = bookingRequests;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.request_fragment_design, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        /*
         * This is to setting update
         * Convert string into date
         * convert date into string*/
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = null;
            try {
                date = sdf.parse(bookingRequests.get(position).getBookingDate());
            } catch (Exception ex) {
                Toast.makeText(context, "Exception: " + ex, Toast.LENGTH_SHORT).show();
            }
            SimpleDateFormat sdf2 = new SimpleDateFormat("MMM dd HH:mm a");
            assert date != null;
            String dateOne = sdf2.format(date);
            holder.date.setText(dateOne);
        } catch (Exception ex) {
            Toast.makeText(context, "Date Parse failed.", Toast.LENGTH_SHORT).show();
        }
        if (Username.username.equals(EncodeUser.enCodeUserId(bookingRequests.get(position).getCustomerId(),
                bookingRequests.get(position).getCustomerUsername()))) {
            holder.requestDesc.setText(Html.fromHtml("You have sent request to " + "<b>" + bookingRequests.get(position).getSpecialistName() + "</b>"));
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.profile_placeholder);
            Glide.with(context)
                    .load(BaseURL.BaseURL + bookingRequests.get(position).getSpecialistImage())
                    .apply(options).into(holder.requestImage);
        } else {
            holder.requestDesc.setText(Html.fromHtml("<b>" + bookingRequests.get(position).getCustomerName() + "</b>" + " " +
                    "has sent you booking request."));
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.profile_placeholder);
            Glide.with(context)
                    .load(BaseURL.BaseURL + bookingRequests.get(position).getCustomerImage())
                    .apply(options).into(holder.requestImage);
        }
        onParentClick(holder.parent, position);
    }

    private void onParentClick(CardView parent, int position) {
        parent.setOnClickListener(v -> {
            try {
                Intent myIntent = new Intent(context, RequestActivity.class);
                if (Username.username.equals(EncodeUser.enCodeUserId(bookingRequests.get(position).getCustomerId(),
                        bookingRequests.get(position).getCustomerUsername()))) {
                    if (SelectedRequestPosition.setContents(position, true, BookingRequestService.bookingRequests.get(position).getBookingId())) {
                        context.startActivity(myIntent);
                    }
                } else {
                    if (SelectedRequestPosition.setContents(position, false, BookingRequestService.bookingRequests.get(position).getBookingId())) {
                        context.startActivity(myIntent);
                    }
                }
            } catch (Exception ex) {
                Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookingRequests.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView requestDesc, date;
        private CircleImageView requestImage;
        private CardView parent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            requestDesc = itemView.findViewById(R.id.description);
            requestImage = itemView.findViewById(R.id.requestImage);
            date = itemView.findViewById(R.id.sendDate);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}
