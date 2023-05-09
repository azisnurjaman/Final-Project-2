package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelStoreOwner;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AboutActivity extends AppCompatActivity {
    private ImageView gitHub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        gitHub = findViewById(R.id.ivGithub);

        gitHub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo("https://github.com/azisnurjaman/Final-Project-2");
            }
        });
    }

    private void goTo(String s){
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}