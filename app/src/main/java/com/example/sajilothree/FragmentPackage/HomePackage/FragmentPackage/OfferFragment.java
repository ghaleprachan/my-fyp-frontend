package com.example.sajilothree.FragmentPackage.HomePackage.FragmentPackage;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.AdapterPackages.HomeAdapters.OfferAdapter;
import com.example.sajilothree.ApiCallsPackage.UserSavePosts.UserSavedPostsCall;
import com.example.sajilothree.ModelsPackage.HomeModel.OfferModelPackage.OfferModel;
import com.example.sajilothree.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class OfferFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.offer_fragment_layout, container, false);
    }

    private ShimmerFrameLayout loading;
    private RecyclerView offersView;
    private ImageView notData;
    private SwipeRefreshLayout refresh;
    private LinearLayout retry;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userInterface(view);

        UserSavedPostsCall.callSaveOfferId(getContext());

        onRefreshPull();
        offerAPICall();
        onTryAgain();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onTryAgain() {
        retry.setOnClickListener(v -> offerAPICall());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("ResourceAsColor")
    private void onRefreshPull() {
        refresh.setColorSchemeResources(
                R.color.colorAccent,
                R.color.blue,
                R.color.colorPrimary
        );
        refresh.setOnRefreshListener(this::offerAPICall);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void offerAPICall() {
        if (loading.getVisibility() == View.GONE) {
            loading.setVisibility(View.VISIBLE);
        }
        retry.setVisibility(View.GONE);
        offersView.setVisibility(View.GONE);
        loading.startShimmer();
        StringRequest request = new StringRequest(Request.Method.GET, BaseURL.BaseURL + BaseURL.getOffer, response -> {
            try {
                retry.setVisibility(View.GONE);
                refresh.setRefreshing(false);
                loading.startShimmer();
                loading.setVisibility(View.GONE);
                offersView.setVisibility(View.VISIBLE);
                OfferModel offers = new GsonBuilder().create().fromJson(response,
                        OfferModel.class);
                AddToAdapter(offers);
            } catch (Exception ex) {
                Toast.makeText(getContext(), "Something went wrong" + ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            refresh.setRefreshing(false);
            loading.setVisibility(View.GONE);
            offersView.setVisibility(View.GONE);
            notData.setVisibility(View.GONE);
            retry.setVisibility(View.VISIBLE);
        });
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(request);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void AddToAdapter(OfferModel offers) {
        if (offers.getResult().size() == 0) {
            notData.setVisibility(View.VISIBLE);
            offersView.setVisibility(View.GONE);
        }
        OfferAdapter offerAdapter = new OfferAdapter(getActivity(), offers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        offersView.setLayoutManager(layoutManager);
        offersView.addItemDecoration(new DividerItemDecoration(requireActivity(), 0));
        offersView.setAdapter(offerAdapter);
    }

    private void userInterface(View view) {
        loading = view.findViewById(R.id.offerShimmer);
        offersView = view.findViewById(R.id.recyclerView);
        notData = view.findViewById(R.id.notPosts);
        refresh = view.findViewById(R.id.refreshOffer);
        retry = view.findViewById(R.id.tryAgain);
    }
}
