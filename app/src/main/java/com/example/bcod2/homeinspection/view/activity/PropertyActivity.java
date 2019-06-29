package com.example.bcod2.homeinspection.view.activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bcod2.homeinspection.R;
import com.example.bcod2.homeinspection.roomdatabase.Property;
import com.example.bcod2.homeinspection.utilities.H;
import com.example.bcod2.homeinspection.viewmodel.PropertyViewModel;

public class PropertyActivity  extends AppCompatActivity implements View.OnClickListener {
    public static final String NOTE_PROPERTY_NAME = "new_property_name";
    public static final String NOTE_PROPERTY_ADDRESS = "new_property_address";
    public static final String UPDATED_PROPERTY_NAME = "update_property_name";
    public static final String UPDATED_PROPERTY_ADDRESS = "update_property_address";
    public static final String PROPERTY_ID = "property_id";
    public static String mFrom;
    private EditText etPropertyName, etPropertyAddress;
    Button btnSave, btnCancel,btn_Update;
    Context context;
    Intent intent;
    TextView tvAddProperty;
    int propertyId;
    PropertyViewModel propertyViewModel;
    LiveData<Property> propertyLD;

  /*  public  static void open(Context mContext, int from)
    {
        mFrom=from;
        mContext.startActivity(new Intent(mContext,PropertyActivity.class));

    }*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);
        context=PropertyActivity.this;
        intent=getIntent();
        etPropertyName = findViewById(R.id.et_NameProperty);
        etPropertyAddress = findViewById(R.id.et_AddressProperty);
        tvAddProperty = findViewById(R.id.tvAddProperty);
       btnSave = findViewById(R.id.btnSave);
      btnCancel = findViewById(R.id.btnCancel);
        btn_Update = findViewById(R.id.btn_Update);
      btnSave.setOnClickListener(this);
      btnCancel.setOnClickListener(this);
        btn_Update.setOnClickListener(this);
        propertyViewModel=ViewModelProviders.of(this).get(PropertyViewModel.class);
        mFrom=intent.getStringExtra("From");

        if(mFrom.equals("fromAdd"))
        {
            H.L("InADDD");
            tvAddProperty.setText("Add Property");
            btn_Update.setVisibility(View.GONE);
            btnSave.setVisibility(View.VISIBLE);
        }else if(mFrom.equals("fromUpdate"))
        {
            tvAddProperty.setText("Update Property");
            btn_Update.setVisibility(View.VISIBLE);
            btnSave.setVisibility(View.GONE);
            propertyId= intent.getIntExtra("property_id",0);
            H.L("gggggg"+propertyId);
            propertyLD=propertyViewModel.getProperty(propertyId);
            propertyLD.observe(this, new Observer<Property>() {
                @Override
                public void onChanged(@Nullable Property property) {
                    etPropertyName.setText(property.getPropertyName());
                    etPropertyAddress.setText(property.getPropertyAddress());
                }
            });

        }

    }

    public static void open(Context context) {
        context.startActivity(new Intent(context, PropertyActivity.class));
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btnSave:
                    validation();
                    break;
                case R.id.btnCancel:
                       finish();
                    break;
                    case R.id.btn_Update:
                        validation();
                    break;

            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }


    public void validation()
    {
        if(etPropertyName.getText().toString().length()<=0)
        {
            H.T(context, getResources().getString(R.string.enter_property_name));
        } else if(etPropertyAddress.getText().toString().length()<=0)
        {
            H.T(context, getResources().getString(R.string.enter_property_address));
        } else
        {
            try {
                if(mFrom.equals("fromAdd")) {
                    save();
                }else if(mFrom.equals("fromUpdate")) {
                    H.L("InUpdate");
                    updateProperty();
                }
            }catch (Exception e)
            {

            }
        }
    }

    public void save()
    {
        Intent resultIntent = new Intent();

        if (TextUtils.isEmpty(etPropertyName.getText()) && TextUtils.isEmpty(etPropertyAddress.getText()) ) {
            setResult(RESULT_CANCELED, resultIntent);
        } else {
            try {
                String name = etPropertyName.getText().toString();
                String address = etPropertyAddress.getText().toString();
                resultIntent.putExtra(NOTE_PROPERTY_NAME, name);
                resultIntent.putExtra(NOTE_PROPERTY_ADDRESS, address);
                setResult(RESULT_OK, resultIntent);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        finish();
    }

    public void updateProperty()
    {
        H.L("iiiiidddffggg"+propertyId);
        Intent resultIntent = new Intent();
        if (TextUtils.isEmpty(etPropertyName.getText()) && TextUtils.isEmpty(etPropertyAddress.getText()) ) {
            setResult(RESULT_CANCELED, resultIntent);
        } else {
            try {
                String name = etPropertyName.getText().toString();
                String address = etPropertyAddress.getText().toString();
                resultIntent.putExtra(PROPERTY_ID, propertyId);
                resultIntent.putExtra(UPDATED_PROPERTY_NAME, name);
                resultIntent.putExtra(UPDATED_PROPERTY_ADDRESS, address);
                setResult(RESULT_OK, resultIntent);
                finish();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        finish();
    }
}
