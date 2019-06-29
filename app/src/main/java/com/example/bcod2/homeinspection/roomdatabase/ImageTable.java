package com.example.bcod2.homeinspection.roomdatabase;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity(tableName = "image", indices = {@Index(value = {"Image_id"}, unique = true)})
public class ImageTable {


    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "Image_id")
    int imageId;

    @NonNull
    @ColumnInfo(name = "Room_id")
    int roomId;

      @NonNull
    @ColumnInfo(name = "Image_Url")
      String imageUrl;

    @NonNull
    @ColumnInfo(name = "Image_TimeStamp")
    String imageTimeStamp;

    public ImageTable(int imageId, int roomId,  String imageUrl, String imageTimeStamp) {
        this.imageId = imageId;
        this.roomId = roomId;
        this.imageUrl = imageUrl;
        this.imageTimeStamp = imageTimeStamp;
    }

    public int getImageId() {
        return imageId;
    }

    public int getRoomId() {
        return roomId;
    }

    @NonNull
    public String getImageUrl() {
        return imageUrl;
    }

    @NonNull
    public String getImageTimeStamp() {
        return imageTimeStamp;
    }
/*
    @NonNull
    @ColumnInfo(name="Image",typeAffinity = ColumnInfo.BLOB)
    byte[] image;*/
}


