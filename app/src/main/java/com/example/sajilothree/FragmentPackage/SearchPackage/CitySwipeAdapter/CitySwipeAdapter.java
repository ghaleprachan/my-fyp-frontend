package com.example.sajilothree.FragmentPackage.SearchPackage.CitySwipeAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.sajilothree.APIPackage.BaseUrlPackage.BaseURL;
import com.example.sajilothree.ModelsPackage.SearchModel.CitiesModelPackage.CitiesModel;
import com.example.sajilothree.R;

public class CitySwipeAdapter extends PagerAdapter {
    private Context context;
    private CitiesModel citiesModels;

    public CitySwipeAdapter(Context context, CitiesModel citiesModels) {
        this.context = context;
        this.citiesModels = citiesModels;
    }

    @Override
    public int getCount() {
        return citiesModels.getResult().size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        View item_view = layoutInflater
                .inflate(R.layout.cities_swap_view_design,
                        container, false);
        ImageView cityImage = item_view.findViewById(R.id.cityImg);
        TextView cityName = item_view.findViewById(R.id.cityName);
        Glide.with(context)
                .load(BaseURL.BaseURL + citiesModels.getResult().get(position).getDisctrictImage())
                .into(cityImage);
        cityName.setText(citiesModels.getResult().get(position).getDistrictName());

        container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
