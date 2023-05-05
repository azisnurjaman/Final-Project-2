package com.example.e_commerce;

import java.io.Serializable;

public class Stock implements Serializable {
    private String id;
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Stock(){

    }

    public Stock(String id, int quantity){
        this.id = id;
        this.quantity = quantity;
    }
}
