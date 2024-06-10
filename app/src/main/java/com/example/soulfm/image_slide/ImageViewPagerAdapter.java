package com.example.soulfm.image_slide;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.soulfm.R;

import java.util.List;

public class ImageViewPagerAdapter extends PagerAdapter {

    List<Image> imageList;

    public ImageViewPagerAdapter(List<Image> imageList) {
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_image_slide, container,false);

        ImageView iv_image_slide = view.findViewById(R.id.iv_image_slide);

        Image image = imageList.get(position);
        iv_image_slide.setImageResource(image.getImageId());

        //add view vào container
        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        if(imageList != null){
            return imageList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
