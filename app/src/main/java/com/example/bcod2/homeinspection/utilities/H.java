package com.example.bcod2.homeinspection.utilities;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * Created by Krupal on 9/20/2017.
 **/

public class H {

    public static final int MEDIA_TYPE_IMAGE = 1;

    /**
     * static method to Show the Toast
     *
     * @param context Context of the Activity from where to show the Toast
     * @param text    Text which to show in the Toast message.
     */
    public static void T(Context context, String text) {
        Toast.makeText(context, text + "", Toast.LENGTH_SHORT).show();
    }

    /**
     * Static method to show the Log from any class
     *
     * @param msg text message which will be shown in the log.
     */

    public static void L(String msg) {
        Log.d("TAG", msg + "");
    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
