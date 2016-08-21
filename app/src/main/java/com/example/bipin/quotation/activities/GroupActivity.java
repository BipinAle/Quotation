package com.example.bipin.quotation.activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bipin.quotation.Fragments.Exit;
import com.example.bipin.quotation.Fragments.Favourite;
import com.example.bipin.quotation.Fragments.Home;
import com.example.bipin.quotation.Fragments.Setting;
import com.example.bipin.quotation.R;
import com.example.bipin.quotation.adapters.FavouriteAdapter;
import com.example.bipin.quotation.adapters.GroupAdapter;
import com.example.bipin.quotation.pojos.Group;
import com.example.bipin.quotation.utility.Constants;
import com.example.bipin.quotation.utility.Utility;
import com.example.bipin.quotation.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GroupActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private GroupAdapter adapter;
    private SwipeRefreshLayout pullToRef;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    NavigationView navigationView;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerclose);
        if (drawerLayout != null)
            drawerLayout.addDrawerListener(actionBarDrawerToggle);


        pullToRef = (SwipeRefreshLayout) findViewById(R.id.pullToRefresh);
        if (pullToRef != null)
            pullToRef.setOnRefreshListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.categoryRv);
        adapter = new GroupAdapter(this);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            recyclerView.setAdapter(adapter);
        }

        navigationView= (NavigationView) findViewById(R.id.navigation_view);

        Utility.menuItemColorGarney(navigationView);


        //nav menu listner  and steps to jump to fragment
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                String title;
                Fragment fragment;

                switch(item.getItemId())
                {
                    case R.id.home:

                        title="Home";
                        fragment=new Home();
                        break;
                    case R.id.setting:
                        title="Setting";
                        fragment=new Setting();
                        break;
                    case R.id.exit:
                        title="Exit";
                        fragment=new Exit();
                        break;
                    case R.id.favourites:
                        title="Favourites";
                        fragment=new Favourite();
                        break;

                    default:
                        title="defaults";
                        fragment=null;
                }
                if (fragment!=null)
                {

                    getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
                    if (getSupportActionBar()!=null)
                    {
                        getSupportActionBar().setTitle(title);
                        recyclerView.setVisibility(View.INVISIBLE);
                        drawerLayout.closeDrawers();
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    }
                }
                return false;
            }
        });

        sendJsonRequest();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.i("tag", "rag");
        if(item.getItemId() == android.R.id.home){

            this.finish();

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    public void enableProgress() {
        pullToRef.post(new Runnable() {
            @Override
            public void run() {
                pullToRef.setEnabled(true);
            }
        });
    }

    public void disableProgress() {
        pullToRef.post(new Runnable() {
            @Override
            public void run() {
                pullToRef.setEnabled(false);
            }
        });
    }

    private void sendJsonRequest() {
        enableProgress();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                Constants.DETAIL_URL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        disableProgress();
                        Toast.makeText(getApplicationContext(), "Downloading", Toast.LENGTH_SHORT).show();
                        ArrayList<Group> parsedData = parseData(response);

                        adapter.setData(parsedData);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        disableProgress();
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();

                    }
                });
        VolleySingleton.getInstance().getQueue().add(request);

    }

    private ArrayList<Group> parseData(JSONObject response) {
        ArrayList<Group> tempData = new ArrayList<>();

        try {
            JSONArray data = response.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                JSONObject item = data.getJSONObject(i);
                String groupName = item.getString("groupName");
                String groupCode = item.getString("groupCode");
                String date = item.getString("dateCreated");
                tempData.add(new Group(groupName, groupCode, date));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return tempData;
    }

    @Override
    public void onRefresh() {

        sendJsonRequest();

    }

    //to bring hamburger icon in action
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item != null && item.getItemId() == android.R.id.home) {
//            toggle();
//
//        }
//        return super.onOptionsItemSelected(item);
//    }


//
//    private void toggle() {
//        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START);
//        } else {
//            drawerLayout.openDrawer(GravityCompat.START);
//        }
//    }

}
