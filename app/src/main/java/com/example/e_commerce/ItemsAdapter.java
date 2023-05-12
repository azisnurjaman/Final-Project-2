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
    private ItemListener listener;

    public ItemsAdapter(ArrayList<Stock> listItem, Context context, ItemListener listener){
        this.listItem = listItem;
        this.context = context;
        this.listener = listener;
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
        final String n = listItem.get(position).getName();
        final String c = listItem.get(position).getCategory();
        final String d = listItem.get(position).getDescription();
        final String q = listItem.get(position).getQuantity();

        holder.TxtProduct.setText(n);
        holder.TxtProductCategory.setText(c);
        holder.TxtDesc.setText(d);
        holder.TxtQuantity.setText(q);
        holder.imgItemMenu.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PopupMenu popupMenu = new PopupMenu(context, holder.imgItemMenu);
            popupMenu.inflate(R.menu.item_menu);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.action_delete:
                            listener.delete(listItem.get(position), position);
                            return true;

                        default:
                            return false;
                    }
                }
            });
            popupMenu.show();
        }
        });
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ImgItem, imgItemMenu;
        private TextView TxtProduct, TxtProductCategory, TxtDesc, TxtQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ImgItem = itemView.findViewById(R.id.ImgItem);
            TxtProduct = itemView.findViewById(R.id.TxtProduct);
            TxtProductCategory = itemView.findViewById(R.id.TxtProductCategory);
            TxtQuantity = itemView.findViewById(R.id.TxtQuantity);
            TxtDesc = itemView.findViewById(R.id.TxtDesc);
            imgItemMenu = itemView.findViewById(R.id.ImgItemMenu);
        }
    }
    public interface ItemListener{
        void delete(Stock stock, int position);
    }
}
