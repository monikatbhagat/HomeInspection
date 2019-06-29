package com.example.bcod2.homeinspection.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.bcod2.homeinspection.roomdatabase.HomeInspectionDb;
import com.example.bcod2.homeinspection.roomdatabase.ImageDao;
import com.example.bcod2.homeinspection.roomdatabase.ImageTable;
import com.example.bcod2.homeinspection.roomdatabase.ItemDao;

import java.util.ArrayList;
import java.util.List;

public class ImageRepository {
    private ImageDao imageDao;
    private HomeInspectionDb homeInspectionDb;
    LiveData<List<ImageTable>> mAllImagesofRoom;
    LiveData<List<String>> mAllImagesUrlsofRoom;

    public ImageRepository(Application application) {
        homeInspectionDb=HomeInspectionDb.getDatabase(application);
        imageDao=homeInspectionDb.imageDao();
//        mAllItems=itemDao.getAllItem();
    }

    public void insertImage(ImageTable imageTable)
    {
        new InsertImageAsyncTask(imageDao).execute(imageTable);
    }
    public LiveData<List<ImageTable>> getRoomImagesList(int roomId)
    {

        mAllImagesofRoom=imageDao.getRoomImagesList(roomId);
        return  mAllImagesofRoom;
    }

  public LiveData<List<String>> getRoomImagesUrls(int roomId)
    {

        mAllImagesUrlsofRoom=imageDao.getRoomImagesUrls(roomId);
        return  mAllImagesUrlsofRoom;
    }


    private class OperationsAsyncTask extends AsyncTask<ImageTable, Void, Void> {

        ImageDao mAsyncTaskDao;

        OperationsAsyncTask(ImageDao dao) {
            this.mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(ImageTable... imageTables) {
            return null;
        }
    }

    private class InsertImageAsyncTask extends OperationsAsyncTask
    {
        public InsertImageAsyncTask(ImageDao mImageDao)
        {
            super(mImageDao);
        }

        @Override
        protected Void doInBackground(ImageTable... imageTables) {

            mAsyncTaskDao.insertImage(imageTables[0]);
            return null;
        }
    }

}
