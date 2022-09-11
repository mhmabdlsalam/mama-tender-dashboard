package com.arrowsdashboard.mamatenderdash;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;



public class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView cart_img;
    TextView cart_name, cart_price,plus,number,negative,order_id,order_price,order_status
            ,cart_size,cart_content,cart_adds,additional_cart,orderf_id,orderf_total,user_name,user_phone
            ,tax,extra,discount,total_price,delivery;
    Button order_details ;
    ImageView specific_notification ;
    ConstraintLayout user_card ;
    public ItemClickListner listner;


    public viewholder(View itemView) {
        super(itemView);
        cart_img = itemView.findViewById(R.id.cart_img);
        user_name = itemView.findViewById(R.id.textView20);
        user_phone = itemView.findViewById(R.id.textView19);
        cart_name = itemView.findViewById(R.id.cart_name);
        cart_price = itemView.findViewById(R.id.cart_price);
        delivery = itemView.findViewById(R.id.textView23);
        tax = itemView.findViewById(R.id.textView25);
        specific_notification = itemView.findViewById(R.id.specific_notification);
        extra = itemView.findViewById(R.id.textView27);
        discount = itemView.findViewById(R.id.textView30);
        total_price = itemView.findViewById(R.id.textView32);
        number = itemView.findViewById(R.id.number);
        order_details = itemView.findViewById(R.id.order_details);
        order_id = itemView.findViewById(R.id.order_id);
        order_price = itemView.findViewById(R.id.order_price);
        order_status = itemView.findViewById(R.id.order_status);
        cart_size = itemView.findViewById(R.id.cart_size);
        cart_content = itemView.findViewById(R.id.cart_content);
        cart_adds = itemView.findViewById(R.id.cart_adds);
        additional_cart = itemView.findViewById(R.id.additional_cart);

        orderf_id = itemView.findViewById(R.id.textView14);
        orderf_total = itemView.findViewById(R.id.textView16);
        user_card = itemView.findViewById(R.id.user_card);


    }

    public void setitemclicklistner(ItemClickListner listner) {
        this.listner = listner;
    }

    @Override
    public void onClick(View v) {
        listner.onClick(v, getAdapterPosition(), false);
    }
}
