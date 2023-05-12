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

        auth.signInWithEmailAndPassword(staffEmail, staffPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            FirebaseUser user = task.getResult().getUser();
                            if (user != null) {
                                String uid = user.getUid();
                                DatabaseReference staffsRef = FirebaseDatabase.getInstance().getReference().child("staffs").child(uid);
                                staffsRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            Intent intent = new Intent(StaffLoginActivity.this, StaffActivity.class);
                                            startActivity(intent);
                                            Toast.makeText(StaffLoginActivity.this, "Login successfully!", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(StaffLoginActivity.this, "Login Failed, please check your credentials and try again!", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(StaffLoginActivity.this, "Login Failed, please check your credentials and try again!", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        } else {
                            Toast.makeText(StaffLoginActivity.this, "Login Failed, please check your credentials and try again!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}