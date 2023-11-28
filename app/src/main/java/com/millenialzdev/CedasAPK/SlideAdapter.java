package com.millenialzdev.CedasAPK;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class SlideAdapter extends PagerAdapter {

    private Context context;
    private List<Slide> slideList;

    public SlideAdapter(Context context, List<Slide> slideList) {
        this.context = context;
        this.slideList = slideList;
    }

    @Override
    public int getCount() {
        return slideList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View slideLayout = inflater.inflate(R.layout.slide_item, null);

        ImageView imageViewSlide = slideLayout.findViewById(R.id.imageView);
        imageViewSlide.setImageResource(slideList.get(position).getImageResourceId());

        container.addView(slideLayout);
        return slideLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}


