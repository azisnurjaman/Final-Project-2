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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddStaffActivity extends AppCompatActivity {
    public EditText inputStaffName, inputStaffEmail, inputStaffPassword, inputConfirmStaffPassword;
    private Button btnAddStaff;
    private ProgressBar progress;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);

        inputStaffName = findViewById(R.id.etStaffName);
        inputStaffEmail = findViewById(R.id.etStaffEmail);
        inputStaffPassword = findViewById(R.id.etStaffPassword);
        inputConfirmStaffPassword = findViewById(R.id.etStaffConfirmPassword);

        progress = (ProgressBar) findViewById(R.id.progress);

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
        String staffName, staffEmail, staffPassword, staffConfirmPassword;
        staffName = inputStaffName.getText().toString();
        staffEmail = inputStaffEmail.getText().toString();
        staffPassword = inputStaffPassword.getText().toString();
        staffConfirmPassword = inputConfirmStaffPassword.getText().toString();

        if (staffName.isEmpty()){
            inputStaffName.setError("User Name is required!");
            inputStaffName.requestFocus();
            return;
        }
        if (staffEmail.isEmpty()){
            inputStaffEmail.setError("Email is required!");
            inputStaffEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(staffEmail).matches()){
            inputStaffEmail.setError("Invalid email!");
            inputStaffEmail.requestFocus();
            return;
        }
        if (staffPassword.isEmpty()){
            inputStaffPassword.setError("Password is required!");
            inputStaffPassword.requestFocus();
            return;
        }
        if (staffPassword.length() <= 5){
            inputStaffPassword.setError("Min password length should be more then 5 characters!");
            inputStaffPassword.requestFocus();
            return;
        } else if (staffConfirmPassword.equals(staffPassword)){
            inputConfirmStaffPassword.requestFocus();
        } else {
            inputConfirmStaffPassword.setError("Please check your password again!");
            return;
        }

        progress.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(staffEmail, staffPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Staff staff = new Staff(staffName, staffEmail, staffPassword);

                    FirebaseDatabase.getInstance("https://final-project-44dce-default-rtdb.asia-southeast1.firebasedatabase.app/")
                            .getReference("staffs")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(staff).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        progress.setVisibility(View.GONE);
                                        finish();
                                        Toast.makeText(AddStaffActivity.this, "Staff has been registered successfully!",
                                                Toast.LENGTH_LONG).show();
                                    } else {
                                        progress.setVisibility(View.GONE);
                                        Toast.makeText(AddStaffActivity.this, "Failed to register, try again!",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                } else {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(AddStaffActivity.this, "Email already exists!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        }
}