package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddStockActivity extends AppCompatActivity {

    private EditText etIdItem, etQuantity, etProductName, etProductDescription, etProductPicture;

    private Button btnAddStock;

    private Spinner dropdownCategory;

    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);

        db = FirebaseDatabase.getInstance();

        etIdItem = (EditText) findViewById(R.id.etIdItem);
        etQuantity = (EditText) findViewById(R.id.etQuantity);
        etProductName = (EditText) findViewById(R.id.etProductName);
        etProductDescription = (EditText) findViewById(R.id.etProductDescription);
        etProductPicture = (EditText) findViewById(R.id.etProductPicture);

        btnAddStock = (Button) findViewById(R.id.btnAddStock);

        dropdownCategory = (Spinner) findViewById(R.id.dropdownCategory);

        btnAddStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStock();
            }
        });
    }


    private void addStock() {
        String idItem = etIdItem.getText().toString();
        String quantity = etQuantity.getText().toString();
        String category = dropdownCategory.getSelectedItem().toString().toLowerCase(Locale.ROOT);
        String productName = etProductName.getText().toString();
        String productDescription = etProductDescription.getText().toString();
        String productPicture = etProductPicture.getText().toString();

        if (idItem.isEmpty()) {
            etIdItem.setError("ID Item is required!");
            etIdItem.requestFocus();
            return;
        }
        if (quantity.isEmpty()) {
            etQuantity.setError("Quantity is required!");
            etQuantity.requestFocus();
            return;
        }
        if (productName.isEmpty()) {
            etProductName.setError("Product Name is required!");
            etProductName.requestFocus();
            return;
        }
        if (productDescription.isEmpty()) {
            etProductDescription.setError("Product Description is required!");
            etProductDescription.requestFocus();
            return;
        }
        if (productPicture.isEmpty()) {
            etProductPicture.setError("Product Picture Link is required!");
            etProductPicture.requestFocus();
            return;
        }

        Stock stock = new Stock (idItem, quantity, category, productName, productDescription, productPicture);
        db.getInstance("https://final-project-44dce-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("stocks")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(stock).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(AddStockActivity.this, "Stock has been successfully added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddStockActivity.this, AdminActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddStockActivity.this, "The stock has not been successfully added", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}