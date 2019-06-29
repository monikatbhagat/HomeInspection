package com.example.bcod2.homeinspection.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.bcod2.homeinspection.roomdatabase.HomeInspectionDb;
import com.example.bcod2.homeinspection.roomdatabase.Item;
import com.example.bcod2.homeinspection.roomdatabase.ItemDao;

import java.util.List;

public class ItemRepository  {

    private ItemDao itemDao;
    private HomeInspectionDb homeInspectionDb;
    private LiveData<List<Item>> mAllItems;
    private LiveData<Item> mItem;


    public ItemRepository(Application application) {
        homeInspectionDb=HomeInspectionDb.getDatabase(application);
        itemDao=homeInspectionDb.itemDao();
        mAllItems=itemDao.getAllItem();
    }

    public LiveData<Item> getItemFromItemId(int itemId)
    {
       mItem=itemDao.getItemFromItemId(itemId);
       return mItem;
    }

    public LiveData<List<Item>> getAllItems()
    {

        return  mAllItems;
    }
    public LiveData<List<Item>> getRoomItems(int roomId)
    {

        mAllItems=itemDao.getRoomItemList(roomId);
        return  mAllItems;
    }

    public void insert(Item item)
    {

        new InsertAsyncTask(itemDao).execute(item);
    }

    public void deleteItem(Item item)
    {
        new DeleteItemAsyncTask(itemDao).execute(item);
    }
     public void updateItem(Item item)
    {
        new UpdateItemAsyncTask(itemDao).execute(item);
    }


    public void deleteItemForRoom(int roomID)
    {
        new DeleteItemForRoom(itemDao).execute(roomID);
    }

    private class OperationsAsyncTask extends AsyncTask<Item, Void, Void> {

        ItemDao mAsyncTaskDao;

        OperationsAsyncTask(ItemDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            return null;
        }
    }

    private class InsertAsyncTask extends OperationsAsyncTask
    {
        public InsertAsyncTask(ItemDao mItemDao)
        {
            super(mItemDao);
        }

        @Override
        protected Void doInBackground(Item... items) {

            mAsyncTaskDao.insert(items[0]);
            return null;
        }

    }

    private class DeleteItemAsyncTask extends OperationsAsyncTask
    {
        public DeleteItemAsyncTask(ItemDao mItemDao)
        {
            super(mItemDao);
        }

        @Override
        protected Void doInBackground(Item... items) {

            mAsyncTaskDao.deleteItem(items[0]);
            return null;
        }
    }
    private class DeleteItemForRoom extends AsyncTask<Integer,Void,Void>
    {
        ItemDao mItemDao;
        public DeleteItemForRoom(ItemDao itemDao)
        {
            this.mItemDao=itemDao;
        }
        @Override
        protected Void doInBackground(Integer... integers) {
            mItemDao.deleteItemForRoom(integers[0]);
            return null;
        }
    }

    private class UpdateItemAsyncTask extends OperationsAsyncTask
    {
        public UpdateItemAsyncTask(ItemDao mItemDao)
        {
            super(mItemDao);
        }

        @Override
        protected Void doInBackground(Item... items) {

            mAsyncTaskDao.updateItem(items[0]);
            return null;
        }
    }

}
