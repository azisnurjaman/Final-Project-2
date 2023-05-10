package com.example.e_commerce;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseStockUtils {
    private static final FirebaseDatabase firebaseDatabase = FirebaseDatabase
            .getInstance("https://final-project-44dce-default-rtdb.asia-southeast1.firebasedatabase.app/");
    public  static final String ITEMS_PATH = "products";

    public static DatabaseReference getRefrence(String path){
        return firebaseDatabase.getReference(path);
    }
}
