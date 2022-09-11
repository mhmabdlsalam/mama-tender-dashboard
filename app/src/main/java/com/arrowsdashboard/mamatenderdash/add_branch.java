package com.arrowsdashboard.mamatenderdash;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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

public class add_branch extends AppCompatActivity {
    ImageView add_branch ;
    String address ,lat ,lng,name ;
    TextView addresstxt ;
    Button  conbtn ;
    
    ProgressBar pload ;
    EditText address_anme ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_branch);

        add_branch = findViewById(R.id.add_branch);
        addresstxt = findViewById(R.id.addresstxt);
        conbtn = findViewById(R.id.conbtn);
        pload = findViewById(R.id.pload);
        address_anme = findViewById(R.id.address_anme);

        conbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pload.setVisibility(View.VISIBLE);
                name = address_anme.getText().toString();
                if (TextUtils.isEmpty(address)){
                    Toast.makeText(add_branch.this, "من فضلك قم بتحديد الموقع", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(address_anme.getText().toString())){
                    address_anme.setError("");
                }
                else {
                    saveaddress();
                }

            }
        });

        add_branch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(add_branch.this,map_activity.class),1000);
            }
        });

    }

    private void saveaddress() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Const.HTTPS_RESTAUARANT_ARROWSCARS_COM + "/api/dashboard/branches/create";
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
                                startActivity(new Intent(add_branch.this,branches.class));
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
                params.put("name",name);
                params.put("address",address);
                params.put("lat",lat);
                params.put("lng",lng);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(getRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1000 && data !=null){
            address = data.getStringExtra("editTextValue");
            lat = data.getStringExtra("lat");
            lng = data.getStringExtra("lng");
            addresstxt.setText(address);
        }
    }
}