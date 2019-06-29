package com.example.bcod2.homeinspection.utilities;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by krupal on 4/6/2017.
 **/

public class Helper {

    public static final int MEDIA_TYPE_IMAGE = 1;

    public static final String RUPEESYMBOL = "\u20B9";

    public static void showDialog(Context context, String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(message);
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public static InputFilter getEditTextFilter() {
        return new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                boolean keepOriginal = true;
                StringBuilder sb = new StringBuilder(end - start);
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    if (isCharAllowed(c)) // put your condition here
                        sb.append(c);
                    else
                        keepOriginal = false;
                }
                if (keepOriginal)
                    return null;
                else {
                    if (source instanceof Spanned) {
                        SpannableString sp = new SpannableString(sb);
                        TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                        return sp;
                    } else {
                        return sb;
                    }
                }
            }

            private boolean isCharAllowed(char c) {
                Pattern ps = Pattern.compile("^[-_%a-zA-Z0-9., ]+$");
                Matcher ms = ps.matcher(String.valueOf(c));
                return ms.matches();
            }
        };
    }

    /**
     * Method to copy the file from source location to destination location
     *
     * @param sourceLocation Location of the file from where file is going to be copy.
     * @param targetLocation Location where file is going to be copied.
     * @param bitmap         Bitmap to store the Image file which is going to be copy.
     */

    public static void copyDirectory(File sourceLocation, File targetLocation, Bitmap bitmap) {
        if (sourceLocation.isDirectory()) {

            if (!targetLocation.exists() && !targetLocation.mkdirs()) {
                targetLocation.mkdir();
            }

            String[] children = sourceLocation.list();
            for (String aChildren : children) {
                copyDirectory(new File(sourceLocation, aChildren), new File(targetLocation, aChildren), bitmap);
            }
        } else {

            File directory = targetLocation.getParentFile();
            if (directory != null && !directory.exists() && !directory.mkdirs()) {
                targetLocation.mkdir();
            }

            FileOutputStream out;
            try {
                //			Log.e("targetLocation", ""+targetLocation.toString());
                out = new FileOutputStream(targetLocation);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            } catch (IOException e) {
//                ////e.printStackTrace();StackTrace();
            }
        }
    }


    public static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    @Nullable
    public static File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + "/HomeInspection/");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "HomeInspection" + timeStamp + ".png");
        } else {
            return null;
        }
        return mediaFile;
    }

    public static boolean copyDirectoryOneLocationToAnotherLocation(File sourceLocation, File targetLocation, Bitmap bitmap) {
        try {
            if (sourceLocation.isDirectory()) {

                if (!targetLocation.exists() && !targetLocation.mkdirs()) {
                    targetLocation.mkdir();
                }

                String[] children = sourceLocation.list();
                for (String aChildren : children) {
                    copyDirectoryOneLocationToAnotherLocation(new File(sourceLocation, aChildren), new File(targetLocation, aChildren), bitmap);
                }
            } else {

                File directory = targetLocation.getParentFile();
                if (directory != null && !directory.exists() && !directory.mkdirs()) {
                    targetLocation.mkdir();
                }

                FileOutputStream out;
                try {
                    //			Log.e("targetLocation", ""+targetLocation.toString());
                    out = new FileOutputStream(targetLocation);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                } catch (FileNotFoundException e) {
//                    ////e.printStackTrace();StackTrace();
                    return false;
                } catch (IOException e) {
//                    ////e.printStackTrace();StackTrace();
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
//            ////e.printStackTrace();StackTrace();
            return false;
        }
    }

    public static boolean isEmailValid(String email) {
//        Log.e("Error", "Email" + email);
        boolean matchFound1;
        boolean returnResult = true;
        email = email.trim();
        if (email.equalsIgnoreCase(""))
            returnResult = false;
        else if (!Character.isLetter(email.charAt(0)))
            returnResult = false;
        else {
            Pattern p1 = Pattern.compile("^\\.|^\\@ |^_");
            Matcher m1 = p1.matcher(email.toString());
            matchFound1 = m1.matches();

            Pattern p = Pattern.compile("^[a-zA-z0-9._-]+[@]{1}+[a-zA-Z0-9]+[.]{1}+[a-zA-Z]{2,4}$");
            // Match the given string with the pattern
            Matcher m = p.matcher(email.toString());

            // check whether match is found
            boolean matchFound = m.matches();

            StringTokenizer st = new StringTokenizer(email, ".");
            String lastToken = null;
            while (st.hasMoreTokens()) {
                lastToken = st.nextToken();
            }
            if (matchFound && lastToken.length() >= 2
                    && email.length() - 1 != lastToken.length() && matchFound1 == false) {

                returnResult = true;
            } else returnResult = false;
        }
        return returnResult;
    }

    /**
     * static method to Show the Toast
     *
     * @param context Context of the Activity from where to show the Toast
     * @param text    Text which to show in the Toast message.
     */
    public static void Toast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * Static method to show the Log from any class
     *
     * @param msg text message which will be shown in the log.
     */
    public static void LOG(String msg)
    {
        //Log.d("KPTAG", msg);
    }

    /**
     * Static method to get the IP Address of the device.
     *
     * @return IP Address of the Mobile device
     */
//    public static String getLocalIpAddress() {
//        try {
//            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
//                 en.hasMoreElements(); ) {
//                NetworkInterface intf = en.nextElement();
//                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
//                    InetAddress inetAddress = enumIpAddr.nextElement();
//                    if (!inetAddress.isLoopbackAddress()) {
//                        return inetAddress.getHostAddress();
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            Log.e("IP Address", ex.toString());
//        }
//        return null;
//    }
    public static String getLocalIpAddress() {
        try {
            boolean useIPv4 = true;
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':') < 0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "";
    }

    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        return sdf.format(c.getTime());
    }


}
