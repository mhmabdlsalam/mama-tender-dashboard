package com.arrowsdashboard.mamatenderdash;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class home_page extends AppCompatActivity {

    CardView social_card ,pointsCard, delivery_card ,promo_code_card,users_card,banner_card,cat_card,branchcard,drinks_card,finished_order ;
    ImageView notificationt ;
    Switch aSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        promo_code_card = findViewById(R.id.promo_code_card);
        delivery_card = findViewById(R.id.delivery_card);
        social_card = findViewById(R.id.social_card);
        banner_card = findViewById(R.id.banner_card);
        cat_card = findViewById(R.id.cat_card);
        branchcard = findViewById(R.id.branchcard);
        drinks_card = findViewById(R.id.drinks_card);
        notificationt = findViewById(R.id.notificationt);
        finished_order = findViewById(R.id.finished_order);
        users_card = findViewById(R.id.cardView2);
        aSwitch = findViewById(R.id.swtch);
        pointsCard = findViewById(R.id.pointsCard);


        pointsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevelant.page = "point";
                startActivity(new Intent(home_page.this,pin_code.class));

            }
        });




        users_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevelant.page = "user";
                startActivity(new Intent(home_page.this,pin_code.class));

            }
        });
        notificationt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home_page.this, request_activity.class));
                overridePendingTransition(0,0);
            }
        });

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.soun);
        FirebaseDatabase.getInstance().getReference("availability").child(getResources().getString(R.string.branch_name)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.getValue()!=null&&((boolean)snapshot.getValue())!=true){
                    aSwitch.setChecked(false);
                    aSwitch.setText("مغلق");
                }else {
                    aSwitch.setChecked(true);
                    aSwitch.setText("متاح");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("Orders").child(getResources().getString(R.string.branch_name)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    notificationt.setImageResource(R.drawable.notification);
                    mp.start();
                }else {
                    notificationt.setImageResource(R.drawable.nota);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseDatabase.getInstance().getReference("Orders").child(getResources().getString(R.string.branch_name)).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                notificationt.setImageResource(R.drawable.notification);
                mp.start();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        aSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                buttonView.setText("متاح");
            }else {
                buttonView.setText("مغلق");
            }
            FirebaseDatabase.getInstance().getReference("availability").child(getResources().getString(R.string.branch_name)).setValue(isChecked);
        });

        branchcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home_page.this,branches.class));
            }
        });
        drinks_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home_page.this,drinks.class));
            }
        });

        cat_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home_page.this,add_category.class));
            }
        });

        banner_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home_page.this,banners.class));
            }
        });

        social_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home_page.this,social_phones.class));
            }
        });
        delivery_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevelant.page = "delivery";
                startActivity(new Intent(home_page.this,pin_code.class));
            }
        });
        promo_code_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevelant.page = "promo";
                startActivity(new Intent(home_page.this,pin_code.class));
            }
        });

        finished_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home_page.this,finished_order.class));
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home_page);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.orders:
                        startActivity(new Intent(home_page.this, request_activity.class));
                        overridePendingTransition(0,0);
                        return true ;

                    case R.id.home_page:
                        return true;

                    case R.id.menu:
                        startActivity(new Intent(home_page.this, cat_page.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }
}