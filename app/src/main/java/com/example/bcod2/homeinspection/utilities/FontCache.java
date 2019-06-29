package com.example.bcod2.homeinspection.utilities;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

//import android.util.Log;

/**
 * Created by Parth Dani on 16-06-2017.
 * BCOD Mobili Solutions
 * dani.parth15@gmail.com
 */
public class FontCache {
    private static HashMap<String, Typeface> fontCache = new HashMap<>();

    public static Typeface getTypeface(String fontname, Context context) {
        Typeface typeface = fontCache.get(fontname);

        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + fontname);
            } catch (Exception e) {
                return null;
            }

//            Log.e("Font Asset", "fontname:" + fontname);
            fontCache.put(fontname, typeface);
        }

        return typeface;
    }
}
