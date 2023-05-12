package com.example.e_commerce;

import java.io.Serializable;

public class Stock {
    private String id, quantity, name, description, category, picture;

    public Stock(){

    }

    public Stock(String id, String quantity, String category, String name, String description, String picture){
        this.id = id;
        this.quantity = quantity;
        this.category = category;
        this.name = name;
        this.description = description;
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
