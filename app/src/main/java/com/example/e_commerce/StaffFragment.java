package com.example.e_commerce;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class StaffFragment extends Fragment {

    private String TAG = StaffFragment.class.getSimpleName();
    private Button btnLogout;
    private TextView staffName, staffemail;
    private FirebaseUser staff;
    private DatabaseReference dbReference;
    private String staffID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_profile, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        btnLogout = v.findViewById(R.id.btnLogout);
        staff = FirebaseAuth.getInstance().getCurrentUser();
        dbReference = FirebaseDatabase.getInstance().getReference("staff");
        staffID = staff.getUid();

        final TextView name = v.findViewById(R.id.tvStaffName);
        final TextView email = v.findViewById(R.id.tvStaffEmail);

        dbReference.child(staffID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Staff staffProfile = dataSnapshot.getValue(Staff.class);

                if (staffProfile != null){
                    String staffName = staffProfile.staffName;
                    String staffEmail = staffProfile.staffEmail;

                    name.setText(staffName);
                    email.setText(staffEmail);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

        btnLogout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getActivity(), UserLoginActivity.class));
            Toast.makeText(getActivity(), "Logout successfully!",
                    Toast.LENGTH_LONG).show();
            System.exit(0);
        });
    }
}