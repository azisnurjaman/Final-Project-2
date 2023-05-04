package com.example.e_commerce;

public class Stock {
    private String idItem;

    private String quantity;

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Stock(String idItem, String quantity) {
        this.idItem = idItem;
        this.quantity = quantity;
    }
}
