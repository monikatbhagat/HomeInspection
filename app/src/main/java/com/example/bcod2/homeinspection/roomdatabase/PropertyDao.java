package com.example.bcod2.homeinspection.roomdatabase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
@Dao
public interface PropertyDao {

    @Insert
    void insert(Property property);

    @Query("SELECT * FROM property")
    LiveData<List<Property>> getAllProperty();

    @Query("SELECT * FROM property WHERE Property_id=:propertyId")
    LiveData<Property> getProperty(int propertyId);

    @Delete
    int delete(Property property);

    @Update
    void updateProperty(Property property);
}
