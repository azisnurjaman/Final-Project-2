package com.example.e_commerce;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.FocusFinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentProduct extends Fragment{
    private String TAG = FragmentProduct.class.getSimpleName();
    private RecyclerView viewItem;
    private ArrayList<Stock> listItem;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product, container, false);
        initView(v);
        layoutManager = new LinearLayoutManager(getActivity());
        viewItem.setLayoutManager(layoutManager);

        FirebaseStockUtils.getRefrence(FirebaseStockUtils.ITEMS_PATH).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listItem = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Stock stock = snapshot.getValue(Stock.class);
                    stock.setId(snapshot.getKey());
                    listItem.add(stock);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getDetails() + " | " + databaseError.getMessage());
            }
        });
        return v;
    }

    private void initView(View v){
        viewItem = v.findViewById(R.id.ViewItem);
    }
}