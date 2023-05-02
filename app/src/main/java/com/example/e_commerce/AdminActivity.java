package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Button btnAddStaff = findViewById(R.id.btnStaff);
        Button btnAddStock = findViewById(R.id.btnStock);

        btnAddStaff.setOnClickListener(view -> {
            Intent intent = new Intent(AdminActivity.this, AddStaffActivity.class);
            startActivity(intent);
        });
        btnAddStock.setOnClickListener(view -> {
            Intent intent = new Intent(AdminActivity.this, AddStockActivity.class);
            startActivity(intent);
        });
    }
}