package com.example.bcod2.homeinspection.roomdatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "item",indices = {@Index(value = {"Item_id"},
        unique = true)})

public class Item {


    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "Item_id")
    int itemId;

    @NonNull
    @ColumnInfo(name = "Room_id")
    int roomId;

    @NonNull
    @ColumnInfo(name = "Item_name")
    private String itemName;


    @NonNull
    @ColumnInfo(name = "Item_cost")
    private int itemCost;



    @ColumnInfo(name = "Item_note")
    private String itemNote;


    public Item(int itemId, int roomId, String itemName, int itemCost, String itemNote) {
        this.itemId = itemId;
        this.roomId = roomId;
        this.itemName = itemName;
        this.itemCost = itemCost;
        this.itemNote = itemNote;
    }

    @NonNull
    public int getItemId() {
        return itemId;
    }

    @NonNull
    public int getRoomId() {
        return roomId;
    }

    @NonNull
    public String getItemName() {
        return itemName;
    }

    @NonNull
    public int getItemCost() {
        return itemCost;
    }

    public String getItemNote() {
        return itemNote;
    }
}
