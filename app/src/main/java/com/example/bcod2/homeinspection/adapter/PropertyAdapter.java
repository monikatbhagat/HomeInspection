package com.example.bcod2.homeinspection.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.example.bcod2.homeinspection.R;
import com.example.bcod2.homeinspection.view.activity.HomeActivity;
import com.example.bcod2.homeinspection.view.activity.InsideHomeActivity;
import com.example.bcod2.homeinspection.roomdatabase.Property;
import com.example.bcod2.homeinspection.utilities.H;
import com.example.bcod2.homeinspection.view.activity.PropertyActivity;
import com.example.bcod2.homeinspection.viewmodel.ItemViewModel;
import com.example.bcod2.homeinspection.viewmodel.RoomViewModel;

import java.util.List;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder> {

    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<Property> mProperty;
    private OnDeleteClickListener onDeleteClickListener;
    private RoomViewModel mRoomViewModel;
    private ItemViewModel mItemViewModel;
    private OnDeletePropertyRoomClickListener mOnDeletePropertyRoomClickListener;
    private OnDeleteItemsofRooms mOnDeleteItemsofRooms;


    public PropertyAdapter(Context context, OnDeleteClickListener listener,OnDeletePropertyRoomClickListener onDeletePropertyRoomClickListener,OnDeleteItemsofRooms onDeleteItemsofRooms,RoomViewModel roomViewModel) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
        this.onDeleteClickListener = listener;
        this.mOnDeletePropertyRoomClickListener=onDeletePropertyRoomClickListener;
        this.mOnDeleteItemsofRooms=onDeleteItemsofRooms;
        this.mRoomViewModel=roomViewModel;
//        this.mItemViewModel=itemViewModel;
    }

    @NonNull
    @Override
    public PropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.list_property, parent, false);
//        mItemViewModel=ViewModelProviders.of((FragmentActivity) mContext).get(ItemViewModel.class);
        PropertyViewHolder viewHolder = new PropertyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PropertyViewHolder holder, final int position) {
        if (mProperty != null) {
            final Property property = mProperty.get(position);
       H.L("Testingggg");
            holder.setData(property.getPropertyName(), position);
            holder.tvNameProperty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    InsideHomeActivity.open(mContext, (String) holder.tvNameProperty.getText());
                    InsideHomeActivity.open(mContext, property.getPropertyName(),property.getPropertyAddress(),property.getId());
                }
            });
            holder.setListeners();
        } else {
            // Covers the case of data not being ready yet.
            holder.tvNameProperty.setText(R.string.no_property);
        }

    }

    @Override
    public int getItemCount() {
        if (mProperty != null)
            return mProperty.size();
        else return 0;
    }

    public void setProerty(List<Property> properties) {
        mProperty = properties;
        notifyDataSetChanged();
    }


    public class PropertyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNameProperty;
        private int mPosition;

        public PropertyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameProperty = itemView.findViewById(R.id.tv_NameProperty);
        }

        public void setData(String nameProperty, int position) {
            tvNameProperty.setText(nameProperty);
            mPosition = position;
        }

        public void setListeners() {
            tvNameProperty.setOnLongClickListener(new View.OnLongClickListener() {
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
                            H.L("property iddd..Edit" + mProperty.get(mPosition).getId());

                            Intent intent = new Intent(mContext, PropertyActivity.class);
                            intent.putExtra("property_id", mProperty.get(mPosition).getId());
                            intent.putExtra("From","fromUpdate");
                            ((Activity) mContext).startActivityForResult(intent, HomeActivity.UPDATE_NOTE_ACTIVITY_REQUEST_CODE);
                            dialog.dismiss();

                            /*Intent intent = new Intent(mContext, EditPropertyActivity.class);
                            intent.putExtra("property_id", mProperty.get(mPosition).getId());
                            ((Activity) mContext).startActivityForResult(intent, HomeActivity.UPDATE_NOTE_ACTIVITY_REQUEST_CODE);
                            dialog.dismiss();*/
                        }
                    });

                    btn_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (onDeleteClickListener != null) {
                                H.L("property position ---in deleteeee button" + mProperty.get(mPosition).getId());
                                onDeleteClickListener.OnDeleteClickListener(mProperty.get(mPosition));
                                mOnDeletePropertyRoomClickListener.OnDeletePropertyRoomClickListener(mProperty.get(mPosition).getId());
                                mRoomViewModel.getRoomIdListofProperty(mProperty.get(mPosition).getId()).observe((LifecycleOwner) mContext, new Observer<List<Integer>>() {
                                    @Override
                                    public void onChanged(@Nullable List<Integer> integers) {

                                        for(int i=0;i<=integers.size();i++)
                                        {
                                            H.L("dddfghghghghghjhj"+i);
                                            mOnDeleteItemsofRooms.OnDeleteItemsofRooms(i);
                                        }


                                    }
                                });


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

    public interface OnDeleteClickListener {
        void OnDeleteClickListener(Property property);
    }
    public interface OnDeletePropertyRoomClickListener {
        void OnDeletePropertyRoomClickListener(int propertyId);
    }

    public interface OnDeleteItemsofRooms{
        void OnDeleteItemsofRooms(int roomId);
    }


}
