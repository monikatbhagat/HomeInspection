package com.example.bcod2.homeinspection.view.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bcod2.homeinspection.R;
import com.example.bcod2.homeinspection.roomdatabase.RoomTable;
import com.example.bcod2.homeinspection.utilities.H;
import com.example.bcod2.homeinspection.viewmodel.RoomViewModel;

public class AddRoomFragment extends Fragment implements View.OnClickListener{
    Context context;
    TextView et_RoomName,et_RoomDes,tv_UpdateProperty;
    Button btn_cancel,btn_Save,btn_Update;
    private RoomViewModel mRoomViewModel;
    static int mPropertyId,mRoomId;
    static String mRoomName,mFrom;
    FragmentManager fm;
    int flag=1;
    int flagForUpdate=2;
    LiveData<RoomTable> mRoomTable;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_room, container, false);
        context = getActivity();
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bindViews();
    }
    public void bindViews()
    {
        mPropertyId= getArguments().getInt("PropertyId");
        mRoomName= getArguments().getString("RoomName");
        mFrom=getArguments().getString("From");
        H.L("gggghghghaddd "+mPropertyId);
        H.L("gggghghghaddd nnnn "+mRoomName);
        et_RoomName=getActivity().findViewById(R.id.et_RoomName);
        et_RoomDes=getActivity().findViewById(R.id.et_RoomDes);
        tv_UpdateProperty=getActivity().findViewById(R.id.tv_UpdateProperty);
        btn_Save=getActivity().findViewById(R.id.btn_Save);
        btn_cancel=getActivity().findViewById(R.id.btn_cancel);
        btn_Update=getActivity().findViewById(R.id.btn_Update);
        btn_cancel.setOnClickListener(this);
        btn_Update.setOnClickListener(this);
        btn_Save.setOnClickListener(this);
        mRoomViewModel = ViewModelProviders.of(this).get(RoomViewModel.class);

        if(mFrom.equals("fromAddRoom"))
        {
            tv_UpdateProperty.setText("Add Room");
            btn_Save.setVisibility(View.VISIBLE);
            btn_Update.setVisibility(View.GONE);
        } else if(mFrom.equals("fromUpdateRoom"))
        {
            tv_UpdateProperty.setText("Update Room");
            btn_Save.setVisibility(View.GONE);
            btn_Update.setVisibility(View.VISIBLE);
            mRoomId= getArguments().getInt("room_id");
            mRoomTable=mRoomViewModel.getRoomFromRoomId(mRoomId);
            mRoomTable.observe(this, new Observer<RoomTable>() {
                @Override
                public void onChanged(@Nullable RoomTable roomTable) {
                    et_RoomName.setText(roomTable.getRoomName());
                    et_RoomDes.setText(roomTable.getRoomDescription());
                }
            });
        }



    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_cancel:

                break;
            case R.id.btn_Save:
                validation();
                break;
                case R.id.btn_Update:
                validation();
                break;
        }
    }


    public void validation()
    {
        if(et_RoomName.getText().toString().length()<=0)
        {
            H.T(context, getResources().getString(R.string.enter_room_name));
        } else if(et_RoomDes.getText().toString().length()<=0)
        {
            H.T(context, getResources().getString(R.string.enter_room_desc));
        } else
        {
            try {

                if(mFrom.equals("fromAddRoom")) {
                    save();
                }else  if(mFrom.equals("fromUpdateRoom"))
                {
                    updateRoom();
                }
            }catch (Exception e)
            {

            }
        }
    }

    public void updateRoom()
    {
        String innerRoomName, innerRoomDescription;
        Bundle bundle=new Bundle();
        if (TextUtils.isEmpty(et_RoomName.getText()) && TextUtils.isEmpty(et_RoomDes.getText()) ) {

            Toast.makeText(context, "Enter both fields value", Toast.LENGTH_SHORT).show();
        }
        else {
            try {


                innerRoomName = et_RoomName.getText().toString();
                innerRoomDescription = et_RoomDes.getText().toString();
                bundle.putInt("PropertyId",mPropertyId);
                bundle.putInt("rooomID",mRoomId);
                bundle.putString("RoomName",mRoomName);
                bundle.putString("UpdatedInnerRoomName",innerRoomName);
                bundle.putString("UpdatedInnerRoomDescription",innerRoomDescription);
                bundle.putInt("InnerRoom_flag",flagForUpdate);
                NewRoomScreenFragment newRoomScreenFragment = new NewRoomScreenFragment();
                fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
                FragmentTransaction transaction = fm.beginTransaction();
                newRoomScreenFragment.setArguments(bundle);
                transaction.replace(R.id.frameLayoutRoom, newRoomScreenFragment);
//                 transaction.addToBackStack(null);
                transaction.commit();
            }catch (Exception e)
            {
              e.printStackTrace();
            }
        }
    }

    public void save()
    {

        String innerRoomName, innerRoomDescription;
        Bundle bundle=new Bundle();
        if (TextUtils.isEmpty(et_RoomName.getText()) && TextUtils.isEmpty(et_RoomDes.getText()) ) {

            Toast.makeText(context, "Enter both fields value", Toast.LENGTH_SHORT).show();
        }
         else {
             try {


                 innerRoomName = et_RoomName.getText().toString();
                 innerRoomDescription = et_RoomDes.getText().toString();
                 bundle.putInt("PropertyId",mPropertyId);
                 bundle.putString("RoomName",mRoomName);
                 bundle.putString("InnerRoomName",innerRoomName);
                 bundle.putString("InnerRoomDescription",innerRoomDescription);
                 bundle.putInt("InnerRoom_flag",flag);
                 NewRoomScreenFragment newRoomScreenFragment = new NewRoomScreenFragment();
                 fm = getActivity().getSupportFragmentManager();
                 fm.popBackStack();
                 FragmentTransaction transaction = fm.beginTransaction();
                 newRoomScreenFragment.setArguments(bundle);
                 transaction.replace(R.id.frameLayoutRoom, newRoomScreenFragment);
//                 transaction.addToBackStack(null);
                 transaction.commit();
                    /* bundle.putInt("PropertyId",mPropertyId);
                     bundle.putString("RoomName",mRoomName);
                     bundle.putString("InnerRoomName",innerRoomName);
                     bundle.putString("InnerRoomDescription",innerRoomDescription);
                     bundle.putInt("InnerRoom_flag",flag);

                     fm = getActivity().getSupportFragmentManager();
                     NewRoomScreenFragment newRoomScreenFragment = new NewRoomScreenFragment();
                     newRoomScreenFragment.setArguments(bundle);
                     FragmentTransaction transaction = fm.beginTransaction();
                     transaction.replace(R.id.frameLayoutRoom, newRoomScreenFragment);
                     transaction.addToBackStack(null);
                     transaction.commit();*/

             }catch (Exception e)
             {

             }
    }

    }
}
