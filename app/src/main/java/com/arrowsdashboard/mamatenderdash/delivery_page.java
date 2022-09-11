package com.arrowsdashboard.mamatenderdash;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class delivery_page extends AppCompatActivity {

    EditText tax_text,addition_txt ;
    Button active,delivery_btn;
    String tax , addition ;
    ProgressBar pload ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_page);

        delivery_btn = findViewById(R.id.delivery_btn);
        tax_text = findViewById(R.id.tax_text);
        addition_txt = findViewById(R.id.addition_txt);
        active = findViewById(R.id.active);
        pload = findViewById(R.id.pload);
        getdata();

        delivery_btn.setOnClickListener(v -> {
            startActivity(new Intent(delivery_page.this,AddDeliveryPage.class));
        });
        active.setOnClickListener(v -> {
            tax = tax_text.getText().toString();
            addition = addition_txt.getText().toString();
            if (TextUtils.isEmpty(tax)&&TextUtils.isEmpty(addition)){
                Toast.makeText(delivery_page.this,"انت لم تقم بأدخال اي بيانات",Toast.LENGTH_LONG).show();
            }else {
                pload.setVisibility(View.VISIBLE);
                save_data();
            }

        });


    }

    private void getdata(){

        String url = Const.HTTPS_RESTAUARANT_ARROWSCARS_COM+"/api/dashboard/accounts";
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        pload.setVisibility(View.GONE);
                        if (jsonObject.getBoolean("status")){
                            JSONObject dataob = jsonObject.getJSONObject("data");
                            tax_text.setText(dataob.getString("tax"));
                            addition_txt.setText(dataob.getString("fees_value"));
                        }else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // TODO Auto-generated method stub
                    Log.d("ERROR","error => "+error.toString());
                    pload.setVisibility(View.GONE);

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

    private void save_data() {

        String url = Const.HTTPS_RESTAUARANT_ARROWSCARS_COM+"/api/dashboard/accounts/create";
        StringRequest getRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        pload.setVisibility(View.GONE);
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getBoolean("status")){
                            Toast.makeText(delivery_page.this,"تم الحفظ بنجاح",Toast.LENGTH_LONG).show();
                            finish();
                        }else {
                            Toast.makeText(delivery_page.this,"حاول مرة اخري",Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // TODO Auto-generated method stub
                    Log.d("ERROR","error => "+error.toString());
                    pload.setVisibility(View.GONE);

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
                params.put("tax",tax);
                params.put("fees_value",addition);

                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(getRequest);
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(delivery_page.this,home_page.class));
        super.onBackPressed();
    }
}