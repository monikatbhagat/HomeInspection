package com.example.bcod2.homeinspection.utilities;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.example.bcod2.homeinspection.R;


public class CustomFontTextView extends AppCompatTextView {

    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        Log.e("Font Asset", "fontname called");
        applyCustomFont(context, attrs);
    }

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context, attrs);
    }

    private void applyCustomFont(Context context, AttributeSet attrs) {
        TypedArray attributeArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.CustomFontTextView);

        String fontName = attributeArray.getString(R.styleable.CustomFontTextView_fontc);
        int textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL);

//        Log.e("Font Asset", "fontname:" + fontName);

        Typeface customFont;

//        if (fontName.contentEquals(context.getString(R.string.font_name_pluto_light))) {
////            Log.e("Font Asset", "pluto_light:" + fontName);
//            customFont = FontCache.getTypeface("plutolight.ttf", context);
//        } else if (fontName.contentEquals(context.getString(R.string.font_name_pluto_medium))) {
////            Log.e("Font Asset", "pluto_light:" + fontName);
//            customFont = FontCache.getTypeface("pluto_medium.otf", context);
//
//        } else
            if (fontName.contentEquals(context.getString(R.string.font_name_Roboto_Regular))) {
                customFont = FontCache.getTypeface("Roboto_Regular.ttf", context);

        }else if (fontName.contentEquals(context.getString(R.string.font_name_Roboto_Medium)))
        {
            customFont = FontCache.getTypeface("Roboto_Medium.ttf", context);
        }
        else if (fontName.contentEquals(context.getString(R.string.font_name_Robot_Bold)))
        {
            customFont = FontCache.getTypeface("Robot_Bold.ttf", context);
        } else {
            // no matching font found
            // return null so Android just uses the standard font (Roboto)
            customFont = FontCache.getTypeface("Roboto_Regular.ttf", context);
        }

        setTypeface(customFont);

        attributeArray.recycle();
    }
     /*     customFont = FontCache.getTypeface("poppins_regular.ttf", context);
        } else if (fontName.contentEquals(context.getString(R.string.font_name_poppins_semibold))) {
            customFont = FontCache.getTypeface("poppins_semibold.ttf", context);
        } else if (fontName.contentEquals(context.getString(R.string.font_name_poppins_bold))) {
            customFont = FontCache.getTypeface("poppins_bold.ttf", context);
        } else {
            // no matching font found
            // return null so Android just uses the standard font (Roboto)
            customFont = null;
        }*/
}
