package com.example.sajilothree.AdapterPackages.DisplaySearchAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sajilothree.ActivitiesPackage.SpProfilePackage.SpProfileActivity;
import com.example.sajilothree.ModelsPackage.SearchResultPacakge.SearchResultModel;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.UsernameHolder.OtherPersonUserId;


import de.hdodenhof.circleimageview.CircleImageView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
    private Context mContext;
    private SearchResultModel searchResults;

    public SearchAdapter(Context mContext, SearchResultModel searchResults) {
        this.mContext = mContext;
        this.searchResults = searchResults;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.search_result_desing, parent, false);
        return new SearchAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.profile_placeholder);
        Glide.with(mContext).load(searchResults.getResult().get(position).getUserProfileImage())
                .apply(options).into(holder.userProfileImage);

        holder.ratingBar.setRating(
                Float.parseFloat(String.valueOf(searchResults.getResult().get(position).getUserRating())));
        holder.profileName.setText(searchResults.getResult().get(position).getFullName());
        holder.userProfession.setText(searchResults.getResult().get(position).getProfessionName());
        onVisitProfile(holder.visitProfile, position);
    }

    private void onVisitProfile(LinearLayout visitProfile, int position) {
        visitProfile.setOnClickListener(v->{
            try {
                /// you need to change here 
                // TODO: 24-Mar-20 change here add username from api
                if (OtherPersonUserId.encode(searchResults.getResult().get(position).getUserId(),
                        searchResults.getResult().get(position).getUserName())) {
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
        return searchResults.getResult().size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView userProfileImage;
        private TextView profileName, userProfession;
        private RatingBar ratingBar;
        private LinearLayout visitProfile;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userProfileImage = itemView.findViewById(R.id.userProfileImage);
            profileName = itemView.findViewById(R.id.profileName);
            ratingBar = itemView.findViewById(R.id.ratings);
            userProfession = itemView.findViewById(R.id.userProfession);
            visitProfile = itemView.findViewById(R.id.parentVisitProfile);
        }
    }
}
