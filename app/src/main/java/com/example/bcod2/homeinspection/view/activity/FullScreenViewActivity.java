package com.example.bcod2.homeinspection.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.bcod2.homeinspection.R;
import com.example.bcod2.homeinspection.adapter.FullScreenImageAdapter;
import com.example.bcod2.homeinspection.roomdatabase.ImageTable;
import com.example.bcod2.homeinspection.utilities.H;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FullScreenViewActivity extends AppCompatActivity {

    private FullScreenImageAdapter adapter;
    private ViewPager viewPager;
    static String mCurrentImage;
    static int mPosition;
    static List<ImageTable> mImageTableList;
    ArrayList<ImageTable> list;
    ArrayList<String> image_data;

    public static void open(Context context, List<ImageTable> imageTableList,int position, String currentImage) {
        H.L("4444");
        mImageTableList=imageTableList;
        mCurrentImage=currentImage;
        mPosition=position;
        context.startActivity(new Intent(context, FullScreenViewActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        viewPager = (ViewPager) findViewById(R.id.pager);
         list= new ArrayList<ImageTable>(mImageTableList);
        image_data = new ArrayList<>();
            for (int k = 0; k < list.size(); k++) { ;
                H.L("immmm"+list.get(k).getImageUrl());

                image_data.add(list.get(k).getImageUrl());
            }

        H.L("101010"+mCurrentImage);
            int pos = image_data.indexOf(mCurrentImage);
            // int pos1 = image_data.indexOf(image1);

            // displaying selected image first
            viewPager.setCurrentItem(pos);
            //  viewPager.setCurrentItem(pos1);
//            Collections.reverse(image_data);
            H.L("hhhjhjjj"+image_data);
            adapter = new FullScreenImageAdapter(FullScreenViewActivity.this, image_data);
            viewPager.setAdapter(adapter);
    }
}
