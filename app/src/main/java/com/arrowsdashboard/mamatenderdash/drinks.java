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
import androidx.recyclerview.widget.GridLayoutManager;
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

public class drinks extends AppCompatActivity {

    ImageView add_img ;
    
    ProgressBar pload ;
    List<drink> list_of_drinks ;
    RecyclerView drinks_rec ;
    SubItemAdapter adapter ;
    RecyclerView.LayoutManager manager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks);
        add_img = findViewById(R.id.add_img);
        pload = findViewById(R.id.pload);
        drinks_rec = findViewById(R.id.drinks_rec);
        manager= new GridLayoutManager(this,2);
        drinks_rec.setHasFixedSize(true);
        drinks_rec.setLayoutManager(manager);

        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(drinks.this,add_drink.class));
            }
        });

        getalldeinks();

    }

    private void getalldeinks() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Const.HTTPS_RESTAUARANT_ARROWSCARS_COM + "/api/dashboard/drinks";
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            pload.setVisibility(View.GONE);
                            if (jsonObject.getBoolean("status")){
                                list_of_drinks = new ArrayList<>();
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0 ; i<jsonArray.length() ; i++){
                                    JSONObject o1 = jsonArray.getJSONObject(i);
                                    drink drink = new drink(o1.getString("name"),o1.getString("photo"),o1.getInt("id"));
                                    list_of_drinks.add(drink);
                                }

                                adapter = new SubItemAdapter(drinks.this,list_of_drinks,1,1,1,1);
                                drinks_rec.setAdapter(adapter);

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
                        Toast.makeText(drinks.this,"sssss",Toast.LENGTH_LONG).show();

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
        startActivity(new Intent(drinks.this,home_page.class));
        super.onBackPressed();
    }
}