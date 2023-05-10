package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {

    Button btnAddStaff, btnAddStock, btnAdminLogout;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        btnAddStaff = findViewById(R.id.btnStaff);
        btnAddStock = findViewById(R.id.btnStock);
        btnAdminLogout = findViewById(R.id.btnAdminLogout);

        auth = FirebaseAuth.getInstance();

        btnAddStaff.setOnClickListener(view -> {
            Intent intent = new Intent(AdminActivity.this, AddStaffActivity.class);
            startActivity(intent);
        });

        btnAddStock.setOnClickListener(view -> {
            Intent intent = new Intent(AdminActivity.this, AddStockActivity.class);
            startActivity(intent);
        });

        btnAdminLogout.setOnClickListener(view -> {
            auth.signOut();
            finish();
            Intent intent = new Intent(AdminActivity.this, StartActivity.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Logout Successfully!", Toast.LENGTH_LONG).show();
        });
    }
}