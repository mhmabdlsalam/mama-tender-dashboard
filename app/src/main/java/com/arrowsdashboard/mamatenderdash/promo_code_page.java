package com.arrowsdashboard.mamatenderdash;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

public class promo_code_page extends AppCompatActivity {

    ImageView add_img ;
    List<String> desc_type_list  ;
    ArrayAdapter<String> desc_adapter ;
    String promo , type , discount ;
    CheckBox only_one_use ;
    RecyclerView promo_codes_recycler ;
    List<promocode> listOfpromo ;
    ProgressBar pload1 ;

    ProgressBar pload ;
    RecyclerView.LayoutManager manager ;
    SubItemAdapter adapter ;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_code_page);
        add_img  = findViewById(R.id.add_img);
        pload1  = findViewById(R.id.pload);
        promo_codes_recycler  = findViewById(R.id.promo_codes_recycler);
        desc_type_list = new ArrayList<>();
        promo_codes_recycler.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        promo_codes_recycler.setLayoutManager(manager);

        desc_type_list.add("حدد نوع الخصم");
        desc_type_list.add("نسبة");
        desc_type_list.add("مبلغ");

        getallpromo();

        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertadd22 = new AlertDialog.Builder(promo_code_page.this);
                final AlertDialog alert22 = alertadd22.create();
                LayoutInflater factory = LayoutInflater.from(promo_code_page.this);
                alert22.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                 view = factory.inflate(R.layout.add_promo_code, null);
                alert22.setView(view);
                alert22.show();
                final Spinner type_disc =  view.findViewById(R.id.type_disc);
                Button add_promo = view.findViewById(R.id.button);
                only_one_use = view.findViewById(R.id.only_one_use);
                EditText disc = view.findViewById(R.id.disc);
                EditText promocode = view.findViewById(R.id.promocode);
                pload = view.findViewById(R.id.pload);
                add_promo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        promo = promocode.getText().toString();
                        discount = disc.getText().toString();
                        type = type_disc.getSelectedItem().toString();
                        if (TextUtils.isEmpty(promocode.getText().toString())){
                            promocode.setError("");
                        }else if (TextUtils.isEmpty(disc.getText().toString())){
                            disc.setError("");
                        }else if (type_disc.getSelectedItemPosition()==0){
                            Toast.makeText(promo_code_page.this, "حدد نوع الخصم", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            pload.setVisibility(View.VISIBLE);
                            add_promoCode();
                        }
                    }
                });

                desc_adapter =new ArrayAdapter<String>(promo_code_page.this
                        ,R.layout.spiner_text,desc_type_list);
                type_disc.setAdapter(desc_adapter);

            }
        });
    }

    private void getallpromo() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Const.HTTPS_RESTAUARANT_ARROWSCARS_COM + "/api/dashboard/coupons";
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            pload1.setVisibility(View.GONE);
                            if (jsonObject.getBoolean("status")){
                                listOfpromo = new ArrayList<>();
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0 ; i<jsonArray.length() ; i++){
                                    JSONObject o1 = jsonArray.getJSONObject(i);
                                    promocode banner = new promocode(o1.getString("coupon_code")
                                            ,o1.getString("coupon_value")
                                            ,o1.getString("coupon_type")
                                            ,o1.getInt("id"));
                                    listOfpromo.add(banner);
                                }

                                adapter = new SubItemAdapter(promo_code_page.this,listOfpromo,1,1,1,1,1,1);
                                promo_codes_recycler.setAdapter(adapter);

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
                        Toast.makeText(promo_code_page.this,"sssss",Toast.LENGTH_LONG).show();

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

    private void add_promoCode() {

        String url = Const.HTTPS_RESTAUARANT_ARROWSCARS_COM + "/api/dashboard/coupons/create";
        if (only_one_use.isChecked()){
            url = Const.HTTPS_RESTAUARANT_ARROWSCARS_COM + "/api/dashboard/coupons/create?unlimited="+1;
        }

        StringRequest getRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try {
                            pload.setVisibility(View.GONE);
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("status")){
                                startActivity(new Intent(promo_code_page.this,promo_code_page.class));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR","error => "+error.toString());

                    }
                }
        )
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + prevelant.token);
                params.put("resturant", getResources().getString(R.string.restaurant_name_));
                return params;
            }

            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                params.put("coupon_code",promo);
                params.put("coupon_value",discount);
                params.put("coupon_type",type);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(getRequest);

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(promo_code_page.this,home_page.class));
        super.onBackPressed();
    }
}