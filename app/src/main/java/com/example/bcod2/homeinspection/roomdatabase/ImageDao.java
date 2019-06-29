package com.example.bcod2.homeinspection.roomdatabase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ImageDao {

    @Insert
    void insertImage(ImageTable imageTable);

    @Query("SELECT * FROM image WHERE Room_id=:roomId")
    LiveData<List<ImageTable>> getRoomImagesList(int roomId);

    @Query("SELECT Image_Url FROM image WHERE Room_id=:roomId")
    LiveData<List<String>> getRoomImagesUrls(int roomId);

}
