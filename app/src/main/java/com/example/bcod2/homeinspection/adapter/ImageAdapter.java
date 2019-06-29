

package com.example.bcod2.homeinspection.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bcod2.homeinspection.R;
import com.example.bcod2.homeinspection.roomdatabase.ImageTable;
import com.example.bcod2.homeinspection.utilities.H;
import com.example.bcod2.homeinspection.view.activity.FullScreenViewActivity;
import com.example.bcod2.homeinspection.view.activity.InspectionActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private final LayoutInflater layoutInflater;
    private List<ImageTable> mImageList;
    private ImageTable image;
    Context mContext;
    File file;

    public ImageAdapter(Context context, List<ImageTable> imageTableList) {
        H.L("222");
        mContext=context;
        mImageList=imageTableList;
        layoutInflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View imageView=layoutInflater.inflate(R.layout.list_inspection_image,viewGroup,false);
        ImageViewHolder viewHolder=new ImageViewHolder(imageView);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, final int position) {

        if(mImageList!=null)
        {
            image=mImageList.get(position);
            imageViewHolder.setData(image.getImageUrl(),image.getImageTimeStamp(),position);

            imageViewHolder.iv_Image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    H.L("333");
                     FullScreenViewActivity.open(mContext,mImageList,position,image.getImageUrl());

                }
            });


        }
        else
        {

        }

    }

    @Override
    public int getItemCount() {
        if (mImageList != null)
            return mImageList.size();
        else
            return 0;
    }

    public void setImage(List<ImageTable> imagesTable) {
        mImageList= imagesTable;
        notifyDataSetChanged();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder
    {
        ImageView iv_Image;
        TextView tv_TimeStamp;
        private int mPosition;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_Image=itemView.findViewById(R.id.iv_Image);
            tv_TimeStamp=itemView.findViewById(R.id.tv_TimeStamp);

        }

        public void setData(String imageUrl, String imageTimeStamp,int position) {
            file=new File(imageUrl);
            Glide.with(mContext).load(file).into(iv_Image);
           /* Picasso.with(mContext)
                    .load(file)
                    .placeholder(R.drawable.progress_loading_big)
                    .into(iv_Image);*/
//            iv_Image.setImageURI(Uri.parse(String.valueOf(imageUrl)));
            tv_TimeStamp.setText(imageTimeStamp);
            mPosition = position;
        }
    }

    // region Interfaces
    public interface OnImageClickListener {
        void onImageClick(int position);
    }

    public interface ImageThumbnailLoader {
        void loadImageThumbnail(ImageView iv, String imageUrl, int dimension);
    }
    // endregion
}
