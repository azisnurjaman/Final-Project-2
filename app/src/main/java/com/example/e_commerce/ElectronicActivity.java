package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ElectronicActivity extends AppCompatActivity {
    private String TAG = UserActivity.class.getSimpleName();

    private FrameLayout container;
    private BottomNavigationView nav;

    private final FragmentElectronic fragmentElectronic = new FragmentElectronic();
    private final UserFragment userFragment = new UserFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronic);

        initView();

        loadFragment(fragmentElectronic);

        nav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.action_beranda :
                                startActivity(new Intent(ElectronicActivity.this, UserActivity.class));
                                finish();
                                return true;
                            case R.id.action_profile :
                                startActivity(new Intent(ElectronicActivity.this, UserProfileActivity.class));
                                finish();
                                return true;
                        }
                        return false;
                    }
                }
        );
    }

    private void initView(){
        container = findViewById(R.id.container);
        nav = findViewById(R.id.nav);
    }

    private void loadFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}