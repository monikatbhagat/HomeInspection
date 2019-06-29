package com.example.bcod2.homeinspection.roomdatabase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface RoomDao {
    @Insert(onConflict = OnConflictStrategy.FAIL)
    void insertRoom(RoomTable roomTable);

    @Query("SELECT * FROM room WHERE Property_id=:propertyId AND Room_name=:roomName")
    LiveData<RoomTable> getRoom(int propertyId, String roomName);

    @Query("SELECT * FROM room WHERE Property_id=:propertyId AND InnerRoom_Flag=:innerRoomFlag")
    LiveData<List<RoomTable>> getInnerRoomList(int propertyId, int innerRoomFlag);

    @Query("SELECT Room_id FROM room WHERE Property_id=:propertyId AND InnerRoom_Flag=:innerRoomFlag")
    LiveData<List<Integer>> getInnerRoomIdsListForProperty(int propertyId, int innerRoomFlag);

    @Query("DELETE FROM room WHERE Property_id=:propertyId")
    void deleteRoomForProperty(int propertyId);

    @Query("SELECT Room_id FROM room WHERE Property_id=:propertyId")
    LiveData<List<Integer>>  getRoomIdListOfProperty(int propertyId );

    @Delete
    void deleteRoom(RoomTable roomTable);

    @Query("SELECT * FROM room WHERE Room_id=:roomId")
    LiveData<RoomTable> getRoomFromRoomId(int roomId);

    @Update
    void updateRoom(RoomTable roomTable);

//    @Query("SELECT Room_id FROM room WHERE Property_id=:propertyId AND Room_name=:roomName")
//    LiveData<Integer> getRoomId(int propertyId, String roomName);




}
