package com.example.carfinder;

public class Car {
    public int carid;
    public String make;
    public String model;
    public int price;
    public int year;
    public int doors;
    public String fueltype;
    public int epapassenger;
    public String transmission;
    public String engine;
    public double weight;
    public double length;
    public double width;
    public int towingcapacity;
    public double trunkcapacity;
    public int horsepower;
    public int horsepowerrpm;
    public int torque;
    public int torquerpm;
    public int mpgcity;
    public int mpghighway;
    public int mpgcombined;
    public boolean luxury;
    public boolean sport;
    
    public String toString() {
        return (carid + " - " + make + " - " + model + " - " + year + " - " + price + " - " + weight);
    }

}