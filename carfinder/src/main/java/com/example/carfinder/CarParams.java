package com.example.carfinder;
import java.util.Map;

import java.util.HashMap;

//Object used in CarDao to find cars with these values
public class CarParams {
    Map<String, Object>  hm;

    public CarParams() {
        hm = new HashMap<>();
    }

    public void minyear(int minyear) {
        hm.put("minyear", minyear);
    }
    public void maxyear(int maxyear) {
        hm.put("maxyear", maxyear);
    }
    public void minprice(int minprice) {
        hm.put("minprice", minprice);
    }
    public void maxprice(int maxprice) {
        hm.put("maxprice", maxprice);
    }
    public void doors(int doors) {
        hm.put("doors", doors);
    }
    // Automatic | Manual
    public void transmission(String transmission) {
        hm.put("transmission", transmission);
    }
    // Toyota, Honda, BMW, etc
    public void make(String make) {
        hm.put("make", make);
    }
    // Electric | Hybrid | Gas
    public void fueltype(String fueltype) {
        hm.put("fueltype", fueltype);
    }
    //based on combined mpg
    public void minmpgcombined(int minmpgcombined) {
        hm.put("minmpgcombined", minmpgcombined);
    }
    public void luxury(boolean luxury) {
        hm.put("luxury", luxury);
    }
    public void sport(boolean sport) {
        hm.put("sport", sport);
    }

    public Map<String, Object> getMap() {
        return hm;
    }
}
