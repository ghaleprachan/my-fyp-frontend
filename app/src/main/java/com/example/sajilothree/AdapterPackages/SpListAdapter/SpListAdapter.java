package com.example.sajilothree.AdapterPackages.SpListAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.sajilothree.ModelsPackage.SPListSharePropertyModel.SPListAPIModel.SpListModel;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.UsernameHolder.OtherPersonUserId;

import java.util.Base64;


public class SpListAdapter extends RecyclerView.Adapter<SpListAdapter.MyViewHolder> {
    private Context context;
    private SpListModel spListModel;

    public SpListAdapter(Context context, SpListModel spListModel) {
        this.context = context;
        this.spListModel = spListModel;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.sp_provider_list_design, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.spRating.setRating(Float.parseFloat(String.valueOf(spListModel.getResult().get(position).getUserRating())));
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.profile_placeholder);

        Glide.with(context)
                .load(BaseURL.BaseURL + spListModel.getResult().get(position).getUserProfileImage())
                .apply(options).into(holder.spImage);
        holder.spName.setText(spListModel.getResult().get(position).getFullName());
        holder.spProfession.setText(spListModel.getResult().get(position).getProfessionName());
        visitProfileClick(holder.visitProfile, position);
    }

    private void visitProfileClick(CardView visitProfile, int position) {
        visitProfile.setOnClickListener(v -> {
            try {
                if (OtherPersonUserId.encode(spListModel.getResult().get(position).getUserId(),
                        spListModel.getResult().get(position).getUsername())) {
                    context.startActivity(new Intent(context, SpProfileActivity.class));
                    ((AppCompatActivity) context).overridePendingTransition(R.anim.nav_default_pop_enter_anim,
                            R.anim.nav_default_pop_exit_anim);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public int getItemCount() {
        return spListModel.getResult().size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView spImage;
        private TextView spName, spProfession;
        private RatingBar spRating;
        private CardView visitProfile;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            spImage = itemView.findViewById(R.id.spProfileImage);
            spName = itemView.findViewById(R.id.spName);
            spProfession = itemView.findViewById(R.id.spProfession);
            spRating = itemView.findViewById(R.id.spRatings);
            visitProfile = itemView.findViewById(R.id.visitSPProfile);
        }
    }
}
