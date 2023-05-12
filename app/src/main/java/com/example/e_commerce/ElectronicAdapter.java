package com.example.e_commerce;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ElectronicAdapter extends RecyclerView.Adapter<ElectronicAdapter.ViewHolder> {
    private ArrayList<Stock> listItem;
    private Context context;

    public ElectronicAdapter(ArrayList<Stock> listItem, Context context){
        this.listItem = listItem;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.electronic, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final String n = listItem.get(position).getName();
        final String c = listItem.get(position).getCategory();
        final String q = listItem.get(position).getQuantity();
        final String d = listItem.get(position).getDescription();
        final String imageURL = listItem.get(position).getPicture();

        holder.TxtProduct.setText("Product Name : \n" + n);
        holder.TxtProductCategory.setText("\nCategory : \n" + c);
        holder.TxtQuantity.setText("\nQuantity : \n " + q);
        holder.TxtDesc.setText("\nDescription : \n" +d);
        Picasso.get().load(imageURL).into(holder.ImgItem);
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ImgItem;
        private TextView TxtProduct, TxtProductCategory, TxtDesc, TxtQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ImgItem = itemView.findViewById(R.id.ImgItem);
            TxtProduct = itemView.findViewById(R.id.TxtProduct);
            TxtProductCategory = itemView.findViewById(R.id.TxtProductCategory);
            TxtQuantity = itemView.findViewById(R.id.TxtQuantity);
            TxtDesc = itemView.findViewById(R.id.TxtDesc);
        }
    }
}

