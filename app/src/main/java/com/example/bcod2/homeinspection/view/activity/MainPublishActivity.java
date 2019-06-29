package com.example.bcod2.homeinspection.view.activity;

import android.Manifest;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bcod2.homeinspection.R;
import com.example.bcod2.homeinspection.adapter.ItemAdapter;
import com.example.bcod2.homeinspection.roomdatabase.Item;
import com.example.bcod2.homeinspection.roomdatabase.RoomTable;
import com.example.bcod2.homeinspection.utilities.H;
import com.example.bcod2.homeinspection.viewmodel.ItemViewModel;
import com.example.bcod2.homeinspection.viewmodel.RoomViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.bcod2.homeinspection.view.fragment.FragmentItemQuotation.STORAGE_CODE;

public class MainPublishActivity  extends AppCompatActivity implements View.OnClickListener {
    Context context;
    Button btn_Publish;
    ConstraintLayout cl_MainPublishActivity,cl_Inner_MainPublishActivity;
    private ItemAdapter itemAdapter_LivingRoom,itemAdapter_BedRoom,itemAdapter_PowderRoom,itemAdapter_Kitchen,
                    itemAdapter_StudyRoom;
    ItemViewModel itemViewModel;
    private RoomViewModel mRoomViewModel;
    private static String mNameProperty;
    private static int mIdProperty;
    TextView tv_HomeName;
    private Bitmap bitmap;
    RecyclerView.LayoutManager layoutManager,layoutManager_forBedRoom, layoutManager_forPowder,layoutManager_forKitchen,layoutManager_forStudy;
    RecyclerView rv_LivingRoom, rv_BedRoom,rv_PowderRoom,rv_Kitchen,rv_StudyRoom,rv_StoreRoom,rv_OtherRoom;
     List<List<Item>> itemList;
     public static List<List<Item>> itemList2;

