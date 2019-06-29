package com.example.bcod2.homeinspection.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.bcod2.homeinspection.repository.ItemRepository;
import com.example.bcod2.homeinspection.roomdatabase.Item;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {
    ItemRepository itemRepository;
    private LiveData<List<Item>> mAllItems;
    private LiveData<Item> mItem;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        itemRepository=new ItemRepository(application);
        mAllItems=itemRepository.getAllItems();
    }

    public void insert(Item item)
    {

        itemRepository.insert(item);
    }

    public void deleteItem(Item item)
    {
        itemRepository.deleteItem(item);
    }

    public void updateItem(Item item)
    {
        itemRepository.updateItem(item);
    }

    public void deleteRoomItem(int roomId)
    {
        itemRepository.deleteItemForRoom(roomId);
    }
    public LiveData<List<Item>> getAllItems()
    {

        return  mAllItems;
    }

    public LiveData<List<Item>> getRoomItems(int roomId)
    {
        mAllItems=itemRepository.getRoomItems(roomId);
        return  mAllItems;
    }

    public LiveData<Item> getItemFromItemId(int itemId)
    {
        mItem=itemRepository.getItemFromItemId(itemId);
        return mItem;
    }
}
