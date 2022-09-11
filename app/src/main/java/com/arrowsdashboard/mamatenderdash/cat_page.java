package com.arrowsdashboard.mamatenderdash;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class cat_page extends AppCompatActivity {
    ImageView add_img ;
    RecyclerView prodrec ;
    SubItemAdapter adapter ;
    List<cat> list_of_categories ;
    List<String> comlis ;
    List<size> sizlis ;
    List<adds> addslis ;
    RecyclerView.LayoutManager manager ;
    
    ProgressBar pload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_page);

        add_img = findViewById(R.id.add_img);
        pload = findViewById(R.id.pload);
        prodrec = findViewById(R.id.prodrec);
        manager= new GridLayoutManager(this,2);
        prodrec.setHasFixedSize(true);
        prodrec.setLayoutManager(manager);

        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(cat_page.this,add_product.class));
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.orders:
                        startActivity(new Intent(cat_page.this, request_activity.class));
                        overridePendingTransition(0,0);
                        return true ;

                    case R.id.home_page:
                        startActivity(new Intent(cat_page.this, home_page.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.menu:
                        return true;
                }
                return false;
            }
        });

        getallcat();


    }
    private void getallcat() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Const.HTTPS_RESTAUARANT_ARROWSCARS_COM + "/api/dashboard/categories";
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            pload.setVisibility(View.GONE);
                            if (jsonObject.getBoolean("status")){
                                list_of_categories = new ArrayList<>();
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0 ; i<jsonArray.length() ; i++){

                                    JSONObject o1 = jsonArray.getJSONObject(i);
                                    cat cat1 = new cat(o1.getString("name"),o1.getString("photo"),o1.getInt("id"));
                                    list_of_categories.add(cat1);

                                }

                                adapter = new SubItemAdapter(cat_page.this,list_of_categories,1,1,1,1,1, 1,1);
                                prodrec.setAdapter(adapter);

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
                        pload.setVisibility(View.GONE);
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
        startActivity(new Intent(cat_page.this,home_page.class));
        super.onBackPressed();
    }

}