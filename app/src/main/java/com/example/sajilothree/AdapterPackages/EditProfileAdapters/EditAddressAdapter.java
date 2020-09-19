package com.example.sajilothree.AdapterPackages.EditProfileAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.ActivitiesPackage.EditProfile.AddNewDetailsForm.AddNewDetails;
import com.example.sajilothree.ActivitiesPackage.EditProfile.AddNewDetailsHolder.NewDetailsTypeHolder;
import com.example.sajilothree.ModelsPackage.RemoveContentModel.RemoveContentResponse;
import com.example.sajilothree.ModelsPackage.UpdateProfile.UpdatableDetails.Address;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.UpdateProfile.UpdateProfileService;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.List;

public class EditAddressAdapter extends RecyclerView.Adapter<EditAddressAdapter.MyViewHolder> {
    private Context mContext;
    private List<Address> addresses;
    private int index;

    public EditAddressAdapter(Context mContext, List<Address> addresses) {
        this.mContext = mContext;
        this.addresses = addresses;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.update_views_designs, parent, false);
        return new EditAddressAdapter.MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.address.setText(addresses.get(position).getDistrictName() + " - " +
                addresses.get(position).getMunicipalityName() + ", " +
                addresses.get(position).getCurrentLocation());

        holder.expand.setVisibility(View.VISIBLE);
        holder.content.setVisibility(View.GONE);
        onMoreClick(holder.expand, holder.content, holder.hide, position);
        onLessClick(holder.expand, holder.content, holder.hide);
        holder.addressNum.setText("Address " + (position + 1));

        onAddressClick(holder.address, position);
        onRemoveClick(holder.remove, holder.loadingAnimation, position);

        /*setDistricts(holder.districts, holder.municipality,
                addresses.get(position).getDistrictName(), addresses.get(position).getMunicipalityName());
        onSelectedDistrictChange(holder.districts, holder.municipality,
                addresses.get(position).getMunicipalityName());*/
    }

    /*
     * This is to remove the items from the newAddresses
     * It will check if the address is new or old
     * if id is new it will directly delete address from the arraylist
     * while if its old it will hit the api to delelte and on return notify the set change in adapter*/
    private void onRemoveClick(ImageView remove, ImageView loadingAnimation, int position) {
        remove.setOnClickListener(v -> {
            if (UpdateProfileService.newAddresses.get(position).getAddressId() == -1) {
                UpdateProfileService.newAddresses.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, UpdateProfileService.newAddresses.size());
            } else {
                removeApiCall(position, remove, loadingAnimation);
            }
        });
    }

    private void removeApiCall(int position, ImageView remove, ImageView loadingAnimation) {
        try {
            remove.setVisibility(View.GONE);
            loadingAnimation.setVisibility(View.VISIBLE);
            AnimationDrawable animation = (AnimationDrawable) loadingAnimation.getDrawable();
            animation.start();

            StringRequest request = new StringRequest(
                    Request.Method.DELETE,
                    BaseURL.removeAddress + "?id=" + UpdateProfileService.newAddresses.get(position).getAddressId(),
                    response -> {
                        remove.setVisibility(View.VISIBLE);
                        loadingAnimation.setVisibility(View.GONE);
                        animation.stop();
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            RemoveContentResponse contentResponse = gson.fromJson(response, RemoveContentResponse.class);
                            if (contentResponse.getSuccess()) {
                                UpdateProfileService.newAddresses.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, UpdateProfileService.newAddresses.size());
                            } else {
                                Toast.makeText(mContext, "Failed: " + contentResponse.getResult(), Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception ex) {
                            Toast.makeText(mContext, "Exception: " + ex, Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        remove.setVisibility(View.VISIBLE);
                        loadingAnimation.setVisibility(View.GONE);
                        animation.stop();
                        Toast.makeText(mContext, "Remove Failed", Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(mContext, "Failed to remove: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    private void onAddressClick(TextView address, int position) {
        address.setOnClickListener(v -> {
            if (NewDetailsTypeHolder.addNewPosition(position) && NewDetailsTypeHolder.setAddNew("Address")) {
                mContext.startActivity(new Intent(mContext, AddNewDetails.class));
                ((AppCompatActivity) mContext).overridePendingTransition(R.anim.bottom_up, R.anim.nothing);
            }
        });
    }


    /*private void onSelectedDistrictChange(Spinner districts, Spinner municipality,
                                          String selectedMunicipality) {
        districts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setMunicipalities(districts.getSelectedItem().toString(), municipality, selectedMunicipality);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setDistricts(Spinner districts, Spinner municipality, String selectedDistrict,
                              String selectedMunicipality) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                android.R.layout.simple_spinner_dropdown_item, DistrictServices.filterDistricts()) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        districts.setAdapter(adapter);
        if (selectedDistrict != null) {
            int spinnerPosition = adapter.getPosition(selectedDistrict);
            districts.setSelection(spinnerPosition);
        }
        setMunicipalities(districts.getSelectedItem().toString(), municipality, selectedMunicipality);
    }

    private void setMunicipalities(String selectedDistrict, Spinner municipality, String selectedMunicipality) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                android.R.layout.simple_spinner_dropdown_item,
                DistrictServices.getMunicipalityName(selectedDistrict)) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        municipality.setAdapter(adapter);
        if (selectedMunicipality != null) {
            int spinnerPosition = adapter.getPosition(selectedMunicipality);
            municipality.setSelection(spinnerPosition);
        }
    }
*/

    private void onLessClick(ImageView expand, LinearLayout content, ImageView hide) {
        hide.setOnClickListener(v -> {
            content.setVisibility(View.GONE);
            expand.setVisibility(View.VISIBLE);
            hide.setVisibility(View.GONE);
        });
    }

    private void onMoreClick(ImageView expand, LinearLayout content, ImageView hide, int position) {
        expand.setOnClickListener(v -> {
            if (content.getVisibility() == View.VISIBLE &&
                    hide.getVisibility() == View.VISIBLE && expand.getVisibility() == View.GONE) {
                hide.setVisibility(View.GONE);
                expand.setVisibility(View.VISIBLE);
                content.setVisibility(View.GONE);
            } else {
                index = position;
                notifyDataSetChanged();
            }
        });
        if (index == position) {
            hide.setVisibility(View.VISIBLE);
            expand.setVisibility(View.GONE);
            content.setVisibility(View.VISIBLE);
        } else {
            hide.setVisibility(View.GONE);
            expand.setVisibility(View.VISIBLE);
            content.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView address, addressNum;
        private ImageView expand, hide, remove, loadingAnimation;
        private LinearLayout content;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.description);
            addressNum = itemView.findViewById(R.id.title);
            expand = itemView.findViewById(R.id.expand);
            hide = itemView.findViewById(R.id.hide);
            content = itemView.findViewById(R.id.content);
            remove = itemView.findViewById(R.id.remove);
            loadingAnimation = itemView.findViewById(R.id.loadingAnimation);
        }
    }
}
