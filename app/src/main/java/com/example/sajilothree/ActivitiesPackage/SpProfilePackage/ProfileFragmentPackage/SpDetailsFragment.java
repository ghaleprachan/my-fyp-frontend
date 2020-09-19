package com.example.sajilothree.ActivitiesPackage.SpProfilePackage.ProfileFragmentPackage;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.ActivitiesPackage.BookingProcessPackage.BookingPackage.BookingActivity;
import com.example.sajilothree.ActivitiesPackage.ChatActivityPackage.ChatSystemActivity;
import com.example.sajilothree.AdapterPackages.ProfileAdapters.AddressesAdapter;
import com.example.sajilothree.AdapterPackages.ProfileAdapters.ContactsAdapter;
import com.example.sajilothree.AdapterPackages.ProfileAdapters.EmailsAdapter;
import com.example.sajilothree.ApiCallsPackage.UserFavorites.AddUserToFavorites;
import com.example.sajilothree.MainActivity;
import com.example.sajilothree.ModelsPackage.BookingDetailsModel.NeededBookingDetails.BookingDetailsModel;
import com.example.sajilothree.ModelsPackage.ProfileModelPackage.ProfileDetails.ProfileDetailsModel;
import com.example.sajilothree.R;
import com.example.sajilothree.ServicesPackage.BookingServices.BookingDetailsProfile.BookingDetailsServices;
import com.example.sajilothree.ServicesPackage.ChatSystemReceiverID.ChatSystemReceiverIdHolder;
import com.example.sajilothree.ServicesPackage.UsernameHolder.OtherPersonUserId;
import com.example.sajilothree.ServicesPackage.UsernameHolder.Username;
import com.example.sajilothree.VeryUserModel.VerifyUser;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

public class SpDetailsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_user_details_layout, container, false);
    }

    private CardView addressCardView, phoneCardView, emailCardView;
    private String profileDetailsUrl = BaseURL.BaseURL + BaseURL.profileDetailsURL;
    private RecyclerView emailsView, contactsView, addressesView;
    private ShimmerFrameLayout loading;
    private LinearLayout profileDetails, sendBookingRequest, favourites;
    private TextView recommendations;
    private static CardView hideCardView;
    private ImageView startChat, fav_add, fav_added;
    private ImageView bookingIcon, bookingLoading;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userInterface(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            profileDetailsAPiCall();
        }
        AddUserToFavorites.getUserFavId(getContext(), fav_added, fav_add);

        verifyUser();
        onStartChat();

        onBookClick();

        onFavoriteClick();
    }

    private void onFavoriteClick() {
        favourites.setOnClickListener(v -> {
            AddUserToFavorites.addToFavorites(getContext());
            if (fav_added.getVisibility() == View.VISIBLE) {
                fav_add.setVisibility(View.VISIBLE);
                fav_added.setVisibility(View.GONE);
            } else {
                fav_add.setVisibility(View.GONE);
                fav_added.setVisibility(View.VISIBLE);
            }
        });
    }

    private void onBookClick() {
        sendBookingRequest.setOnClickListener(v -> {
            bookingDetailsApiCall();
        });
    }

    private void bookingDetailsApiCall() {
        try {
            bookingIcon.setVisibility(View.GONE);
            bookingLoading.setVisibility(View.VISIBLE);
            AnimationDrawable animation = (AnimationDrawable) bookingLoading.getDrawable();
            animation.start();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Customer", Username.username);
            jsonObject.put("ServiceProvider", OtherPersonUserId.UserId);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, BaseURL.bookingDetails, jsonObject, response -> {
                animation.stop();
                bookingLoading.setVisibility(View.GONE);
                bookingIcon.setVisibility(View.VISIBLE);
                try {
                    BookingDetailsModel bookingDetailsModel = new GsonBuilder().create().fromJson(String.valueOf(response), BookingDetailsModel.class);
                    if (bookingDetailsModel.getStatus()) {
                        if (BookingDetailsServices.addBookingModel(bookingDetailsModel)) {
                            startActivity(new Intent(getContext(), BookingActivity.class));
                            requireActivity().overridePendingTransition(R.anim.bottom_up,
                                    R.anim.nothing);
                        }
                    } else {
                        Toast.makeText(getContext(), "Server Issues: " + bookingDetailsModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    Toast.makeText(getContext(), "Failed to parse!" + ex, Toast.LENGTH_SHORT).show();
                }
            }, error -> {
                animation.stop();
                bookingLoading.setVisibility(View.GONE);
                bookingIcon.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            });
            request.setRetryPolicy(new DefaultRetryPolicy(
                    90000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Exception: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    private void onStartChat() {
        startChat.setOnClickListener(v -> {
            if (ChatSystemReceiverIdHolder.addReceiverId(OtherPersonUserId.UserId)) {
                if (!OtherPersonUserId.otherUserName.isEmpty()) {
                    ChatSystemReceiverIdHolder.addPersonName(OtherPersonUserId.otherUserName);
                    startActivity(new Intent(getContext(), ChatSystemActivity.class));
                }
            }
        });
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

    private void userInterface(View view) {
        hideCardView = view.findViewById(R.id.hideCardView);
        addressesView = view.findViewById(R.id.addressesView);
        contactsView = view.findViewById(R.id.contactsView);
        emailsView = view.findViewById(R.id.emailsView);
        loading = view.findViewById(R.id.detailsLoading);
        profileDetails = view.findViewById(R.id.profileDetails);
        recommendations = view.findViewById(R.id.recommendations);

        addressCardView = view.findViewById(R.id.addressCardView);
        phoneCardView = view.findViewById(R.id.phoneCardView);
        emailCardView = view.findViewById(R.id.emailCardView);

        startChat = view.findViewById(R.id.startChat);

        sendBookingRequest = view.findViewById(R.id.book);

        bookingIcon = view.findViewById(R.id.bookingIcon);
        bookingLoading = view.findViewById(R.id.bookingLoading);

        favourites = view.findViewById(R.id.favourites);

        fav_add = view.findViewById(R.id.not_fav);
        fav_added = view.findViewById(R.id.fab);
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
