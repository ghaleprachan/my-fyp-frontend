package com.example.sajilothree.AdapterPackages.SearchAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.ActivitiesPackage.SpProfilePackage.SpProfileActivity;
import com.example.sajilothree.ModelsPackage.SearchModel.RecommendedSPModel.RecommendedSPModel;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.UsernameHolder.OtherPersonUserId;

public class RecommendedSPAdapter extends RecyclerView.Adapter<RecommendedSPAdapter.MyViewHolder> {
    private Context mContext;
    private RecommendedSPModel serviceProviders;

    public RecommendedSPAdapter(Context mContext, RecommendedSPModel serviceProviders) {
        this.mContext = mContext;
        this.serviceProviders = serviceProviders;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.search_recommended_sp_design,
                        parent, false);
        return new RecommendedSPAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.profile_placeholder);
        Glide.with(mContext)
                .load(BaseURL.BaseURL + serviceProviders.getResult().get(position).getUserProfileImage())
                .apply(options).into(holder.spImage);
        holder.spName.setText(serviceProviders.getResult().get(position).getFullName());
        if (serviceProviders.getResult().get(position).getUserRating() != null) {
            holder.spRatings.setRating(Float.parseFloat(String.valueOf(serviceProviders.getResult().get(position).getUserRating())));
        } else {
            holder.spRatings.setRating(Float.parseFloat(String.valueOf(0.0)));
        }
        onVisitProfile(holder.parentVisitProfile, position);
    }

    private void onVisitProfile(CardView parentVisitProfile, int position) {
        parentVisitProfile.setOnClickListener(v -> {
            try {
                if (OtherPersonUserId.encode(serviceProviders.getResult().get(position).getUserId(),
                        serviceProviders.getResult().get(position).getUsername())) {
                    mContext.startActivity(new Intent(mContext, SpProfileActivity.class));
                    ((AppCompatActivity) mContext).overridePendingTransition(R.anim.nav_default_pop_enter_anim,
                            R.anim.nav_default_pop_exit_anim);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceProviders.getResult().size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView spImage;
        private TextView spName;
        private RatingBar spRatings;
        private CardView parentVisitProfile;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            spImage = itemView.findViewById(R.id.spImage);
            spName = itemView.findViewById(R.id.spName);
            spRatings = itemView.findViewById(R.id.spRatings);
            parentVisitProfile = itemView.findViewById(R.id.parentVisitProfile);
        }
    }
}
