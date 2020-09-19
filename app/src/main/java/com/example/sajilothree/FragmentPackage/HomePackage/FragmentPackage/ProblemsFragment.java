package com.example.sajilothree.FragmentPackage.HomePackage.FragmentPackage;

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
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.AdapterPackages.HomeAdapters.ProblemsAdapter;
import com.example.sajilothree.ModelsPackage.HomeModel.ProblemModelPackage.ProblemModel;
import com.example.sajilothree.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Objects;

public class ProblemsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.problems_fragment_layout, container, false);
    }

    private ShimmerFrameLayout loading;
    private RecyclerView problemsView;
    private ImageView notData;
    private SwipeRefreshLayout refresh;
    private LinearLayout retry;
    private TextView errorMsg;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userInterface(view);
        onRefreshPull();
        problemAPICall();
        onTryAgain();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onTryAgain() {
        retry.setOnClickListener(v -> problemAPICall());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onRefreshPull() {
        refresh.setColorSchemeResources(
                R.color.colorAccent,
                R.color.blue,
                R.color.colorPrimary
        );
        refresh.setOnRefreshListener(this::problemAPICall);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void problemAPICall() {
        if (loading.getVisibility() == View.GONE) {
            loading.setVisibility(View.VISIBLE);
        }
        retry.setVisibility(View.GONE);
        problemsView.setVisibility(View.GONE);
        loading.startShimmer();
        StringRequest request = new StringRequest(
                Request.Method.GET,
                BaseURL.BaseURL + BaseURL.getProblem,
                response -> {
                    try {
                        retry.setVisibility(View.GONE);
                        refresh.setRefreshing(false);
                        loading.startShimmer();
                        loading.setVisibility(View.GONE);
                        problemsView.setVisibility(View.VISIBLE);
                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();
                        ProblemModel problems = gson.fromJson(response,
                                ProblemModel.class);
                        AddToAdapter(problems);
                    } catch (Exception ex) {
                        System.out.println("Error!");
//                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    refresh.setRefreshing(false);
                    loading.setVisibility(View.GONE);
                    problemsView.setVisibility(View.GONE);
                    notData.setVisibility(View.GONE);
                    retry.setVisibility(View.VISIBLE);
//                        errorMsg.setText(error.toString());
                }
        );
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(request);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void AddToAdapter(ProblemModel problems) {
        if (problems.getResult().size() == 0) {
            notData.setVisibility(View.VISIBLE);
            problemsView.setVisibility(View.GONE);
        }

        problemsView.addItemDecoration(new DividerItemDecoration(requireActivity(), 0));
        ProblemsAdapter problemsAdapter = new ProblemsAdapter(getActivity(), problems);
        problemsView.setAdapter(problemsAdapter);
    }


    private void userInterface(View view) {
        loading = view.findViewById(R.id.problemShimmer);
        problemsView = view.findViewById(R.id.problemsListView);
        notData = view.findViewById(R.id.noData);
        refresh = view.findViewById(R.id.refreshProblem);
        retry = view.findViewById(R.id.tryAgain);
        errorMsg = view.findViewById(R.id.errorMsg);
    }
}
