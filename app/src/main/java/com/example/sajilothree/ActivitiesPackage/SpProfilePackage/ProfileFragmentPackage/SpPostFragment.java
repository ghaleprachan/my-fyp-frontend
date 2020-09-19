package com.example.sajilothree.ActivitiesPackage.SpProfilePackage.ProfileFragmentPackage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.AdapterPackages.ProfileAdapters.ProfileOfferAdapter;
import com.example.sajilothree.AdapterPackages.ProfileAdapters.ProfileProblemAdapter;
import com.example.sajilothree.ModelsPackage.ProfilePostsModelPackage.PostsModel;
import com.example.sajilothree.R;
import com.example.sajilothree.VeryUserModel.VerifyUser;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import lecho.lib.hellocharts.model.Line;

public class SpPostFragment extends Fragment {

    private ImageView filterTop;
    private RecyclerView offers, problems;
    private TextView postDisplayType;
    private ShimmerFrameLayout loading;
    private LinearLayout container;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_post_layout, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userInterface(view);
        serviceProviderAPICall();
        onFilterTopClick();
    }

    private void onFilterTopClick() {
        filterTop.setOnClickListener(v -> {
            LayoutInflater layoutInflater = (LayoutInflater)
                    requireContext()
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            @SuppressLint("InflateParams") View modalBottomSheet = layoutInflater
                    .inflate(R.layout.profile_posts_filter_options, null);
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
            bottomSheetDialog.setContentView(modalBottomSheet);
            bottomSheetDialog.setCanceledOnTouchOutside(true);
            bottomSheetDialog.setCancelable(true);
            bottomSheetDialog.show();

            LinearLayout offersBtn = Objects.requireNonNull(bottomSheetDialog.findViewById(R.id.onlyOffers));
            offersBtn.setOnClickListener(v1 -> {
                offers.setVisibility(View.VISIBLE);
                problems.setVisibility(View.GONE);
                postDisplayType.setText(R.string.offersPost);
                bottomSheetDialog.hide();
            });
            LinearLayout problemsBtn = Objects.requireNonNull(bottomSheetDialog.findViewById(R.id.onlyProblems));
            problemsBtn.setOnClickListener(v2 -> {
                problems.setVisibility(View.VISIBLE);
                offers.setVisibility(View.GONE);
                postDisplayType.setText(R.string.problems);
                bottomSheetDialog.hide();
            });
            LinearLayout allBtn = Objects.requireNonNull(bottomSheetDialog.findViewById(R.id.allPosts));
            allBtn.setOnClickListener(v3 -> {
                problems.setVisibility(View.VISIBLE);
                offers.setVisibility(View.VISIBLE);
                postDisplayType.setText(R.string.all);
                bottomSheetDialog.hide();
            });
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void serviceProviderAPICall() {
        try {
            if (loading.getVisibility() == View.GONE) {
                loading.setVisibility(View.VISIBLE);
            }
            if (container.getVisibility() == View.VISIBLE) {
                container.setVisibility(View.GONE);
            }
            loading.startShimmer();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tokenNumber", VerifyUser.userVerification());
            RequestQueue requestQueue;
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    BaseURL.BaseURL + BaseURL.getProfilePosts,
                    jsonObject,
                    response -> {
                        loading.stopShimmer();
                        loading.setVisibility(View.GONE);
                        container.setVisibility(View.VISIBLE);
                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();
                        PostsModel posts = gson.fromJson(String.valueOf(response), PostsModel.class);
                        addPostsView(posts);
                    },
                    error -> {
                        Toast.makeText(getContext(), "Server Error: " + error, Toast.LENGTH_SHORT).show();
                        container.setVisibility(View.VISIBLE);
                        loading.stopShimmer();
                        loading.setVisibility(View.GONE);
                    }
            );
            requestQueue = Volley.newRequestQueue(requireContext());
            requestQueue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addPostsView(PostsModel posts) {
        offers.setLayoutManager(new LinearLayoutManager(getContext()));
        ProfileOfferAdapter offerAdapter = new ProfileOfferAdapter(getActivity(), posts);
        offers.setAdapter(offerAdapter);

        problems.setLayoutManager(new LinearLayoutManager(getContext()));
        ProfileProblemAdapter problemAdapter = new ProfileProblemAdapter(getActivity(), posts);
        problems.setAdapter(problemAdapter);
    }

    private void userInterface(View view) {
        filterTop = view.findViewById(R.id.filterPosts);
        offers = view.findViewById(R.id.offers);
        problems = view.findViewById(R.id.problems);
        postDisplayType = view.findViewById(R.id.filteredPostType);
        loading = view.findViewById(R.id.profilePostsLoading);
        container = view.findViewById(R.id.container);
    }
}
