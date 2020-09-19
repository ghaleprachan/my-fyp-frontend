package com.example.sajilothree.ActivitiesPackage.CProfilePackage.CustomerFragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.ActivitiesPackage.ChatActivityPackage.ChatSystemActivity;
import com.example.sajilothree.AdapterPackages.ProfileAdapters.AddressesAdapter;
import com.example.sajilothree.AdapterPackages.ProfileAdapters.ContactsAdapter;
import com.example.sajilothree.AdapterPackages.ProfileAdapters.EmailsAdapter;
import com.example.sajilothree.MainActivity;
import com.example.sajilothree.ModelsPackage.ProfileModelPackage.ProfileDetails.ProfileDetailsModel;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.ChatSystemReceiverID.ChatSystemReceiverIdHolder;
import com.example.sajilothree.ServicesPackage.UsernameHolder.OtherPersonUserId;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;
import com.example.sajilothree.VeryUserModel.VerifyUser;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class CustomerDetailsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_user_details_layout, container, false);
    }

    private CardView addressCardView, phoneCardView, emailCardView;
    private String profileDetailsUrl = BaseURL.BaseURL + BaseURL.profileDetailsURL;
    private RecyclerView emailsView, contactsView, addressesView;
    private ShimmerFrameLayout loading;
    private LinearLayout profileDetails;
    private TextView recommendations;
    private static CardView hideCardView;
    private ImageView startChat;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userInterface(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            profileDetailsAPiCall();
        }
        verifyUser();
        onStartChatClick();
    }

    private void onStartChatClick() {
        startChat.setOnClickListener(v -> {
            try {
                if (ChatSystemReceiverIdHolder.addReceiverId(OtherPersonUserId.UserId) &&
                        ChatSystemReceiverIdHolder.addPersonName(OtherPersonUserId.otherUserName)) {
//                if (!OtherPersonUserId.otherUserName.isEmpty()) {
                    startActivity(new Intent(getContext(), ChatSystemActivity.class));
//                }
                }
            } catch (Exception ex) {
                Toast.makeText(getContext(), "Null pointer exception: " + ex, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void userInterface(View view) {
        hideCardView = view.findViewById(R.id.hideCardView);
        addressesView = view.findViewById(R.id.addressesView);
        contactsView = view.findViewById(R.id.contactsView);
        emailsView = view.findViewById(R.id.emailsView);
        loading = view.findViewById(R.id.detailsLoading);
        profileDetails = view.findViewById(R.id.profileDetails);
        recommendations = view.findViewById(R.id.recommendations);
        startChat = view.findViewById(R.id.startChat);
        addressCardView = view.findViewById(R.id.addressCardView);
        phoneCardView = view.findViewById(R.id.phoneCardView);
        emailCardView = view.findViewById(R.id.emailCardView);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void verifyUser() {
        try {
            if (VerifyUser.userVerification().equals(Username.username)) {
                hideCardView.setVisibility(View.GONE);
            } else {
                hideCardView.setVisibility(View.VISIBLE);
            }
        } catch (Exception ex) {
            hideCardView.setVisibility(View.GONE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void profileDetailsAPiCall() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tokenNumber", VerifyUser.userVerification());

            loading.setVisibility(View.VISIBLE);
            profileDetails.setVisibility(View.GONE);
            loading.startShimmer();
            RequestQueue requestQueue;
            @SuppressLint("SetTextI18n")
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    profileDetailsUrl,
                    jsonObject,
                    response -> {
                        loading.stopShimmer();
                        loading.setVisibility(View.GONE);
                        profileDetails.setVisibility(View.VISIBLE);
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            ProfileDetailsModel details = gson.fromJson(String.valueOf(response),
                                    ProfileDetailsModel.class);
                            recommendations.setText(details.getResult().getRecommendations() + " People");
                            fillUpDetails(details);
                        } catch (Exception ex) {
                            Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        loading.stopShimmer();
                        loading.setVisibility(View.GONE);
                        profileDetails.setVisibility(View.GONE);
//                        Toast.makeText(getContext(), "Error " + error.toString(), Toast.LENGTH_SHORT).show();
                        if (getContext() != null) {
                            final AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getContext());
                            dlgAlert.setMessage("Error!\n " + error);
                            dlgAlert.setTitle("Roof Care");
                            dlgAlert.setPositiveButton("Ok",
                                    (dialog, which) -> {
                                        dlgAlert.create().dismiss();
                                        startActivity(new Intent(getContext(), MainActivity.class));
                                        requireActivity().overridePendingTransition(0, 0);
                                    });
                            dlgAlert.setCancelable(false);
                            dlgAlert.create().show();
                        }
                    });
            requestQueue = Volley.newRequestQueue(requireContext());
            requestQueue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void fillUpDetails(ProfileDetailsModel details) {
        RecyclerView.LayoutManager addressLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView.LayoutManager contactLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView.LayoutManager emailLayoutManager = new LinearLayoutManager(getContext());

        addressesView.setLayoutManager(addressLayoutManager);
        contactsView.setLayoutManager(contactLayoutManager);
        emailsView.setLayoutManager(emailLayoutManager);
        if (details.getResult().getAddresses().size() > 0) {
            addressCardView.setVisibility(View.VISIBLE);
            AddressesAdapter addressesAdapter = new AddressesAdapter(getContext(), details.getResult().getAddresses());
            addressesView.setAdapter(addressesAdapter);
        } else {
            if (addressCardView.getVisibility() == View.VISIBLE) {
                addressCardView.setVisibility(View.GONE);
            }
        }

        if (details.getResult().getContacts().size() > 0) {
            phoneCardView.setVisibility(View.VISIBLE);
            ContactsAdapter contactsAdapter = new ContactsAdapter(getContext(), details.getResult().getContacts());
            contactsView.setAdapter(contactsAdapter);
        } else {
            if (phoneCardView.getVisibility() == View.VISIBLE) {
                phoneCardView.setVisibility(View.GONE);
            }
        }
        if (details.getResult().getEmails().size() > 0) {
            emailCardView.setVisibility(View.VISIBLE);
            EmailsAdapter emailsAdapter = new EmailsAdapter(getContext(), details.getResult().getEmails());
            emailsView.setAdapter(emailsAdapter);
        } else {
            if (emailCardView.getVisibility() == View.VISIBLE) {
                emailCardView.setVisibility(View.GONE);
            }
        }
    }
}
