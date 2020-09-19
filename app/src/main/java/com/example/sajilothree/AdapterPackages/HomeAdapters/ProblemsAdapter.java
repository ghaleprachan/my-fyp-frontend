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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.ActivitiesPackage.CProfilePackage.CProfileActivity;
import com.example.sajilothree.ActivitiesPackage.FullImageViewPackage.FullImageActivity;
import com.example.sajilothree.ActivitiesPackage.SpProfilePackage.SpProfileActivity;
import com.example.sajilothree.ModelsPackage.HomeModel.ProblemModelPackage.ProblemModel;
import com.example.sajilothree.ModelsPackage.ReportResponsePackage.ReportResponseModel;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.BookingServices.BookingRequest.BookingRequestService;
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

public class ProblemsAdapter extends RecyclerView.Adapter<ProblemsAdapter.MyViewHolder> {
    private Context context;
    private ProblemModel problems;

    public ProblemsAdapter(Context context, ProblemModel offerList) {
        this.context = context;
        this.problems = offerList;
    }

    @NonNull
    @Override
    public ProblemsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.problems_fragment_design, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull ProblemsAdapter.MyViewHolder holder, int position) {
//        This is for image placeholder

//        This is for if image is not added
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.profile_placeholder);
        if (problems.getResult().get(position).getProblemImage() == null) {
            holder.postImage.setVisibility(View.GONE);
        } else {
            Glide.with(context).load(BaseURL.BaseURL + problems.getResult().get(position).getProblemImage())
                    .into(holder.postImage);
        }
        Glide.with(context).load(BaseURL.BaseURL + problems.getResult().get(position).getUserPhoto())
                .apply(options).into(holder.profileImage);

        holder.postDescription.setText(problems.getResult().get(position).getProblemDescription());
        holder.postedDate.setText(BookingRequestService.chatSystemDate(problems.getResult().get(position).getPostedDate()));
        holder.profileName.setText(problems.getResult().get(position).getPostedBy());

        VisitProfile(holder.visitProfile, position);
        onMoreOptionClick(holder.moreOptions, position);

        onCallUserClick(holder.phoneCall, position);

        onPostImageClick(holder.postImage, position);
    }

    private void onPostImageClick(ImageView postImage, int position) {
        postImage.setOnClickListener(v -> {
            Intent intent = new Intent(context, FullImageActivity.class);
            intent.putExtra("imageUrl", problems.getResult().get(position).getProblemImage());
            context.startActivity(intent);
            ((AppCompatActivity) context).overridePendingTransition(R.anim.nav_default_pop_enter_anim, R.anim.nothing);
        });
    }

    private void onCallUserClick(ImageView phoneCall, int position) {
        phoneCall.setOnClickListener(v -> {
            try {
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context,
                        android.R.layout.simple_list_item_1);
                for (int i = 0; i < problems.getResult().get(position).getContacts().size(); i++) {
                    arrayAdapter.add(problems.getResult().get(position).getContacts().get(i).getContactNumber());
                }
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
                builder.setTitle("Select to call");
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
    private void VisitProfile(LinearLayout visitProfile, final int position) {
        visitProfile.setOnClickListener(v -> {
            try {
                if (OtherPersonUserId.encode(problems.getResult().get(position).getPostedById(),
                        problems.getResult().get(position).getPostedByUsername())) {
                    if (problems.getResult().get(position).getUserType().equals(UserTypes.customer)) {
                        context.startActivity(new Intent(context, CProfileActivity.class));
                        ((AppCompatActivity) context).overridePendingTransition(R.anim.nav_default_pop_enter_anim,
                                R.anim.nav_default_pop_exit_anim);
                    } else if (problems.getResult().get(position).getUserType().equals(UserTypes.sp)) {
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

            modalBottomSheet.findViewById(R.id.visitProfile).setOnClickListener(v1 -> {
                try {
                    if (OtherPersonUserId.encode(problems.getResult().get(position).getPostedById(),
                            problems.getResult().get(position).getPostedByUsername())) {
                        if (problems.getResult().get(position).getUserType().equals(UserTypes.customer)) {
                            context.startActivity(new Intent(context, CProfileActivity.class));
                            ((AppCompatActivity) context).overridePendingTransition(R.anim.nav_default_pop_enter_anim,
                                    R.anim.nav_default_pop_exit_anim);
                        } else if (problems.getResult().get(position).getUserType().equals(UserTypes.sp)) {
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
                        reportProblemAPI(message, position);
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

    private void reportProblemAPI(String message, int position) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ReportedBy", Username.username);
            jsonObject.put("PostId", problems.getResult().get(position).getProblemId());
            jsonObject.put("ReportText", message);
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    BaseURL.reportProblem,
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
                    error -> Toast.makeText(context, "Error: " + error, Toast.LENGTH_SHORT).show()
            );
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(context, "Exception: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return problems.getResult().size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView profileImage;
        ExpandableTextView postDescription;
        private TextView profileName, postedDate;
        private ImageView postImage, moreOptions, phoneCall, interested;
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
            postedDate = view.findViewById(R.id.problemPostedDate);
            moreOptions = view.findViewById(R.id.moreOptions);
            visitProfile = view.findViewById(R.id.viewProfile);
            phoneCall = view.findViewById(R.id.phoneCall);
            interested = view.findViewById(R.id.interested);
        }
    }
}
