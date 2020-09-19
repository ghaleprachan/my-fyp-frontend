package com.example.sajilothree.AdapterPackages.SearchAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sajilothree.ModelsPackage.SearchModel.ServiceModel;
import com.example.sajilothree.R;

import java.util.ArrayList;

public class RecentSearchServices extends RecyclerView.Adapter<RecentSearchServices.MyViewHolder> {
    private Context context;
    private ArrayList<ServiceModel> serviceModels;

    public RecentSearchServices(Context context, ArrayList<ServiceModel> serviceModels) {
        this.context = context;
        this.serviceModels = serviceModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.search_fragment_recent_design,
                        parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.profileImage.setImageResource(serviceModels.get(position).getServiceImage());
        holder.profileName.setText(serviceModels.get(position).getServiceName());
    }

    @Override
    public int getItemCount() {
        return serviceModels.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView profileName;
        ImageView profileImage;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            profileName = itemView.findViewById(R.id.personName);
            profileImage = itemView.findViewById(R.id.profileImage);
        }
    }
}
