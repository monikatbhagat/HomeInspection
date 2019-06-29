package com.example.bcod2.homeinspection.adapter;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bcod2.homeinspection.roomdatabase.RoomTable;
import com.example.bcod2.homeinspection.view.activity.InspectionActivity;
import com.example.bcod2.homeinspection.view.activity.ItemActivity;
import com.example.bcod2.homeinspection.view.activity.NewRoomActivity;
import com.example.bcod2.homeinspection.viewmodel.RoomViewModel;
import com.example.bcod2.homeinspection.R;
import com.example.bcod2.homeinspection.utilities.H;

public class QuotationAdapter extends RecyclerView.Adapter<QuotationAdapter.QuotationView>   {

    private Context mContext;
    private String[] mList;
    private int mPropertyId;
    private RoomViewModel mRoomViewModel;
    private LiveData<RoomTable> roomTableLiveData;
    private LiveData<Integer> roomId;
    private String mRoomName,mFrom;


    public QuotationAdapter(Context context, String[] list, int propertyId,RoomViewModel roomViewModel,String from) {
        mContext = context;
        mList = list;
        mPropertyId=propertyId;
        mRoomViewModel=roomViewModel;
        mFrom=from;
    }

    @NonNull
    @Override
    public QuotationView onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_quotation_home, parent, false);
        QuotationView quotationView = new QuotationView(view);
        return quotationView;
    }

    @Override
    public void onBindViewHolder(@NonNull final QuotationView holder, final int position) {
        holder.tv_NameQuotation.setText(mList[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(mFrom.equals("fromQuotation"))
              {
                  if (holder.getAdapterPosition() == 0) {
                      try
                      {
                          H.L("oooooo......"+mPropertyId);
                          RoomTable roomTable =new RoomTable(0,mPropertyId,0,"Living","");
                          mRoomViewModel.insert(roomTable);

                          mRoomViewModel.getRoom(mPropertyId, "Living").observe((LifecycleOwner) mContext, new Observer<RoomTable>() {
                              @Override
                              public void onChanged(@Nullable RoomTable roomTable) {
                                  try{
                                      mRoomName="Living Room";
                                      H.L("myIDRoom"+ roomTable.getRoom_id());
                                      H.L("myIDProperty"+ roomTable.getId());
                                      ItemActivity.open(mContext,roomTable.getRoom_id(),"Living Room","RoomFrom");
                                  /*  Intent intent = new Intent(mContext, ItemActivity.class);
                                    mContext.startActivity(intent);*/
//                                    Bundle mBundle = new Bundle();
//                                    mBundle.putString("RoomName", mRoomName);
//                                    intent.putExtras(mBundle);
//                                    Intent intent = new Intent(mContext, ItemQuotationActivity.class);

                                  }catch (Exception e)
                                  {
                                      e.printStackTrace();
                                  }
//                            roomTable.getRoomName();

                              }
                          });
                      }catch (Exception e)
                      {
                          e.printStackTrace();
                      }
                   /* String name=rt.getRoomName();

                    if(!name.equals("Living") && name.equals(""))
                    {
                        H.L("111111");
                        RoomTable roomTable =new RoomTable(0,mPropertyId,"Living");
                        mRoomViewModel.insert(roomTable);
                    }
                    H.L("222222");
                    Intent intent = new Intent(mContext, ItemQuotationActivity.class);
                    mContext.startActivity(intent);*/



                      /*  if(!roomName.equals(mRoomViewModel.getRoomName(mPropertyId, "Living")) && mRoomViewModel.getRoomName(mPropertyId, "Living")!=null)
                        {
                            RoomTable roomTable =new RoomTable(0,mPropertyId,"Living");
                            mRoomViewModel.insert(roomTable);
                        }
                    Intent intent = new Intent(mContext, ItemQuotationActivity.class);
                    mContext.startActivity(intent);*/

//           mRoomViewModel.insert(roomTable);
                  /*  RoomTable roomTable=new RoomTable();
                    roomTable.setId(0);
                    roomTable.setRoom_id(mPropertyId);
                    roomTable.setRoomName("Living Room");*/

//                   holder.set();
                  } else if (holder.getAdapterPosition() == 1) {

                      try {
                          NewRoomActivity.open(mContext,mPropertyId,"Bed Room",mFrom);

                      }catch (Exception e)
                      {
                      }

                  } else if (holder.getAdapterPosition() == 2) {
                      try
                      {
                          RoomTable roomTable =new RoomTable(0,mPropertyId,7,"Powder","");
                          mRoomViewModel.insert(roomTable);

                          mRoomViewModel.getRoom(mPropertyId, "Powder").observe((LifecycleOwner) mContext, new Observer<RoomTable>() {
                              @Override
                              public void onChanged(@Nullable RoomTable roomTable) {
                                  try{
                                      ItemActivity.open(mContext,roomTable.getRoom_id(),"Powder Room","RoomFrom");

                                  }catch (Exception e)
                                  {
                                      e.printStackTrace();
                                  }
                              }
                          });
                      }catch (Exception e)
                      {
                          e.printStackTrace();
                      }

                  } else if (holder.getAdapterPosition() == 3) {
                      try
                      {
                          RoomTable roomTable =new RoomTable(0,mPropertyId,8,"Kitchen","");
                          mRoomViewModel.insert(roomTable);

                          mRoomViewModel.getRoom(mPropertyId, "Kitchen").observe((LifecycleOwner) mContext, new Observer<RoomTable>() {
                              @Override
                              public void onChanged(@Nullable RoomTable roomTable) {
                                  try{
                                      ItemActivity.open(mContext,roomTable.getRoom_id(),"Kitchen","RoomFrom");
                                  }catch (Exception e)
                                  {
                                      e.printStackTrace();
                                  }
                              }
                          });
                      }catch (Exception e)
                      {
                          e.printStackTrace();
                      }
                  }else if (holder.getAdapterPosition() == 4) {
                      try
                      {
                          RoomTable roomTable =new RoomTable(0,mPropertyId,9,"Study","");
                          mRoomViewModel.insert(roomTable);

                          mRoomViewModel.getRoom(mPropertyId, "Study").observe((LifecycleOwner) mContext, new Observer<RoomTable>() {
                              @Override
                              public void onChanged(@Nullable RoomTable roomTable) {
                                  try{
                                      ItemActivity.open(mContext,roomTable.getRoom_id(),"Study Room","StudyFrom");

                                  }catch (Exception e)
                                  {
                                      e.printStackTrace();
                                  }
                              }
                          });
                      }catch (Exception e)
                      {
                          e.printStackTrace();
                      }

                  }else if (holder.getAdapterPosition() == 5) {
                      try {
                          NewRoomActivity.open(mContext,mPropertyId,"Store Room",mFrom);

                      }catch (Exception e)
                      {
                      }
                  }else if (holder.getAdapterPosition() == 6) {

                      try {
                          NewRoomActivity.open(mContext,mPropertyId,"Other Room",mFrom);

                      }catch (Exception e)
                      {
                      }
                  }
              }else if(mFrom.equals("fromInspection"))
              {
                  if (holder.getAdapterPosition() == 0){
                      try
                      {
                          RoomTable roomTable =new RoomTable(0,mPropertyId,0,"Living","");
                          mRoomViewModel.insert(roomTable);

                          mRoomViewModel.getRoom(mPropertyId, "Living").observe((LifecycleOwner) mContext, new Observer<RoomTable>() {
                              @Override
                              public void onChanged(@Nullable RoomTable roomTable) {
                                  try {
                                      InspectionActivity.open(mContext,roomTable.getRoom_id(),"Living Room");
                                  } catch (Exception e) {
                                  }
                              }
                          });
                      } catch (Exception e)
                      {
                          e.printStackTrace();
                      }
                  } else if (holder.getAdapterPosition() == 1) {
                      try {
                          NewRoomActivity.open(mContext,mPropertyId,"Bed Room",mFrom);

                      }catch (Exception e)
                      {
                      }
                  } else if (holder.getAdapterPosition() == 2) {
                      try
                      {
                          RoomTable roomTable =new RoomTable(0,mPropertyId,7,"Powder","");
                          mRoomViewModel.insert(roomTable);

                          mRoomViewModel.getRoom(mPropertyId, "Powder").observe((LifecycleOwner) mContext, new Observer<RoomTable>() {
                              @Override
                              public void onChanged(@Nullable RoomTable roomTable) {
                                  try{
                                      InspectionActivity.open(mContext,roomTable.getRoom_id(),"Powder Room");
                                  }catch (Exception e)
                                  {
                                      e.printStackTrace();
                                  }
                              }
                          });
                      }catch (Exception e)
                      {
                          e.printStackTrace();
                      }
                  } else if (holder.getAdapterPosition() == 3) {
                      try
                      {
                          RoomTable roomTable =new RoomTable(0,mPropertyId,8,"Kitchen","");
                          mRoomViewModel.insert(roomTable);

                          mRoomViewModel.getRoom(mPropertyId, "Kitchen").observe((LifecycleOwner) mContext, new Observer<RoomTable>() {
                              @Override
                              public void onChanged(@Nullable RoomTable roomTable) {
                                  try{
                                      InspectionActivity.open(mContext,roomTable.getRoom_id(),"Kitchen");
                                  }catch (Exception e)
                                  {
                                      e.printStackTrace();
                                  }
                              }
                          });
                      }catch (Exception e)
                      {
                          e.printStackTrace();
                      }
                  }else if (holder.getAdapterPosition() == 4) {
                      try
                      {
                          RoomTable roomTable =new RoomTable(0,mPropertyId,9,"Study","");
                          mRoomViewModel.insert(roomTable);

                          mRoomViewModel.getRoom(mPropertyId, "Study").observe((LifecycleOwner) mContext, new Observer<RoomTable>() {
                              @Override
                              public void onChanged(@Nullable RoomTable roomTable) {
                                  try{
                                      InspectionActivity.open(mContext,roomTable.getRoom_id(),"Study Room");
                                  }catch (Exception e)
                                  {
                                      e.printStackTrace();
                                  }
                              }
                          });
                      }catch (Exception e)
                      {
                          e.printStackTrace();
                      }
                  }else if (holder.getAdapterPosition() == 5) {
                      try {
                          NewRoomActivity.open(mContext,mPropertyId,"Store Room",mFrom);

                      }catch (Exception e)
                      {
                      }
                  }else if (holder.getAdapterPosition() == 6) {
                      try {
                          NewRoomActivity.open(mContext,mPropertyId,"Other Room",mFrom);

                      }catch (Exception e)
                      {
                      }
                  }
              }
            }
        });

    }

    @Override
    public int getItemCount() {

        return mList.length;
    }

    public  class QuotationView extends RecyclerView.ViewHolder
    {
        TextView tv_NameQuotation;


        public QuotationView(@NonNull final View itemView) {
            super(itemView);
            tv_NameQuotation=itemView.findViewById(R.id.tv_NameQuotation);

        }

       public void set()
       {
//           RoomTable roomEntity=new RoomTable(0,mPropertyId,"Living");
//           mRoomViewModel.insert(roomEntity);

//           RoomTable roomEntity=new RoomTable();
//           roomEntity.setId(0);
//           roomEntity.setRoom_id(mPropertyId);
//           roomEntity.setRoomName("Living Room");
//           mRoomViewModel.insert(roomEntity);

       }
    }

}
