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

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileProblemAdapter extends RecyclerView.Adapter<ProfileProblemAdapter.ViewHolder> {
    private Context context;
    private PostsModel allPosts;

    public ProfileProblemAdapter(Context context, PostsModel allPosts) {
        this.context = context;
        this.allPosts = allPosts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.profile_post_layout_design, parent, false);
        return new ProfileProblemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.profile_placeholder);
        if (allPosts.getResult().getProblems().get(position).getProblemImage() == null) {
            holder.myPostImage.setVisibility(View.GONE);
        } else {
            Glide.with(context).load(BaseURL.BaseURL + allPosts.getResult().getProblems().get(position).getProblemImage())
                    .into(holder.myPostImage);
        }
        Glide.with(context).load(allPosts.getResult().getImage())
                .apply(options).into(holder.myImage);
        holder.myName.setText(allPosts.getResult().getFullname());
        holder.myPostDescription.setText(allPosts.getResult().getProblems().get(position).getProblemDescription());
        holder.myPostedDate.setText(allPosts.getResult().getProblems().get(position).getPostedDate());
        holder.postType.setText(R.string.typeProblem);
        holder.myOfferValidDate.setVisibility(View.GONE);

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
                deleteProblemAPICall(position);
            });

        });
    }

    private void deleteProblemAPICall(int position) {
        try {
            StringRequest request = new StringRequest(
                    Request.Method.DELETE,
                    BaseURL.deleteProblem + allPosts.getResult().getProblems().get(position).getProblemId(),
                    response -> {
                        try {
                            allPosts.getResult().getProblems().remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, allPosts.getResult().getProblems().size());
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
        return allPosts.getResult().getProblems().size();
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
