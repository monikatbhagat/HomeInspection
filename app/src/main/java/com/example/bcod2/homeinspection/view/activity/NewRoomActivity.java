package com.example.bcod2.homeinspection.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.bcod2.homeinspection.R;
import com.example.bcod2.homeinspection.utilities.H;
import com.example.bcod2.homeinspection.view.fragment.NewRoomScreenFragment;

public class NewRoomActivity  extends AppCompatActivity implements View.OnClickListener  {
    Context context;
    FragmentManager fm;
    private static int mIdProperty;
    private static  String mRoomName,mFrom;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=NewRoomActivity.this;
        setContentView(R.layout.activity_new_rooms);
        bindViews();
    }

    public static void open(Context context,int propertyId, String roomName,String from) {
        mIdProperty=propertyId;
        H.L("iiiiihhhhh "+mIdProperty);
        mRoomName=roomName;
        mFrom=from;
        context.startActivity(new Intent(context, NewRoomActivity.class));
    }

    private void bindViews() {
       /* tv_AddItem=findViewById(R.id.tv_AddItem);
        tv_RoomName=findViewById(R.id.tv_RoomName);
        tv_RoomName.setText(mNameRoom);
        tv_AddItem.setOnClickListener(this);*/
        fm = getSupportFragmentManager();
        NewRoomScreenFragment newRoomScreenFragment = new NewRoomScreenFragment();
        FragmentTransaction transaction = fm.beginTransaction();
        Bundle bundle=new Bundle();
        bundle.putString("RoomName",mRoomName);
        bundle.putInt("PropertyId",mIdProperty);
        bundle.putString("From",mFrom);
        newRoomScreenFragment.setArguments(bundle);
        transaction.replace(R.id.frameLayoutRoom, newRoomScreenFragment);
        transaction.commit();

    }
    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }
        else {
            getFragmentManager().popBackStack();
        }
    }
    @Override
    public void onClick(View v) {

    }
}
