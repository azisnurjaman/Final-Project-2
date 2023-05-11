package com.example.e_commerce;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentProduct extends Fragment implements ItemsAdapter.ItemListener{
    private final String TAG = FragmentProduct.class.getSimpleName();
    private RecyclerView viewItem;
    private ArrayList<ItemsActivity> listItem;
    private RecyclerView.LayoutManager layoutManager;
    private ItemsAdapter adapter;

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
                    ItemsActivity item = snapshot.getValue(ItemsActivity.class);
                    item.setKey(snapshot.getKey());
                    listItem.add(item);
                }
                adapter = new ItemsAdapter(listItem, getActivity(), FragmentProduct.this);
                viewItem.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getDetails() + " | " + databaseError.getMessage());
            }
        });
        return v;
    }

    private void initView(View v) {
    }

    public void setViewItem(RecyclerView viewItem) {
        this.viewItem = viewItem;
    }
}
