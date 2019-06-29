package com.example.bcod2.homeinspection.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.bcod2.homeinspection.R;
import com.example.bcod2.homeinspection.utilities.H;
import com.example.bcod2.homeinspection.view.fragment.FragmentItemQuotation;

public class ItemActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    FragmentManager fm;
    private static String mNameRoom,mfrom;
    private  static int mIdRoom;
    TextView tv_AddItem,tv_RoomName;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=ItemActivity.this;
        setContentView(R.layout.activity_item);
        bindViews();
    }

    public static void open(Context context, int idRoom, String nameRoom, String from) {
        mNameRoom=nameRoom;
        mIdRoom=idRoom;
        mfrom=from;
        context.startActivity(new Intent(context, ItemActivity.class));
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Add your code here

    }

    private void bindViews() {

       /* tv_AddItem=findViewById(R.id.tv_AddItem);
        tv_RoomName=findViewById(R.id.tv_RoomName);
        tv_RoomName.setText(mNameRoom);
        tv_AddItem.setOnClickListener(this);*/
        fm = getSupportFragmentManager();

        H.L("RoomIdggggg"+mIdRoom);
        H.L("RoomNamegggg"+mNameRoom);

        FragmentItemQuotation fragmentItemQuotation = new FragmentItemQuotation();
        FragmentTransaction transaction = fm.beginTransaction();
        Bundle bundle=new Bundle();
        bundle.putInt("RoomId",mIdRoom);
        bundle.putString("RoomName",mNameRoom);
        fragmentItemQuotation.setArguments(bundle);
        transaction.replace(R.id.frameLayoutItem, fragmentItemQuotation);
        transaction.commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            /*case R.id.tv_AddItem:
                fm =getSupportFragmentManager();
                AddItemFragment addItemFragment = new AddItemFragment();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.frameLayoutItem, addItemFragment);
                transaction.commit();
                break;*/
        }

    }
}
