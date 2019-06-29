package com.example.bcod2.homeinspection.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.bcod2.homeinspection.roomdatabase.HomeInspectionDb;
import com.example.bcod2.homeinspection.roomdatabase.RoomDao;
import com.example.bcod2.homeinspection.roomdatabase.RoomTable;
import com.example.bcod2.homeinspection.utilities.H;

import java.util.List;

public class RoomtableRepository  {
    private RoomDao roomDao;
    private HomeInspectionDb homeInspectionDb;
    private LiveData<RoomTable> mRoomName;
    private LiveData<List<RoomTable>> mInnerRoomList;
    private LiveData<List<Integer>> mRoomIdsofProperty;



    public RoomtableRepository(Application application,int propertyId, String roomName)
    {
        homeInspectionDb=HomeInspectionDb.getDatabase(application);
        roomDao=homeInspectionDb.roomDao();
    }



    public LiveData<RoomTable> getRoom(int propertyId, String roomName )
    {
        mRoomName=roomDao.getRoom(propertyId,roomName);
        return  mRoomName;
//        return  roomDao.getRoomName(propertyId,roomName);

    }

    public LiveData<RoomTable> getRoomFromRoomId(int roomId)
    {
        mRoomName=roomDao.getRoomFromRoomId(roomId);
        return  mRoomName;

    }

    public void updateRoom(RoomTable roomTable)
    {

        new UpdateRoomAsynTask(roomDao).execute(roomTable);
    }

    public void deleteRoomForProprty(int propertyId)
    {
        new DeleteAsyncTask(roomDao).execute(propertyId);
    }

    public void deleteRoom(RoomTable roomTable)
    {
        new DeleteRoomTask(roomDao).execute(roomTable);
    }

    public LiveData<List<RoomTable>> getInnerRoomList(int propertyId, int innerRoomFlag )
    {
        mInnerRoomList=roomDao.getInnerRoomList(propertyId,innerRoomFlag);
        return  mInnerRoomList;

    }


    public LiveData<List<Integer>> getRoomIdListofProperty(int propertyId)
    {
        mRoomIdsofProperty=roomDao.getRoomIdListOfProperty(propertyId);
        return mRoomIdsofProperty;

    }
    public LiveData<List<Integer>> getInnerRoomIdListofProperty(int propertyId,int innerRoomFlag)
    {
        mRoomIdsofProperty=roomDao.getInnerRoomIdsListForProperty(propertyId, innerRoomFlag);
        return mRoomIdsofProperty;

    }

    public void insert(RoomTable roomTable)
    {

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
            try {
                H.L("InsertAsyncTask.  mAsyncTaskDao.insertRoom....11111");
                mAsyncTaskDao.insertRoom(roomEntities[0]);
            }catch (Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }
    }


    private class DeleteRoomTask extends OperationsAsyncTask
    {
        public DeleteRoomTask(RoomDao dao) {
            super(dao);
        }
        @Override
        protected Void doInBackground(RoomTable... roomEntities) {
            try {
                mAsyncTaskDao.deleteRoom(roomEntities[0]);
            }catch (Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }
    }
   private class UpdateRoomAsynTask extends OperationsAsyncTask
    {
        public UpdateRoomAsynTask(RoomDao dao) {
            super(dao);
        }
        @Override
        protected Void doInBackground(RoomTable... roomEntities) {
            try {
                mAsyncTaskDao.updateRoom(roomEntities[0]);
            }catch (Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Integer,Void,Void>
    {
        RoomDao mRoomDao;
        public DeleteAsyncTask(RoomDao roomDao) {
            this.mRoomDao=roomDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            mRoomDao.deleteRoomForProperty(integers[0]);
            return null;
        }
    }


    /*private class RoomNameAsyncTask extends AsyncTask<String, Void, String>
    {
        String name;
        public RoomNameAsyncTask() {
        }

        @Override
        protected String doInBackground(String...strings) {
            H.L("InsertAsyncTask.  mAsyncTaskDao.insertRoom....11111");
           name=getRoomName(mPropetyId,mRoomName);
            return name;
        }
    }*/





}
