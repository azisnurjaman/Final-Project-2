package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentOthers extends Fragment {
    private RecyclerView recyclerView;
    private ItemsAdapter itemsAdapter;
    private ArrayList<Stock> stockList;
    private DatabaseReference databaseReference;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product, container, false);

        recyclerView = v.findViewById(R.id.ViewItem);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        stockList = new ArrayList<>();
        itemsAdapter = new ItemsAdapter(stockList, getContext());
        recyclerView.setAdapter(itemsAdapter);

        databaseReference = FirebaseStockUtils.getRefrence(FirebaseStockUtils.ITEMS_PATH);
        databaseReference.orderByChild("category").equalTo("Others").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                stockList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Stock stock = dataSnapshot.getValue(Stock.class);
                    stockList.add(stock);
                }
                itemsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load data.", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}