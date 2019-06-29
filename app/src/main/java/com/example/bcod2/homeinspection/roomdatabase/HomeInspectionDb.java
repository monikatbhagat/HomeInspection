package com.example.bcod2.homeinspection.roomdatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities ={Property.class, RoomTable.class, Item.class, ImageTable.class}, version = 5)
public abstract class HomeInspectionDb  extends RoomDatabase {

    public abstract PropertyDao propertyDao();
    public abstract RoomDao roomDao();
    public abstract ItemDao itemDao();
    public  abstract ImageDao imageDao();

    private static volatile HomeInspectionDb homeInspectionDbInstance;


    public static HomeInspectionDb getDatabase(final Context context)
    {
        if(homeInspectionDbInstance==null)
        {
            synchronized (HomeInspectionDb.class)
            {
                if (homeInspectionDbInstance == null) {
                    homeInspectionDbInstance = Room.databaseBuilder(context.getApplicationContext(),
                            HomeInspectionDb.class, "homeinspection_database")
                            .build();
                }
            }
        }
        return homeInspectionDbInstance;
    }
}

