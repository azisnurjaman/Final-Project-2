package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UserLoginActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private ProgressBar progress;
    private Button btn;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        etEmail = findViewById(R.id.etUserEmail);
        etPassword = findViewById(R.id.etUserPassword);
        btn = findViewById(R.id.btnUserLogin);
        progress = findViewById(R.id.progress);

        auth = FirebaseAuth.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountLogin();
            }
        });
    }

    private void accountLogin() {
        String email = etEmail.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();

        if (email.isEmpty()) {
            etEmail.setError("Email is required!");
            etEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Invalid email!");
            etEmail.requestFocus();
            return;
        }
        if (pass.isEmpty()) {
            etPassword.setError("Password is required!");
            etPassword.requestFocus();
            return;
        }

        progress.setVisibility(View.VISIBLE);
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && task.getResult() != null && task.getResult().getUser() != null) {
                    FirebaseUser user = task.getResult().getUser();
                    if (user != null) {
                        String uid = user.getUid();
                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("users").child(uid);
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    progress.setVisibility(View.GONE);
                                    startActivity(new Intent(UserLoginActivity.this, UserActivity.class));
                                    Toast.makeText(UserLoginActivity.this, "Login successfully!",
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    progress.setVisibility(View.GONE);
                                    Toast.makeText(UserLoginActivity.this, "Login Failed, please check your credentials and try again!",
                                            Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                progress.setVisibility(View.GONE);
                                Toast.makeText(UserLoginActivity.this, "Login Failed, please check your credentials and try again!",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } else {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(UserLoginActivity.this, "Login Failed, please check your credentials and try again!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
