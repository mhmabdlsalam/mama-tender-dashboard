package com.arrowsdashboard.mamatenderdash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class point_activity extends AppCompatActivity {

    EditText points , dinnar ;

    Button save_data ;

    String s_points , s_dinnar ;
    ProgressBar pload ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);

        points = findViewById(R.id.points);
        dinnar = findViewById(R.id.dinnar);
        save_data = findViewById(R.id.save_data);
        pload = findViewById(R.id.pload);

        FirebaseDatabase.getInstance().getReference("point_system").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    pload.setVisibility(View.INVISIBLE);
                    points.setText(snapshot.child("for_sale").getValue().toString());
                    dinnar.setText(snapshot.child("for_buy").getValue().toString());
                }else {
                    pload.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        save_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_points = points.getText().toString();
                s_dinnar = dinnar.getText().toString();


                if (TextUtils.isEmpty(s_points)){
                    points.setError("ادخل كم نقطة");
                }
                else if (TextUtils.isEmpty(s_dinnar)){
                    dinnar.setError("ادخل كم دينار");
                }
                else {
                    pload.setVisibility(View.VISIBLE);
                    HashMap<String , String> point_system_map = new HashMap<>();
                    point_system_map.put("for_sale",s_points);
                    point_system_map.put("for_buy",s_dinnar);
                    FirebaseDatabase.getInstance().getReference("point_system").setValue(point_system_map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            pload.setVisibility(View.INVISIBLE);
                            Toast.makeText(point_activity.this, "تم الحفظ", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(point_activity.this,home_page.class));
        super.onBackPressed();
    }
}