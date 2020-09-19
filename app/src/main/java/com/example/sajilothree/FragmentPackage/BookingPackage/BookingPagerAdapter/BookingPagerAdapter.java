package com.example.sajilothree.FragmentPackage.BookingPackage.BookingPagerAdapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.sajilothree.FragmentPackage.BookingPackage.FragmentsPackage.Bookings;
import com.example.sajilothree.FragmentPackage.BookingPackage.FragmentsPackage.Messages;
import com.example.sajilothree.FragmentPackage.BookingPackage.FragmentsPackage.Requests;
import com.example.sajilothree.R;

public class BookingPagerAdapter extends FragmentPagerAdapter {
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.messages, R.string.bookings, R.string.booking_requests};//new int[]{R.string.messages, R.string.bookings};
    private final Context mContext;

    public BookingPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if (position == 0) {
            return new Messages();
        } else if (position == 1) {
            return new Bookings();
        } else if (position == 2) {
            return new Requests();
        } else {
            return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 3;
    }
}
