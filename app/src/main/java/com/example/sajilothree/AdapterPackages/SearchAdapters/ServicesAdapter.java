package com.example.sajilothree.AdapterPackages.SearchAdapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sajilothree.ActivitiesPackage.ServiceProvidersPackage.ServiceProvidersList;
import com.example.sajilothree.ModelsPackage.SearchModel.ServicesModel.ServicesModel;
import com.example.sajilothree.ModelsPackage.SPListSharePropertyModel.ShareModel;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.ServiceProviderListShare.SpListShareService;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.MyViewHolder> {
    private Context context;
    private ServicesModel services;

    public ServicesAdapter(Context context, ServicesModel serviceModels) {
        this.context = context;
        this.services = serviceModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.search_fragment_services_design,
                        parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.serviceName.setText(services.getResult().get(position).getProfessionName());

        Glide.with(context).load(services.getResult().get(position).getProfessionImage())
                .into(holder.serviceImage);

        onProfessionalClick(holder.parentCardView, position, holder.serviceImage, holder.serviceName);
    }

    // This is self made method to share the properties between activities
    private void onProfessionalClick(CardView parentCardView, final int position, final ImageView serviceImage, final TextView serviceName) {
        parentCardView.setOnClickListener(v -> {
            ShareModel shareModel = new ShareModel(services.getResult().get(position).getProfessionImage(),
                    services.getResult().get(position).getProfessionName());
            if (SpListShareService.addProperties(shareModel)) {
                Intent sharedIntent = new Intent(context, ServiceProvidersList.class);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(serviceImage, "professionImage");
                pairs[1] = new Pair<View, String>(serviceName, "professionHeading");

                ActivityOptions options = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.
                            makeSceneTransitionAnimation((Activity) context, pairs);
                }
                assert options != null;
                context.startActivity(sharedIntent, options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return services.getResult().size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView serviceName;
        ImageView serviceImage;
        CardView parentCardView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceName = itemView.findViewById(R.id.serviceName);
            serviceImage = itemView.findViewById(R.id.serviceImage);
            parentCardView = itemView.findViewById(R.id.parentCardView);
        }
    }
}
