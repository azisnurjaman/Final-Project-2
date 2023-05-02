package com.example.e_commerce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnAddStaff, btnAddStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        btnAddStaff = (Button) findViewById(R.id.btnStaff);
        btnAddStock = (Button) findViewById(R.id.btnStock);

        btnAddStaff.setOnClickListener(this);
        btnAddStock.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnStaff:
                Intent intentStaff = new Intent(AdminActivity.this, AddStaffActivity.class);
                startActivity(intentStaff);
                break;

        }
    }
}