    public static void open(Context context, String nameProperty, int idProperty)
    {
        mNameProperty=nameProperty;
        mIdProperty=idProperty;
        context.startActivity(new Intent(context, MainPublishActivity.class));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_publish);
        context = MainPublishActivity.this;
        bindViews();
    }

    public void bindViews()
    {
        itemList= new ArrayList<>();
        itemList2= new ArrayList<>();
        btn_Publish=findViewById(R.id.btn_Publish);
        cl_MainPublishActivity=findViewById(R.id.cl_MainPublishActivity);
        cl_Inner_MainPublishActivity=findViewById(R.id.cl_Inner_MainPublishActivity);
        tv_HomeName=findViewById(R.id.tv_HomeName);
        tv_HomeName.setText(mNameProperty);
        H.L("pppIDDD"+mIdProperty);
        rv_LivingRoom=findViewById(R.id.rv_LivingRoom);
        rv_BedRoom=findViewById(R.id.rv_BedRoom);
        rv_PowderRoom=findViewById(R.id.rv_PowderRoom);
        rv_Kitchen=findViewById(R.id.rv_Kitchen);
        rv_StudyRoom=findViewById(R.id.rv_StudyRoom);
        rv_StoreRoom=findViewById(R.id.rv_StoreRoom);
        rv_OtherRoom=findViewById(R.id.rv_OtherRoom);
        btn_Publish.setOnClickListener(this);
        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        mRoomViewModel = ViewModelProviders.of(this).get(RoomViewModel.class);

        itemAdapter_LivingRoom=new ItemAdapter(context);
        layoutManager = new LinearLayoutManager(context);
        rv_LivingRoom.setLayoutManager(layoutManager);
        rv_LivingRoom.setAdapter(itemAdapter_LivingRoom);

        itemAdapter_BedRoom=new ItemAdapter(context);
        layoutManager_forBedRoom = new LinearLayoutManager(context);
        rv_BedRoom.setLayoutManager(layoutManager_forBedRoom);
        rv_BedRoom.setAdapter(itemAdapter_BedRoom);

        itemAdapter_PowderRoom=new ItemAdapter(context);
        layoutManager_forPowder = new LinearLayoutManager(context);
        rv_PowderRoom.setLayoutManager(layoutManager_forPowder);
        rv_PowderRoom.setAdapter(itemAdapter_PowderRoom);

        itemAdapter_Kitchen=new ItemAdapter(context);
        layoutManager_forKitchen = new LinearLayoutManager(context);
        rv_Kitchen.setLayoutManager(layoutManager_forKitchen);
        rv_Kitchen.setAdapter(itemAdapter_Kitchen);

        itemAdapter_StudyRoom=new ItemAdapter(context);
        layoutManager_forStudy = new LinearLayoutManager(context);
        rv_StudyRoom.setLayoutManager(layoutManager_forStudy);
        rv_StudyRoom.setAdapter(itemAdapter_StudyRoom);


        try{
            mRoomViewModel.getRoom(mIdProperty, "Living").observe((LifecycleOwner) this, new Observer<RoomTable>() {
                @Override
                public void onChanged(@Nullable RoomTable roomTable) {
                    try{
                        itemViewModel.getRoomItems(roomTable.getRoom_id()).observe((LifecycleOwner) context, new Observer<List<Item>>() {
                            @Override
                            public void onChanged(@Nullable List<Item> items) {


                                itemAdapter_LivingRoom.setItem(items);
                            }

                        });

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





       /* try {
            mRoomViewModel.getInnerRoomIdListofProperty(mIdProperty, 1).observe(this, new Observer<List<Integer>>() {
                @Override
                public void onChanged(@Nullable List<Integer> integers) {
                    for(int i=0;i<=integers.size();i++)
                    {
                        itemViewModel.getRoomItems(i).observe((LifecycleOwner) context, new Observer<List<Item>>() {
                            @Override
                            public void onChanged(@Nullable List<Item> items) {
                                itemAdapter_BedRoom.setItem(items);
                            }
                        });
                    }

                }
            });
        }catch (Exception e)
        {
            e.printStackTrace();
        }*/




    try {
        mRoomViewModel.getInnerRoomList(mIdProperty, 1).observe(this, new Observer<List<RoomTable>>() {
            @Override
            public void onChanged(@Nullable List<RoomTable> roomTables) {

                for (RoomTable mRoomTableNew : roomTables) {
                    H.L("rrooIIDDDDD Beedddd" + mRoomTableNew.getRoom_id());
                    try {

                        itemViewModel.getRoomItems(mRoomTableNew.getRoom_id()).observe((LifecycleOwner) context, new Observer<List<Item>>() {
                            @Override
                            public void onChanged(@Nullable List<Item> items) {
                                itemList.add(items);
                                itemList2.addAll(itemList);
                                itemAdapter_BedRoom.setItemNew(itemList2);
                            }
                        });

                        H.L("listzzzzzz" + itemList2.size());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }



            }
        });
    }catch (Exception e)
    {
        e.printStackTrace();
    }


        try{
            mRoomViewModel.getRoom(mIdProperty, "Powder").observe((LifecycleOwner) this, new Observer<RoomTable>() {
                @Override
                public void onChanged(@Nullable RoomTable roomTable) {
                    try{

                        itemViewModel.getRoomItems(roomTable.getRoom_id()).observe((LifecycleOwner) context, new Observer<List<Item>>() {
                            @Override
                            public void onChanged(@Nullable List<Item> items) {


                                itemAdapter_PowderRoom.setItem(items);
                            }

                        });

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


        try{
            mRoomViewModel.getRoom(mIdProperty, "Kitchen").observe((LifecycleOwner) this, new Observer<RoomTable>() {
                @Override
                public void onChanged(@Nullable RoomTable roomTable) {
                    try{
                        H.L("rrooIIDDDDD kitchen"+roomTable.getRoom_id());
                        itemViewModel.getRoomItems(roomTable.getRoom_id()).observe((LifecycleOwner) context, new Observer<List<Item>>() {
                            @Override
                            public void onChanged(@Nullable List<Item> items) {


                                itemAdapter_Kitchen.setItem(items);
                            }

                        });

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

        try{
            mRoomViewModel.getRoom(mIdProperty, "Study").observe((LifecycleOwner) this, new Observer<RoomTable>() {
                @Override
                public void onChanged(@Nullable RoomTable roomTable) {
                    try{
                        H.L("rrooIIDDDDD kitchen"+roomTable.getRoom_id());
                        itemViewModel.getRoomItems(roomTable.getRoom_id()).observe((LifecycleOwner) context, new Observer<List<Item>>() {
                            @Override
                            public void onChanged(@Nullable List<Item> items) {


                                itemAdapter_StudyRoom.setItem(items);
                            }

                        });

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


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_Publish:

                if(Build.VERSION.SDK_INT> Build.VERSION_CODES.M)
                {
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED)
                    {
                        String[] permissions={Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, STORAGE_CODE);
                    }
                    else
                    {

                        bitmap = loadBitmapFromView(cl_Inner_MainPublishActivity, cl_Inner_MainPublishActivity.getWidth(), cl_Inner_MainPublishActivity.getHeight());
                        createPdf();
                    }
                }
                else
                {
                    bitmap = loadBitmapFromView(cl_Inner_MainPublishActivity, cl_Inner_MainPublishActivity.getWidth(), cl_Inner_MainPublishActivity.getHeight());
                    createPdf();

                }
                break;


        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case STORAGE_CODE:{
                if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    //permission was granted from popup, call createPdf method
                    bitmap = loadBitmapFromView(cl_Inner_MainPublishActivity, cl_Inner_MainPublishActivity.getWidth(), cl_Inner_MainPublishActivity.getHeight());
                    createPdf();

                }
                else
                {
                    // //permission was denied from popup, show error
                    Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }


    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }


    private void createPdf(){
        WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 2).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);
        String mFileName= new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.getDefault()).format(System.currentTimeMillis());
        // write the document content
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/mypdf/";
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }

        String targetPdf = directory_path+mFileName+".pdf";
        // write the document content
//        String targetPdf = "/sdcard/pdffromScroll.pdf";
        File filePath;
        filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
        Toast.makeText(context, "PDF is created!!!", Toast.LENGTH_SHORT).show();

        PreviewActivity.open(context,filePath);

    }
}
