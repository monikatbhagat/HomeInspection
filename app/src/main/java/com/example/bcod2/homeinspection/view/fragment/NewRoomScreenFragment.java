package com.example.bcod2.homeinspection.view.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bcod2.homeinspection.R;
import com.example.bcod2.homeinspection.adapter.RoomAdapter;
import com.example.bcod2.homeinspection.roomdatabase.RoomTable;
import com.example.bcod2.homeinspection.utilities.H;
import com.example.bcod2.homeinspection.viewmodel.ItemViewModel;
import com.example.bcod2.homeinspection.viewmodel.RoomViewModel;

import java.util.List;

public class NewRoomScreenFragment extends Fragment implements View.OnClickListener,RoomAdapter.OnDeleteRoomItemsClickListener,RoomAdapter.OnDeleteRoomClickListener{
    Context context;
    FragmentManager fm;
    TextView tv_AddRoom,tv_Room;
    static int propertyId;
    static String roomName;
    private RoomAdapter roomAdapter;
    Bundle bundle;
    private boolean isViewCreated = false;
    private boolean visible = false;
    private RoomViewModel mRoomViewModel;
    private ItemViewModel mItemViewModel;
    int flagof_Room,idRoom;
    String innerRoomName,innerRoomDes,from;

    public NewRoomScreenFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_roomscreen, container, false);
        context = getActivity();
        isViewCreated = true;
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (visible) {
            bindViews();
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bindViews();
    }

    public void bindViews() {

        roomName = getArguments().getString("RoomName");
        from = getArguments().getString("From");
        propertyId = getArguments().getInt("PropertyId");
        flagof_Room = getArguments().getInt("InnerRoom_flag");
        mRoomViewModel = ViewModelProviders.of(this).get(RoomViewModel.class);
        mItemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        /*if(flagof_Room==1)
        {
            innerRoomName=getArguments().getString("InnerRoomName");
            innerRoomDes=getArguments().getString("InnerRoomDescription");


                RoomTable roomTable =new RoomTable(0,propertyId,1,innerRoomName,innerRoomDes);
                mRoomViewModel.insert(roomTable);

                Toast.makeText(
                        getContext(),
                        "New Bed Room Saved",
                        Toast.LENGTH_LONG).show();

        }*/

        if (flagof_Room == 1) {
            innerRoomName = getArguments().getString("InnerRoomName");
            innerRoomDes = getArguments().getString("InnerRoomDescription");

            if (roomName.equals("Bed Room")) {
                RoomTable roomTable = new RoomTable(0, propertyId, 1, innerRoomName, innerRoomDes);
                mRoomViewModel.insert(roomTable);

                Toast.makeText(
                        getContext(),
                        "New"+roomName+"Room Saved",
                        Toast.LENGTH_LONG).show();

            } else if (roomName.equals("Store Room")) {
                RoomTable roomTable = new RoomTable(0, propertyId, 2, innerRoomName, innerRoomDes);
                mRoomViewModel.insert(roomTable);
                Toast.makeText(
                        getContext(),
                        "New"+roomName+"Room Saved",
                        Toast.LENGTH_LONG).show();

            } else if (roomName.equals("Other Room")) {
                RoomTable roomTable = new RoomTable(0, propertyId, 3, innerRoomName, innerRoomDes);
                mRoomViewModel.insert(roomTable);
                Toast.makeText(
                        getContext(),
                        "New"+roomName+"Room Saved",
                        Toast.LENGTH_LONG).show();
            }
        }
        else if(flagof_Room == 2)
        {
            innerRoomName = getArguments().getString("UpdatedInnerRoomName");
            innerRoomDes = getArguments().getString("UpdatedInnerRoomDescription");
            idRoom = getArguments().getInt("rooomID");

            H.L("roomUUUUUUUu");

            if (roomName.equals("Bed Room")) {
                H.L("InnnnnnnnBBBBBeedd+222232");
                RoomTable roomTable = new RoomTable(idRoom, propertyId, 1, innerRoomName, innerRoomDes);
                mRoomViewModel.updateRoom(roomTable);

                Toast.makeText(
                        getContext(),
                        roomName+"Room Updated",
                        Toast.LENGTH_LONG).show();

            } else if (roomName.equals("Store Room")) {
                RoomTable roomTable = new RoomTable(idRoom, propertyId, 2, innerRoomName, innerRoomDes);
                mRoomViewModel.updateRoom(roomTable);
                Toast.makeText(
                        getContext(),
                        roomName+"Room Updated",
                        Toast.LENGTH_LONG).show();

            } else if (roomName.equals("Other Room")) {
                RoomTable roomTable = new RoomTable(idRoom, propertyId, 3, innerRoomName, innerRoomDes);
                mRoomViewModel.updateRoom(roomTable);
                Toast.makeText(
                        getContext(), roomName+"Room Updated",
                        Toast.LENGTH_LONG).show();
            }

        }

        tv_AddRoom = getActivity().findViewById(R.id.tv_AddRoom);
        tv_AddRoom.setOnClickListener(this);
        tv_Room = getActivity().findViewById(R.id.tv_Room);
        tv_Room.setText(roomName);
//        mRoomViewModel = ViewModelProviders.of(this).get(RoomViewModel.class);
        RecyclerView recyclerView = getActivity().findViewById(R.id.rv_newRoom);
        roomAdapter = new RoomAdapter(context, propertyId,roomName, mRoomViewModel,this,this,from);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerView.setAdapter(roomAdapter);

/*
            mRoomViewModel.getInnerRoomList(propertyId, 1).observe(this, new Observer<List<RoomTable>>() {
                @Override
                public void onChanged(@Nullable List<RoomTable> roomTables) {
                    roomAdapter.setRooms(roomTables);

                }
            });*/


            if (roomName.equals("Bed Room")) {

                mRoomViewModel.getInnerRoomList(propertyId, 1).observe(this, new Observer<List<RoomTable>>() {
                    @Override
                    public void onChanged(@Nullable List<RoomTable> roomTables) {
                        roomAdapter.setRooms(roomTables);
                    }
                });
            } else if (roomName.equals("Store Room")) {
                mRoomViewModel.getInnerRoomList(propertyId, 2).observe(this, new Observer<List<RoomTable>>() {
                    @Override
                    public void onChanged(@Nullable List<RoomTable> roomTables) {
                        roomAdapter.setRooms(roomTables);

                    }
                });
            } else if (roomName.equals("Other Room")) {
                mRoomViewModel.getInnerRoomList(propertyId, 3).observe(this, new Observer<List<RoomTable>>() {
                    @Override
                    public void onChanged(@Nullable List<RoomTable> roomTables) {
                        roomAdapter.setRooms(roomTables);

                    }
                });
            }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_AddRoom:
                fm = getActivity().getSupportFragmentManager();
                bundle=new Bundle();
                bundle.putInt("PropertyId",propertyId);
                bundle.putString("RoomName",roomName);
                bundle.putString("From","fromAddRoom");
                AddRoomFragment addRoomFragment = new AddRoomFragment();
                addRoomFragment.setArguments(bundle);
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.frameLayoutRoom, addRoomFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
        }
    }


    @Override
    public void OnDeleteRoomClickListener(RoomTable roomTable) {
        mRoomViewModel.deleteRoom(roomTable);
    }

    @Override
    public void OnDeleteRoomItemsClickListener(int roomId) {
        mItemViewModel.deleteRoomItem(roomId);
    }
}
