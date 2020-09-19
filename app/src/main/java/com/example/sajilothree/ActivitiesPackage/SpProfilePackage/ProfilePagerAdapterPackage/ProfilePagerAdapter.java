package com.example.sajilothree.ActivitiesPackage.SpProfilePackage.ProfilePagerAdapterPackage;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.sajilothree.ActivitiesPackage.SpProfilePackage.ProfileFragmentPackage.SpAboutFragment;
import com.example.sajilothree.ActivitiesPackage.SpProfilePackage.ProfileFragmentPackage.SpDetailsFragment;
import com.example.sajilothree.ActivitiesPackage.SpProfilePackage.ProfileFragmentPackage.SpFeedbackFragment;
import com.example.sajilothree.ActivitiesPackage.SpProfilePackage.ProfileFragmentPackage.SpPostFragment;
import com.example.sajilothree.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class ProfilePagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.profile_tab_one, R.string.profile_tab_two, R.string.profile_tab_three, R.string.profile_tab_four};
    private final Context mContext;

    public ProfilePagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if (position == 0) {
            return new SpDetailsFragment();
        } else if (position == 1) {
            return new SpAboutFragment();
        } else if (position == 2) {
            return new SpPostFragment();
        } else if (position == 3) {
            return new SpFeedbackFragment();
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
        return 4;
    }
}