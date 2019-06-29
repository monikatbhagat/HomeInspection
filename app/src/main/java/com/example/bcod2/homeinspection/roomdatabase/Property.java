package com.example.bcod2.homeinspection.roomdatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
@Entity(tableName = "property",indices = {@Index(value ="Property_id",
        unique = true)})
public class Property {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "Property_id")
    private int id;

    @NonNull
    @ColumnInfo(name = "Property_Name")
    private String mPropertyName;

    @NonNull
    @ColumnInfo(name = "Property_Address")
    private String mPropertyAddress;

    public Property(int id, String propertyName,  String propertyAddress) {
        this.id = id;
        this.mPropertyName = propertyName;
        this.mPropertyAddress = propertyAddress;
    }
//    public Property( String id,  String propertyName,  String propertyAddress) {
//        this.id = id;
//        this.mPropertyName = propertyName;
//        this.mPropertyAddress = propertyAddress;
//    }

    @NonNull
    public int getId() {
        return id;
    }

    @NonNull
    public String getPropertyName() {
        return this.mPropertyName;
    }

    @NonNull
    public String getPropertyAddress() {
        return this.mPropertyAddress;
    }
}
