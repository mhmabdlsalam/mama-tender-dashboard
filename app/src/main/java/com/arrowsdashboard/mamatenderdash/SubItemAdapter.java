package com.arrowsdashboard.mamatenderdash;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.List;

public class SubItemAdapter extends RecyclerView.Adapter<SubItemAdapter.SubItemViewHolder> {
    Context context ;
    List<size> sizeList ;
    List<adds> addslist ;

    List<String> listofcomp ;
    List<banner> listofbanners ;
    List<branch> listofbranches ;
    List<drink> list_of_drinks ;
    List<product> listofproduct ;
    List<cat> list_of_categories ;
    List<promocode> listOfpromo ;
    Boolean addsflag = false ;
    Boolean comflag = false ;
    Boolean bannerflag = false ;
    Boolean branchflag = false ;
    Boolean drinkflag = false ;
    Boolean productflag = false ;
    Boolean promoflag = false ;
    Boolean cat_flag = false ;


    SubItemAdapter(Context context, List<size> sizeList  ) {
        this.sizeList = sizeList;
        this.context = context ;
    }
    SubItemAdapter(Context context, List<String> listofcomp ,String a  ) {
        this.listofcomp = listofcomp;
        this.context = context ;
        comflag = true;
    }
    SubItemAdapter(Context context, List<adds> addslist,int a ) {
        this.addslist = addslist;
        this.context = context ;
        addsflag = true ;
    }
    SubItemAdapter(Context context, List<banner> listofbanners,int a ,int b) {
        this.listofbanners = listofbanners;
        this.context = context ;
        bannerflag = true ;
    }
    SubItemAdapter(Context context, List<branch> listofbranches,int a ,int b,int c) {
        this.listofbranches = listofbranches;
        this.context = context ;
        branchflag = true ;
    }

    public SubItemAdapter(Context context, List<drink> list_of_drinks, int i, int i1, int i2, int i3) {
        this.list_of_drinks = list_of_drinks;
        this.context = context ;
        drinkflag = true ;
    }

    public SubItemAdapter(Context context, List<product> listofproduct, int i, int i1, int i2, int i3, int i4) {
        this.listofproduct = listofproduct;
        this.context = context ;
        productflag = true ;
    }

    public SubItemAdapter(Context context, List<promocode> listOfpromo, int i, int i1, int i2, int i3, int i4, int i5) {
        this.listOfpromo = listOfpromo;
        this.context = context ;
        promoflag = true ;
    }

    public SubItemAdapter(Context context, List<cat> list_of_categories, int i, int i1, int i2, int i3, int i4, int i5, int i6) {
        this.list_of_categories = list_of_categories;
        this.context = context ;
        cat_flag = true ;
    }

