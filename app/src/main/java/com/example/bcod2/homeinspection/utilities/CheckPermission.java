package com.example.bcod2.homeinspection.utilities;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Krupal on 6/15/2017.
 **/

public class CheckPermission {

    public static final int CAMERA_PERMISSION_REQUEST_CODE = 1;
    public static final int STORAGE_PERMISSION_REQUEST_CODE = 2;
    public static final int PERMISSION_REQUEST_CODE = 4;
    public static final int CALL_PERMISSION_REQUEST_CODE = 5;
    public static final int READ_SMS_PERMISSION_REQUEST_CODE = 6;
    private Activity context;

    public CheckPermission(Activity context) {
        this.context = context;
    }

    public static boolean checkPermissionForExternalStorage(Activity activity) {
        int result =
                ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean requestStoragePermission(Activity activity, int READ_STORAGE_PERMISSION) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        READ_STORAGE_PERMISSION);
            }
        }
        return false;
    }

    //Method is used to check whether camera Permission is provided by user or not.

    public static boolean checkDeviceOS() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    //Method is used to check whether Location Permission is provided by user or not.
    public boolean checkLocationPermission() {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION); // Check Fine Location Permission
        int result1 = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);//Check Coarse Location Permission
        if (result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            requestLocationPermission();
            return false;
        }
    }

    public boolean checkCameraPermission() {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        int result2 = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            requestPermissionForCamera();
            return false;
        }
    }

    public boolean checkStoragePermission() {
        int result1 = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result1 == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            requestPermissionForStorage();
            return false;
        }
    }

    public boolean checkReadSmsPermission() {
        int result1 = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS);
        if (result1 == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            requestPermissionForReadSms();
            return false;
        }
    }

    //Method to check permission to call
    public boolean checkContactsPermission() {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            requestPermissionForCall();
            return false;
        }
    }

    private void requestPermissionForReadSms() {
        ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_SMS}, READ_SMS_PERMISSION_REQUEST_CODE);
    }

    private void requestPermissionForCamera() {
        ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_REQUEST_CODE);
    }

    private void requestPermissionForStorage() {
        ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_REQUEST_CODE);
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}
                , PERMISSION_REQUEST_CODE);
    }

    private void requestPermissionForCall() {
        ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_REQUEST_CODE);
    }


}
