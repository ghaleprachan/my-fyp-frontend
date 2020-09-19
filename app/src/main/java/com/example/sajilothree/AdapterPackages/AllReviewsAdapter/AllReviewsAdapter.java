package com.example.sajilothree.AdapterPackages.AllReviewsAdapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.ActivitiesPackage.CProfilePackage.CProfileActivity;
import com.example.sajilothree.ActivitiesPackage.FeedbackReply.FeedbackReplyActivity;
import com.example.sajilothree.EncodeUserPackage.EncodeUser;
import com.example.sajilothree.ActivitiesPackage.SpProfilePackage.SpProfileActivity;
import com.example.sajilothree.ModelsPackage.ProfileModelPackage.ProfileReviewsModel.ProfileReviewsModel;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.BookingServices.BookingRequest.BookingRequestService;
import com.example.sajilothree.ServicesPackage.UsernameHolder.OtherPersonUserId;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;
import com.example.sajilothree.UserTypeHolder.UserTypes;
import com.google.firebase.auth.PhoneAuthOptions;
import com.ms.square.android.expandabletextview.ExpandableTextView;


import de.hdodenhof.circleimageview.CircleImageView;

public class AllReviewsAdapter extends RecyclerView.Adapter<AllReviewsAdapter.MyViewHolder> {
    private Context mContext;
    private ProfileReviewsModel allReviews;

    public AllReviewsAdapter(Context mContext, ProfileReviewsModel allReviews) {
        this.mContext = mContext;
        this.allReviews = allReviews;
    }

    @NonNull
    @Override
    public AllReviewsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.all_review_design, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.profile_placeholder);
        Glide.with(mContext).load(BaseURL.BaseURL + allReviews.getResult().getFeedbacks().get(position).getUserProfileImage())
                .apply(options).into(holder.reviewerPhoto);

        holder.reviewerName.setText(allReviews.getResult().getFeedbacks().get(position).getFullName());
        holder.rating.setRating(Float.parseFloat(String.valueOf(
                allReviews.getResult().getFeedbacks().get(position).getRating()
        )));
        holder.reviewerReview.setText(allReviews.getResult().getFeedbacks().get(position).getFeedback1());
        holder.reviewDate.setText(BookingRequestService.chatSystemDate(
                allReviews.getResult().getFeedbacks().get(position).getFeedbackDate()));

        onReviewMoreOptionsClick(holder.reviewMoreOptions, position);
        onVisitProfileClick(holder.visitProfile, position);
        if (allReviews.getResult().getFeedbacks().get(position).getReplayCount() == 0) {
            holder.reply.setText("Add Reply");
        } else {
            holder.reply.setText(allReviews.getResult().getFeedbacks().get(position).getReplayCount().toString() + " Reply");
        }

        onReplyClick(holder.reply, position);
    }

    private void onReplyClick(TextView reply, int position) {
        reply.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, FeedbackReplyActivity.class);
            intent.putExtra("FeedbackId", allReviews.getResult().getFeedbacks().get(position).getFeedbackId());
            mContext.startActivity(intent);
            ((AppCompatActivity) mContext).overridePendingTransition(R.anim.bottom_up, R.anim.nothing);
        });
    }

    private void onVisitProfileClick(LinearLayout visitProfile, int position) {
        visitProfile.setOnClickListener(v -> {
            try {
                if (OtherPersonUserId.encode(allReviews.getResult().getFeedbacks().get(position).getFeedbackBy(),
                        allReviews.getResult().getFeedbacks().get(position).getUsername())) {
                    if (allReviews.getResult().getFeedbacks().get(position).getUserType().equals(UserTypes.customer)) {
                        mContext.startActivity(new Intent(mContext, CProfileActivity.class));
                        ((AppCompatActivity) mContext).overridePendingTransition(R.anim.nav_default_pop_enter_anim,
                                R.anim.nav_default_pop_exit_anim);
                    } else if (allReviews.getResult().getFeedbacks().get(position).getUserType().equals(UserTypes.sp)) {
                        mContext.startActivity(new Intent(mContext, SpProfileActivity.class));
                        ((AppCompatActivity) mContext).overridePendingTransition(R.anim.nav_default_pop_enter_anim,
                                R.anim.nav_default_pop_exit_anim);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void onReviewMoreOptionsClick(ImageView reviewMoreOptions, int position) {
        reviewMoreOptions.setOnClickListener(v -> {
            final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Actions");
            builder.setItems(getUserOptions(position), (dialog, item) -> {
                Toast.makeText(mContext, getUserOptions(position)[item], Toast.LENGTH_SHORT).show();
            });
            AlertDialog alert = builder.create();
            alert.show();
            Toast.makeText(mContext, "Position " + position, Toast.LENGTH_SHORT).show();
        });
    }

    private CharSequence[] getUserOptions(int position) {
        if (Username.username.equals(EncodeUser.enCodeUserId(
                allReviews.getResult().getFeedbacks().get(position).getFeedbackBy(),
                allReviews.getResult().getFeedbacks().get(position).getUsername()))) {
            return new String[]{"Report", "Delete"};
        } else {
            return new String[]{"Report"};
        }
    }

    @Override
    public int getItemCount() {
        return allReviews.getResult().getFeedbacks().size();
    }

    static class MyViewHolder extends ViewHolder /*implements View.OnCreateContextMenuListener*/ {
        private CircleImageView reviewerPhoto;
        private TextView reviewerName, reviewDate, reply;
        private ExpandableTextView reviewerReview;
        private RatingBar rating;
        private ImageView reviewMoreOptions;
        private LinearLayout visitProfile;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewerPhoto = itemView.findViewById(R.id.reviewerPhoto);
            reviewerName = itemView.findViewById(R.id.reviewerName);
            reviewDate = itemView.findViewById(R.id.reviewDate);
            reviewerReview = itemView.findViewById(R.id.expand_text_view);
            rating = itemView.findViewById(R.id.ratings);
            reviewMoreOptions = itemView.findViewById(R.id.reviewMoreOptions);
            visitProfile = itemView.findViewById(R.id.visitProfile);
            reply = itemView.findViewById(R.id.reply);
            /*reviewMoreOptions.setOnCreateContextMenuListener(this);
            onMoreOptionClick();*/
        }
    }
}
