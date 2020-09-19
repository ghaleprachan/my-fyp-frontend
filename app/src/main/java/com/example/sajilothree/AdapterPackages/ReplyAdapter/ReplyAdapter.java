package com.example.sajilothree.AdapterPackages.ReplyAdapter;

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
import com.example.sajilothree.ActivitiesPackage.SpProfilePackage.SpProfileActivity;
import com.example.sajilothree.EncodeUserPackage.EncodeUser;
import com.example.sajilothree.ModelsPackage.ProfileModelPackage.ProfileReviewsModel.ProfileReviewsModel;
import com.example.sajilothree.ModelsPackage.ReplyModel.ReplyModel;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.BookingServices.BookingRequest.BookingRequestService;
import com.example.sajilothree.ServicesPackage.UsernameHolder.OtherPersonUserId;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;
import com.example.sajilothree.UserTypeHolder.UserTypes;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.MyViewHolder> {
    private Context mContext;
    private ReplyModel allReviews;

    public ReplyAdapter(Context mContext, ReplyModel allReviews) {
        this.mContext = mContext;
        this.allReviews = allReviews;
    }

    @NonNull
    @Override
    public ReplyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
        Glide.with(mContext).load(BaseURL.BaseURL + allReviews.getResult().get(position).getUserProfileImage())
                .apply(options).into(holder.reviewerPhoto);

        holder.reviewerName.setText(allReviews.getResult().get(position).getFullName());
        holder.rating.setVisibility(View.GONE);
        holder.reviewerReview.setText(allReviews.getResult().get(position).getReply());
        holder.reviewDate.setText(BookingRequestService.chatSystemDate(
                allReviews.getResult().get(position).getReplyDate()));

        holder.reviewMoreOptions.setVisibility(View.GONE);
        onVisitProfileClick(holder.visitProfile, position);

        holder.reply.setVisibility(View.GONE);
    }

    private void onVisitProfileClick(LinearLayout visitProfile, int position) {
        visitProfile.setOnClickListener(v -> {
            try {
                if (OtherPersonUserId.encode(allReviews.getResult().get(position).getUserId(),
                        allReviews.getResult().get(position).getUsername())) {
                    if (allReviews.getResult().get(position).getUserType().equals(UserTypes.customer)) {
                        mContext.startActivity(new Intent(mContext, CProfileActivity.class));
                        ((AppCompatActivity) mContext).overridePendingTransition(R.anim.nav_default_pop_enter_anim,
                                R.anim.nav_default_pop_exit_anim);
                    } else if (allReviews.getResult().get(position).getUserType().equals(UserTypes.sp)) {
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

    @Override
    public int getItemCount() {
        return allReviews.getResult().size();
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
