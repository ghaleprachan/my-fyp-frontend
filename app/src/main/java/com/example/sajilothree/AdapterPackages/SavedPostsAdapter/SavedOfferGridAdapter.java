package com.example.sajilothree.AdapterPackages.SavedPostsAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.ActivitiesPackage.UserSavedPosts.SavedOfferListDetails;
import com.example.sajilothree.ModelsPackage.HomeModel.SavePostDetailsModel.NewSaveResponseModel;
import com.example.sajilothree.ModelsPackage.SavedPostDetails.Result;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.BookingServices.BookingRequest.BookingRequestService;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;

public class SavedOfferGridAdapter extends RecyclerView.Adapter<SavedOfferGridAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<Result> offerDetails;

    public SavedOfferGridAdapter(Context mContext, ArrayList<Result> offerDetails) {
        this.mContext = mContext;
        this.offerDetails = offerDetails;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.saved_offer_grid_design, parent, false);
        return new SavedOfferGridAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.shimmer_view_background);
        Glide.with(mContext)
                .load(BaseURL.BaseURL + offerDetails.get(position).getOfferImage())
                .apply(options)
                .into(holder.offerImage);

        RequestOptions optionsForProfile = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.profile_placeholder);
        Glide.with(mContext)
                .load(BaseURL.BaseURL + offerDetails.get(position).getPostedByImage())
                .apply(optionsForProfile)
                .into(holder.postedByImage);

        holder.postedByName.setText(offerDetails.get(position).getPostedByName());
        holder.savedDate.setText(BookingRequestService.myDateFormat(offerDetails.get(position).getSaveDate(),
                "MMM dd, 'at' h:ss a"));
        onParentClick(holder.parentCard, position);

        onRemoveClick(holder.remove, position);

    }

    private void onRemoveClick(ImageView remove, int position) {
        remove.setOnClickListener(v -> {
            savePostApi(position);
        });
    }

    private void savePostApi(int position) {
        try {
            JSONObject savePostDetails = new JSONObject();
            savePostDetails.put("PostId", offerDetails.get(position).getOfferId());
            savePostDetails.put("UserId", Username.username);
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    BaseURL.saveOffer,
                    savePostDetails,
                    response -> {
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            NewSaveResponseModel responseModel = gson.fromJson(String.valueOf(response), NewSaveResponseModel.class);
                            if (responseModel.getSuccess()) {
                                offerDetails.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, offerDetails.size());
                                Toast.makeText(mContext, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, "Failed: " + responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(mContext, "Failed to parse: " + response, Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        Toast.makeText(mContext, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
            );
            request.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 50000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 50000;
                }

                @Override
                public void retry(VolleyError error) {

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(mContext, "Exception: " + ex, Toast.LENGTH_SHORT).show();
        }

    }

    private void onParentClick(CardView parentCard, int position) {
        parentCard.setOnClickListener(v -> {
            Intent changeAct = new Intent(mContext, SavedOfferListDetails.class);
            changeAct.putExtra("position", position);
            mContext.startActivity(changeAct);
        });
    }

    @Override
    public int getItemCount() {
        return offerDetails.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView offerImage, postedByImage;
        private CardView parentCard;
        private TextView postedByName, savedDate;
        private ImageView remove;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            parentCard = itemView.findViewById(R.id.parentCard);
            offerImage = itemView.findViewById(R.id.postImage);
            postedByImage = itemView.findViewById(R.id.postByImage);
            postedByName = itemView.findViewById(R.id.personName);
            savedDate = itemView.findViewById(R.id.savedDate);
            remove = itemView.findViewById(R.id.remove);
        }
    }
}
