package com.example.bcod2.homeinspection.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bcod2.homeinspection.R;
import com.example.bcod2.homeinspection.utilities.H;

public class InsideHomeActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnQuotation,btn_Inspection, btn_actual;
    TextView tv_Quotation,tv_PropertyAddress;
    private static String mNameProperty,mAddressProperty;
    private static int mIdProperty;
    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_home);
        context=InsideHomeActivity.this;
        tv_Quotation=findViewById(R.id.tv_Quotation);
        tv_PropertyAddress=findViewById(R.id.tv_PropertyAddress);
        tv_Quotation.setText(mNameProperty);
        tv_PropertyAddress.setText(mAddressProperty);
        btnQuotation = findViewById(R.id.btnQuotation);
        btn_Inspection = findViewById(R.id.btn_Inspection);
        btn_actual = findViewById(R.id.btn_actual);

        btnQuotation.setOnClickListener(this);
        btn_Inspection.setOnClickListener(this);
        btn_actual.setOnClickListener(this);

    }
    public static void open(Context context, String nameProperty, String addressProperty,int idProperty) {
        mNameProperty=nameProperty;
        mAddressProperty=addressProperty;
        mIdProperty=idProperty;
        context.startActivity(new Intent(context, InsideHomeActivity.class));

        H.L("iiiiiidddd"+mIdProperty);
    }


    @Override
    public void onClick(View v) {
        try{
            switch (v.getId())
            {
                case R.id.btnQuotation:
                   QuotationActivity.open(context, mNameProperty, mIdProperty,"fromQuotation");
                    break;
                case R.id.btn_Inspection:
                    QuotationActivity.open(context, mNameProperty, mIdProperty,"fromInspection");
                    break;
                case R.id.btn_actual:
                    H.T(context,"Not Working.....");
                    break;




            }

        }catch(Exception e)
        {
            e.printStackTrace();
        }

    }
}
