package com.arrowsdashboard.mamatenderdash;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class finished_order extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    CardView date_card ;
    String regist_year,regist_month,regist_day ,selecteddate="" ;
    TextView datestring ;
    RecyclerView recycler_finished ;
    RecyclerView.LayoutManager manager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_order);
        date_card = findViewById(R.id.cardView3);
        datestring = findViewById(R.id.textView13);
        recycler_finished = findViewById(R.id.recycler_finished);
        recycler_finished.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        recycler_finished.setLayoutManager(manager);
        Calendar calendar =Calendar.getInstance();
        SimpleDateFormat currentdate =new SimpleDateFormat("M dd yyyy");
        String savecurrentdate=currentdate.format(calendar.getTime());
        selecteddate = savecurrentdate.replaceAll( "1","١").replaceAll("2", "٢")
                .replaceAll("3", "٣").replaceAll("4", "٤")
                .replaceAll("5", "٥").replaceAll("6", "٦")
                .replaceAll("7", "٧").replaceAll("8", "٨")
                .replaceAll("9", "٩").replaceAll("0", "٠");
        date_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        finished_order.this,
                        now.get(Calendar.YEAR), // Initial year selection
                        now.get(Calendar.MONTH), // Initial month selection
                        now.get(Calendar.DAY_OF_MONTH) // Inital day selection
                );
                dpd.setMaxDate(now);
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<finished_order_class> options=new FirebaseRecyclerOptions.Builder<finished_order_class>()
                .setQuery(FirebaseDatabase.getInstance().getReference("Finished_orders").child(selecteddate),finished_order_class.class).build();
        FirebaseRecyclerAdapter<finished_order_class, viewholder> adapter=new FirebaseRecyclerAdapter<finished_order_class, viewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final viewholder holder, int position, @NonNull final finished_order_class model) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(
                        finished_order.this,
                        LinearLayoutManager.VERTICAL,
                        false
                );

                String englishNumerals = new BigDecimal(model.getTotal_price().replace(',','.')
                        .replace('٫','.')).toString();

                holder.orderf_id.setText(model.getOrderid());
                holder.orderf_total.setText(" أجمالي السعر : "+new DecimalFormat("###.##").format(Double.valueOf(String.valueOf(Double.parseDouble(englishNumerals))))
                        .replace(',','.').replace('٫','.'));
                holder.orderf_id.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(finished_order.this,finished_order_detalis.class);
                        intent.putExtra("date",selecteddate);
                        intent.putExtra("order_id",model.getOrderid());
                        startActivity(intent);
                    }
                });
                holder.orderf_total.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(finished_order.this,finished_order_detalis.class);
                        intent.putExtra("date",selecteddate);
                        intent.putExtra("order_id",model.getOrderid());
                        startActivity(intent);
                    }
                });


            }
            @NonNull
            @Override
            public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.finished_order_item,parent,false);
                viewholder holder = new viewholder(view);
                return holder ;
            }
        };
        recycler_finished.setAdapter(adapter);
        adapter.startListening();
    }



    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        regist_year = String.valueOf(year );
        regist_month= String.valueOf(monthOfYear+1);
        regist_day=String.valueOf(dayOfMonth);
        int rd = Integer.parseInt(regist_day);
        if (rd<10){
            regist_day = "0"+regist_day;
        }
        selecteddate = regist_month+" "+regist_day+" "+regist_year ;

        selecteddate = selecteddate.replaceAll( "1","١").replaceAll("2", "٢")
            .replaceAll("3", "٣").replaceAll("4", "٤")
            .replaceAll("5", "٥").replaceAll("6", "٦")
            .replaceAll("7", "٧").replaceAll("8", "٨")
            .replaceAll("9", "٩").replaceAll("0", "٠");
        Toast.makeText(finished_order.this, selecteddate, Toast.LENGTH_SHORT).show();
        datestring.setText(regist_day+"-"+regist_month+"-"+regist_year);
        onStart();

    }

}