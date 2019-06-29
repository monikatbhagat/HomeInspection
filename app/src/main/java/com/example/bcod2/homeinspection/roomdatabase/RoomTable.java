package com.example.bcod2.homeinspection.roomdatabase;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
// last added "Room_name"
//@Entity(tableName = "room",indices = {@Index(value = {"Room_id", "Room_name"},
//        unique = true)})
@Entity(tableName = "room",indices = {@Index(value = {"Property_id","Room_name"},
        unique = true)})
public class RoomTable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "Room_id")
    int room_id;


    @NonNull
    @ColumnInfo(name = "Property_id")
    int id;

    @NonNull
    @ColumnInfo(name = "InnerRoom_Flag")
    int innerRoomFlag;

    @NonNull
    @ColumnInfo(name = "Room_name")
    private String roomName;

    @ColumnInfo(name = "Room_description")
    private String roomDescription;

    @NonNull

    public int isInnerRoomFlag() {
        return innerRoomFlag;
    }

    public void setInnerRoomFlag(@NonNull int innerRoomFlag) {
        this.innerRoomFlag = innerRoomFlag;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public RoomTable(int room_id, int id,int innerRoomFlag, String roomName, String roomDescription ) {
        this.room_id = room_id;
        this.id = id;
        this.innerRoomFlag = innerRoomFlag;
        this.roomName = roomName;
        this.roomDescription = roomDescription;
    }

    public void setRoom_id(@NonNull int room_id) {
        this.room_id = room_id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public void setRoomName(@NonNull String roomName) {
        this.roomName = roomName;
    }

    @NonNull
    public int getRoom_id() {
        return room_id;
    }

    @NonNull
    public int getId() {
        return id;
    }

    @NonNull
    public String getRoomName() {

        return roomName;
    }
}
