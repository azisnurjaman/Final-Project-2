package com.example.myproduct;
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

public class ProdukFragment extends Fragment implements ItemAdapter.ItemListener{
    private String TAG = ProdukFragment.class.getSimpleName();
    private FloatingActionButton add;
    private RecyclerView viewItem;
    private ArrayList<Item> listItem;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_produk, container, false);
        initView(v);
        layoutManager = new LinearLayoutManager(getActivity());
        viewItem.setLayoutManager(layoutManager);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ItemActivity.class);
                startActivity(intent);
            }
        });

        FirebaseUtils.getRefrence(FirebaseUtils.ITEMS_PATH).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listItem = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Item item = snapshot.getValue(Item.class);
                    item.setKey(snapshot.getKey());
                    listItem.add(item);
                }
                adapter = new ItemAdapter(listItem, getActivity(), ProdukFragment.this);
                viewItem.setAdapter(adapter);
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
        add = v.findViewById(R.id.Add);
    }

    @Override
    public void delete(Item item, int position) {
        FirebaseUtils.getRefrence(FirebaseUtils.ITEMS_PATH).child(item.getKey()).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity(), "Produk Berhasil Dihapus.", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
