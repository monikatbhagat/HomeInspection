package com.example.bcod2.homeinspection.view.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.TextView;

import com.example.bcod2.homeinspection.R;
import com.example.bcod2.homeinspection.roomdatabase.Item;
import com.example.bcod2.homeinspection.utilities.H;
import com.example.bcod2.homeinspection.viewmodel.ItemViewModel;

public class AddItemFragment extends Fragment implements View.OnClickListener {

    Context context;
    private boolean isViewCreated = false;
    Button btn_cancel, btn_Save, btn_Update;
    EditText et_ItemName,et_ItemCost,et_ItemNotes;
    TextView tv_UpdateProperty;
    int flag=1,itemId, flag_UpdateItem=2;
    String mFrom;
    LiveData<Item> mItem;
    ItemViewModel itemViewModel;
    int roomID;
    String roomName;
  /*  public static final String ITEM_NAME = "new_item_name";
    public static final String ITEM_COST = "new_item_cost";
    public static final String ITEM_NOTE = "new_item_note";*/

    FragmentManager fm;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);
        context = getActivity();
        isViewCreated = true;
        return view;
    }
    public static void open(Context context) {
        context.startActivity(new Intent(context, AddItemFragment.class));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        bindViews();
    }
    public void bindViews()
    {
        et_ItemNotes=getActivity().findViewById(R.id.et_ItemNotes);
        et_ItemName=getActivity().findViewById(R.id.et_ItemName);
        et_ItemCost=getActivity().findViewById(R.id.et_ItemCost);
        tv_UpdateProperty=getActivity().findViewById(R.id.tv_UpdateProperty);
        btn_Save=getActivity().findViewById(R.id.btn_Save);
        btn_cancel=getActivity().findViewById(R.id.btn_cancel);
        btn_Update=getActivity().findViewById(R.id.btn_Update);
        btn_cancel.setOnClickListener(this);
        btn_Save.setOnClickListener(this);
        btn_Update.setOnClickListener(this);
        itemViewModel=ViewModelProviders.of(this).get(ItemViewModel.class);
        mFrom=getArguments().getString("From");
        roomID=getArguments().getInt("RoomId");
        roomName=getArguments().getString("RoomName");

        if(mFrom.equals("fromAddItem"))
        {
          tv_UpdateProperty.setText("Add Item");
          btn_Save.setVisibility(View.VISIBLE);
          btn_Update.setVisibility(View.GONE);
        }else if(mFrom.equals("fromUpdateItem"))
        {
            tv_UpdateProperty.setText("Update Item");
            btn_Save.setVisibility(View.GONE);
            btn_Update.setVisibility(View.VISIBLE);
            itemId=getArguments().getInt("item_id");
            mItem=itemViewModel.getItemFromItemId(itemId);
            mItem.observe(this, new Observer<Item>() {
                @Override
                public void onChanged(@Nullable Item item) {
                    et_ItemName.setText(item.getItemName());
                    et_ItemCost.setText(String.valueOf(item.getItemCost()));
                    et_ItemNotes.setText(item.getItemNote());
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
        if(et_ItemName.getText().toString().length()<=0)
        {
            H.T(context, getResources().getString(R.string.enter_item_name));
        } else if(et_ItemCost.getText().toString().length()<=0)
        {
            H.T(context, getResources().getString(R.string.enter_item_cost));
        }
        else if(!isNumber(et_ItemCost.getText().toString()))
        {
            H.T(context, getResources().getString(R.string.enter_numeric_value));
        }
        else
        {
            try {
                if(mFrom.equals("fromAddItem")) {
                    save();
                }else if(mFrom.equals("fromUpdateItem"))
                {
                    updateItem();
                }
            }catch (Exception e)
            {

            }
        }
    }

    public void updateItem()
    {
        Bundle bundle=new Bundle();
        if (TextUtils.isEmpty(et_ItemName.getText()) && TextUtils.isEmpty(et_ItemCost.getText()) ) {

        } else {
            try {
                String name = et_ItemName.getText().toString();
                int cost = Integer.valueOf(et_ItemCost.getText().toString());
                String note = et_ItemNotes.getText().toString();
//                int roomID = getArguments().getInt("RoomId");
//                String roomName = getArguments().getString("RoomName");
                bundle.putInt("ITEM_Id", itemId);
                bundle.putString("UPDATED_ITEM_NAME", name);
                bundle.putInt("UPDATED_ITEM_COST", cost);
                bundle.putString("UPDATED_ITEM_NOTE", note);
                bundle.putInt("RoomId", roomID);
                bundle.putString("RoomName", roomName);
                bundle.putInt("FLAG", flag_UpdateItem);
                FragmentItemQuotation fragmentItemQuotation = new FragmentItemQuotation();
// fragmentItemQuotation.remove(fragmentItemQuotation);
                fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
                FragmentTransaction transaction = fm.beginTransaction();
                fragmentItemQuotation.setArguments(bundle);
                transaction.replace(R.id.frameLayoutItem, fragmentItemQuotation);
//transaction.addToBackStack(null);
                transaction.commit();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }

    public void save()
    {
        Bundle bundle=new Bundle();
        if (TextUtils.isEmpty(et_ItemName.getText()) && TextUtils.isEmpty(et_ItemCost.getText()) ) {

        } else {
            try {
                String name = et_ItemName.getText().toString();
                int cost = Integer.valueOf(et_ItemCost.getText().toString());
                String note = et_ItemNotes.getText().toString();

                /*resultIntent.putExtra(ITEM_NAME, name);
                resultIntent.putExtra(ITEM_COST, cost);
                resultIntent.putExtra(ITEM_NOTE, note);*/

//                if(et_ItemName.getText().toString().length()<=0)
//                {
//                    H.T(context, getResources().getString(R.string.enter_item_name));
//                } else if(et_ItemCost.getText().toString().length()<=0)
//                {
//                    H.T(context, getResources().getString(R.string.enter_item_name));
//                }
//                else if(!isNumber(et_ItemCost.getText().toString()))
//                {
//                    H.T(context, getResources().getString(R.string.enter_numeric_value));
//                }
//                else
//                {


                   /* bundle.putString("ITEM_NAME",name);
                    bundle.putInt("ITEM_COST",cost);
                    bundle.putString("ITEM_NOTE",note);
                    bundle.putInt("RoomId",roomID);
                    bundle.putString("RoomName",roomName);
                    bundle.putInt("FLAG",flag);
                    fm = getActivity().getSupportFragmentManager();
                    FragmentItemQuotation fragmentItemQuotation = new FragmentItemQuotation();
                    FragmentTransaction transaction = fm.beginTransaction();
                    fragmentItemQuotation.setArguments(bundle);
                    transaction.replace(R.id.frameLayoutItem, fragmentItemQuotation);
                    transaction.commit();*/

                bundle.putString("ITEM_NAME", name);
                bundle.putInt("ITEM_COST", cost);
                bundle.putString("ITEM_NOTE", note);
                bundle.putInt("RoomId", roomID);
                bundle.putString("RoomName", roomName);
                bundle.putInt("FLAG", flag);
                FragmentItemQuotation fragmentItemQuotation = new FragmentItemQuotation();
// fragmentItemQuotation.remove(fragmentItemQuotation);
                fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
                FragmentTransaction transaction = fm.beginTransaction();
                fragmentItemQuotation.setArguments(bundle);
                transaction.replace(R.id.frameLayoutItem, fragmentItemQuotation);
//transaction.addToBackStack(null);
                transaction.commit();




//                }

//                setResult(RESULT_OK, resultIntent);
            }catch (Exception e)
            {
                e.printStackTrace();
            }



        }
    }

    static boolean isNumber(String s)
    {
        //H.L("5");
        for (int i = 0; i < s.length(); i++)
            if (Character.isDigit(s.charAt(i)) == false)
                return false;

        return true;
    }
}
