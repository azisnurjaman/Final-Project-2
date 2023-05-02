package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {

    Button btnUserRegister, btnUserLogin;

    TextView tvAdminLogin, tvStaffLogin, tvAbout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnUserRegister = findViewById(R.id.btnUserRegister);
        btnUserLogin = findViewById(R.id.btnUserLogin);

        tvAdminLogin = findViewById(R.id.tvAdminLogin);
        tvStaffLogin = findViewById(R.id.tvStaffLogin);
        tvAbout = findViewById(R.id.tvAbout);

        btnUserRegister.setOnClickListener(view -> {
            Intent intent = new Intent(StartActivity.this, UserRegisterActivity.class);
            startActivity(intent);
        });

        btnUserLogin.setOnClickListener(view -> {
            Intent intent = new Intent(StartActivity.this, UserLoginActivity.class);
            startActivity(intent);
        });

        tvAdminLogin.setOnClickListener(view -> {
            Intent intent = new Intent(StartActivity.this, AdminLoginActivity.class);
            startActivity(intent);
        });

        tvStaffLogin.setOnClickListener(view -> {
            Intent intent = new Intent(StartActivity.this, StaffLoginActivity.class);
            startActivity(intent);
        });

        tvAbout.setOnClickListener(view -> {
            Intent intent = new Intent(StartActivity.this, AboutActivity.class);
            startActivity(intent);
        });
    }
}