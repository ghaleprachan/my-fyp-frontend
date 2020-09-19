package com.example.sajilothree.AdapterPackages.HomeAdapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.sajilothree.ActivitiesPackage.CProfilePackage.CProfileActivity;
import com.example.sajilothree.ActivitiesPackage.FullImageViewPackage.FullImageActivity;
import com.example.sajilothree.ActivitiesPackage.SpProfilePackage.SpProfileActivity;
import com.example.sajilothree.ModelsPackage.HomeModel.OfferModelPackage.OfferModel;
import com.example.sajilothree.ModelsPackage.HomeModel.SavePostDetailsModel.NewSaveResponseModel;
import com.example.sajilothree.ModelsPackage.ReportResponsePackage.ReportResponseModel;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.BookingServices.BookingRequest.BookingRequestService;
import com.example.sajilothree.ServicesPackage.UserSavedPosts.SavedIdList;
import com.example.sajilothree.ServicesPackage.UsernameHolder.OtherPersonUserId;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;
import com.example.sajilothree.UserTypeHolder.UserTypes;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ms.square.android.expandabletextview.ExpandableTextView;


import org.json.JSONObject;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.MyViewHolder> {
    private Context context;
    private OfferModel offers;

    public OfferAdapter(Context context, OfferModel offerList) {
        this.context = context;
        this.offers = offerList;
    }

    @NonNull
    @Override
    public OfferAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.offer_fragment_design, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull OfferAdapter.MyViewHolder holder, final int position) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.profile_placeholder);
        if (offers.getResult().get(position).getOfferImage() == null) {
            holder.postImage.setVisibility(View.GONE);
        } else {
            Glide.with(context).load(BaseURL.BaseURL + offers.getResult().get(position).getOfferImage())
                    .into(holder.postImage);
        }
        Glide.with(context).load(BaseURL.BaseURL + offers.getResult().get(position).getUserPhoto())
                .apply(options).into(holder.profileImage);

        holder.profileName.setText(offers.getResult().get(position).getPostedBy());
        holder.postDescription.setText(offers.getResult().get(position).getOfferDescription());
        holder.postDate.setText(BookingRequestService.chatSystemDate(offers.getResult().get(position).getPostedDate()));

        visitProfile(holder.visitProfile, position);
        onMoreOptionClick(holder.moreOptions, position);
        onCallUserClick(holder.callUser, position);
        onValidDateClick(holder.validDate, position);
        onPostImageClick(holder.postImage, position);
        if (SavedIdList.checkPostIsSaved(offers.getResult().get(position).getOfferId())) {
            holder.savePost.setVisibility(View.GONE);
            holder.savedPost.setVisibility(View.VISIBLE);
        } else {
            holder.savePost.setVisibility(View.VISIBLE);
            holder.savedPost.setVisibility(View.GONE);
        }
        onSavePostClick(holder.savePost, holder.savedPost, position);
        onSavedPostClick(holder.savedPost, holder.savePost, position);
    }

    private void onSavedPostClick(ImageView savedPost, ImageView savePost, int position) {
        savedPost.setOnClickListener(v -> {
            savedPost.setVisibility(View.GONE);
            savePost.setVisibility(View.VISIBLE);
            savePostApi(position);
        });
    }

    private void onSavePostClick(ImageView savePost, ImageView savedPost, int position) {
        savePost.setOnClickListener(v -> {
            savedPost.setVisibility(View.VISIBLE);
            savePost.setVisibility(View.GONE);
            savePostApi(position);
        });
    }

    private void savePostApi(int position) {
        try {
            JSONObject savePostDetails = new JSONObject();
            savePostDetails.put("PostId", offers.getResult().get(position).getOfferId());
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
                                Toast.makeText(context, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Failed: " + responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(context, "Failed to parse: " + response, Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        Toast.makeText(context, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
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
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(context, "Exception: " + ex, Toast.LENGTH_SHORT).show();
        }

    }

    private void onPostImageClick(ImageView postImage, int position) {
        postImage.setOnClickListener(v -> {
            Intent intent = new Intent(context, FullImageActivity.class);
            intent.putExtra("imageUrl", offers.getResult().get(position).getOfferImage());
            context.startActivity(intent);
            ((AppCompatActivity) context).overridePendingTransition(R.anim.nav_default_pop_enter_anim, R.anim.nothing);
        });
    }

    private void onValidDateClick(TextView validDate, int position) {
        validDate.setOnClickListener(v -> {
            try {
                String input = offers.getResult().get(position).getValidDate();
                String output = input.substring(0, 10);
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setPositiveButton("Ok", (dialog, which) -> dialog.dismiss());
                builder.setTitle("Valid Date:");
                builder.setIcon(R.drawable.ic_date_range_black_24dp);
                builder.setMessage("The offer will be valid till" +
                        "\n" + output);
                AlertDialog alert = builder.create();
                alert.show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Exception: " + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onCallUserClick(ImageView callUser, int position) {
        callUser.setOnClickListener(v -> {
            try {
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context,
                        android.R.layout.simple_list_item_1);
                for (int i = 0; i < offers.getResult().get(position).getContacts().size(); i++) {
                    arrayAdapter.add(offers.getResult().get(position).getContacts().get(i).getContactNumber());
                }
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
                builder.setTitle(R.string.select_to_call);
                builder.setIcon(R.drawable.ic_call_black_24dp);
                if (arrayAdapter.getCount() == 0) {
                    builder.setMessage("No Contacts Available.");
                } else {
                    builder.setAdapter(arrayAdapter, (dialog, item) -> {
                        context.startActivity(new Intent(Intent.ACTION_DIAL,
                                Uri.parse("tel:" + arrayAdapter.getItem(item))));
                    });
                }
                AlertDialog alert = builder.create();
                alert.show();
            } catch (Exception ex) {
                Toast.makeText(context, "Something Wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void visitProfile(LinearLayout visitProfile, final int position) {
        visitProfile.setOnClickListener(v -> {
            try {
                if (OtherPersonUserId.encode(offers.getResult().get(position).getPostedById(),
                        offers.getResult().get(position).getPostedByUsername())) {
                    if (offers.getResult().get(position).getUserType().equals(UserTypes.customer)) {
                        context.startActivity(new Intent(context, CProfileActivity.class));
                        ((AppCompatActivity) context).overridePendingTransition(R.anim.nav_default_pop_enter_anim,
                                R.anim.nav_default_pop_exit_anim);
                    } else if (offers.getResult().get(position).getUserType().equals(UserTypes.sp)) {
                        context.startActivity(new Intent(context, SpProfileActivity.class));
                        ((AppCompatActivity) context).overridePendingTransition(R.anim.nav_default_pop_enter_anim,
                                R.anim.nav_default_pop_exit_anim);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private BottomSheetDialog bottomSheetDialog;

    private void onMoreOptionClick(ImageView moreOptions, final int position) {
        moreOptions.setOnClickListener(v -> {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            Toast.makeText(context, "Make Text " + position, Toast.LENGTH_SHORT).show();
            assert layoutInflater != null;
            @SuppressLint("InflateParams") View modalBottomSheet = layoutInflater.inflate(R.layout.offer_more_options_bottom_sheet, null);
            bottomSheetDialog = new BottomSheetDialog(context);
            bottomSheetDialog.setContentView(modalBottomSheet);
            bottomSheetDialog.setCanceledOnTouchOutside(true);
            bottomSheetDialog.setCancelable(true);
            bottomSheetDialog.show();

            modalBottomSheet.findViewById(R.id.visitProfile).setOnClickListener(v12 -> {
                try {
                    if (OtherPersonUserId.encode(offers.getResult().get(position).getPostedById(),
                            offers.getResult().get(position).getPostedByUsername())) {
                        if (offers.getResult().get(position).getUserType().equals(UserTypes.customer)) {
                            context.startActivity(new Intent(context, CProfileActivity.class));
                            ((AppCompatActivity) context).overridePendingTransition(R.anim.nav_default_pop_enter_anim,
                                    R.anim.nav_default_pop_exit_anim);
                        } else if (offers.getResult().get(position).getUserType().equals(UserTypes.sp)) {
                            context.startActivity(new Intent(context, SpProfileActivity.class));
                            ((AppCompatActivity) context).overridePendingTransition(R.anim.nav_default_pop_enter_anim,
                                    R.anim.nav_default_pop_exit_anim);
                        }
                        bottomSheetDialog.hide();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            LinearLayout report = modalBottomSheet.findViewById(R.id.reportPost);
            onReportPostClick(report, position);
        });
    }

    private void onReportPostClick(LinearLayout report, int position) {
        report.setOnClickListener(v -> {
            showReportDialogBox(position);
            bottomSheetDialog.hide();
        });
    }

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private EditText otherReason;
    private String message = null;

    private void showReportDialogBox(int position) {
        try {
            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.report_post_layput);
            dialog.setCancelable(true);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            otherReason = dialog.findViewById(R.id.otherReasons);
            TextView confirmReport = dialog.findViewById(R.id.confirmReport);
            TextView cancel = dialog.findViewById(R.id.cancel);

            confirmReport.setOnClickListener(v -> {
                try {
                    radioGroup = dialog.findViewById(R.id.radioGroup);
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    radioButton = dialog.findViewById(selectedId);
                    if (radioButton.getText().toString().equalsIgnoreCase("Other")) {
                        radioGroup.setVisibility(View.GONE);
                        otherReason.setVisibility(View.VISIBLE);
                        if (!otherReason.getText().toString().isEmpty()) {
                            message = otherReason.getText().toString();
                        }
                    } else {
                        message = radioButton.getText().toString();
                    }
                    if (!message.isEmpty()) {
                        reportOfferAPI(message, position);
                        dialog.dismiss();
                    }
                } catch (Exception ex) {
                    Toast.makeText(context, "Write your report", Toast.LENGTH_SHORT).show();
                }
            });

            cancel.setOnClickListener(v -> {
                if (otherReason.getVisibility() == View.VISIBLE) {
                    radioGroup.setVisibility(View.VISIBLE);
                    otherReason.setVisibility(View.GONE);
                } else {
                    dialog.dismiss();
                }
            });
            dialog.show();
        } catch (Exception ex) {
            Toast.makeText(context, "Dialog Box Exception: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    private void reportOfferAPI(String message, int position) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ReportedBy", Username.username);
            jsonObject.put("PostId", offers.getResult().get(position).getOfferId());
            jsonObject.put("ReportText", message);
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    BaseURL.reportOffer,
                    jsonObject,
                    response -> {
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            ReportResponseModel reportResponseModel = gson.fromJson(String.valueOf(response), ReportResponseModel.class);
                            if (reportResponseModel.getSuccess()) {
                                Toast.makeText(context, "Report Success", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(context, "Response Ex: " + response.toString(), Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        Toast.makeText(context, "Error: " + error, Toast.LENGTH_SHORT).show();
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
        return offers.getResult().size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView profileImage;
        private TextView profileName, postDate, validDate;
        private ImageView postImage, moreOptions, callUser, savePost, savedPost, loading;
        private ExpandableTextView postDescription;
        private LinearLayout visitProfile;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userInterface(itemView);
        }

        private void userInterface(View view) {
            profileImage = view.findViewById(R.id.profileImage);
            profileName = view.findViewById(R.id.profileName);
            postDescription = view.findViewById(R.id.expand_text_view);
            postImage = view.findViewById(R.id.postImage);
            moreOptions = view.findViewById(R.id.moreOptions);
            postDate = view.findViewById(R.id.postedDate);
            visitProfile = view.findViewById(R.id.viewProfile);
            callUser = view.findViewById(R.id.callUser);
            savePost = view.findViewById(R.id.savePost);
            validDate = view.findViewById(R.id.validateDate);
            savedPost = view.findViewById(R.id.savedPost);
            loading = view.findViewById(R.id.loadingImage);
        }
    }
}
