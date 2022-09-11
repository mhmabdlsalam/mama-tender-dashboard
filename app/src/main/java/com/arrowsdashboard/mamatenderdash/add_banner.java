package com.arrowsdashboard.mamatenderdash;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class add_banner extends AppCompatActivity {
    ProgressBar pload ;
    
    ImageView add_banner ;
    Button active ;
    private Uri uri ;
    String url ;
    private final String upload_URL = Const.HTTPS_RESTAUARANT_ARROWSCARS_COM + "/api/dashboard/upload/image";
    private RequestQueue rQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_banner);
        add_banner = findViewById(R.id.add_banner);
        active = findViewById(R.id.active);
        pload = findViewById(R.id.pload);


        add_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengallary();
            }
        });
        active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(url)){
                    Toast.makeText(add_banner.this,"من فضلك قم بتحديد الصورة",Toast.LENGTH_LONG).show();
                }else {
                    pload.setVisibility(View.VISIBLE);
                    savebanner();
                }
            }
        });

    }

    private void savebanner() {

            RequestQueue queue = Volley.newRequestQueue(this);
            String url1 = Const.HTTPS_RESTAUARANT_ARROWSCARS_COM + "/api/dashboard/ads/create";
            StringRequest getRequest = new StringRequest(Request.Method.POST, url1,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            // response
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                pload.setVisibility(View.GONE);
                                if (jsonObject.getBoolean("status")){
                                    Toast.makeText(add_banner.this,"تم الحفظ بنجاح",Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(add_banner.this,banners.class));
                                }else {
                                    Toast.makeText(add_banner.this,"حال مرة اخري",Toast.LENGTH_LONG).show();
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
                    params.put("ads",url);
                    return params;
                }
            };
            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(getRequest);
    }

    private void opengallary() {
        CropImage.activity(uri)
                .setAspectRatio(1,1)
                .start(add_banner.this);
    }

    private void uploadImage(final Bitmap bitmap){

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, upload_URL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        Log.d("ressssssoo",new String(response.data));

                        rQueue.getCache().clear();
                        try {
                            pload.setVisibility(View.GONE);
                            JSONObject jsonObject = new JSONObject(new String(response.data));
                            if (jsonObject.getBoolean("status")) {

                                Picasso.get().load(jsonObject.getString("data")).into(add_banner);
                                url = jsonObject.getString("data") ;
                            //   user.setAvatar(url);
                             //   PreferenceManager.getInstance(add_banner.this).saveObject("object",user);
                             //   requestWithSomeHttpHeaders();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                // params.put("tags", "ccccc");  add string parameters
                return params;
            }

            /*
             *pass files using below method
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("photo", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + prevelant.token);
                params.put("resturant", getResources().getString(R.string.restaurant_name_));
                return params;
            }
        };

        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rQueue = Volley.newRequestQueue(add_banner.this);
        rQueue.add(volleyMultipartRequest);
    }
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE&&resultCode==RESULT_OK&&data!=null) {
            if (data != null) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                uri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    // imageView.setImageBitmap(bitmap);
                    pload.setVisibility(View.VISIBLE);
                    uploadImage(getResizedBitmap(bitmap,1024,1024));

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(add_banner.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}