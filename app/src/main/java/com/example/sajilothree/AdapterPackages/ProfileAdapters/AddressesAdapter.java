package com.example.sajilothree.AdapterPackages.ProfileAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sajilothree.ModelsPackage.ProfileModelPackage.ProfileDetails.Address;
import com.example.sajilothree.R;

import java.util.List;

public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.MyViewHolder> {
    private Context mContext;
    private List<Address> addresses;

    public AddressesAdapter(Context mContext, List<Address> addresses) {
        this.mContext = mContext;
        this.addresses = addresses;
    }

    @NonNull
    @Override
    public AddressesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.profile_addresses_design, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.address.setText(addresses.get(position).getDistrictName() + " - " +
                addresses.get(position).getMunicipalityName() + ", " + addresses.get(position).getCurrentLocation());
        holder.aboutAddress.setText(addresses.get(position).getAddressType());
    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView address, aboutAddress;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.title);
            aboutAddress = itemView.findViewById(R.id.aboutTitle);
        }
    }
}
