package com.example.e_commerce;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseStockUtils {
    private static final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    public  static final String ITEMS_PATH = "stocks";

    public static DatabaseReference getRefrence(String path){
        return firebaseDatabase.getReference(path);
    }
}
