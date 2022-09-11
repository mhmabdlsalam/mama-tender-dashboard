package com.arrowsdashboard.mamatenderdash;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class categories_page extends AppCompatActivity {
    ImageView add_img ;
    RecyclerView prodrec ;
    SubItemAdapter adapter ;
    List<product> listofproduct ;
    List<String> comlis ;
    List<size> sizlis ;
    List<adds> addslis ;
    RecyclerView.LayoutManager manager ;
    
    ProgressBar pload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_page);

        add_img = findViewById(R.id.add_img);
        pload = findViewById(R.id.pload);
        prodrec = findViewById(R.id.prodrec);
        manager= new GridLayoutManager(this,2);
        prodrec.setHasFixedSize(true);
        prodrec.setLayoutManager(manager);



        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(categories_page.this,add_product.class));
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.orders:
                        startActivity(new Intent(categories_page.this, request_activity.class));
                        overridePendingTransition(0,0);
                        return true ;

                    case R.id.home_page:
                        startActivity(new Intent(categories_page.this, home_page.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.menu:
                        return true;
                }
                return false;
            }
        });

        getalldeinks();
    }

    private void getalldeinks() {
        String url = Const.HTTPS_RESTAUARANT_ARROWSCARS_COM + "/api/dashboard/categories/products/"+getIntent().getIntExtra("id",0);
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ressssspon",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            pload.setVisibility(View.GONE);
                            if (true){
                                listofproduct = new ArrayList<>();
                                JSONArray jsonArray = jsonObject.getJSONArray("products");
                                for (int i = 0 ; i<jsonArray.length() ; i++){
                                    JSONObject o1 = jsonArray.getJSONObject(i);
                                    JSONArray comarr = o1.getJSONArray("components");
                                    JSONArray sizarr = o1.getJSONArray("sizes");
                                    JSONArray addsarr = o1.getJSONArray("additional");
                                    comlis =  new ArrayList<>();
                                    sizlis =  new ArrayList<>();
                                    addslis =  new ArrayList<>();
                                    for (int  j =0 ; j<comarr.length();j++){
                                        comlis.add(comarr.getString(j));
                                    }
                                    for (int  k =0 ; k<sizarr.length();k++){
                                        JSONObject oo = sizarr.getJSONObject(k);
                                        size size = new size(oo.getString("size"),oo.getString("price"));
                                        sizlis.add(size);
                                    }
                                    for (int  k =0 ; k<addsarr.length();k++){
                                        JSONObject oo = addsarr.getJSONObject(k);
                                        adds adds = new adds(oo.getString("addition"),oo.getString("price"));
                                        addslis.add(adds);
                                    }

                                    product product = new product(o1.getString("photo"),o1.getString("name")
                                            ,o1.getString("name"),sizlis,comlis,addslis,String.valueOf(o1.getInt("id")));
                                    listofproduct.add(product);
                                }

                                adapter = new SubItemAdapter(categories_page.this,listofproduct,1,1,1,1,1);
                                prodrec.setAdapter(adapter);

                            }else {
                                ImageView imageView4 = findViewById(R.id.imageView4);
                                TextView textView5 = findViewById(R.id.textView5);
                                imageView4.setVisibility(View.VISIBLE);
                                textView5.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            pload.setVisibility(View.GONE);
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
                        Toast.makeText(categories_page.this,"Error Try again",Toast.LENGTH_LONG).show();
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
}