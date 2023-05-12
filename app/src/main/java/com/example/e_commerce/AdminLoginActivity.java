package com.example.e_commerce;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.e_commerce.databinding.ActivityAdminLoginBinding;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdminLoginActivity extends AppCompatActivity {
    private EditText etAdminEmail, etAdminPassword;
    private Button btnAdminLogin;
    private ProgressBar progress;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        auth = FirebaseAuth.getInstance();

        etAdminEmail = findViewById(R.id.etAdminEmail);
        etAdminPassword = findViewById(R.id.etAdminPassword);
        btnAdminLogin = findViewById(R.id.btnAdminLogin);

        progress = findViewById(R.id.progress);

        btnAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminLogin();
            }
        });
    }

    private void adminLogin() {
        String email = etAdminEmail.getText().toString().trim();
        String pass = etAdminPassword.getText().toString().trim();

        if (email.isEmpty()) {
            etAdminEmail.setError("Email is required!");
            etAdminEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etAdminEmail.setError("Invalid email!");
            etAdminEmail.requestFocus();
            return;
        }
        if (pass.isEmpty()) {
            etAdminPassword.setError("Password is required!");
            etAdminPassword.requestFocus();
            return;
        }

        progress.setVisibility(View.VISIBLE);
        DatabaseReference adminsRef = FirebaseDatabase
                .getInstance("https://final-project-44dce-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("admin");
        Query query = adminsRef.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progress.setVisibility(View.GONE);
                                startActivity(new Intent(AdminLoginActivity.this, AdminActivity.class));
                                Toast.makeText(AdminLoginActivity.this, "Login successfully!",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                progress.setVisibility(View.GONE);
                                Toast.makeText(AdminLoginActivity.this, "Login Failed, please check your credentials and try again!",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(AdminLoginActivity.this, "Login Failed, please check your credentials and try again!",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progress.setVisibility(View.GONE);
                Toast.makeText(AdminLoginActivity.this, "Login Failed, please check your credentials and try again!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
