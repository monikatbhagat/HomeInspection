package com.example.bcod2.homeinspection.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.bcod2.homeinspection.repository.PropertyRepository;
import com.example.bcod2.homeinspection.roomdatabase.Property;
import com.example.bcod2.homeinspection.utilities.H;

import java.util.List;

public class PropertyViewModel extends AndroidViewModel {
    PropertyRepository propertyRepository;
    private LiveData<List<Property>> mAllProperty;

    public PropertyViewModel(@NonNull Application application) {
        super(application);

       propertyRepository=new PropertyRepository(application);
        mAllProperty=propertyRepository.getAllProperties();

    }

    public void insert(Property property)
    {

        propertyRepository.insert(property);
    }

    public LiveData<List<Property>> getAllProperties()
    {

        return  mAllProperty;
    }
 public LiveData<Property> getProperty(int propertyId)
    {

        return  propertyRepository.getProperty(propertyId);
    }


    public void delete(Property property) {
       propertyRepository.delete(property);
    }
    public void updateProperty(Property property) {
       propertyRepository.updateProperty(property);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        H.L("ViewModel Destroyed");
    }




}














/*
package com.example.bcod2.homeinspection.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import com.example.bcod2.homeinspection.roomdatabase.HomeInspectionDb;
import com.example.bcod2.homeinspection.roomdatabase.Property;
import com.example.bcod2.homeinspection.roomdatabase.PropertyDao;
import com.example.bcod2.homeinspection.util.H;

import java.util.List;

public class PropertyViewModel extends AndroidViewModel {
    private PropertyDao propertyDao;
    private HomeInspectionDb homeInspectionDb;
    private LiveData<List<Property>> mAllProperty;

    public PropertyViewModel(@NonNull Application application) {
        super(application);

        homeInspectionDb=HomeInspectionDb.getDatabase(application);
        propertyDao=homeInspectionDb.propertyDao();
        mAllProperty=propertyDao.getAllProperty();

    }

    public void insert(Property property)
    {

        new InsertAsyncTask(propertyDao).execute(property);
    }

    public LiveData<List<Property>> getAllProperties()
    {
        return  mAllProperty;
    }


    public void delete(Property property) {
        new DeleteAsyncTask(propertyDao).execute(property);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        H.L("ViewModel Destroyed");
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


}
*/
