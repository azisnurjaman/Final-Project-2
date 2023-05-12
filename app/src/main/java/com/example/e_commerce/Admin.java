package com.example.e_commerce;

public class Admin {

    public String email, pass, userName;

    public Admin(){

    }
    public Admin(String userName, String email, String pass){
        this.userName = userName;
        this.email = email;
        this.pass = pass;
    }
}