    @NonNull
    @Override
    public SubItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        if (bannerflag){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_card, parent, false);
            return new SubItemViewHolder(view);
        }else if (branchflag){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.branch_card, parent, false);
            return new SubItemViewHolder(view);
        }else if (drinkflag){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drink_card, parent, false);
            return new SubItemViewHolder(view);
        }
        else if (productflag){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card, parent, false);
            return new SubItemViewHolder(view);
        }
        else if (cat_flag){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card, parent, false);
            return new SubItemViewHolder(view);
        }
        else if (promoflag){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.promocode_item, parent, false);
            return new SubItemViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemofproduct, parent, false);
            return new SubItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull SubItemViewHolder subItemViewHolder, @SuppressLint("RecyclerView") final int i) {

        if (addsflag){
            subItemViewHolder.textofpitem.setText(addslist.get(i).getAdds()+" : "+addslist.get(i).getPrice());
            subItemViewHolder.textofpitem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    addslist.remove(i);
                    notifyDataSetChanged();
                    return false;
                }
            });
        } else if (comflag){
            subItemViewHolder.textofpitem.setText(listofcomp.get(i));
            subItemViewHolder.textofpitem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listofcomp.remove(i);
                    notifyDataSetChanged();
                    return false;
                }
            });
        } else if (bannerflag){
            Picasso.get().load(listofbanners.get(i).getUrl()).into(subItemViewHolder.bannerimage);
            subItemViewHolder.bannerimage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    new AlertDialog.Builder(context)
                            .setTitle("Alert")
                            .setMessage("Are you sure you want to delete this item")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    sheard.deletepost(context,Const.HTTPS_RESTAUARANT_ARROWSCARS_COM + "/api/dashboard/ads/delete/",listofbanners.get(i).getId());

                                }
                            }).show();
                    return false;
                }
            });
        }else if (branchflag){
            subItemViewHolder.branch.setText(listofbranches.get(i).getUrl());
            subItemViewHolder.branch.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    new AlertDialog.Builder(context)
                            .setTitle("Alert")
                            .setMessage("Are you sure you want to delete this item")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    sheard.deletepost(context,Const.HTTPS_RESTAUARANT_ARROWSCARS_COM + "/api/dashboard/branches/delete/",listofbranches.get(i).getId());

                                }
                            }).show();
                    return false;
                }
            });

        }else if (drinkflag){
            subItemViewHolder.drink_name.setText(list_of_drinks.get(i).getName());
            Picasso.get().load(list_of_drinks.get(i).getImg()).into(subItemViewHolder.drink_img);
            subItemViewHolder.drink_img.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {


                    new AlertDialog.Builder(context)
                            .setTitle("Alert")
                            .setMessage("Are you sure you want to delete this item")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    sheard.deletepost(context,Const.HTTPS_RESTAUARANT_ARROWSCARS_COM + "/api/dashboard/drinks/delete/",list_of_drinks.get(i).getId());

                                }
                            }).show();

                    return false;
                }
            });

        }else if (promoflag){
            subItemViewHolder.promocode.setText(listOfpromo.get(i).getPromo()+"  "+listOfpromo.get(i).getValue()+"  "+listOfpromo.get(i).getType());
            subItemViewHolder.constrin_promo.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    new AlertDialog.Builder(context)
                            .setTitle("Alert")
                            .setMessage("Are you sure you want to delete this item")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    sheard.deletepost(context,Const.HTTPS_RESTAUARANT_ARROWSCARS_COM + "/api/dashboard/coupons/delete/",listOfpromo.get(i).getId());
                                }
                            }).show();
                    return false;
                }
            });
        }
        else if (productflag){


            subItemViewHolder.drink_name.setText(listofproduct.get(i).getName());
            Picasso.get().load(listofproduct.get(i).getImage()).into(subItemViewHolder.drink_img);
            subItemViewHolder.drink_img.setOnClickListener(v -> {
                Intent intent = new Intent(context,add_product.class);
                intent.putExtra("id",listofproduct.get(i).getId());
                context.startActivity(intent);
            });
            subItemViewHolder.drink_img.setOnLongClickListener(v -> {
                new AlertDialog.Builder(context)
                        .setTitle("Alert")
                        .setMessage("Are you sure you want to delete this item")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sheard.deletepost(context,Const.HTTPS_RESTAUARANT_ARROWSCARS_COM + "/api/dashboard/products/delete/",Integer.parseInt(listofproduct.get(i).getId()));
                                Log.d("urllllllllll",listofproduct.get(i).getId());
                            }
                        }).show();
                return false;
            });

        }
        else if (cat_flag){

            subItemViewHolder.drink_name.setText(list_of_categories.get(i).getName());
            Picasso.get().load(list_of_categories.get(i).getPhoto()).into(subItemViewHolder.drink_img);

            subItemViewHolder.delete_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sheard.deletepost(context,Const.HTTPS_RESTAUARANT_ARROWSCARS_COM + "/api/dashboard/categories/delete/"
                            ,list_of_categories.get(i).getId());
                }
            });

            subItemViewHolder.drink_img.setOnClickListener(v -> {
                Intent intent = new Intent(context,categories_page.class);
                intent.putExtra("id",list_of_categories.get(i).getId());
                context.startActivity(intent);
            });

            subItemViewHolder.drink_img.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    prevelant.cat_item = list_of_categories.get(i);
                    context.startActivity(new Intent(context,edit_cat.class));
                    return false;
                }
            });

        }
        else {
            subItemViewHolder.textofpitem.setText(sizeList.get(i).getSize()+" : "+sizeList.get(i).getPrice());
            subItemViewHolder.textofpitem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    sizeList.remove(i);
                    notifyDataSetChanged();
                    return false;
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        if (addsflag){
            return addslist.size();
        }else if (comflag){
            return listofcomp.size();
        }else if (bannerflag){
            return listofbanners.size();
        }else if (branchflag){
            return listofbranches.size();
        }else if (drinkflag){
            return list_of_drinks.size();
        }else if (promoflag){
            return listOfpromo.size();
        }else if (cat_flag){
            return list_of_categories.size();
        }
        else if (productflag){
            return listofproduct.size();
        }
        else {
            return sizeList.size();
        }

    }

    class SubItemViewHolder extends RecyclerView.ViewHolder {
        TextView textofpitem,branch,drink_name,promocode ;
        ImageView bannerimage,drink_img,delete_img ;
        ConstraintLayout constrin_promo ;

        SubItemViewHolder(View itemView) {
            super(itemView);

            textofpitem = itemView.findViewById(R.id.textofpitem);
            promocode = itemView.findViewById(R.id.textView6);
            bannerimage = itemView.findViewById(R.id.bannerimage);
            branch = itemView.findViewById(R.id.branch);
            drink_name = itemView.findViewById(R.id.drink_name);
            drink_img = itemView.findViewById(R.id.drink_img);
            constrin_promo = itemView.findViewById(R.id.constrin_promo);
            delete_img = itemView.findViewById(R.id.delete_img);


        }
    }


}

