package com.example.bcod2.homeinspection.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.bcod2.homeinspection.R;
import com.example.bcod2.homeinspection.ui.TouchImageView;
import com.example.bcod2.homeinspection.utilities.H;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FullScreenImageAdapter extends PagerAdapter {

    private Activity _activity;
    private ArrayList<String> _imagePaths;
    private LayoutInflater inflater;

    // constructor
    public FullScreenImageAdapter(Activity activity, ArrayList<String> imagePaths) {
        this._activity = activity;
        this._imagePaths = imagePaths;
    }

    @Override
    public int getCount() {
        return this._imagePaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TouchImageView imgDisplay;

        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container,
                false);

        imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);

        try {
            H.L("Create"+_imagePaths);
            /*Picasso.with(_activity)
                    .load(_imagePaths.get(position))
                    .placeholder(R.drawable.progress_loading_big)
                    .into(imgDisplay);*/
            Glide.with(_activity)
                    .load(_imagePaths.get(position))
                    .into(imgDisplay);
        } catch (Exception e) {
            //e.printStackTrace();
        }

        ((ViewPager) container).addView(viewLayout);
        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }

}