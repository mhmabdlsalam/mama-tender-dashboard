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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class social_phones extends AppCompatActivity {


    Button active ;
    ProgressBar  pload ;
    EditText phone1,phone2,phone3,instagramm,twitterr,facebookk,snapchatt,website ;
    String phone1s ="",phone2s ="",phone3s ="",instagrams ="",twitters ="",facebooks ="",snapchats ="",websites ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_phones);

        active = findViewById(R.id.active);
        phone1 = findViewById(R.id.phone1);
        phone2 = findViewById(R.id.phone2);
        phone3 = findViewById(R.id.phone3);
        instagramm = findViewById(R.id.instagramm);
        facebookk = findViewById(R.id.facebookk);
        twitterr = findViewById(R.id.twitterr);
        snapchatt = findViewById(R.id.snapchatt);
        website = findViewById(R.id.website);
        pload = findViewById(R.id.pload);
        getdata();

        active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chekdata();
            }
        });

    }

    private void chekdata() {
        phone1s = phone1.getText().toString();
        phone2s = phone2.getText().toString();
        phone3s = phone3.getText().toString();
        instagrams = instagramm.getText().toString();
        facebooks = facebookk.getText().toString();
        twitters = twitterr.getText().toString();
        websites = website.getText().toString();
        snapchats = snapchatt.getText().toString();

        if (TextUtils.isEmpty(phone1.getText().toString())&&TextUtils.isEmpty(phone2.getText().toString())
        &&TextUtils.isEmpty(phone3.getText().toString())&&TextUtils.isEmpty(instagramm.getText().toString())
        &&TextUtils.isEmpty(facebookk.getText().toString())&&TextUtils.isEmpty(twitterr.getText().toString())
        &&TextUtils.isEmpty(snapchatt.getText().toString())&&TextUtils.isEmpty(website.getText().toString())){
            Toast.makeText(social_phones.this,"انت لم تقم بأدخال اي بيانات",Toast.LENGTH_LONG).show();
        }else {
            pload.setVisibility(View.VISIBLE);
            savedata();
        }
    }

    private void getdata(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Const.HTTPS_RESTAUARANT_ARROWSCARS_COM + "/api/dashboard/socials";
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            pload.setVisibility(View.GONE);
                            if (jsonObject.getBoolean("status")){
                                JSONObject dataob = jsonObject.getJSONObject("data");
                                phone1.setText(dataob.getString("phone_1"));
                                phone2.setText(dataob.getString("phone_2"));
                                phone3.setText(dataob.getString("phone_3"));
                                facebookk.setText(dataob.getString("facebook"));
                                twitterr.setText(dataob.getString("twitter"));
                                instagramm.setText(dataob.getString("instagram"));
                                snapchatt.setText(dataob.getString("snapchat"));
                                website.setText(dataob.getString("website"));
                            }else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            pload.setVisibility(View.GONE);
                        }
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

    private void savedata() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Const.HTTPS_RESTAUARANT_ARROWSCARS_COM + "/api/dashboard/socials/create";
        StringRequest getRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            pload.setVisibility(View.INVISIBLE);
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("status")){
                                startActivity(new Intent(social_phones.this,home_page.class));
                            }else {
                                Toast.makeText(social_phones.this, "حاول مرة اخري", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // response
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR","error => "+error.toString());
                        pload.setVisibility(View.INVISIBLE);
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
                params.put("phone_1",phone1s);
                params.put("phone_2",phone2s);
                params.put("phone_3",phone3s);
                params.put("facebook",facebooks);
                params.put("twitter",twitters);
                params.put("snapchat",snapchats);
                params.put("instagram",instagrams);
                params.put("website",websites);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(getRequest);
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(social_phones.this,home_page.class));
        super.onBackPressed();
    }
}