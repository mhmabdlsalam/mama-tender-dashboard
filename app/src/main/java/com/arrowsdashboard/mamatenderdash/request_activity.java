package com.arrowsdashboard.mamatenderdash;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class request_activity extends AppCompatActivity {
    RecyclerView request_recycler ;
    RecyclerView.LayoutManager layoutManager ;
    DatabaseReference orders_root ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_activity);

        request_recycler = findViewById(R.id.request_recycler);

        orders_root = FirebaseDatabase.getInstance().getReference("Orders").child(getResources().getString(R.string.branch_name));
        layoutManager = new LinearLayoutManager(this);
        request_recycler.setLayoutManager(layoutManager);
        request_recycler.setHasFixedSize(false);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.orders);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.orders:
                        return true ;

                    case R.id.home_page:
                        startActivity(new Intent(request_activity.this, home_page.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.menu:
                        startActivity(new Intent(request_activity.this, cat_page.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<order> options=new FirebaseRecyclerOptions.Builder<order>()
                .setQuery(orders_root,order.class).build();
        FirebaseRecyclerAdapter<order, viewholder> adapter=new FirebaseRecyclerAdapter<order, viewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final viewholder holder, int position, @NonNull final order model) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(
                        request_activity.this,
                        LinearLayoutManager.VERTICAL,
                        false
                );
                String englishNumerals = new BigDecimal(model.getTotal_price().replace(',','.')
                .replace('٫','.')).toString();
                String englishNumerals2 = new BigDecimal(model.getPrice()).toString();
                holder.order_id.setText(model.getOrder_id());

                holder.total_price.setText(new BigDecimal(new DecimalFormat("########.##").format(Double.valueOf(englishNumerals)).replace(',','.')
                        .replace('٫','.')).toString());
                holder.order_status.setText(model.getOrder_status());
                holder.tax.setText(model.getTax());
                holder.extra.setText(model.getExtra());
                holder.discount.setText(new BigDecimal(model.getDiscount().replaceAll("-","")
                .replaceAll(" ","")).toString());
                holder.order_price.setText(englishNumerals2);
                holder.delivery.setText(model.getDelivery());

                holder.order_details.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(request_activity.this,order_details.class);
                        intent.putExtra("oid",model.getOrder_id());
                        intent.putExtra("up",model.getClient().getPhone());
                        intent.putExtra("os",model.getOrder_status());
                        intent.putExtra("un",model.getClient().getName());
                        intent.putExtra("br",model.getBranch());
                        intent.putExtra("op",model.getTotal_price().replace(',','.')
                                .replace('٫','.'));
                        intent.putExtra("ut",model.getClient().getDevice_token());
                        intent.putExtra("omessage",model.getMessage());
                        prevelant.list_of_p = model.getList_of_product();
                        prevelant.address = model.getAddress() ;

                        startActivity(intent);
                    }
                });

            }
            @NonNull
            @Override
            public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,parent,false);
                viewholder holder = new viewholder(view);
                return holder ;
            }
        };
        request_recycler.setAdapter(adapter);
        adapter.startListening();
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(request_activity.this,home_page.class));
        super.onBackPressed();
    }

}