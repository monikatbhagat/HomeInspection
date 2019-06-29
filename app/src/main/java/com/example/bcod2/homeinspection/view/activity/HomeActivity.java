package com.example.bcod2.homeinspection.view.activity;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.bcod2.homeinspection.utilities.H;
import com.example.bcod2.homeinspection.viewmodel.ItemViewModel;
import com.example.bcod2.homeinspection.viewmodel.PropertyViewModel;
import com.example.bcod2.homeinspection.R;
import com.example.bcod2.homeinspection.adapter.PropertyAdapter;
import com.example.bcod2.homeinspection.roomdatabase.Property;
import com.example.bcod2.homeinspection.viewmodel.RoomViewModel;

import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, PropertyAdapter.OnDeleteClickListener,PropertyAdapter.OnDeletePropertyRoomClickListener,PropertyAdapter.OnDeleteItemsofRooms{
    private static final int NEW_PROPERTY_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_NOTE_ACTIVITY_REQUEST_CODE =2;
    private PropertyViewModel propertyViewModel;
    private RoomViewModel roomViewModel;
    private ItemViewModel itemViewModel;
    private PropertyAdapter propertyAdapter;
    private Button btnAddProperty;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mContext=HomeActivity.this;

        btnAddProperty=findViewById(R.id.btnAddProperty);
        btnAddProperty.setOnClickListener(this);
        propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel.class);
        roomViewModel = ViewModelProviders.of(this).get(RoomViewModel.class);
        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        RecyclerView recyclerView= findViewById(R.id.rv_addProperty);
        propertyAdapter=new PropertyAdapter(this,this,this,this,roomViewModel);
        recyclerView.setAdapter(propertyAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));



        propertyViewModel.getAllProperties().observe((LifecycleOwner) this, new Observer<List<Property>>() {
            @Override
            public void onChanged(@Nullable List<Property> properties) {
                propertyAdapter.setProerty(properties);
            }
        });

//        getProperty();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_PROPERTY_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            // Code to insert note
//            final String property_id=UUID.randomUUID().toString();

            Property property=new Property(0,data.getStringExtra(PropertyActivity.NOTE_PROPERTY_NAME), data.getStringExtra(PropertyActivity.NOTE_PROPERTY_ADDRESS));
            propertyViewModel.insert(property);

            Toast.makeText(
                    getApplicationContext(),
                    R.string.saved,
                    Toast.LENGTH_LONG).show();
        }else if(requestCode== UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK)
        {
            H.L("homeuppdateiiddd"+data.getIntExtra(PropertyActivity.PROPERTY_ID,0));
            Property property=new Property(data.getIntExtra(PropertyActivity.PROPERTY_ID,0),data.getStringExtra(PropertyActivity.UPDATED_PROPERTY_NAME), data.getStringExtra(PropertyActivity.UPDATED_PROPERTY_ADDRESS));
            propertyViewModel.updateProperty(property);
            Toast.makeText(
                    getApplicationContext(),
                    R.string.saved,
                    Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btnAddProperty:

                    Intent intent = new Intent(HomeActivity.this, PropertyActivity.class);
                    intent.putExtra("From","fromAdd");
                    startActivityForResult(intent, NEW_PROPERTY_ACTIVITY_REQUEST_CODE);
                    break;
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void OnDeleteClickListener(Property property) {
        propertyViewModel.delete(property);

    }

    @Override
    public void OnDeletePropertyRoomClickListener(int propertyId) {
        roomViewModel.deleteRoomForProperty(propertyId);
    }

    @Override
    public void OnDeleteItemsofRooms(int roomId) {
        itemViewModel.deleteRoomItem(roomId);
    }







  /*  private void getProperty() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                propertyViewModel = ViewModelProviders.of(HomeActivity.this).get(PropertyViewModel.class);

                propertyViewModel.getAllProperties().observe(HomeActivity.this, new Observer<List<Property>>() {
                    @Override
                    public void onChanged(@Nullable List<Property> properties) {
                        propertyAdapter.setProerty(properties);
                    }
                });
                return null;
            }

            @Override
            protected void onPostExecute(Void agentsCount) {

            }
        }.execute();
    }*/
}
