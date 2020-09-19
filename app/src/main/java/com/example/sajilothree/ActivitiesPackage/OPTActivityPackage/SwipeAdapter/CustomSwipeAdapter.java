package com.example.sajilothree.ActivitiesPackage.OPTActivityPackage.SwipeAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.sajilothree.R;

import java.util.Arrays;
import java.util.List;

public class CustomSwipeAdapter extends PagerAdapter {
    private Context mContext;

    private int[] image_resource = {R.drawable.slide_image_one,
            R.drawable.slide_image_two,
            R.drawable.deletelawyer,
            R.drawable.deletebarber};
    private List<String> titles = Arrays.asList("Our Aim!", "Our Motto!", "Three", "Four");
    private List<String> description = Arrays.asList("We are here to provide the best home specialist near you",
            "Quick * Trusted * Cheap",
            "We are here to provide the best home specialist near you",
            "Quick * Trusted * Cheap");

    public CustomSwipeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return image_resource.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == object);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        View item_view = layoutInflater
                .inflate(R.layout.otp_activity_slider_view,
                        container, false);
        ImageView imageView = item_view.findViewById(R.id.image_view);
        TextView textView = item_view.findViewById(R.id.title);
        TextView descriptionHere = item_view.findViewById(R.id.description);

        imageView.setImageResource(image_resource[position]);
        textView.setText(titles.get(position));
        descriptionHere.setText(description.get(position));

        container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
