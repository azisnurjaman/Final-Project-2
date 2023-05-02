package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddStaffActivity extends AppCompatActivity {

    EditText inputStaffName, inputStaffEmail, inputStaffPassword;

    Button btnAddStaff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);

        inputStaffName = (EditText) findViewById(R.id.etStaff);
        inputStaffEmail = (EditText) findViewById(R.id.etStaffEmail);
        inputStaffPassword = (EditText) findViewById(R.id.etStaffPassword);

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
        }

    }
}