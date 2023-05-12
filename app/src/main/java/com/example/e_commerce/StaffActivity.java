package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StaffActivity extends AddStaffActivity {
    TextView tvStaffActivity, tvStaffName, tvStaffEmail;
    Button btnStaffLogout;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    String userID;

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
        reference = FirebaseDatabase.getInstance().getReference("staffs");
        userID = firebaseUser.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Staff userProfile = dataSnapshot.getValue(Staff.class);

                if (userProfile != null){
                    String userName = userProfile.userName;
                    String email = userProfile.email;

                    tvStaffName.setText(userName);
                    tvStaffEmail.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

        btnStaffLogout.setOnClickListener(view -> {
            auth.signOut();
            finish();
            Intent intent = new Intent(StaffActivity.this, StartActivity.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Logout Successfully!", Toast.LENGTH_LONG).show();
        });
    }
}