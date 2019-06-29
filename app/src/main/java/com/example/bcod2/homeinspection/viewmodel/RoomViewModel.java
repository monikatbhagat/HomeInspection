package com.example.bcod2.homeinspection.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.bcod2.homeinspection.repository.RoomtableRepository;
import com.example.bcod2.homeinspection.roomdatabase.RoomTable;
import com.example.bcod2.homeinspection.utilities.H;

import java.util.List;

public class RoomViewModel extends AndroidViewModel {
    RoomtableRepository roomtableRepository;
    int propertyId;
    String roomName;


    public RoomViewModel(@NonNull Application application)
    {
        super(application);
       roomtableRepository=new RoomtableRepository(application,propertyId,roomName);
    }

    public void insert(RoomTable roomTable)
    {

        roomtableRepository.insert(roomTable);
    }

     public LiveData<RoomTable> getRoom(int propertyId, String roomName)
     {
         H.L("222222");
         return roomtableRepository.getRoom(propertyId,roomName);
     }
     public LiveData<RoomTable> getRoomFromRoomId(int roomId)
     {
              return roomtableRepository.getRoomFromRoomId(roomId);
     }

    public void updateRoom(RoomTable roomTable)
    {

        roomtableRepository.updateRoom(roomTable);
    }


    public LiveData<List<RoomTable>> getInnerRoomList(int propertyId, int innerRoomFlag )
    {
        return  roomtableRepository.getInnerRoomList(propertyId, innerRoomFlag);

    }
    public LiveData<List<Integer>> getRoomIdListofProperty(int propertyId )
    {
        return  roomtableRepository.getRoomIdListofProperty(propertyId);

    }
    public LiveData<List<Integer>> getInnerRoomIdListofProperty(int propertyId,int innerRoomFlag )
    {
        return  roomtableRepository.getInnerRoomIdListofProperty(propertyId, innerRoomFlag);

    }

    public void deleteRoomForProperty(int propertyId)
    {
        roomtableRepository.deleteRoomForProprty(propertyId);
    }

    public void deleteRoom(RoomTable roomTable)
    {
        roomtableRepository.deleteRoom(roomTable);
    }

//     public LiveData<Integer> getRoomId(int propertyId, String roomName)
//     {
//         H.L("222222");
//         return roomtableRepository.getRoomId(propertyId,roomName);
//     }




}











/*
package com.example.bcod2.homeinspection.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.bcod2.homeinspection.roomdatabase.HomeInspectionDb;
import com.example.bcod2.homeinspection.roomdatabase.RoomDao;
import com.example.bcod2.homeinspection.roomdatabase.RoomTable;
import com.example.bcod2.homeinspection.util.H;

public class RoomViewModel extends AndroidViewModel {

    private RoomDao roomDao;
    private HomeInspectionDb homeInspectionDb;

    public RoomViewModel(@NonNull Application application)
    {
        super(application);
        homeInspectionDb=HomeInspectionDb.getDatabase(application);
        roomDao=homeInspectionDb.roomDao();
    }
    public void insert(RoomTable roomTable)
    {
        H.L("RoomViewModel. insert....11111");
        new InsertAsyncTask(roomDao).execute(roomTable);
    }


    private class OperationsAsyncTask extends AsyncTask<RoomTable, Void, Void> {

        RoomDao mAsyncTaskDao;

        OperationsAsyncTask(RoomDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(RoomTable... roomEntities) {
            return null;
        }
    }

    private class InsertAsyncTask extends OperationsAsyncTask
    {
        public InsertAsyncTask(RoomDao mRoomDao)
        {
            super(mRoomDao);
        }

        @Override
        protected Void doInBackground(RoomTable... roomEntities) {
            H.L("InsertAsyncTask.  mAsyncTaskDao.insertRoom....11111");
            mAsyncTaskDao.insertRoom(roomEntities[0]);
            return null;
        }
    }


}
*/
