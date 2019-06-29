package com.example.bcod2.homeinspection.view.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bcod2.homeinspection.R;
import com.example.bcod2.homeinspection.adapter.ImageAdapter;
import com.example.bcod2.homeinspection.roomdatabase.ImageTable;
import com.example.bcod2.homeinspection.utilities.H;
import com.example.bcod2.homeinspection.viewmodel.ImageViewModel;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class InspectionActivity extends AppCompatActivity{
    Context context;
    TextView tv_Room,tv_Property;
    static String mRoomName;
    static int mRoomId;
    private ImageViewModel imageViewModel;
    private ImageAdapter imageAdapter;
    public static final int REQUEST_IMAGE = 100;
    public static final int REQUEST_GALLERY_PHOTO = 200;
    private String imageFilePath = "",timeStamp,timeStampNew;
    RecyclerView recyclerView;



    public static void open(Context context,int roomId,String roomName)
    {
        mRoomId=roomId;
        mRoomName=roomName;
        context.startActivity(new Intent(context,InspectionActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection);
        H.L("before 1111");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context=InspectionActivity.this;
        tv_Room=findViewById(R.id.tv_Room);
        tv_Property=findViewById(R.id.tv_Property);
        recyclerView=findViewById(R.id.rv_addPic);
        imageViewModel= ViewModelProviders.of(this).get(ImageViewModel.class);
        tv_Room.setText(mRoomName);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        imageViewModel.getRoomImagesList(mRoomId).observe(this, new Observer<List<ImageTable>>() {
            @Override
            public void onChanged(@Nullable List<ImageTable> imageTables) {
                H.L("111");
                imageAdapter=new ImageAdapter(context,imageTables);
                RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(context);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(imageAdapter);
                imageAdapter.setImage(imageTables);
            }
        });
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(InspectionActivity.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    requestStoragePermission(true);
                } else if (items[item].equals("Choose from Library")) {
                    requestStoragePermission(false);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void requestStoragePermission(final boolean isCamera) {
        Dexter.withActivity(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            if (isCamera) {
                                openCameraIntent();
                            } else {
                                dispatchGalleryIntent();
                            }
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError error) {
                Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
            }
        })
                .onSameThread()
                .check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openSettings();
                dialog.cancel();
            }
        } );
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private void openCameraIntent() {
        H.L("2222");
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            }
            catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Uri photoUri = FileProvider.getUriForFile(this, getPackageName() +".provider", photoFile);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(pictureIntent, REQUEST_IMAGE);
        }
    }


    private File createImageFile() throws IOException{
        H.L("ttttt");
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        timeStampNew = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aaa", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        imageFilePath = image.getAbsolutePath();
        return image;
    }

    private void dispatchGalleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
    }

    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            try {
                if (requestCode == REQUEST_IMAGE) {
                    H.L("ccccccc");
                    //image.setImageURI(Uri.parse(imageFilePath));
                    ImageTable imageTable = new ImageTable(0, mRoomId, imageFilePath, timeStampNew);
                    imageViewModel.insertImage(imageTable);
                    H.T(context, "Image Saved");
                }else  if (requestCode == REQUEST_GALLERY_PHOTO) {
                    H.L("gggggg");
                    Uri selectedImage = data.getData();
                    imageFilePath=getRealPathFromUri(selectedImage);
                    timeStampNew = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aaa", Locale.getDefault()).format(new Date());
                    ImageTable imageTable = new ImageTable(0, mRoomId, imageFilePath, timeStampNew);
                    imageViewModel.insertImage(imageTable);
                    H.T(context, "Image Saved from Gallery");
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "You cancelled the operation", Toast.LENGTH_SHORT).show();
        }
    }



}