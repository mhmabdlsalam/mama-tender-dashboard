package com.arrowsdashboard.mamatenderdash;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class branches extends AppCompatActivity {
    ImageView add_img ;
    
    ProgressBar pload ;
    List<branch> list_of_branches ;
    RecyclerView branches_rec ;
    SubItemAdapter adapter ;
    RecyclerView.LayoutManager manager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branches);
        add_img = findViewById(R.id.add_img);
        pload = findViewById(R.id.pload);
        branches_rec = findViewById(R.id.branches_rec);
        manager= new LinearLayoutManager(this);
        branches_rec.setHasFixedSize(true);
        branches_rec.setLayoutManager(manager);

        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(branches.this,add_branch.class));
            }
        });

        getallbranches();

    }

    private void getallbranches() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Const.HTTPS_RESTAUARANT_ARROWSCARS_COM + "/api/dashboard/branches";
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            pload.setVisibility(View.GONE);
                            if (jsonObject.getBoolean("status")){
                                list_of_branches = new ArrayList<>();
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0 ; i<jsonArray.length() ; i++){
                                    JSONObject o1 = jsonArray.getJSONObject(i);
                                    branch branch = new branch(o1.getString("name")+" : "+o1.getString("address"),o1.getInt("id"));

                                    list_of_branches.add(branch);
                                }

                                adapter = new SubItemAdapter(branches.this,list_of_branches,1,1,1);
                                branches_rec.setAdapter(adapter);

                            }else {
                                ImageView imageView4 = findViewById(R.id.imageView4);
                                TextView textView5 = findViewById(R.id.textView5);
                                imageView4.setVisibility(View.VISIBLE);
                                textView5.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR","error => "+error.toString());
                        Toast.makeText(branches.this,"sssss",Toast.LENGTH_LONG).show();

                    }
                }
        )
        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("resturant", getResources().getString(R.string.restaurant_name_));
                return params;
            }

        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(getRequest);

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(branches.this,home_page.class));
        super.onBackPressed();
    }
}