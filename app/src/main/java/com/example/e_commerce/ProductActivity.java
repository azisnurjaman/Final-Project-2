package com.example.project_ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_ecommerce.adapter.CategoryAdapter;
import com.example.project_ecommerce.adapter.ItemAdapter;
import com.example.project_ecommerce.model.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductActivity extends AppCompatActivity {
    FirebaseFirestore db;
    List<Item> list = new ArrayList<>();
    private ProgressDialog progressDialog;
    ItemAdapter itemAdapter;
    private TextView txtInfo;
    private RecyclerView recyclerView;
    AlertDialog alertDialog1;
    CharSequence[] pakaian = {" All "," Pria "," Wanita "};
    CharSequence[] elektronik = {" All "," Laptop "," Kipas Angin "," TV "," Mouse "," Keyboard "};
    CharSequence[] peralatanRumah = {" All "," Kompor "," Piring "," Sapu "};
    CharSequence[] others = {" All "};
    Button buttonFilter;
    private static  int checkedItem = 0;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        checkedItem = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        recyclerView = (RecyclerView) findViewById(R.id.rvProduct);
        txtInfo = findViewById(R.id.txtInfo);
        db = FirebaseFirestore.getInstance();
        buttonFilter = (Button) findViewById(R.id.buttonFilter);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Get data...");
        Intent intent = getIntent();
        String getCategory = intent.getStringExtra("category").toLowerCase();
        buttonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getCategory.equals("pakaian")) {

                    filterPakaian();
                }else if(getCategory.equals("elektronik")){
                    filterElektronik();
                }else if(getCategory.equals("peralatan rumah tangga")){
                    filterPeralatanRumah();
                }else{
                    filterOthers();
                }
            }
        });

        itemAdapter = new ItemAdapter(this, list);

        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(itemAdapter);
        txtInfo.append(getCategory);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        String getCategory = intent.getStringExtra("category");
        getData("category", getCategory.toLowerCase());
    }

    private void getData(String field, String value){

        progressDialog.show();
        db.collection("item").whereEqualTo(field, value)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.removeAll(list);
                        itemAdapter.notifyDataSetChanged();
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                Item item = new Item(document.getString("id"), document.getString("name"), document.getString("quantity"), document.getString("picture"), document.getString("category"), document.getString("filter"), document.getString("price"), document.getString("description"));
                                item.setDocId(document.getId());
                                list.add(item);
                                Log.i("123",document.getString("filter"));
                                itemAdapter.notifyDataSetChanged();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Data gagal di ambil!", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    public void filterPakaian(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ProductActivity.this);
        builder.setTitle("Select Your Choice");
        builder.setSingleChoiceItems(pakaian, checkedItem, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                switch (item) {
                    case 0:

                        getData("category", "pakaian");
                        break;
                    case 1:
                        getData("filter", "pria");
                        break;
                    case 2:
                        getData("filter", "wanita");
                        break;
                }
                checkedItem = item;
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();
    }

    private void filterElektronik(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ProductActivity.this);
        builder.setTitle("Select Your Choice");
        builder.setSingleChoiceItems(elektronik, checkedItem, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                switch(item) {
                    case 0:
                        getData("category", "elektronik");
                        break;
                    case 1:
                        getData("filter", "laptop");
                        break;
                    case 2:
                        getData("filter", "kipas angin");
                        break;
                    case 3:
                        getData("filter", "tv");
                        break;
                    case 4:
                        getData("filter", "mouse");
                        break;
                    case 5:

                        getData("filter", "keyboard");
                        break;
                }
                checkedItem = item;
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();
    }

    private void filterPeralatanRumah(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ProductActivity.this);
        builder.setTitle("Select Your Choice");
        builder.setSingleChoiceItems(peralatanRumah, checkedItem, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                switch(item) {
                    case 0:
                        getData("category", "peralatan rumah tangga");
                        break;
                    case 1:
                        getData("filter", "kompor");
                        break;
                    case 2:
                        getData("filter", "piring");
                        break;
                    case 3:

                        getData("filter", "sapu");
                        break;
                }
                checkedItem = item;
                alertDialog1.dismiss();
            }
        });

        alertDialog1 = builder.create();
        alertDialog1.show();
    }


    private void filterOthers(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ProductActivity.this);
        builder.setTitle("Select Your Choice");
        builder.setSingleChoiceItems(others, 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch(item) {
                    case 0:

                        getData("category", "others");
                        break;

                }
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();
    }
}
