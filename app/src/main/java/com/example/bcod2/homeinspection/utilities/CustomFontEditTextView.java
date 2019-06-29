package com.example.bcod2.homeinspection.utilities;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.example.bcod2.homeinspection.R;


public class CustomFontEditTextView extends AppCompatEditText {

    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public CustomFontEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        Log.e("Font Asset", "fontname called");
        applyCustomFont(context, attrs);
    }

    public CustomFontEditTextView(Context context, AttributeSet attrs, int defStyle) {
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

        if (fontName.contentEquals(context.getString(R.string.font_name_pluto_light))) {
//            Log.e("Font Asset", "pluto_light:" + fontName);
            customFont = FontCache.getTypeface("plutolight.ttf", context);
        } else if (fontName.contentEquals(context.getString(R.string.font_name_poppins_regular))) {
//            Log.e("Font Asset", "poppins_regular:" + fontName);
            customFont = FontCache.getTypeface("poppins_regular.ttf", context);
        } else if (fontName.contentEquals(context.getString(R.string.font_name_poppins_medium))) {
            customFont = FontCache.getTypeface("poppins_medium.ttf", context);
        } else {
            // no matching font found
            // return null so Android just uses the standard font (Roboto)
            customFont = null;
        }
        setTypeface(customFont);

        attributeArray.recycle();
    }
}
