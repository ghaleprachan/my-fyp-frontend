package com.example.sajilothree.AdapterPackages.ProfileAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sajilothree.ModelsPackage.ProfileModelPackage.ProfileAbout.UserProfession;
import com.example.sajilothree.R;

import java.util.List;

public class AboutFragmentAdapter extends RecyclerView.Adapter<AboutFragmentAdapter.MyViewHolder> {
    private Context context;
    private List<UserProfession> userProfessions;

    public AboutFragmentAdapter(Context context, List<UserProfession> userProfessions) {
        this.context = context;
        this.userProfessions = userProfessions;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.profile_about_profession_design, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.professions.setText(userProfessions.get(position).getProfessionName());
    }

    @Override
    public int getItemCount() {
        return userProfessions.size();
    }

    static class MyViewHolder extends ProfileOfferAdapter.ViewHolder {
        private TextView professions;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            professions = itemView.findViewById(R.id.professions);
        }
    }
}
