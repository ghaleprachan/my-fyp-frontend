package com.example.sajilothree.AdapterPackages.SavedPostsAdapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.ActivitiesPackage.CProfilePackage.CProfileActivity;
import com.example.sajilothree.ActivitiesPackage.SpProfilePackage.SpProfileActivity;
import com.example.sajilothree.ModelsPackage.SavedPostDetails.Result;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.BookingServices.BookingRequest.BookingRequestService;
import com.example.sajilothree.ServicesPackage.UsernameHolder.OtherPersonUserId;
import com.example.sajilothree.UserTypeHolder.UserTypes;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SavedOfferDetailsAdapter extends RecyclerView.Adapter<SavedOfferDetailsAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<Result> offerDetails;

    public SavedOfferDetailsAdapter(Context mContext, ArrayList<Result> offerDetails) {
        this.mContext = mContext;
        this.offerDetails = offerDetails;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.offer_fragment_design, parent, false);
        return new SavedOfferDetailsAdapter.MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.moreOptions.setVisibility(View.GONE);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.shimmer_view_background);
        if (offerDetails.get(position).getOfferImage() != null) {
            Glide.with(mContext)
                    .load(BaseURL.BaseURL + offerDetails.get(position).getOfferImage())
                    .apply(options)
                    .into(holder.offerImage);
        } else {
            holder.offerImage.setVisibility(View.GONE);
        }

        Glide.with(mContext).load(BaseURL.BaseURL + offerDetails.get(position).getPostedByImage())
                .apply(options).into(holder.profileImage);

        holder.profileName.setText(offerDetails.get(position).getPostedByName());
        holder.postDescription.setText(offerDetails.get(position).getOfferDescription());
        holder.postDate.setText(BookingRequestService.chatSystemDate(offerDetails.get(position).getPostedDate()));

        onCallUserClick(holder.callUser, position);
        visitProfile(holder.visitProfile, position);

        try {
            holder.validDate.setText("Valid Date: " + BookingRequestService.dateConversion(
                    offerDetails.get(position).getValidDate()));
        } catch (Exception ex) {
            Toast.makeText(mContext, "Date Exception: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void visitProfile(LinearLayout visitProfile, final int position) {
        visitProfile.setOnClickListener(v -> {
            try {
                if (OtherPersonUserId.encode(offerDetails.get(position).getPostedById(),
                        offerDetails.get(position).getPostedByUsername())) {
                    if (offerDetails.get(position).getUserType().equals(UserTypes.customer)) {
                        mContext.startActivity(new Intent(mContext, CProfileActivity.class));
                        ((AppCompatActivity) mContext).overridePendingTransition(R.anim.nav_default_pop_enter_anim,
                                R.anim.nav_default_pop_exit_anim);
                    } else if (offerDetails.get(position).getUserType().equals(UserTypes.sp)) {
                        mContext.startActivity(new Intent(mContext, SpProfileActivity.class));
                        ((AppCompatActivity) mContext).overridePendingTransition(R.anim.nav_default_pop_enter_anim,
                                R.anim.nav_default_pop_exit_anim);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void onCallUserClick(ImageView callUser, int position) {
        callUser.setOnClickListener(v -> {
            try {
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mContext,
                        android.R.layout.simple_list_item_1);
                for (int i = 0; i < offerDetails.get(position).getContacts().size(); i++) {
                    arrayAdapter.add(offerDetails.get(position).getContacts().get(i).getContactNumber());
                }
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
                builder.setTitle(R.string.select_to_call);
                builder.setIcon(R.drawable.ic_call_black_24dp);
                if (arrayAdapter.getCount() == 0) {
                    builder.setMessage("No Contacts Available.");
                } else {
                    builder.setAdapter(arrayAdapter, (dialog, item) -> {
                        mContext.startActivity(new Intent(Intent.ACTION_DIAL,
                                Uri.parse("tel:" + arrayAdapter.getItem(item))));
                    });
                }
                AlertDialog alert = builder.create();
                alert.show();
            } catch (Exception ex) {
                Toast.makeText(mContext, "Something Wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return offerDetails.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView profileImage;
        private TextView profileName, postDate, validDate;
        private ImageView offerImage, moreOptions, callUser;
        private ExpandableTextView postDescription;
        private LinearLayout visitProfile;

        public MyViewHolder(@NonNull View view) {
            super(view);
            profileImage = view.findViewById(R.id.profileImage);
            profileName = view.findViewById(R.id.profileName);
            postDescription = view.findViewById(R.id.expand_text_view);
            offerImage = view.findViewById(R.id.postImage);
            moreOptions = view.findViewById(R.id.moreOptions);
            postDate = view.findViewById(R.id.postedDate);
            visitProfile = view.findViewById(R.id.viewProfile);
            callUser = view.findViewById(R.id.callUser);
            validDate = view.findViewById(R.id.validateDate);
        }
    }
}
