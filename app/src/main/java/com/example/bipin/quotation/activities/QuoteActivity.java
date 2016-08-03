package com.example.bipin.quotation.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bipin.quotation.R;
import com.example.bipin.quotation.adapters.QuoteAdapter;
import com.example.bipin.quotation.pojos.Quote;
import com.example.bipin.quotation.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QuoteActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private QuoteAdapter adapter;
    private SwipeRefreshLayout ptorf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ptorf = (SwipeRefreshLayout) findViewById(R.id.pullToRef);
        if (ptorf != null)
            ptorf.setOnRefreshListener(this);
        adapter = new QuoteAdapter(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.detailsRv);
        if (recyclerView!=null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }

        sendDetailJsonObjectRequest();

    }

    public void enableProgress() {
        ptorf.post(new Runnable() {
            @Override
            public void run() {
                ptorf.setEnabled(true);
            }
        });
    }

    public void disableProgress() {
        ptorf.post(new Runnable() {
            @Override
            public void run() {
                ptorf.setEnabled(false);
            }
        });
    }

    private void sendDetailJsonObjectRequest() {

        String code = getIntent().getStringExtra("code");
        String URL = "http://quotebucketapi.azurewebsites.net/api/quotegroups/" + code + "/quotes";

        enableProgress();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                URL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        disableProgress();
                        ArrayList<Quote> data = parsedData(response);
                        adapter.setData(data);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        disableProgress();
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();

                    }
                }

        );

        VolleySingleton.getInstance().getQueue().add(request);
    }

    private ArrayList<Quote> parsedData(JSONObject response) {

        ArrayList<Quote> temp = new ArrayList<>();
        try {
            JSONArray data=response.getJSONArray("data");
            for (int i=0;i<data.length();i++)
            {
                JSONObject item=data.getJSONObject(i);
                String content = item.getString("content");
                String groupCode = item.getString("quoteGroupCode");
                String id = item.getString("id");
                String submittedBy = item.getString("submittedBy");
                String pusblishStatus = item.getString("pusblishStatus");
                String dateCreated = item.getString("dateCreated");
                temp.add(new Quote(groupCode,content,submittedBy,pusblishStatus,dateCreated,id));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return temp;

    }

    @Override
    public void onRefresh() {

        sendDetailJsonObjectRequest();
    }
}

