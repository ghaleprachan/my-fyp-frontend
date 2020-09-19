package com.example.sajilothree.ActivitiesPackage.CProfilePackage.PagerAdapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.sajilothree.ActivitiesPackage.CProfilePackage.CustomerFragments.CustomerDetailsFragment;
import com.example.sajilothree.ActivitiesPackage.CProfilePackage.CustomerFragments.CustomerPostFragment;
import com.example.sajilothree.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class CustomerPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.profile_tab_one, R.string.profile_tab_three};
    private final Context mContext;

    public CustomerPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if (position == 0) {
            return new CustomerDetailsFragment();
        } else if (position == 1) {
            return new CustomerPostFragment();
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
        return 2;
    }
}