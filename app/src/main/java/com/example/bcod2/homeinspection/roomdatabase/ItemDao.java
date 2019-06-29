package com.example.bcod2.homeinspection.roomdatabase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.provider.ContactsContract;

import java.util.List;

@Dao
public interface ItemDao  {
    @Insert
    void insert(Item item);

    @Query("SELECT * FROM item ORDER BY Item_name")
    LiveData<List<Item>> getAllItem();

    @Query("SELECT * FROM item WHERE Room_id=:roomId")
    LiveData<List<Item>> getRoomItemList(int roomId);

    @Delete
    void deleteItem(Item item);

    @Query("DELETE FROM item WHERE Room_id=:roomID")
    void deleteItemForRoom(int roomID);

    @Query("SELECT * FROM item WHERE Item_id=:itemId")
    LiveData<Item> getItemFromItemId(int itemId);

    @Update
    void updateItem(Item item);



}
