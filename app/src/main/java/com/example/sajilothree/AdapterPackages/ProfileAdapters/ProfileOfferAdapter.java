package com.example.sajilothree.AdapterPackages.ProfileAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.ModelsPackage.ProfilePostsModelPackage.PostsModel;
import com.example.sajilothree.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileOfferAdapter extends RecyclerView.Adapter<ProfileOfferAdapter.ViewHolder> {
    private Context context;
    private PostsModel allPosts;

    public ProfileOfferAdapter(Context context, PostsModel allPosts) {
        this.context = context;
        this.allPosts = allPosts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.profile_post_layout_design, parent, false);
        return new ProfileOfferAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.profile_placeholder);
        if (allPosts.getResult().getOffers().get(position).getOfferImage() == null) {
            holder.myPostImage.setVisibility(View.GONE);
        } else {
            Glide.with(context).load(BaseURL.BaseURL + allPosts.getResult().getOffers().get(position).getOfferImage())
                    .into(holder.myPostImage);
        }
        Glide.with(context).load(allPosts.getResult().getImage())
                .apply(options).into(holder.myImage);

        holder.myName.setText(allPosts.getResult().getFullname());
        holder.myPostDescription.setText(allPosts.getResult().getOffers().get(position).getOfferDescription());
        holder.myPostedDate.setText(allPosts.getResult().getOffers().get(position).getPostedDate());
        holder.postType.setText(R.string.typeOffer);
        try {
            Date getDate = Calendar.getInstance().getTime();
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(getDate);

            String input = allPosts.getResult().getOffers().get(position).getValidDate();
            String output = input.substring(0, 10);

            Date currentDate = dateFormat.parse(formattedDate);
            Date offerDate = dateFormat.parse(output);
            if (Objects.requireNonNull(offerDate).after(currentDate)) {
                holder.myOfferValidDate.setText(R.string.valid);
            } else {
                holder.myOfferValidDate.setText(R.string.expired);
            }
        } catch (Exception ex) {
            Toast.makeText(context, "Error: " + ex, Toast.LENGTH_SHORT).show();
            holder.myOfferValidDate.setVisibility(View.GONE);
        }
        onMoreOptions(holder.moreOptions, position);
    }

    private BottomSheetDialog bottomSheetDialog;

    private void onMoreOptions(ImageView moreOptions, int position) {
        moreOptions.setOnClickListener(v -> {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            Toast.makeText(context, "Make Text " + position, Toast.LENGTH_SHORT).show();
            assert layoutInflater != null;
            @SuppressLint("InflateParams") View modalBottomSheet = layoutInflater.inflate(R.layout.profile_more_options, null);
            bottomSheetDialog = new BottomSheetDialog(context);
            bottomSheetDialog.setContentView(modalBottomSheet);
            bottomSheetDialog.setCanceledOnTouchOutside(true);
            bottomSheetDialog.setCancelable(true);
            bottomSheetDialog.show();

            modalBottomSheet.findViewById(R.id.delete_post).setOnClickListener(v12 -> {
                bottomSheetDialog.hide();
                deleteOfferAPICall(position);
            });

        });
    }

    private void deleteOfferAPICall(int position) {
        try {
            StringRequest request = new StringRequest(
                    Request.Method.DELETE,
                    BaseURL.deleteOffer + allPosts.getResult().getOffers().get(position).getOfferId(),
                    response -> {
                        try {
                            allPosts.getResult().getOffers().remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, allPosts.getResult().getOffers().size());
                        } catch (Exception ex) {
                            Toast.makeText(context, "Response Exception: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        Toast.makeText(context, "Errro: " + error, Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(context, "Exception: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return allPosts.getResult().getOffers().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView myImage;
        private TextView myName, myPostedDate, myOfferValidDate, postType;
        private ExpandableTextView myPostDescription;
        private ImageView myPostImage, moreOptions;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myImage = itemView.findViewById(R.id.profileImage);
            myName = itemView.findViewById(R.id.profileName);
            myPostedDate = itemView.findViewById(R.id.postedDate);
            myOfferValidDate = itemView.findViewById(R.id.offerValidDate);
            myPostImage = itemView.findViewById(R.id.postImage);
            moreOptions = itemView.findViewById(R.id.moreOptions);
            myPostDescription = itemView.findViewById(R.id.expand_text_view);
            postType = itemView.findViewById(R.id.postType);
        }
    }
}
