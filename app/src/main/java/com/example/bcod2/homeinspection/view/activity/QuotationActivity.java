package com.example.bcod2.homeinspection.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bcod2.homeinspection.viewmodel.RoomViewModel;
import com.example.bcod2.homeinspection.R;
import com.example.bcod2.homeinspection.adapter.QuotationAdapter;
import com.example.bcod2.homeinspection.utilities.H;

public class QuotationActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    QuotationAdapter quotationAdapter;
    RecyclerView recyclerView;
    TextView tv_Quotation2,tv_Quotation;
    Button btn_Publish;
    private static String mNameProperty;
    private static int mIdProperty;
    private RoomViewModel mRoomViewModel;
    private static String mFrom;


    public static void open(Context context, String nameProperty,int idProperty,String from) {
        mNameProperty=nameProperty;
        mIdProperty=idProperty;
        mFrom=from;
        context.startActivity(new Intent(context, QuotationActivity.class));
        H.L("QuotationActivity iiiiiidddd"+mIdProperty);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(mFrom.equals("fromInspection"))
        {
            setTheme(R.style.Inspection);
        }else if(mFrom.equals("fromQuotation"))
        {
            setTheme(R.style.AppTheme);
        }
        setContentView(R.layout.activity_quotation);
        context=QuotationActivity.this;
        tv_Quotation2=findViewById(R.id.tv_Quotation2);
        tv_Quotation=findViewById(R.id.tv_Quotation);
        tv_Quotation2.setText(mNameProperty);
        recyclerView=findViewById(R.id.rv_quotation);
        btn_Publish=findViewById(R.id.btn_Publish);

        if(mFrom.equals("fromQuotation"))
        {
            tv_Quotation.setText("Make Quotation of");
            btn_Publish.setVisibility(View.VISIBLE);
        }else if(mFrom.equals("fromInspection"))
        {
            tv_Quotation.setText("Inspection of");
            btn_Publish.setVisibility(View.GONE);
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        String[] array = new String[]{"Living Room", "Bed Room", "Powder Room","Kitchen","Study Room","Store Room", "Other Room"};
        mRoomViewModel = ViewModelProviders.of(this).get(RoomViewModel.class);
        quotationAdapter= new QuotationAdapter(context,array,mIdProperty,mRoomViewModel,mFrom);
        recyclerView.setAdapter(quotationAdapter);
        btn_Publish.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_Publish:
                MainPublishActivity.open(context,mNameProperty,mIdProperty);
                Toast.makeText(context, "Not working", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
