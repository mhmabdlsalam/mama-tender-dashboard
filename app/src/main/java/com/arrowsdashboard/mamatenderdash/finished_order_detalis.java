package com.arrowsdashboard.mamatenderdash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class finished_order_detalis extends AppCompatActivity {

    DatabaseReference cart_root ;
    String adds,content ,size,drink ;
    RecyclerView order_rec ;
    RecyclerView.LayoutManager manager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_order_detalis);

        order_rec = findViewById(R.id.order_rec);
        order_rec.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        order_rec.setLayoutManager(manager);


        cart_root = FirebaseDatabase.getInstance().getReference("Finished_orders").child(getIntent().getStringExtra("date"))
                .child(getIntent().getStringExtra("order_id"))
                .child("list_of_product");

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
                        finished_order_detalis.this,
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
                for (int k = 0 ; k < model.getAddsList().size() ;k++ ){
                    if (model.getAddsList().get(k).isIs_need()){
                        String englishNumerals = new BigDecimal(model.getAddsList().get(k).getPrice()).toString();
                        item_price +=  (Double.parseDouble(englishNumerals)*Double.parseDouble(model.getNumber()));
                        adds += " "+model.getAddsList().get(k).getAdds();
                        holder.cart_adds.setText( " الأضافات : "+model.getNumber()+" x "+adds);
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


                holder.cart_price.setText(new DecimalFormat("###.##").format(Double.valueOf(String.valueOf(Integer.parseInt(model.getNumber())*Double.parseDouble(model.getTotal_price())))).replace(',','.')
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
        order_rec.setAdapter(adapter);
        adapter.startListening();
    }
}