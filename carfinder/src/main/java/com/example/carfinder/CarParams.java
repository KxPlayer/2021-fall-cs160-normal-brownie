package com.example.carfinder;
import java.util.Map;

import com.fasterxml.jackson.databind.node.IntNode;

import java.util.HashMap;

//Object used in CarDao to find cars with these values
//Not all values need to be set, it is fine to leave as null (optional parameters)
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
    public Map<String, Object> getMap() {
        //hm.put("minprice", minprice);
        //hm.put("maxprice", maxprice);
        //hm.put("doors", doors);
        return hm;
    }
}
