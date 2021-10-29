package com.example.carfinder.accountdata;

public class User {
    public int userid;
    public String username;
    public String password;
    
    public String toString() {
        return (userid + " - " + username + " - " + password);
    }

}