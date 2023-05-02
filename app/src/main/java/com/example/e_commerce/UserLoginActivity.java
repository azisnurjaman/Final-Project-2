package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UserLoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;

    private Button btn;

    private List<String> ListData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        etUsername = findViewById(R.id.etUserEmail);
        etPassword = findViewById(R.id.etUserPassword);
        btn = findViewById(R.id.btnUserLogin);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountLogin();
            }
        });
    }

    private void accountLogin() {
        String username, password;
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please fill Username and Password blank field", Toast.LENGTH_LONG).show();
        }

    }
}