package com.example.sajilothree.ActivitiesPackage.SpProfilePackage.ProfileFragmentPackage;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.APIPackage.BaseUrlPackage.ChatHubUrlHolder;
import com.example.sajilothree.ActivitiesPackage.AddReview.AddReviewActivity;
import com.example.sajilothree.AdapterPackages.AllReviewsAdapter.AllReviewsAdapter;
import com.example.sajilothree.AdapterPackages.AllReviewsAdapter.OtherReviewsAdapter;
import com.example.sajilothree.ModelsPackage.ProfileModelPackage.ProfileReviewsModel.ProfileReviewsModel;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.FeedbackService.FeedbackServices;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;
import com.example.sajilothree.VeryUserModel.VerifyUser;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class SpFeedbackFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_feedback_layout, container, false);
    }

    private TextView all, positive, critical, oneStar, twoStar, threeStar, fourStar, fiveStart;
    private Button addReview;
    private ShimmerFrameLayout loading;
    private String getAllReviewsURL = BaseURL.BaseURL + BaseURL.getUserFeedBacks;
    private RecyclerView recyclerView;
    private HubConnection hubConnection;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userInterface(view);
        all.setBackground(ContextCompat.getDrawable(requireContext(),
                R.drawable.profile_reviews_heading_selected_bg));
        verifyUser();
        onAllClick();
        onPositiveClick();
        onCriticalClick();
        onOneStarClick();
        onTwoStarClick();
        onThreeStarClick();
        onFourStarClick();
        onFiveStarClick();
        allReviewsApiCall();

        onAddReviewClick();

        buildHubConnection();
        getLastFeedback();


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void verifyUser() {
        try {
            if (VerifyUser.userVerification().equals(Username.username)) {
                addReview.setVisibility(View.GONE);
            } else {
                addReview.setVisibility(View.VISIBLE);
            }
        } catch (Exception ex) {
            addReview.setVisibility(View.GONE);
        }
    }

    private void buildHubConnection() {
        hubConnection = HubConnectionBuilder.create(ChatHubUrlHolder.chatHubApi).build();
        hubConnection.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void getLastFeedback() {
        try {
            hubConnection.on("GetFeedback", (feedBackTo, feedbackBy) -> {
                allReviewsApiCall();
            }, String.class, String.class);
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Exception = " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    private void onAddReviewClick() {
        addReview.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Make text", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getContext(), AddReviewActivity.class));
            (requireActivity()).
                    overridePendingTransition(R.anim.nav_default_pop_enter_anim,
                            R.anim.nothing);
        });
    }

    private void userInterface(View view) {
        all = view.findViewById(R.id.allReviews);
        positive = view.findViewById(R.id.positive);
        critical = view.findViewById(R.id.critical);
        oneStar = view.findViewById(R.id.oneStar);
        twoStar = view.findViewById(R.id.twoStar);
        threeStar = view.findViewById(R.id.threeStart);
        fourStar = view.findViewById(R.id.fourStart);
        fiveStart = view.findViewById(R.id.fiveStar);
        addReview = view.findViewById(R.id.addReviewBtn);
        loading = view.findViewById(R.id.loadingReviews);
        recyclerView = view.findViewById(R.id.allReviewsView);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void allReviewsApiCall() {
        try {
            loading.setVisibility(View.VISIBLE);
            loading.startShimmer();
            RequestQueue requestQueue;

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tokenNumber", VerifyUser.userVerification());

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    getAllReviewsURL,
                    jsonObject,
                    response -> {
                        loading.stopShimmer();
                        loading.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            ProfileReviewsModel profileHeading = gson.fromJson(String.valueOf(response),
                                    ProfileReviewsModel.class);
                            addFeedbackToService(profileHeading);
                            addToRecyclerView(profileHeading);
                        } catch (Exception ex) {
                            Toast.makeText(getContext(), "Failed to get reviews! " + ex, Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        recyclerView.setVisibility(View.GONE);
                        loading.stopShimmer();
                        loading.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                    });
            requestQueue = Volley.newRequestQueue(requireContext());
            requestQueue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addFeedbackToService(ProfileReviewsModel reviewsModel) {
        FeedbackServices.addFeedback(reviewsModel.getResult().getFeedbacks());
    }

    private void addToRecyclerView(ProfileReviewsModel profileHeading) {
        RecyclerView.LayoutManager addressLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(addressLayoutManager);
        AllReviewsAdapter adapter = new AllReviewsAdapter(getContext(), profileHeading);
        if (profileHeading.getResult().getFeedbacks().size() > 0) {
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(getContext(), "Not Reviews", Toast.LENGTH_SHORT).show();
        }
    }

    private void addReviewsOnOtherView(int ratingCount) {
        RecyclerView.LayoutManager addressLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(addressLayoutManager);
        OtherReviewsAdapter adapter = new OtherReviewsAdapter(getContext(), FeedbackServices.filterByRatings(ratingCount));
        recyclerView.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onFiveStarClick() {
        fiveStart.setOnClickListener(v -> {
            fiveSelected();
            addReviewsOnOtherView(5);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onFourStarClick() {
        fourStar.setOnClickListener(v -> {
            fourSelected();
            addReviewsOnOtherView(4);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onThreeStarClick() {
        threeStar.setOnClickListener(v -> {
            threeSelected();
            addReviewsOnOtherView(3);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onTwoStarClick() {
        twoStar.setOnClickListener(v -> {
            twoSelected();
            addReviewsOnOtherView(2);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onOneStarClick() {
        oneStar.setOnClickListener(v -> {
            oneSelected();
            addReviewsOnOtherView(1);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onCriticalClick() {
        critical.setOnClickListener(v -> {
            criticalSelected();
            addReviewsOnOtherView(1);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onPositiveClick() {
        positive.setOnClickListener(v -> {
            positiveSelected();
            addReviewsOnOtherView(5);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onAllClick() {
        all.setOnClickListener(v -> {
            allSelected();
            allReviewsApiCall();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void allSelected() {
        all.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_selected_bg));
        positive.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        critical.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        oneStar.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        twoStar.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        threeStar.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        fourStar.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        fiveStart.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void positiveSelected() {
        all.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        positive.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_selected_bg));
        critical.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        oneStar.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        twoStar.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        threeStar.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        fourStar.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        fiveStart.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void criticalSelected() {
        all.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        positive.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        critical.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_selected_bg));
        oneStar.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        twoStar.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        threeStar.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        fourStar.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        fiveStart.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void oneSelected() {
        all.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        positive.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        critical.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        oneStar.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_selected_bg));
        twoStar.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        threeStar.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        fourStar.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        fiveStart.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void twoSelected() {
        all.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        positive.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        critical.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        oneStar.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        twoStar.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_selected_bg));
        threeStar.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        fourStar.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        fiveStart.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void threeSelected() {
        all.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        positive.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        critical.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        oneStar.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        twoStar.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        threeStar.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_selected_bg));
        fourStar.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        fiveStart.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void fourSelected() {
        all.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        positive.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        critical.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        oneStar.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        twoStar.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        threeStar.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        fourStar.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_selected_bg));
        fiveStart.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void fiveSelected() {
        all.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        positive.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        critical.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        oneStar.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        twoStar.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        threeStar.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        fourStar.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_not_selected_bg));
        fiveStart.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.profile_reviews_heading_selected_bg));
    }
}
