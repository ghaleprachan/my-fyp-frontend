package com.example.sajilothree.ActivitiesPackage.DisplaySearchPackage.PagerAdapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.sajilothree.ActivitiesPackage.DisplaySearchPackage.FragmentsPacakges.AllSearchResult;
import com.example.sajilothree.ActivitiesPackage.DisplaySearchPackage.FragmentsPacakges.OrgFragment;
import com.example.sajilothree.ActivitiesPackage.DisplaySearchPackage.FragmentsPacakges.PersonFragment;
import com.example.sajilothree.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SearchPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.all_searches, R.string.person_searches, R.string.institute_searches};
    private final Context mContext;

    public SearchPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if (position == 0) {
            return new AllSearchResult();
        } else if (position == 1) {
            return new PersonFragment();
        } else if (position == 2) {
            return new OrgFragment();
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