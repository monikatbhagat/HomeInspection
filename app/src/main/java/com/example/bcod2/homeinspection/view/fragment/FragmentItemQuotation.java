package com.example.bcod2.homeinspection.view.fragment;

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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bcod2.homeinspection.R;
import com.example.bcod2.homeinspection.adapter.ItemAdapter;
import com.example.bcod2.homeinspection.roomdatabase.Item;
import com.example.bcod2.homeinspection.utilities.H;
import com.example.bcod2.homeinspection.view.activity.PreviewActivity;
import com.example.bcod2.homeinspection.viewmodel.ItemViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static android.support.v4.content.ContextCompat.checkSelfPermission;

public class FragmentItemQuotation  extends Fragment implements View.OnClickListener, ItemAdapter.OnDeleteClickListenerofItem {
    Context context;
    private boolean isViewCreated = false;
    TextView tv_AddItem,tv_RoomName;
    FragmentManager fm;
    int flagItem,itemCost;
    private boolean visible = false;
    static String roomName;
    static  int roomId,itemId;
    String itemName,itemNote,fileName;
    private ItemAdapter itemAdapter;
    Button button;
    private Bitmap bitmap;
    private NestedScrollView scrollView;
    private ConstraintLayout cl_fragment_item_quotation,cl_inner;
    RecyclerView recyclerView;
//    private static final int NEW_ITEM_REQUEST_CODE = 1;
//    public static final int UPDATE_ITEM_REQUEST_CODE =2;
    private ItemViewModel itemViewModel;
    public static final int STORAGE_CODE=1000;

    public FragmentItemQuotation()
    {
        //required
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        H.L("aaa");
        View view = inflater.inflate(R.layout.fragment_item_quotation, container, false);
        context = getActivity();
        isViewCreated = true;
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        H.L("aaa");
        tv_AddItem.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);
        if (visible) {
            bindViews();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        H.L("ccc");
        bindViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        H.L("ddd");
    }

    public void bindViews()
    {
        roomId=getArguments().getInt("RoomId");
        roomName=getArguments().getString("RoomName");

        H.L("RoomIdwwwww"+roomId);
        H.L("RoomNamewwwww"+roomName);
        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        tv_AddItem=getActivity().findViewById(R.id.tv_AddItem);
        tv_RoomName=getActivity().findViewById(R.id.tv_RoomName);
        button=getActivity().findViewById(R.id.button);
        cl_fragment_item_quotation=getActivity().findViewById(R.id.cl_fragment_item_quotation);
        cl_inner=getActivity().findViewById(R.id.cl_inner);
        scrollView=getActivity().findViewById(R.id.scrollView);
        tv_RoomName.setText(roomName);
        button.setOnClickListener(this);
        tv_AddItem.setOnClickListener(this);

        flagItem=getArguments().getInt("FLAG");
        if(flagItem==1)
        {
            H.L("In Fragment00000ItemmmmAdddddIttt");
             itemName=getArguments().getString("ITEM_NAME");
             itemCost=getArguments().getInt("ITEM_COST");
             itemNote=getArguments().getString("ITEM_NOTE");
//             itemNote=getArguments().getString("ROOM_ID");
            Item item=new Item(0,roomId,itemName,itemCost,itemNote);
            itemViewModel.insert(item);

            Toast.makeText(
                   getContext(),
                  "Item Saved",
                    Toast.LENGTH_LONG).show();
        }else if(flagItem==2)
        {
            H.L("In UUUPPDD");
            itemName=getArguments().getString("UPDATED_ITEM_NAME");
            itemCost=getArguments().getInt("UPDATED_ITEM_COST");
            itemNote=getArguments().getString("UPDATED_ITEM_NOTE");
            itemId=getArguments().getInt("ITEM_Id");


//             itemNote=getArguments().getString("ROOM_ID");
            Item item=new Item(itemId,roomId,itemName,itemCost,itemNote);
            itemViewModel.updateItem(item);

            Toast.makeText(
                    getContext(),
                    "Item Saved",
                    Toast.LENGTH_LONG).show();
        }

        recyclerView= getActivity().findViewById(R.id.rv_Item);
        itemAdapter=new ItemAdapter(context,roomId,roomName,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(itemAdapter);
        tv_AddItem.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);

        itemViewModel.getRoomItems(roomId).observe((LifecycleOwner) this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> items) {
                itemAdapter.setItem(items);
            }

        });

    }


    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_AddItem:
                fm = getActivity().getSupportFragmentManager();

                Bundle bundleNew=new Bundle();
                bundleNew.putInt("RoomId",roomId);
                bundleNew.putString("RoomName",roomName);
                bundleNew.putString("From","fromAddItem");
                AddItemFragment addItemFragment = new AddItemFragment();
                addItemFragment.setArguments(bundleNew);
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.frameLayoutItem, addItemFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.button:

                if(Build.VERSION.SDK_INT> Build.VERSION_CODES.M)
                {
                    if(checkSelfPermission(context,Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED)
                    {
                        String[] permissions={Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, STORAGE_CODE);
                    }
                    else
                    {
                        tv_AddItem.setVisibility(View.GONE);
                        button.setVisibility(View.GONE);
//                        Log.d("size"," "+cl_fragment_item_quotation.getWidth() +"  "+cl_fragment_item_quotation.getWidth());
//                        bitmap = loadBitmapFromView(cl_fragment_item_quotation, cl_fragment_item_quotation.getWidth(), cl_fragment_item_quotation.getHeight());
//                        createPdf();
                        Log.d("size"," "+cl_inner.getWidth() +"  "+cl_inner.getWidth());
                        bitmap = loadBitmapFromView(cl_inner, cl_inner.getWidth(), cl_inner.getHeight());
                        createPdf();
                    }
                }
                else
                {
                    tv_AddItem.setVisibility(View.GONE);
                    button.setVisibility(View.GONE);
                    Log.d("size"," "+cl_inner.getWidth() +"  "+cl_inner.getWidth());
                    bitmap = loadBitmapFromView(cl_inner, cl_inner.getWidth(), cl_inner.getHeight());
                    createPdf();

//                    bitmap = loadBitmapFromView(cl_fragment_item_quotation, cl_fragment_item_quotation.getWidth(), cl_fragment_item_quotation.getHeight());
//                    createPdf();
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
                    /*Log.d("size"," "+recyclerView.getWidth() +"  "+recyclerView.getWidth());
                    bitmap = loadBitmapFromView(recyclerView, recyclerView.getWidth(), recyclerView.getHeight());
                    createPdf();*/
                    tv_AddItem.setVisibility(View.GONE);
                    button.setVisibility(View.GONE);
                    Log.d("size"," "+cl_inner.getWidth() +"  "+cl_inner.getWidth());
                    bitmap = loadBitmapFromView(cl_inner, cl_inner.getWidth(), cl_inner.getHeight());
                    createPdf();

//                    bitmap = loadBitmapFromView(cl_fragment_item_quotation, cl_fragment_item_quotation.getWidth(), cl_fragment_item_quotation.getHeight());
//                    createPdf();
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
   /* private void createPdf(){
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
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
//        String targetPdf = "/sdcard/pdffromlayout.pdf";
        File filePath;
        filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(context, "PDF is created!!!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
        PreviewActivity.open(context,filePath);


    }


*/


    private void createPdf(){
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
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

    @Override
    public void OnDeleteClickListenerofItem(Item item) {

        itemViewModel.deleteItem(item);
    }
}
