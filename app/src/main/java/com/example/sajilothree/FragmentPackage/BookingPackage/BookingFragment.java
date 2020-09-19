package com.example.sajilothree.FragmentPackage.BookingPackage;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.sajilothree.FragmentPackage.BookingPackage.BookingPagerAdapter.BookingPagerAdapter;
import com.example.sajilothree.FragmentPackage.HomePackage.SectionPagerAdapter.SectionsPagerAdapter;
import com.example.sajilothree.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class BookingFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.booking_fragment, container, false);
    }

    private ViewPager viewPager;
    private TabLayout tabs;
//    private Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userInterface(view);
        /*((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        toolbar.setTitle("Bookings");*/

        BookingPagerAdapter bookingPagerAdapter = new BookingPagerAdapter(getContext(),
                getChildFragmentManager());
        viewPager.setAdapter(bookingPagerAdapter);
        tabs.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(3);
        setTabIcons();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setTabIcons() {
        Objects.requireNonNull(tabs.getTabAt(0)).setIcon(R.drawable.ic_message_black_24dp);
        Objects.requireNonNull(tabs.getTabAt(1)).setIcon(R.drawable.ic_assignment_turned_in_black_24dp);
        Objects.requireNonNull(tabs.getTabAt(2)).setIcon(R.drawable.ic_person_add_black_24dp);
    }

    private void userInterface(View view) {
//        toolbar = view.findViewById(R.id.bookingToolbar);
        viewPager = view.findViewById(R.id.bookingViewPager);
        tabs = view.findViewById(R.id.bookingTabs);
    }

}
