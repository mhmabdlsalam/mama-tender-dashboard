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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class add_product extends AppCompatActivity {
    public static ArrayAdapter<String> category_adapter;
    private final String upload_URL = Const.HTTPS_RESTAUARANT_ARROWSCARS_COM + "/api/dashboard/upload/image";
    CardView switcher;
    Switch sw_available;
    ImageView addimage;
    EditText name, sizetext, sizeprice, comptxt, extratxt, extraprice;
    Button addbtn, addbtn2, addbtn3, confirm_b;
    RecyclerView sizeRecycler, componantRecycler, extrarecycler;
    List<size> sizeList;
    List<adds> addsList;
    List<String> comlist;
    SubItemAdapter adapter;
    List<String> catlist;
    List<cat> catlist2;
    String url_img, drink_;
    RecyclerView.LayoutManager manager;
    RecyclerView.LayoutManager manager1;
    RecyclerView.LayoutManager manager2;
    Spinner categorySpinner;
    List<String> cattlist;
    ProgressBar pload;
    String product_id;
    private Uri uri;
    private RequestQueue rQueue;
    int availability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        switcher = findViewById(R.id.switcher);
        sw_available = findViewById(R.id.switch1);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            product_id = extras.getString("id");
            Log.println(Log.INFO,"hereeee",product_id);
        }
        switcher.setVisibility(View.GONE);

        addimage = findViewById(R.id.addimage);

        name = findViewById(R.id.name);
        sizetext = findViewById(R.id.sizetext);
        sizeprice = findViewById(R.id.sizeprice);
        comptxt = findViewById(R.id.comptxt);
        extratxt = findViewById(R.id.extratxt);
        extraprice = findViewById(R.id.extraprice);
        addbtn = findViewById(R.id.addbtn);
        addbtn2 = findViewById(R.id.addbtn2);
        addbtn3 = findViewById(R.id.addbtn3);
        confirm_b = findViewById(R.id.confirm_b);
        categorySpinner = findViewById(R.id.categorySpinner);
        sizeRecycler = findViewById(R.id.sizeRecycler);
        componantRecycler = findViewById(R.id.componantRecycler);
        extrarecycler = findViewById(R.id.extrarecycler);
        pload = findViewById(R.id.pload);
        catlist2 = new ArrayList<>();


        getcategories();
        confirm_b.setOnClickListener(v -> checkdata());
        sw_available.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (availability==0) {
                    availability=1;
                    setAvailability(product_id, availability);
                }
            }else{
                if (availability==1) {
                    availability = 0;
                    setAvailability(product_id, availability);
                }


            }
        });

        addimage.setOnClickListener(v -> CropImage.activity(uri)
                .setAspectRatio(1, 1)
                .start(add_product.this));

        sizeList = new ArrayList<>();
        addsList = new ArrayList<>();
        comlist = new ArrayList<>();
        manager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        manager1 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        manager2 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        sizeRecycler.setHasFixedSize(true);
        sizeRecycler.setLayoutManager(manager);
        extrarecycler.setLayoutManager(manager1);
        componantRecycler.setLayoutManager(manager2);
        extrarecycler.setHasFixedSize(true);
        componantRecycler.setHasFixedSize(true);


        addbtn.setOnClickListener(v -> {
            if (TextUtils.isEmpty(sizetext.getText().toString())) {
                sizetext.setError("");
            } else if (TextUtils.isEmpty(sizeprice.getText().toString())) {
                sizeprice.setError("");
            } else if (alredy_exsist_size(sizetext.getText().toString())) {
                Toast.makeText(add_product.this, "تم اَضافة من قبل", Toast.LENGTH_SHORT).show();
            } else {
                size sizeitem = new size(sizetext.getText().toString(), sizeprice.getText().toString());
                sizeList.add(sizeitem);
                adapter = new SubItemAdapter(add_product.this, sizeList);
                sizeRecycler.setAdapter(adapter);
                sizetext.setText("");
                sizeprice.setText("");
            }
        });

        addbtn3.setOnClickListener(v -> {
            if (TextUtils.isEmpty(extratxt.getText().toString())) {
                extratxt.setError("");
            } else if (TextUtils.isEmpty(extraprice.getText().toString())) {
                extraprice.setError("");
            } else if (alredy_exsist(extratxt.getText().toString())) {
                Toast.makeText(add_product.this, "تم اَضافة من قبل", Toast.LENGTH_SHORT).show();
            } else {
                adds addsitem = new adds(extratxt.getText().toString(), extraprice.getText().toString());
                addsList.add(addsitem);
                adapter = new SubItemAdapter(add_product.this, addsList, 3);
                extrarecycler.setAdapter(adapter);
                extratxt.setText("");
                extraprice.setText("");
            }
        });
        addbtn2.setOnClickListener(v -> {
            if (TextUtils.isEmpty(comptxt.getText().toString())) {
                comptxt.setError("");
            } else if (comlist.contains(comptxt.getText().toString().toLowerCase(Locale.ROOT))) {
                Toast.makeText(add_product.this, "تم اَضافة من قبل", Toast.LENGTH_SHORT).show();
            } else {
                comlist.add(comptxt.getText().toString().toLowerCase(Locale.ROOT));
                adapter = new SubItemAdapter(add_product.this, comlist, "3");
                componantRecycler.setAdapter(adapter);
                comptxt.setText("");
            }
        });


    }

    private void setAvailability(String id, int availability) {
        pload.setVisibility(View.VISIBLE);
        String url = Const.HTTPS_RESTAUARANT_ARROWSCARS_COM + "/api/dashboard/product/availability/update/" + id;
        url = url + "?availability=" + availability;
        StringRequest getRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    // response
                    try {

                        pload.setVisibility(View.GONE);
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getBoolean("status")) {
                            Toast.makeText(add_product.this, "done", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Log.println(Log.ERROR,"hhhhereeeee", e.getMessage());
                        e.printStackTrace();
                    }
                },
                error -> {
                    // TODO Auto-generated method stub
                    Log.println(Log.ERROR,"ERROR", "error => " + error.toString());

                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + prevelant.token);
                params.put("resturant", getResources().getString(R.string.restaurant_name_));
                return params;
            }


        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(getRequest);
    }

    private void getData(String id) {
        String url = Const.HTTPS_RESTAUARANT_ARROWSCARS_COM + "/api/dashboard/product/" + id;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray components = jsonObject.getJSONArray("components");
                    JSONArray sizes = jsonObject.getJSONArray("sizes");
                    JSONArray additional = jsonObject.getJSONArray("additional");
                    for (int i = 0; i < components.length(); i++) {
                        comlist.add(components.getString(i));
                    }
                    for (int i = 0; i < sizes.length(); i++) {
                        JSONObject size_object = sizes.getJSONObject(i);
                        size size = new size(size_object.getString("size"), size_object.getString("price"));
                        sizeList.add(size);
                    }
                    for (int i = 0; i < additional.length(); i++) {
                        JSONObject additional_object = additional.getJSONObject(i);
                        adds adds = new adds(additional_object.getString("addition"), additional_object.getString("price"));
                        addsList.add(adds);
                    }
                    JSONObject category_object = jsonObject.getJSONObject("category");
                    String category_name = category_object.getString("name");
                    String name = jsonObject.getString("name");
                    url_img = jsonObject.getString("photo");
                    availability = jsonObject.getInt("availability");
                    Log.println(Log.ERROR,"hhhhereeeee",jsonObject.getString("availability") );

                    showData(name, url_img, category_name, comlist, sizeList, addsList, availability);

                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("resturant", getResources().getString(R.string.restaurant_name_));
                return params;
            }


        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

    }

    private void showData(String name, String url_img, String category_name, List<String> comlist, List<size> sizeList, List<adds> addsList, int availability) {

        this.name.setText(name);
        Picasso.get().load(url_img).into(addimage);

        categorySpinner.setSelection(catlist.indexOf(category_name));
        adapter = new SubItemAdapter(add_product.this, sizeList);
        sizeRecycler.setAdapter(adapter);
        adapter = new SubItemAdapter(add_product.this, addsList, 3);
        extrarecycler.setAdapter(adapter);
        adapter = new SubItemAdapter(add_product.this, comlist, "3");
        componantRecycler.setAdapter(adapter);
        sw_available.setChecked(availability == 1);
    }

    private boolean alredy_exsist_size(String toString) {
        for (int i = 0; i < sizeList.size(); i++) {
            if (sizeList.get(i).getSize().equalsIgnoreCase(toString)) {
                return true;
            }
        }
        return false;
    }

    private boolean alredy_exsist(String s) {

        for (int i = 0; i < addsList.size(); i++) {
            if (addsList.get(i).getAdds().equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }

    private void checkdata() {
        if (TextUtils.isEmpty(url_img)) {
            Toast.makeText(this, "من فضلك قم بتحديد الصورة", Toast.LENGTH_SHORT).show();
        } else if (categorySpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "من فضلك قم بتحديد الفئة", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(name.getText().toString())) {
            Toast.makeText(this, "من فضلك أكتب اسم الوجبة", Toast.LENGTH_SHORT).show();
        } else if (sizeList.size() == 0) {
            Toast.makeText(this, "من فضلك قم بتحديد أحجام الوجبة", Toast.LENGTH_SHORT).show();
        } else if (comlist.size() == 0) {
            Toast.makeText(this, "من فضلك قم بأضافة مكونات الوجبة", Toast.LENGTH_SHORT).show();
        } else {
            if (TextUtils.isEmpty(product_id)) {
                pload.setVisibility(View.VISIBLE);
                save_product();
            } else {
                pload.setVisibility(View.VISIBLE);
                update_product();
            }

        }
    }

    private void update_product() {
        String url = Const.HTTPS_RESTAUARANT_ARROWSCARS_COM + "/api/dashboard/products/update/" + product_id;
        url = url + "?name=" + name.getText().toString();
        url = url + "&category_id=" + catlist2.get((categorySpinner.getSelectedItemPosition() - 1)).getId();

        url = url + "&photo=" + url_img;

        for (int i = 0; i < comlist.size(); i++) {
            url = url + "&components[" + i + "]=" + comlist.get(i);
        }
        for (int i = 0; i < sizeList.size(); i++) {
            url = url + "&sizes[" + i + "][size]=" + sizeList.get(i).getSize();
            url = url + "&sizes[" + i + "][price]=" + sizeList.get(i).getPrice();
        }
        for (int i = 0; i < addsList.size(); i++) {
            url = url + "&additional[" + i + "][addition]=" + addsList.get(i).getAdds();
            url = url + "&additional[" + i + "][price]=" + addsList.get(i).getPrice();
        }

        StringRequest getRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    // response
                    try {
                        pload.setVisibility(View.GONE);
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getBoolean("status")) {
                            Toast.makeText(add_product.this, "done", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(add_product.this, cat_page.class));
                        }
                    } catch (JSONException e) {
                        Log.d("hhhhereeeee", e.getMessage());
                        e.printStackTrace();
                    }
                },
                error -> {
                    // TODO Auto-generated method stub
                    Log.d("ERROR", "error => " + error.toString());

                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + prevelant.token);
                params.put("resturant", getResources().getString(R.string.restaurant_name_));
                return params;
            }


        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(getRequest);
    }

    private void save_product() {
        String url = Const.HTTPS_RESTAUARANT_ARROWSCARS_COM + "/api/dashboard/products/create";
        url = url + "?name=" + name.getText().toString();
        url = url + "&category_id=" + catlist2.get((categorySpinner.getSelectedItemPosition() - 1)).getId();
        url = url + "&photo=" + url_img;
        for (int i = 0; i < comlist.size(); i++) {
            url = url + "&components[" + i + "]=" + comlist.get(i);
        }
        for (int i = 0; i < sizeList.size(); i++) {
            url = url + "&sizes[" + i + "][size]=" + sizeList.get(i).getSize();
            url = url + "&sizes[" + i + "][price]=" + sizeList.get(i).getPrice();
        }
        for (int i = 0; i < addsList.size(); i++) {
            url = url + "&additional[" + i + "][addition]=" + addsList.get(i).getAdds();
            url = url + "&additional[" + i + "][price]=" + addsList.get(i).getPrice();
        }

        StringRequest getRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    // response
                    try {
                        pload.setVisibility(View.GONE);
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getBoolean("status")) {
                            Toast.makeText(add_product.this, "done", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(add_product.this, cat_page.class));
                        }
                    } catch (JSONException e) {
                        Log.d("hhhhereeeee", e.getMessage());
                        e.printStackTrace();
                    }
                },
                error -> {
                    // TODO Auto-generated method stub
                    Log.d("ERROR", "error => " + error.toString());

                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + prevelant.token);
                params.put("resturant", getResources().getString(R.string.restaurant_name_));
                return params;
            }


        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(getRequest);
    }

    private void getcategories() {
        String url1 = Const.HTTPS_RESTAUARANT_ARROWSCARS_COM + "/api/dashboard/categories";
        StringRequest getRequest = new StringRequest(Request.Method.GET, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try {
                            catlist = new ArrayList<>();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("data");
                            catlist.add("أختار الفئة");
                            pload.setVisibility(View.GONE);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o1 = array.getJSONObject(i);
                                catlist.add(o1.getString("name"));
                                cat cat = new cat(o1.getString("name"), o1.getInt("id"));

                                catlist2.add(cat);

                            }
                            if (!TextUtils.isEmpty(product_id)) {
                                getData(product_id);
                                switcher.setVisibility(View.VISIBLE);
                            }
                            if (catlist.size() == 0) {
                                startActivity(new Intent(add_product.this, add_category.class));
                            } else {
                                category_adapter = new ArrayAdapter<String>(add_product.this
                                        , R.layout.spiner_text, catlist);
                                categorySpinner.setAdapter(category_adapter);
                            }

                        } catch (JSONException e) {
                            Log.d("hhhhereeeee", e.getMessage());
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR", "error => " + error.toString());

                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + prevelant.token);
                params.put("resturant", getResources().getString(R.string.restaurant_name_));
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(getRequest);
    }

    private void uploadImage(final Bitmap bitmap) {

            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, upload_URL,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {
                            Log.d("ressssssoo", new String(response.data));

                            rQueue.getCache().clear();
                            try {
                                JSONObject jsonObject = new JSONObject(new String(response.data));
                                if (jsonObject.getBoolean("status")) {

                                    Picasso.get().load(jsonObject.getString("data")).into(addimage);
                                    url_img = jsonObject.getString("data");
                                    //   user.setAvatar(url);
                                    //   PreferenceManager.getInstance(add_banner.this).saveObject("object",user);
                                    //   requestWithSomehttpsHeaders();
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
            rQueue = Volley.newRequestQueue(add_product.this);

            rQueue.add(volleyMultipartRequest);
    }

    public byte[] getFileDataFromDrawable(@NonNull Bitmap bitmap) {
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

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            if (data != null) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                uri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    // imageView.setImageBitmap(bitmap);
                    uploadImage(getResizedBitmap(bitmap, 1024, 1024));

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(add_product.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public Bitmap getResizedBitmap(@NonNull Bitmap bm, int newWidth, int newHeight) {
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
}