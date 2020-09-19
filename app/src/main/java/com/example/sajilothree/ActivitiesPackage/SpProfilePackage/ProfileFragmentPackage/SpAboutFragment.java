package com.example.sajilothree.ActivitiesPackage.SpProfilePackage.ProfileFragmentPackage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.AdapterPackages.ProfileAdapters.AboutFragmentAdapter;
import com.example.sajilothree.ModelsPackage.ProfileModelPackage.ProfileAbout.ProfileAboutResponse;
import com.example.sajilothree.ModelsPackage.ProfileModelPackage.UserStates.UserStatesResponse;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.UsernameHolder.OtherPersonUserId;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.GsonBuilder;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import org.json.JSONObject;

import java.util.Objects;

import lecho.lib.hellocharts.model.Line;

public class SpAboutFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_about_layout, container, false);
    }

    //    private PieChartView completeTask, onBudget, onTime, repeatHire;
    private CircularProgressBar completeTask, onBudget, onTime;
    private TextView completeTaskPer;
    private TextView onBudgetPer;
    private TextView onTimePer;
    private RecyclerView recyclerView;
    private CardView one, two, three;
    private ShimmerFrameLayout shimmer;
    private ExpandableTextView aboutUser;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userInterface(view);
        userStatesApiCall();
        AboutDetailsAPICall();
    }

    private void userStatesApiCall() {
        try {
            String token;
            if (Username.username.equals(OtherPersonUserId.UserId)) {
                token = Username.username;
            } else {
                token = OtherPersonUserId.UserId;
            }
            if (token == null) {
                Toast.makeText(getContext(), "Token Empty", Toast.LENGTH_SHORT).show();
            }
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    BaseURL.getUserStates + token,
                    response -> {
                        try {
                            UserStatesResponse userStatesResponse = new GsonBuilder().create().fromJson(response, UserStatesResponse.class);
                            if (userStatesResponse.getSuccess()) {
                                one.setVisibility(View.VISIBLE);
                                completeTask((int) Math.round(userStatesResponse.getResult().getCompleted()));
                                onBudget((int) Math.round(userStatesResponse.getResult().getOnBudget()));
                                onTime((int) Math.round(userStatesResponse.getResult().getOnTime()));
                            }
                        } catch (Exception ex) {
                            Toast.makeText(getContext(), "Not states found", Toast.LENGTH_SHORT).show();
                        }
                    }, error -> {
                Toast.makeText(getContext(), "Error to get states: " + error, Toast.LENGTH_SHORT).show();
            }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
            requestQueue.add(request);
        } catch (Exception ex) {
            Log.d("Exception States", Objects.requireNonNull(ex.getMessage()));
        }
    }

    private void AboutDetailsAPICall() {
        try {
            String token;
            one.setVisibility(View.GONE);
            two.setVisibility(View.GONE);
            three.setVisibility(View.GONE);
            shimmer.setVisibility(View.VISIBLE);
            shimmer.startShimmer();
            JSONObject jsonObject = new JSONObject();
            if (Username.username.equals(OtherPersonUserId.UserId)) {
                token = Username.username;
            } else {
                token = OtherPersonUserId.UserId;
            }
            if (token == null) {
                Toast.makeText(getContext(), "Token Empty", Toast.LENGTH_SHORT).show();
            }
            jsonObject.put("tokenNumber", token);
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    BaseURL.getProfileAbout,
                    jsonObject,
                    response -> {
                        one.setVisibility(View.VISIBLE);
                        two.setVisibility(View.VISIBLE);
                        three.setVisibility(View.VISIBLE);
                        shimmer.setVisibility(View.GONE);
                        shimmer.stopShimmer();
                        try {
                            ProfileAboutResponse profileAboutResponse = new GsonBuilder().create().fromJson(
                                    String.valueOf(response), ProfileAboutResponse.class
                            );
                            if (profileAboutResponse.getSuccess()) {
                                aboutUser.setText(profileAboutResponse.getResult().getAboutUser());
                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                AboutFragmentAdapter aboutFragmentAdapter =
                                        new AboutFragmentAdapter(getContext(), profileAboutResponse.getResult().getUserProfessions());
                                recyclerView.setAdapter(aboutFragmentAdapter);
                            }
                        } catch (Exception ex) {
                            Toast.makeText(getContext(), "Response Failed", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        one.setVisibility(View.VISIBLE);
                        two.setVisibility(View.VISIBLE);
                        three.setVisibility(View.VISIBLE);
                        shimmer.setVisibility(View.GONE);
                        shimmer.stopShimmer();
                        Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Exception: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void onTime(Integer time) {
        onTime.setProgress(time);
        onTimePer.setText(time.toString() + "%");
    }

    @SuppressLint("SetTextI18n")
    private void onBudget(Integer budget) {
        onBudget.setProgress(budget);
        onBudgetPer.setText(budget.toString() + "%");
    }

    @SuppressLint("SetTextI18n")
    private void completeTask(Integer completedTask) {
        completeTask.setProgress(completedTask);
        completeTaskPer.setText(completedTask.toString() + "%");
    }

    private void userInterface(View view) {
        completeTask = view.findViewById(R.id.completedTask);
        completeTaskPer = view.findViewById(R.id.completedTaskPercent);
        onBudget = view.findViewById(R.id.onBudget);
        onBudgetPer = view.findViewById(R.id.onBudgetPercent);
        onTime = view.findViewById(R.id.onTime);
        onTimePer = view.findViewById(R.id.onTimePercent);
        recyclerView = view.findViewById(R.id.professionsView);
        one = view.findViewById(R.id.one);
        two = view.findViewById(R.id.two);
        three = view.findViewById(R.id.three);
        shimmer = view.findViewById(R.id.shimmer);
        aboutUser = view.findViewById(R.id.expand_text_view);
    }
}

    /*private void repeatHire() {
        List<SliceValue> onTimeData = new ArrayList<>();
        onTimeData.add(new SliceValue((float) 35, Color.parseColor("#2E86C1")).setLabel(""));
        onTimeData.add(new SliceValue((float) (100 - 35), Color.parseColor("#cdc9c9")).setLabel(""));

        PieChartData pieChartData = new PieChartData(onTimeData);
        pieChartData.setHasLabels(true);

        pieChartData.setHasCenterCircle(true).setCenterText1("35%")
                .setCenterText1FontSize(12).setCenterText1Color(Color.parseColor("#000000"));
        repeatHire.setPieChartData(pieChartData);
    }

    private void onTime() {
        List<SliceValue> onTimeData = new ArrayList<>();
        onTimeData.add(new SliceValue((float) 45, Color.parseColor("#2E86C1")).setLabel(""));
        onTimeData.add(new SliceValue((float) (100 - 45), Color.parseColor("#cdc9c9")).setLabel(""));

        PieChartData pieChartData = new PieChartData(onTimeData);
        pieChartData.setHasLabels(true);

        pieChartData.setHasCenterCircle(true).setCenterText1("45%")
                .setCenterText1FontSize(12).setCenterText1Color(Color.parseColor("#000000"));
        onTime.setPieChartData(pieChartData);
    }

    private void onBudget() {
        List<SliceValue> onBudgetData = new ArrayList<>();
        onBudgetData.add(new SliceValue((float) 100, Color.parseColor("#2E86C1")).setLabel(""));
        onBudgetData.add(new SliceValue((float) (0), Color.parseColor("#cdc9c9")).setLabel(""));

        PieChartData pieChartData = new PieChartData(onBudgetData);
        pieChartData.setHasLabels(true);

        pieChartData.setHasCenterCircle(true).setCenterText1("100%")
                .setCenterText1FontSize(12).setCenterText1Color(Color.parseColor("#000000"));
        onBudget.setPieChartData(pieChartData);
    }

    @SuppressLint("SetTextI18n")
    private void completeTask() {
        List<SliceValue> completeTaskData = new ArrayList<>();
        completeTaskData.add(new SliceValue((float) 75, Color.parseColor("#2E86C1")).setLabel(""));
        completeTaskData.add(new SliceValue((float) (100 - 75), Color.parseColor("#cdc9c9")).setLabel(""));

        PieChartData pieChartData = new PieChartData(completeTaskData);
        pieChartData.setHasLabels(true);

        pieChartData.setHasCenterCircle(true).setCenterText1("75%")
                .setCenterText1FontSize(12).setCenterText1Color(Color.parseColor("#000000"));
        completeTask.setPieChartData(pieChartData);
    }*/