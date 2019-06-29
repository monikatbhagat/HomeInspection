package com.example.bcod2.homeinspection.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.bcod2.homeinspection.roomdatabase.HomeInspectionDb;
import com.example.bcod2.homeinspection.roomdatabase.Property;
import com.example.bcod2.homeinspection.roomdatabase.PropertyDao;

import java.util.List;

public class PropertyRepository {

    private PropertyDao propertyDao;
    private HomeInspectionDb homeInspectionDb;
    private LiveData<List<Property>> mAllProperty;

    public PropertyRepository(Application application) {
        homeInspectionDb=HomeInspectionDb.getDatabase(application);
        propertyDao=homeInspectionDb.propertyDao();
        mAllProperty=propertyDao.getAllProperty();
    }


    public LiveData<List<Property>> getAllProperties()
    {

        return  mAllProperty;
    }

    public LiveData<Property> getProperty(int propertyId)
    {

        return  propertyDao.getProperty(propertyId);
    }

    public void insert(Property property)
    {

        new InsertAsyncTask(propertyDao).execute(property);
    }

    public void delete(Property property) {

        new DeleteAsyncTask(propertyDao).execute(property);
    }
    public void updateProperty(Property property) {

        new UpdatePropertyAsyncTask(propertyDao).execute(property);
    }


    private class OperationsAsyncTask extends AsyncTask<Property, Void, Void> {

        PropertyDao mAsyncTaskDao;

        OperationsAsyncTask(PropertyDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Property... properties) {
            return null;
        }
    }

    private class InsertAsyncTask extends OperationsAsyncTask
    {
        public InsertAsyncTask(PropertyDao mPropertyDao)
        {
            super(mPropertyDao);
        }

        @Override
        protected Void doInBackground(Property... properties) {

            mAsyncTaskDao.insert(properties[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends OperationsAsyncTask
    {
        public DeleteAsyncTask(PropertyDao mPropertyDao)
        {
            super(mPropertyDao);
        }

        @Override
        protected Void doInBackground(Property... properties) {

            mAsyncTaskDao.delete(properties[0]);
            return null;
        }
    }
    private class UpdatePropertyAsyncTask extends OperationsAsyncTask
        {
            public UpdatePropertyAsyncTask(PropertyDao mPropertyDao)
            {
                super(mPropertyDao);
            }

            @Override
            protected Void doInBackground(Property... properties) {

                mAsyncTaskDao.updateProperty(properties[0]);
                return null;
            }
        }

}
