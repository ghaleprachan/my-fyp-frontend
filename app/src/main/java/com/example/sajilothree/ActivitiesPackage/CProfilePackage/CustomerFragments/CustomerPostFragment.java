package com.example.sajilothree.ActivitiesPackage.CProfilePackage.CustomerFragments;

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
import com.example.sajilothree.ServicesPackage.UsernameHolder.OtherPersonUserId;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;
import com.example.sajilothree.VeryUserModel.VerifyUser;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class CustomerPostFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_post_layout, container, false);
    }

    private RelativeLayout filterView;
    private RecyclerView offers, problems;
    private ShimmerFrameLayout loading;
    private LinearLayout container;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userInterface(view);
        showNeededView();
        customerPostsAPICall();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void customerPostsAPICall() {
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
                        try {
                            PostsModel posts = gson.fromJson(String.valueOf(response), PostsModel.class);
                            addPostsView(posts);
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                    },
                    error -> {
                        Toast.makeText(getContext(), "Server Error: " + error, Toast.LENGTH_SHORT).show();
                        loading.stopShimmer();
                        loading.setVisibility(View.GONE);
                        container.setVisibility(View.VISIBLE);
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

    private void showNeededView() {
        if (filterView.getVisibility() == View.VISIBLE) {
            filterView.setVisibility(View.GONE);
        }
    }

    private void userInterface(View view) {
        offers = view.findViewById(R.id.offers);
        problems = view.findViewById(R.id.problems);
        filterView = view.findViewById(R.id.filter);
        loading = view.findViewById(R.id.profilePostsLoading);
        container = view.findViewById(R.id.container);
    }
}
