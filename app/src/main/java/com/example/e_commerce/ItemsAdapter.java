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

import java.util.ArrayList;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
    private ArrayList<Stock> listItem;
    private Context context;

    public ItemsAdapter(ArrayList<Stock> listItem, Context context){
        this.listItem = listItem;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final String np = listItem.get(position).getName();
        final String jp = listItem.get(position).getCategory();
        final String h = listItem.get(position).getQuantity();
        final String s = listItem.get(position).getDescription();

        holder.TxtProduct.setText(np);
        holder.TxtProductCategory.setText(jp);
        holder.TxtQuantity.setText(h);
        holder.TxtDesc.setText(String.valueOf(s));
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
/*    public interface ItemListener{
        void delete(Stock stock, int position);
    }*/
}
