package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StaffActivity extends AddStaffActivity {

    TextView tvStaffActivity, tvStaffName, tvStaffEmail;

    Button btnStaffLogout;

    FirebaseAuth auth;

    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        tvStaffActivity = findViewById(R.id.tvStaffActivity);
        tvStaffEmail = findViewById(R.id.tvStaffEmail);
        tvStaffName = findViewById(R.id.tvStaffName);

        btnStaffLogout = findViewById(R.id.btnStaffLogout);

        auth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {
            tvStaffName.setText(firebaseUser.getDisplayName());
            tvStaffEmail.setText(firebaseUser.getEmail());
        }
    }
}