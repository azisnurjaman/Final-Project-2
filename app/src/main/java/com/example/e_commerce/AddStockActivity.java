package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;

public class AddStockActivity extends AppCompatActivity {
    private String TAG = AddStockActivity.class.getSimpleName();
    private EditText id, quantity;
    private Button submit;
    private String i;
    private int q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);

        initView();

        final Stock stocks = (Stock) getIntent().getSerializableExtra("data");
        if (stocks != null){
            id.setText(stocks.getId());
            quantity.setText(stocks.getQuantity());

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        } else {
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    i = FirebaseStockUtils.getRefrence(FirebaseStockUtils.ITEMS_PATH).push().getKey();
                    q = Integer.parseInt(quantity.getText().toString().trim());

                    Stock stocks = new Stock(i, q);

                    FirebaseStockUtils.getRefrence(FirebaseStockUtils.ITEMS_PATH).child(i).setValue(stocks);
                    onBackPressed();
                    Toast.makeText(AddStockActivity.this, "Stock added successfully!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void initView(){
        /*id = findViewById(R.id.idStock);*/
        quantity = findViewById(R.id.quantityStock);
        submit = findViewById(R.id.btnAddStock);
    }
}