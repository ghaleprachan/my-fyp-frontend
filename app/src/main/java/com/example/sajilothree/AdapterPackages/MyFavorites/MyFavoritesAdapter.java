package com.example.sajilothree.AdapterPackages.MyFavorites;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.ActivitiesPackage.CProfilePackage.CProfileActivity;
import com.example.sajilothree.ActivitiesPackage.SpProfilePackage.SpProfileActivity;
import com.example.sajilothree.AdapterPackages.SavedPostsAdapter.SavedOfferDetailsAdapter;
import com.example.sajilothree.ApiCallsPackage.UserFavorites.AddUserToFavorites;
import com.example.sajilothree.ModelsPackage.UserFavoriteModels.FavoritesDetails.Result;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.UsernameHolder.OtherPersonUserId;
import com.example.sajilothree.UserTypeHolder.UserTypes;

import java.util.List;

import lecho.lib.hellocharts.model.Line;

public class MyFavoritesAdapter extends RecyclerView.Adapter<MyFavoritesAdapter.MyViewHolder> {
    private Context context;
    private List<Result> favorites;

    public MyFavoritesAdapter(Context context, List<Result> favorites) {
        this.context = context;
        this.favorites = favorites;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_favorites_layout_design, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(favorites.get(position).getFullName());
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.profile_placeholder);
        Glide.with(context)
                .load(BaseURL.BaseURL + favorites.get(position).getUserProfileImage())
                .apply(options)
                .into(holder.profileImage);

        onRemoveClick(holder.remove, position);
        onVisitProfileClick(holder.visitProfile, position);
    }

    private void onVisitProfileClick(LinearLayout visitProfile, int position) {
        visitProfile.setOnClickListener(v -> {
            try {
                if (OtherPersonUserId.encode(favorites.get(position).getUserId(),
                        favorites.get(position).getUsername())) {
                    context.startActivity(new Intent(context, SpProfileActivity.class));
                    ((AppCompatActivity) context).overridePendingTransition(R.anim.nav_default_pop_enter_anim,
                            R.anim.nav_default_pop_exit_anim);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void onRemoveClick(Button remove, int position) {
        remove.setOnClickListener(v -> {
            if (OtherPersonUserId.encode(favorites.get(position).getUserId(), favorites.get(position).getUsername())) {
                if (AddUserToFavorites.addToFavorites(context)) {
                    favorites.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, favorites.size());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView profileImage;
        private TextView name;
        private Button remove;
        private LinearLayout visitProfile;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profileImage);
            name = itemView.findViewById(R.id.name);
            remove = itemView.findViewById(R.id.remove);
            visitProfile = itemView.findViewById(R.id.visitProfile);
        }
    }
}
