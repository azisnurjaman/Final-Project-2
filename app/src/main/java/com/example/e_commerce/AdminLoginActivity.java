package com.example.e_commerce;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.e_commerce.databinding.ActivityAdminLoginBinding;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class AdminLoginActivity extends AppCompatActivity {

    private TextView tvAdminLogin;

    private EditText etAdminEmail, etAdminPassword;

    private Button btnAdminLogin;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        auth = FirebaseAuth.getInstance();

        tvAdminLogin = findViewById(R.id.tvAdminLogin);
        etAdminEmail = findViewById(R.id.etAdminEmail);
        etAdminPassword = findViewById(R.id.etAdminPassword);
        btnAdminLogin = findViewById(R.id.btnAdminLogin);

        btnAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminLogin();
            }
        });
    }

    private void adminLogin() {
        String adminEmail, adminPassword;
        adminEmail = etAdminEmail.getText().toString();
        adminPassword = etAdminPassword.getText().toString();
        auth.signInWithEmailAndPassword(adminEmail, adminPassword)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful() && task.getResult() != null) {
                                    Intent intent = new Intent(AdminLoginActivity.this, AdminActivity.class);
                                    startActivity(intent);
                                    if (task.getResult().getUser() != null) {
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Incorrect Email or Password" , Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Incorrect Email or Password", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
    }
}
