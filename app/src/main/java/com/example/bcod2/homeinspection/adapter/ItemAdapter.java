package com.example.bcod2.homeinspection.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.bcod2.homeinspection.R;
import com.example.bcod2.homeinspection.roomdatabase.Item;
import com.example.bcod2.homeinspection.utilities.H;
import com.example.bcod2.homeinspection.view.fragment.AddItemFragment;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{

    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<Item> mItem;
    private int mRoomId;
    private String mRoomName;
//    private List<List<Item>>  itemList=new ArrayList<List<Item>>();
    Item item;
    private OnDeleteClickListenerofItem mOnDeleteClickListenerofItem;


  /*  public ItemAdapter(Context context, OnDeleteClickListenerofItem listener) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
        this.onDeleteClickListenerofItem = listener;
    }*/
  public ItemAdapter(Context context,int roomId, String roomName,OnDeleteClickListenerofItem onDeleteClickListenerofItem) {
      layoutInflater = LayoutInflater.from(context);
      this.mRoomId=roomId;
      this.mRoomName=roomName;
      this.mOnDeleteClickListenerofItem=onDeleteClickListenerofItem;
      mContext = context;
  }

    public ItemAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        if (mItem != null) {
            item = mItem.get(position);

//            holder.tv_ItemName.setText(item.getItemName());
//            holder.tv_ItemCost.setText(String.valueOf(item.getItemCost()));
//            holder.tv_ItemNote.setText(item.getItemNote());


            holder.setData(item.getItemName(),item.getItemCost(),item.getItemNote(), position);
            holder.setListeners();
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    Toast.makeText(mContext, "itemmmmm", Toast.LENGTH_SHORT).show();
                }
            });
            holder.setListeners();
        } else {
            // Covers the case of data not being ready yet.
//            Toast.makeText(mContext, "No item...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        if (mItem != null)
            return mItem.size();
        else
            return 0;
    }
    public void setItem(List<Item> item) {
        mItem= item;
        notifyDataSetChanged();
    }

    public void setItemNew(List<List<Item>> item) {

      if(item!=null)
      {
          for (int i = 0; i <item.size(); i++)
          {

              mItem= item.get(i);
              notifyDataSetChanged();
          }
      }
      else
      {
          H.L("empty list");
      }

//        itemList= item;

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_ItemName, tv_ItemCost, tv_ItemNote;
        private int mPosition;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ItemName = itemView.findViewById(R.id.tv_ItemName);
            tv_ItemCost = itemView.findViewById(R.id.tv_ItemCostList);
            tv_ItemNote = itemView.findViewById(R.id.tv_ItemNote);
        }

        public void setData(String itemName,int itemCost,String itemNote,int position) {
            tv_ItemName.setText(itemName);
            tv_ItemCost.setText(String.valueOf(itemCost));
            tv_ItemNote.setText(itemNote);
            mPosition = position;
        }

        public void setListeners() {
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final Dialog dialog = new Dialog(mContext);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.edit_delete_dialogue);
                    dialog.setCancelable(true);

                    Button btn_edit = (Button) dialog.findViewById(R.id.btn_edit);
                    final Button btn_delete = (Button) dialog.findViewById(R.id.btn_delete);

                    btn_edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Bundle bundle=new Bundle();
                            bundle.putInt("item_id",mItem.get(mPosition).getItemId());
                            bundle.putInt("RoomId",mRoomId);
                            bundle.putString("RoomName",mRoomName);
                            bundle.putString("From","fromUpdateItem");
                            AppCompatActivity activity=(AppCompatActivity)mContext;
                            Fragment addItemFragment=new AddItemFragment();
                            addItemFragment.setArguments(bundle);
                            activity.getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.frameLayoutItem,addItemFragment)
                                    .addToBackStack(null)
                                    .commit();
                            dialog.dismiss();
                        }
                    });

                    btn_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (mOnDeleteClickListenerofItem != null) {
                                mOnDeleteClickListenerofItem.OnDeleteClickListenerofItem(mItem.get(mPosition));
                                H.L("Item Deleted...");
                            }
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    return true;
                }

            });
        }
    }

    public interface OnDeleteClickListenerofItem {
        void OnDeleteClickListenerofItem(Item item);
    }
}

