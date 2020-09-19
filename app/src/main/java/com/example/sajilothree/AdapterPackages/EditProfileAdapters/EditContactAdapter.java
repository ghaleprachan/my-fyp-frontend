package com.example.sajilothree.AdapterPackages.EditProfileAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.ActivitiesPackage.EditProfile.AddNewDetailsForm.AddNewDetails;
import com.example.sajilothree.ActivitiesPackage.EditProfile.AddNewDetailsHolder.NewDetailsTypeHolder;
import com.example.sajilothree.ModelsPackage.RemoveContentModel.RemoveContentResponse;
import com.example.sajilothree.ModelsPackage.UpdateProfile.UpdatableDetails.Contact;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.UpdateProfile.UpdateProfileService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class EditContactAdapter extends RecyclerView.Adapter<EditContactAdapter.MyViewHolder> {
    private Context mContext;
    private List<Contact> contacts;
    private int index;

    public EditContactAdapter(Context mContext, List<Contact> contacts) {
        this.mContext = mContext;
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.update_views_designs, parent, false);
        return new EditContactAdapter.MyViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.contactNumber.setText(contacts.get(position).getContactNumber());
        onMoreClick(holder.expand, holder.contactContents, holder.hide, position);
        onLessClick(holder.expand, holder.contactContents, holder.hide);
        holder.title.setText("Contact " + (position + 1));

        onContactClick(holder.contactNumber, position);

        onRemoveClick(holder.remove, holder.loadingAnimation, position);
    }

    private void onRemoveClick(ImageView remove, ImageView loadingAnimation, int position) {
        remove.setOnClickListener(v -> {
            if (UpdateProfileService.newContacts.get(position).getContactId() == -1) {
                UpdateProfileService.newContacts.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, UpdateProfileService.newContacts.size());
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
                    BaseURL.removeContact + "?id=" + UpdateProfileService.newContacts.get(position).getContactId(),
                    response -> {
                        remove.setVisibility(View.VISIBLE);
                        loadingAnimation.setVisibility(View.GONE);
                        animation.stop();
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            RemoveContentResponse contentResponse = gson.fromJson(response, RemoveContentResponse.class);
                            if (contentResponse.getSuccess()) {
                                UpdateProfileService.newContacts.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, UpdateProfileService.newContacts.size());
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

    private void onContactClick(TextView contactNumber, int position) {
        contactNumber.setOnClickListener(v -> {
            if (NewDetailsTypeHolder.addNewPosition(position) && NewDetailsTypeHolder.setAddNew("Contact")) {
                mContext.startActivity(new Intent(mContext, AddNewDetails.class));
                ((AppCompatActivity) mContext).overridePendingTransition(R.anim.bottom_up, R.anim.nothing);
            }
        });
    }

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
        return contacts.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView expand, hide, remove, loadingAnimation;
        private TextView contactNumber, title;
        private LinearLayout contactContents;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            contactNumber = itemView.findViewById(R.id.description);
            expand = itemView.findViewById(R.id.expand);
            hide = itemView.findViewById(R.id.hide);
            title = itemView.findViewById(R.id.title);
            contactContents = itemView.findViewById(R.id.content);
            remove = itemView.findViewById(R.id.remove);
            loadingAnimation = itemView.findViewById(R.id.loadingAnimation);
        }
    }
}
