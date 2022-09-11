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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login_page extends AppCompatActivity {

    EditText username ,password;
    Button loginbtn ;
    ProgressBar pload ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginbtn = findViewById(R.id.loginbtn);
        pload = findViewById(R.id.pload);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(username.getText().toString())){
                    username.setError("");
                }else if (TextUtils.isEmpty(password.getText().toString())){
                    password.setError("");
                }else {
                    pload.setVisibility(View.VISIBLE);
                    login();
                }
            }
        });

    }

    private void login() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Const.HTTPS_RESTAUARANT_ARROWSCARS_COM + "/api/auth/login";
        StringRequest getRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            pload.setVisibility(View.INVISIBLE);
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("status")){
                                prevelant.token = jsonObject.getJSONObject("data").getString("access_token");
                                FirebaseMessaging.getInstance().getToken()
                                        .addOnCompleteListener(new OnCompleteListener<String>() {
                                            @Override
                                            public void onComplete(@NonNull Task<String> task) {
                                                if (task.isSuccessful()) {
                                                    String deviceToken = task.getResult();
                                                    FirebaseDatabase.getInstance().getReference("Dashboard_Token")
                                                            .child(getResources().getString(R.string.branch_name))
                                                            .child("Token").setValue(deviceToken).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()){
                                                                startActivity(new Intent(login_page.this,home_page.class));
                                                                finish();
                                                            }
                                                        }
                                                    });

                                                }

                                                // Get new FCM registration token



                                            }
                                        });



                            }else {
                                Toast.makeText(login_page.this
                                        , "من فضلك تأكد من المعلومات و اعد المحاولة", Toast.LENGTH_LONG).show();
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
                        pload.setVisibility(View.INVISIBLE);
                        Log.d("ERROR","error => "+error.toString());
                        Toast.makeText(login_page.this,"Connection Error",Toast.LENGTH_LONG).show();


                    }
                }
        )
        {

            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("username",username.getText().toString());
                params.put("password",password.getText().toString());
                return params;
            }

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