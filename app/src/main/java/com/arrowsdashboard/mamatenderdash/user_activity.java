package com.arrowsdashboard.mamatenderdash;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class user_activity extends AppCompatActivity {
    RecyclerView recycler_view ;
    DatabaseReference user_ref ;
    Button send_notification_btn ;
    RecyclerView.LayoutManager layoutManager ;
    ApiInterface apiInterface;
    List<String> device_token_list ;
    Intent intent ;
    Retrofit retrofit;
    public static final int MULTIPLE_PERMISSIONS = 10;
    String[] permissions= new String[]{
            Manifest.permission.READ_PHONE_STATE
    };
    private static final int REQUEST_CALL =1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        recycler_view = findViewById(R.id.recycler_view);
        send_notification_btn = findViewById(R.id.send_notification_btn);

        intent = new Intent(Intent.ACTION_CALL).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);



        retrofit = new Retrofit.Builder()
                .baseUrl("https://fcm.googleapis.com/fcm/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        user_ref = FirebaseDatabase.getInstance().getReference("Users");
        layoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(layoutManager);
        recycler_view.setHasFixedSize(false);

        send_notification_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertadd22 = new AlertDialog.Builder(user_activity.this);
                final AlertDialog alert22 = alertadd22.create();
                LayoutInflater factory = LayoutInflater.from(user_activity.this);
                alert22.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                final View view = factory.inflate(R.layout.notification_layoout, null);
                alert22.setView(view);
                alert22.show();
                final Button send =  view.findViewById(R.id.send_notification_b);
                final EditText notification_text =  view.findViewById(R.id.notification_text);
                final Button cancel =  view.findViewById(R.id.cancel_notification_b);
                final ProgressBar pload =  view.findViewById(R.id.pload);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert22.cancel();
                    }
                });

                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pload.setVisibility(View.VISIBLE);
                        for (int i = 0 ; i<device_token_list.size() ; i++){
                            send_notification(getResources().getString(R.string.restaurant_name)
                                    ,notification_text.getText().toString()
                                    ,device_token_list.get(i));
                        }
                        FirebaseDatabase.getInstance().getReference("messages").push().setValue(notification_text.getText().toString());

                        pload.setVisibility(View.GONE);
                        alert22.cancel();
                        Toast.makeText(user_activity.this, "تم الأرسال بنجاح", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
        device_token_list = new ArrayList<>();

        FirebaseRecyclerOptions<user> options=new FirebaseRecyclerOptions.Builder<user>()
                .setQuery(user_ref,user.class).build();
        FirebaseRecyclerAdapter<user, viewholder> adapter=new FirebaseRecyclerAdapter<user, viewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final viewholder holder, int position, @NonNull final user model) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(
                        user_activity.this,
                        LinearLayoutManager.VERTICAL,
                        false
                );

                device_token_list.add(position,model.getDevice_token());
                Log.d("list_size",""+device_token_list.size());
                holder.user_name.setText(model.getName());
                holder.user_phone.setText(model.getPhone());

                holder.specific_notification.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder alertadd22 = new AlertDialog.Builder(user_activity.this);
                        final AlertDialog alert22 = alertadd22.create();
                        LayoutInflater factory = LayoutInflater.from(user_activity.this);
                        alert22.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        final View view = factory.inflate(R.layout.notification_layoout, null);
                        alert22.setView(view);
                        alert22.show();
                        final Button send =  view.findViewById(R.id.send_notification_b);
                        final EditText notification_text =  view.findViewById(R.id.notification_text);
                        final Button cancel =  view.findViewById(R.id.cancel_notification_b);
                        final ProgressBar pload =  view.findViewById(R.id.pload);
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alert22.cancel();
                            }
                        });

                        send.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pload.setVisibility(View.VISIBLE);
                                send_notification(getResources().getString(R.string.restaurant_name)
                                        ,notification_text.getText().toString()
                                        ,model.getDevice_token());
                                pload.setVisibility(View.GONE);
                                alert22.cancel();
                                Toast.makeText(user_activity.this, "تم الأرسال بنجاح", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                holder.user_card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        makePhoneCall(model.getPhone());
                    }
                });



            }
            @NonNull
            @Override
            public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card,parent,false);
                viewholder holder = new viewholder(view);
                return holder ;
            }
        };
        recycler_view.setAdapter(adapter);
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
            result = ContextCompat.checkSelfPermission(user_activity.this,p);
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

        if (ContextCompat.checkSelfPermission(user_activity.this,
                android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(user_activity.this,
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {

            intent.setData(Uri.parse("tel:" + number));
            intent.putExtra("com.android.phone.force.slot", true);
            intent.putExtra("Cdma_Supp", true);
            startActivity(intent);
        }
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
}