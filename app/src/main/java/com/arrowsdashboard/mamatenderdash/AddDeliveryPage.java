package com.arrowsdashboard.mamatenderdash;


import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddDeliveryPage extends AppCompatActivity {
    RecyclerView rv_areas;
    Button add_new_btn;
    DeliveryAdapter adapter;

    List<DeliveryModel> deliveryModelList;

    String area_value, delivery_value;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference dbReference;

    public String getUID() {
        return dbReference.child(Const.DELIVERY_PATH).push().getKey();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_delivery_page);

        rv_areas = findViewById(R.id.rv_delivery_areas);
        add_new_btn = findViewById(R.id.add_new_area);



        firebaseDatabase = FirebaseDatabase.getInstance();
        dbReference = firebaseDatabase.getReference();
        dbReference.keepSynced(true);


        getData();

        add_new_btn.setOnClickListener(v -> {
            show_dialog();
        });
    }

    private void getData() {
        deliveryModelList = new ArrayList<>();

        dbReference.child(Const.DELIVERY_PATH).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DeliveryModel deliveryModel = dataSnapshot.getValue(DeliveryModel.class);

                    deliveryModelList.add(deliveryModel);
                }
                adapter = new DeliveryAdapter(deliveryModelList, AddDeliveryPage.this, dbReference);
                rv_areas.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void show_dialog() {
        DeliveryDialog delivery_dialog = new DeliveryDialog(AddDeliveryPage.this);
        delivery_dialog.add_btn.setOnClickListener(v -> {
            delivery_dialog.getValidator().validate();
            if (delivery_dialog.isValidate()) {
                area_value = delivery_dialog.area_input.getText().toString();
                delivery_value = delivery_dialog.delivery_input.getText().toString();
                addDataToList(area_value, delivery_value);
                delivery_dialog.dismiss();
            }
        });
        delivery_dialog.show();
    }

    private void addDataToList(String area_value, String delivery_value) {

        DeliveryModel deliveryModel = new DeliveryModel(getUID(), delivery_value, area_value);
        dbReference.child(Const.DELIVERY_PATH).child(deliveryModel.getId()).setValue(deliveryModel);
        getData();

    }


}