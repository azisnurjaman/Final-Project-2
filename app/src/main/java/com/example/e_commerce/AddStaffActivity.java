package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddStaffActivity extends AppCompatActivity {

    public EditText inputStaffName, inputStaffEmail, inputStaffPassword;

    private Button btnAddStaff;

    FirebaseAuth auth;

    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);

        inputStaffName = findViewById(R.id.etStaff);
        inputStaffEmail = findViewById(R.id.etStaffEmail);
        inputStaffPassword = findViewById(R.id.etStaffPassword);

        btnAddStaff = findViewById(R.id.btnAddStaff);

        auth = FirebaseAuth.getInstance();

        btnAddStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStaffAccount();
            }
        });
    }

    private void addStaffAccount() {
        String staffName, staffEmail, staffPassword;
        staffName = inputStaffName.getText().toString();
        staffEmail = inputStaffEmail.getText().toString();
        staffPassword = inputStaffPassword.getText().toString();

        if (staffName.isEmpty() || staffEmail.isEmpty() || staffPassword.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please fill Staff Name, Email, and Password blank field ", Toast.LENGTH_LONG).show();
            return;
        } if (staffPassword.length() < 6) {
            inputStaffPassword.setError("Min Password length is 6 characters !!!");
        }
        auth.createUserWithEmailAndPassword(staffEmail, staffPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful() && task.getResult()!=null){
                            FirebaseUser firebaseUser = task.getResult().getUser();
                            if (firebaseUser!=null) {
                                UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(staffName)
                                        .build();
                                firebaseUser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getApplicationContext(), "Staff added succesfully", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(AddStaffActivity.this, AdminActivity.class);
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                Toast.makeText(getApplicationContext(),"Staff not added sucessfully", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }
}