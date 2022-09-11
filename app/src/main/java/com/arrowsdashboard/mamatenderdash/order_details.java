package com.arrowsdashboard.mamatenderdash;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.arrowsdashboard.mamatenderdash.auth.ApiInterface;
import com.arrowsdashboard.mamatenderdash.auth.Models.Request.Notification;
import com.arrowsdashboard.mamatenderdash.auth.Models.Request.Post;
import com.arrowsdashboard.mamatenderdash.auth.Models.Responce.Response;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class order_details extends AppCompatActivity {
    RecyclerView cart_recycler ;
    RecyclerView.LayoutManager layoutManager ;
    DatabaseReference cart_root ;
    String adds,content ,size,drink ;
    TextView preparing ,inway ,deiverd ;
    Boolean b1=false ,b2=false ,b3 =false ;
    TextView client_name,client_address ,client_phone,message  ;
    private static final int REQUEST_CALL =1 ;
    String order_status = "no" ;
    ApiInterface apiInterface;
    Intent intent ;
    Retrofit retrofit;
    public static final int MULTIPLE_PERMISSIONS = 10;
    String[] permissions= new String[]{
            Manifest.permission.READ_PHONE_STATE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        cart_recycler = findViewById(R.id.cart_recycler);
        preparing = findViewById(R.id.preparing);
        inway = findViewById(R.id.inway);
        deiverd = findViewById(R.id.deiverd);
        client_name = findViewById(R.id.textView10);
        client_address = findViewById(R.id.textView11);
        client_phone = findViewById(R.id.textView12);
        message = findViewById(R.id.textView21);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://fcm.googleapis.com/fcm/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        if (TextUtils.isEmpty(getIntent().getStringExtra("omessage"))){
            message.setText("لاتوجد رسالة");
        }else {
            message.setText(getIntent().getStringExtra("omessage"));
        }



        intent = new Intent(Intent.ACTION_CALL).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (prevelant.address!=null){
            client_phone.setText(prevelant.address.getAddress()+" رقم المبني: "+prevelant.address.getBuild_number()
            +" رقم الطابق: "+prevelant.address.getFloor_number()+" علامة مميزة: "+prevelant.address.getLandmark()+" المنطقة: "+prevelant.address.getUser_area().getArea() +" السعر : "+prevelant.address.getUser_area().getPrice());
        }else {
            client_phone.setText(getIntent().getStringExtra("br"));
            client_phone.setClickable(false);
            client_phone.setEnabled(false);
        }

        client_address.setText(getIntent().getStringExtra("up"));
        client_name.setText(getIntent().getStringExtra("un"));

        checkPermissions();

        client_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall(getIntent().getStringExtra("up"));
            }
        });

        client_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "https://maps.google.com/maps?q=loc:" + prevelant.address.getLat() + "," + prevelant.address.getLng() + ")";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });

        cart_root = FirebaseDatabase.getInstance().getReference("Orders").child(getResources().getString(R.string.branch_name))
                .child(getIntent().getStringExtra("oid"))
        .child("list_of_product");
        layoutManager = new LinearLayoutManager(this);
        cart_recycler.setLayoutManager(layoutManager);
        cart_recycler.setHasFixedSize(false);

        if (getIntent().getStringExtra("os").equalsIgnoreCase("تم أرسال طلبك")){
            preparing.setText("قبول الطلب");
            deiverd.setText("رفض الطلب");
            inway.setVisibility(View.GONE);

        }


        if (getIntent().getStringExtra("os").equalsIgnoreCase(preparing.getText().toString())){
            preparing.setBackgroundColor(Color.argb(255,172,172,172));
            preparing.setClickable(false);
            b1 = true ;
            order_status = "pre" ;

        }
        if (getIntent().getStringExtra("os").equalsIgnoreCase(inway.getText().toString())){
            inway.setBackgroundColor(Color.argb(255,172,172,172));
            inway.setClickable(false);
            preparing.setBackgroundColor(Color.argb(255,172,172,172));
            preparing.setClickable(false);
            b2 = true ;
            b1 = true ;
            order_status = "iw" ;
        }
        if (getIntent().getStringExtra("os").equalsIgnoreCase(deiverd.getText().toString())){
            deiverd.setBackgroundColor(Color.argb(255,172,172,172));
            deiverd.setClickable(false);
            inway.setBackgroundColor(Color.argb(255,172,172,172));
            inway.setClickable(false);
            preparing.setBackgroundColor(Color.argb(255,172,172,172));
            preparing.setClickable(false);
            b3 = true ;
            b2 = true ;
            b1 = true ;

        }

        preparing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!b1){
                    FirebaseDatabase.getInstance().getReference("User_Orders").child(getResources().getString(R.string.branch_name)).child(getIntent().getStringExtra("up"))
                            .child(getIntent().getStringExtra("oid")).child("order_status").setValue(preparing.getText().
                            toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                FirebaseDatabase.getInstance().getReference("Orders").child(getResources().getString(R.string.branch_name)).child(getIntent().getStringExtra("oid"))
                                        .child("order_status").setValue(preparing.getText().
                                        toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            if (preparing.getText().toString().equalsIgnoreCase("قبول الطلب")){
                                                send_notification(getResources().getString(R.string.restaurant_name),"تم "+preparing.getText().toString(),getIntent().getStringExtra("ut"));
                                            }else {
                                                send_notification(getResources().getString(R.string.restaurant_name),"طلبك "+preparing.getText().toString(),getIntent().getStringExtra("ut"));

                                            }
                                            preparing.setBackgroundColor(Color.argb(255,172,172,172));
                                            preparing.setClickable(false);
                                            startActivity(new Intent(order_details.this,request_activity.class));
                                        }
                                    }
                                });

                            }
                        }
                    });
                }
            }
        });
        inway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!b2){
                    FirebaseDatabase.getInstance().getReference("User_Orders").child(getResources().getString(R.string.branch_name)).child(getIntent().getStringExtra("up"))
                            .child(getIntent().getStringExtra("oid")).child("order_status").setValue(inway.getText().
                            toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                FirebaseDatabase.getInstance().getReference("Orders").child(getResources().getString(R.string.branch_name))
                                        .child(getIntent().getStringExtra("oid"))
                                        .child("order_status").setValue(inway.getText().
                                        toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            send_notification(getResources().getString(R.string.restaurant_name),"طلبك "+inway.getText().toString(),getIntent().getStringExtra("ut"));

                                            inway.setBackgroundColor(Color.argb(255,172,172,172));
                                            inway.setClickable(false);
                                            preparing.setBackgroundColor(Color.argb(255,172,172,172));
                                            preparing.setClickable(false);
                                            startActivity(new Intent(order_details.this,request_activity.class));
                                        }
                                    }
                                });

                            }
                        }
                    });
                }
            }
        });
        deiverd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!b3){
                    if (deiverd.getText().toString().equalsIgnoreCase("رفض الطلب")){
                        FirebaseDatabase.getInstance().getReference("User_Orders").child(getResources().getString(R.string.branch_name)).child(getIntent().getStringExtra("up"))
                                .child(getIntent().getStringExtra("oid")).child("order_status").setValue(deiverd.getText().
                                toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    FirebaseDatabase.getInstance().getReference("Orders").child(getResources().getString(R.string.branch_name)).child(getIntent().getStringExtra("oid"))
                                            .setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                deiverd.setBackgroundColor(Color.argb(255,172,172,172));
                                                deiverd.setClickable(false);
                                                inway.setBackgroundColor(Color.argb(255,172,172,172));
                                                inway.setClickable(false);
                                                preparing.setBackgroundColor(Color.argb(255,172,172,172));
                                                preparing.setClickable(false);
                                                startActivity(new Intent(order_details.this,request_activity.class));
                                                send_notification(getResources().getString(R.string.restaurant_name),"تم "+deiverd.getText().toString(),getIntent().getStringExtra("ut"));

                                            }
                                        }
                                    });

                                }
                            }
                        });
                    }else {
                        if (!order_status.equalsIgnoreCase("no")&&
                                !order_status.equalsIgnoreCase("pre")){

                            FirebaseDatabase.getInstance().getReference("User_Orders").child(getResources().getString(R.string.branch_name)).child(getIntent().getStringExtra("up"))
                                    .child(getIntent().getStringExtra("oid")).child("order_status").setValue(deiverd.getText().
                                    toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        FirebaseDatabase.getInstance().getReference("Orders").child(getResources().getString(R.string.branch_name)).child(getIntent().getStringExtra("oid"))
                                                .setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    Calendar calendar =Calendar.getInstance();
                                                    SimpleDateFormat currentdate =new SimpleDateFormat("M dd yyyy");
                                                    String savecurrentdate=currentdate.format(calendar.getTime());

                                                    savecurrentdate = savecurrentdate.replaceAll( "1","١").replaceAll("2", "٢")
                                                            .replaceAll("3", "٣").replaceAll("4", "٤")
                                                            .replaceAll("5", "٥").replaceAll("6", "٦")
                                                            .replaceAll("7", "٧").replaceAll("8", "٨")
                                                            .replaceAll("9", "٩").replaceAll("0", "٠");

                                                    final HashMap<String,Object> hashs =  new HashMap<>();
                                                    hashs.put("total_price",getIntent().getStringExtra("op"));
                                                    hashs.put("orderid",getIntent().getStringExtra("oid"));
                                                    hashs.put("list_of_product",prevelant.list_of_p);

                                                    FirebaseDatabase.getInstance().getReference("Finished_orders").child(savecurrentdate)
                                                            .child(getIntent().getStringExtra("oid")).setValue(hashs).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()){

                                                                deiverd.setBackgroundColor(Color.argb(255,172,172,172));
                                                                deiverd.setClickable(false);
                                                                inway.setBackgroundColor(Color.argb(255,172,172,172));
                                                                inway.setClickable(false);
                                                                preparing.setBackgroundColor(Color.argb(255,172,172,172));
                                                                preparing.setClickable(false);
                                                                startActivity(new Intent(order_details.this,request_activity.class));
                                                                send_notification(getResources().getString(R.string.restaurant_name),deiverd.getText().toString(),getIntent().getStringExtra("ut"));

                                                            }
                                                        }
                                                    });


                                                }
                                            }
                                        });

                                    }
                                }
                            });
                        }else {
                            AlertDialog alertDialog = new AlertDialog.Builder(order_details.this).create();
                            alertDialog.setMessage("من فضلك أضغط علي جاري التحضير و في الطريق اولا");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }
                    }

                }
            }
        });
    }

    private void send_notification(String name, String message,String user_token) {
        apiInterface = retrofit.create(ApiInterface.class);
        Notification notification = new Notification(name,message);
        Post post = new Post(notification , user_token);
        //save the message to firebase
        //make the api call that lunch the notification
        Call<Response> sendNotify = apiInterface.sendNotification(post);
        sendNotify.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                System.out.println(response);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<product> options=new FirebaseRecyclerOptions.Builder<product>()
                .setQuery(cart_root,product.class).build();
        FirebaseRecyclerAdapter<product, viewholder> adapter=new FirebaseRecyclerAdapter<product, viewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final viewholder holder, int position, @NonNull final product model) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(
                        order_details.this,
                        LinearLayoutManager.VERTICAL,
                        false
                );


                Picasso.get().load(model.getImage()).into(holder.cart_img);
                holder.cart_name.setText(model.getName());
                holder.number.setText(model.getNumber());



                Double item_price = 0.0;
                String englishNumerals2 = new BigDecimal(model.getPrice()).toString();
                item_price = Double.parseDouble(englishNumerals2)*Integer.parseInt(model.getNumber());
                adds = "";
                if (model.getAddsList() != null){

                    for (int k = 0 ; k < model.getAddsList().size() ;k++ ){
                        if (model.getAddsList().get(k).isIs_need()){
                            String englishNumerals = new BigDecimal(model.getAddsList().get(k).getPrice()).toString();
                            item_price +=  (Double.parseDouble(englishNumerals)*Double.parseDouble(model.getNumber()));
                            adds += " "+model.getAddsList().get(k).getAdds();
                            holder.cart_adds.setText( " الأضافات : "+model.getNumber()+" x "+adds);
                        }
                    }

                }

                content = "";
                if (model.getContent()!=null){
                    for (int k = 0 ; k < model.getContent().size() ;k++ ){
                        if (model.getContent().get(k).getNeed()){
                            content += " "+model.getContent().get(k).getName();
                            holder.cart_content.setText(content);
                        }
                    }
                }



                drink = "";
                if (model.getList_of_drinks()!=null){
                    for (int k = 0 ; k < model.getList_of_drinks().size() ;k++ ){
                        if (model.getList_of_drinks().get(k).isIs_need()){
                            drink += " "+model.getList_of_drinks().get(k).getName();
                            holder.additional_cart.setText(drink);
                        }
                    }
                }



                size = "";
                if (model.getSizes()!=null){
                    for (int k = 0 ; k < model.getSizes().size() ;k++ ){
                        if (model.getSizes().get(k).getIs_need()){
                            size += " "+model.getSizes().get(k).getSize();
                            holder.cart_size.setText(size);
                        }
                    }
                }


                holder.cart_price.setText(new DecimalFormat("#########.##").format(Double.valueOf(String.valueOf(Integer.parseInt(model.getNumber())*Double.parseDouble(model.getTotal_price())))).replace(',','.')
                        .replace('٫','.'));


            }
            @NonNull
            @Override
            public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
                viewholder holder = new viewholder(view);
                return holder ;
            }
        };
        cart_recycler.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissionsList, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissionsList, grantResults);
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0) {
                    String permissionsDenied = "";
                    for (String per : permissionsList) {
                        if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                            permissionsDenied += "\n" + per;
                        }
                    }
                    // Show permissionsDenied
                }
            }

        }
    }
    private  boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(order_details.this,p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()])
                    ,MULTIPLE_PERMISSIONS );
            return false;
        }
        return true;
    }

    public void makePhoneCall(String number) {

        if (ContextCompat.checkSelfPermission(order_details.this,
                android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(order_details.this,
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {

            intent.setData(Uri.parse("tel:" + number));
            intent.putExtra("com.android.phone.force.slot", true);
            intent.putExtra("Cdma_Supp", true);
            startActivity(intent);
        }
    }
}