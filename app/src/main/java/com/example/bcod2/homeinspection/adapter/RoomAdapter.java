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
import com.example.bcod2.homeinspection.roomdatabase.RoomTable;
import com.example.bcod2.homeinspection.utilities.H;
import com.example.bcod2.homeinspection.view.activity.InspectionActivity;
import com.example.bcod2.homeinspection.view.activity.ItemActivity;
import com.example.bcod2.homeinspection.view.fragment.AddRoomFragment;
import com.example.bcod2.homeinspection.viewmodel.RoomViewModel;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {
    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<RoomTable> mRoomTableList;
    private RoomViewModel mRoomViewModel;
    private int mPropertyId;
    private  String innerRoomName,mRoomName;
    private OnDeleteRoomItemsClickListener mOnDeleteRoomItemsClickListener;
    private OnDeleteRoomClickListener mOnDeleteRoomClickListener;
    private String mFrom;

    public RoomAdapter(Context mContext,int propertyId, String roomName,RoomViewModel roomViewModel,OnDeleteRoomClickListener onDeleteRoomClickListener,OnDeleteRoomItemsClickListener onDeleteRoomItemsClickListener,String from) {
        layoutInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        mRoomViewModel=roomViewModel;
        mPropertyId=propertyId;
        mRoomName=roomName;
        mFrom=from;
        this.mOnDeleteRoomClickListener=onDeleteRoomClickListener;
        this.mOnDeleteRoomItemsClickListener=onDeleteRoomItemsClickListener;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_room, parent, false);
        View view = layoutInflater.inflate(R.layout.list_room, parent, false);
        RoomViewHolder viewHolder = new RoomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {

        if (mRoomTableList != null) {
        final RoomTable mRoomTable=mRoomTableList.get(position);

            holder.setData(mRoomTable.getRoomName(), position);

            holder.tv_NameRoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    H.L("inneeerdfxccvxcvbcbx");
                    innerRoomName=mRoomTable.getRoomName();
                    if(mFrom.equals("fromQuotation"))
                    {
                        try{
                            ItemActivity.open(mContext,mRoomTable.getRoom_id(),innerRoomName,"InnerRoomAdapterFrom");
                        }catch (Exception e)
                        {

                        }
                    } else if(mFrom.equals("fromInspection"))
                    {
                        try{
                            InspectionActivity.open(mContext,mRoomTable.getRoom_id(),innerRoomName);
                        }catch (Exception e)
                        {

                        }

                    }
                }
            });

            holder.setListeners();
        } else {
            // Covers the case of data not being ready yet.
            holder.tv_NameRoom.setText(R.string.no_property);
        }
    }

    @Override
    public int getItemCount() {
        if (mRoomTableList != null)
            return mRoomTableList.size();
        else return 0;
    }

    public void setRooms(List<RoomTable> roomTables) {
        mRoomTableList= roomTables;
        notifyDataSetChanged();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_NameRoom;
        private int mPosition;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_NameRoom=itemView.findViewById(R.id.tv_NameRoom);
        }
        public void setData(String nameRoom, int position) {
            tv_NameRoom.setText(nameRoom);
            mPosition = position;
        }

        public void setListeners() {
            tv_NameRoom.setOnLongClickListener(new View.OnLongClickListener() {
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
                            bundle.putInt("room_id", mRoomTableList.get(mPosition).getRoom_id());
                            bundle.putInt("PropertyId", mPropertyId);
                            bundle.putString("From","fromUpdateRoom");
                            bundle.putString("RoomName",mRoomName);
                            AppCompatActivity activity = (AppCompatActivity)mContext;
                            Fragment addRoomFragment = new AddRoomFragment();
                            addRoomFragment.setArguments(bundle);
                            activity.getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.frameLayoutRoom, addRoomFragment)
                                    .addToBackStack(null)
                                    .commit();
                            dialog.dismiss();
                        }
                    });

                    btn_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (mOnDeleteRoomClickListener != null) {
                                // Wait for item delete..because when we delete a room, corresponding item enrties should be delete

                                H.L("room position ---in deleteeee button" + mRoomTableList.get(mPosition).getRoom_id());
                                mOnDeleteRoomClickListener.OnDeleteRoomClickListener(mRoomTableList.get(mPosition));
                                 mOnDeleteRoomItemsClickListener.OnDeleteRoomItemsClickListener(mRoomTableList.get(mPosition).getRoom_id());


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


    public interface OnDeleteRoomItemsClickListener {
        void OnDeleteRoomItemsClickListener(int roomId);
    }
    public interface OnDeleteRoomClickListener {
        void OnDeleteRoomClickListener(RoomTable roomTable);
    }
}
