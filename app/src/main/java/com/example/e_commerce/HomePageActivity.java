package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class HomePageActivity extends AppCompatActivity {

    private Button btnFashion, btnElectronics, btnBooks, btnOthers;

    private ImageView imgBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        btnFashion = findViewById(R.id.btnFashion);
        btnElectronics = findViewById(R.id.btnElectronics);
        btnBooks = findViewById(R.id.btnBooks);
        btnOthers = findViewById(R.id.btnOthers);
    }
}