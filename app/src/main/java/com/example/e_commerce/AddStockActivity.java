package com.example.e_commerce;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.service.media.MediaBrowserService;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class AddStockActivity extends AppCompatActivity {
    private EditText etQuantity, etProductName, etProductDescription;
    private ImageView img;
    private Button btnAddStock;
    private Spinner dropdownCategory;
    private FirebaseDatabase db;
    private String id;
    private FirebaseStorage storage;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);

        db = FirebaseDatabase.getInstance();
        img = findViewById(R.id.etImage);
        etQuantity = findViewById(R.id.etQuantity);
        etProductName = findViewById(R.id.etProductName);
        etProductDescription = findViewById(R.id.etProductDescription);
        btnAddStock = findViewById(R.id.btnAddStock);
        dropdownCategory = findViewById(R.id.dropdownCategory);

        storage  = FirebaseStorage.getInstance();
        storageRef  = storage.getReference("images").child("IMG" + new Date().getTime() + ".png");

        btnAddStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStock();
            }
        });

        img.setOnClickListener(v -> {
            selectImage();
        });
    }

    private void selectImage(){
        final CharSequence[] items = {"Take Photo", "Choose From Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddStockActivity.this);
        builder.setTitle(getString(R.string.choose_image));
        builder.setIcon(R.drawable.logo);
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Take Photo")){
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 10);
            } else if (items[item].equals("Choose From Library")){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image"), 20);
            } else if (items[item].equals("Canel")){
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 20 && resultCode == RESULT_OK && data != null){
            final Uri path = data.getData();
            Thread thread = new Thread(() -> {
               try {
                   InputStream inputStream = getContentResolver().openInputStream(path);
                   Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                   img.post(() -> {
                       img.setImageBitmap(bitmap);
                   });
               } catch (Exception e){
                    e.printStackTrace();
               }
            });
            thread.start();
        }
        if (requestCode == 10 && resultCode == RESULT_OK){
            final Bundle bundle = data.getExtras();
            Thread thread = new Thread(() -> {
                Bitmap bitmap = (Bitmap) bundle.get("data");
                img.post(() -> {
                    img.setImageBitmap(bitmap);
                });
            });
            thread.start();
        }
    }

    private void upload(String id) {
        img.setDrawingCacheEnabled(true);
        img.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();

        String imageId = UUID.randomUUID().toString();

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imageRef = storageRef.child("images/" + imageId + ".jpg");
        UploadTask uploadTask = imageRef.putBytes(imageBytes);

        uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw task.getException();
            }
            return imageRef.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String imageUrl = task.getResult().toString();
                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
                databaseRef.child("products").child(id).child("picture").setValue(imageUrl);
            } else {
                Exception e = task.getException();
                Toast.makeText(AddStockActivity.this, "Image upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addStock() {
        id = FirebaseStockUtils.getRefrence(FirebaseStockUtils.ITEMS_PATH).push().getKey();
        String quantity = etQuantity.getText().toString();
        String category = dropdownCategory.getSelectedItem().toString();
        String productName = etProductName.getText().toString();
        String productDescription = etProductDescription.getText().toString();
        String image = img.getDrawable().toString();

        if (img.getDrawable() == null) {
            Toast.makeText(AddStockActivity.this, "Product image is required!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (productName.isEmpty()) {
            etProductName.setError("Product name is required!");
            etProductName.requestFocus();
            return;
        }
        if (quantity.isEmpty()) {
            etQuantity.setError("Quantity is required!");
            etQuantity.requestFocus();
            return;
        }
        if (quantity.equals("0")) {
            etQuantity.setError("Quantity should not be less than 1!");
            etQuantity.requestFocus();
            return;
        }
        if (productDescription.isEmpty()) {
            etProductDescription.setError("Product description is required!");
            etProductDescription.requestFocus();
            return;
        }
        try {
            Stock stock = new Stock(id, quantity, category, productName, productDescription, image);
            FirebaseStockUtils.getRefrence(FirebaseStockUtils.ITEMS_PATH).child(id)
                    .setValue(stock).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            upload(id);
                            Toast.makeText(AddStockActivity.this, "Product has been successfully added!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddStockActivity.this, AdminActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddStockActivity.this, "Product failed to add!", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(AddStockActivity.this, "Product image is required!", Toast.LENGTH_SHORT).show();
        }
    }
}