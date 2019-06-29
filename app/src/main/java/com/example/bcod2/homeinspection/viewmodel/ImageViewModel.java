package com.example.bcod2.homeinspection.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.bcod2.homeinspection.repository.ImageRepository;
import com.example.bcod2.homeinspection.roomdatabase.ImageTable;

import java.util.ArrayList;
import java.util.List;

public class ImageViewModel extends AndroidViewModel {

    ImageRepository imageRepository;
    LiveData<List<ImageTable>> mAllImagesofRoom;
    LiveData<List<String>> mAllImagesUrlsofRoom;

    public ImageViewModel(@NonNull Application application) {
        super(application);
        imageRepository=new ImageRepository(application);

    }

    public  void insertImage(ImageTable imageTable)
    {
        imageRepository.insertImage(imageTable);
    }
    public LiveData<List<ImageTable>> getRoomImagesList(int roomId)
    {

        mAllImagesofRoom=imageRepository.getRoomImagesList(roomId);
        return  mAllImagesofRoom;
    }

    public LiveData<List<String>> getRoomImagesUrls(int roomId)
    {

        mAllImagesUrlsofRoom=imageRepository.getRoomImagesUrls(roomId);
        return  mAllImagesUrlsofRoom;
    }


}
