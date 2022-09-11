package com.arrowsdashboard.mamatenderdash;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.ViewHolder> {
    View view;
    private final List<DeliveryModel> deliveryModelList;
    private final Context context;
    private final DatabaseReference dbReference;


    public DeliveryAdapter(List<DeliveryModel> deliveryModelList, Context context, DatabaseReference dbReference) {
        this.deliveryModelList = deliveryModelList;
        this.context = context;
        this.dbReference = dbReference;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_area_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DeliveryModel model = deliveryModelList.get(position);
        holder.area.setText(model.getArea());
        holder.price.setText(model.getPrice() + " جنيه ");
        holder.edit_btn.setOnClickListener(v -> {
            show_dialog(position, model);
        });
        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("تحذير")
                    .setMessage("هل انت متأكد من مسح الموقع")
                    .setPositiveButton("موافق", (dialog, which) -> {
                        deliveryModelList.remove(position);
                        dbReference.child(Const.DELIVERY_PATH).child(model.getId()).removeValue();
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }).setNegativeButton("الغاء", (dialog, which) -> {
                       dialog.dismiss();
                    }).show();

            return false;
        });

    }

    @Override
    public int getItemCount() {
        return deliveryModelList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void show_dialog(int p, DeliveryModel model) {
        DeliveryDialog delivery_dialog = new DeliveryDialog(context);
        delivery_dialog.add_btn.setText("حفظ التعديل");
        delivery_dialog.delivery_input.setText(model.getPrice());
        delivery_dialog.area_input.setText(model.getArea());
        delivery_dialog.add_btn.setOnClickListener(v -> {
            delivery_dialog.getValidator().validate();
            if (delivery_dialog.isValidate()) {
                model.setArea(delivery_dialog.area_input.getText().toString());
                model.setPrice(delivery_dialog.delivery_input.getText().toString());
                dbReference.child(Const.DELIVERY_PATH).child(model.getId()).setValue(model);
                notifyDataSetChanged();
                delivery_dialog.dismiss();
            }

        });
        delivery_dialog.show();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView price, area;
        Button edit_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.delivery_text);
            area = itemView.findViewById(R.id.area_text);
            edit_btn = itemView.findViewById(R.id.edit_btn);
        }
    }


}