package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class StaffLoginActivity extends AppCompatActivity {
    
    private TextView tvStaffLogin;
    
    private EditText etStaffEmail, etStaffPassword;
    
    private Button btnStaffLogin;
    
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);

        auth = FirebaseAuth.getInstance();
        
        tvStaffLogin = findViewById(R.id.tvStaffLogin);
        
        etStaffEmail = findViewById(R.id.etStaffEmail);
        etStaffPassword = findViewById(R.id.etStaffPassword);
        
        btnStaffLogin = findViewById(R.id.btnStaffLogin);

        btnStaffLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                staffLogin();
            }
        });
    }

    private void staffLogin() {
        String staffEmail, staffPassword;
        staffEmail = etStaffEmail.getText().toString();
        staffPassword = etStaffPassword.getText().toString();

        DatabaseReference staffRef = FirebaseDatabase
                .getInstance("https://final-project-44dce-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("staffs");
        Query query = staffRef.orderByChild("email").equalTo(staffEmail);
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