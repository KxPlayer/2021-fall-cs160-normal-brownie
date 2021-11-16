package com.example.carfinder;

import com.opencsv.bean.CsvBindByPosition;

public class CarCSV {
    public int carid;
    @CsvBindByPosition(position = 0)
    public String make;
    @CsvBindByPosition(position = 1)
    public String model;
    @CsvBindByPosition(position = 2)
    public int price;
    @CsvBindByPosition(position = 3)
    public int year;
    @CsvBindByPosition(position = 4)
    public int doors;
    @CsvBindByPosition(position = 5)
    public String fueltype;
    @CsvBindByPosition(position = 6)
    public int epapassenger;
    @CsvBindByPosition(position = 7)
    public String transmission;
    @CsvBindByPosition(position = 8)
    public String engine;
    @CsvBindByPosition(position = 9)
    public double weight;
    @CsvBindByPosition(position = 10)
    public double length;
    @CsvBindByPosition(position = 11)
    public double width;
    @CsvBindByPosition(position = 12)
    public int towingcapacity;
    @CsvBindByPosition(position = 13)
    public double trunkcapacity;
    @CsvBindByPosition(position = 14)
    public int horsepower;
    @CsvBindByPosition(position = 15)
    public int horsepowerrpm;
    @CsvBindByPosition(position = 16)
    public int torque;
    @CsvBindByPosition(position = 17)
    public int torquerpm;
    @CsvBindByPosition(position = 18)
    public int mpgcity;
    @CsvBindByPosition(position = 19)
    public int mpghighway;
    @CsvBindByPosition(position = 20)
    public int mpgcombined;
    @CsvBindByPosition(position = 21)
    public boolean luxury;
    @CsvBindByPosition(position = 22)
    public boolean sport;

    public int getCarid() {
        return carid;
    }

    public void setCarid(int carid) {
        this.carid = carid;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDoors() {
        return doors;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }

    public String getFueltype() {
        return fueltype;
    }

    public void setFueltype(String fueltype) {
        this.fueltype = fueltype;
    }

    public int getEpapassenger() {
        return epapassenger;
    }

    public void setEpapassenger(int epapassenger) {
        this.epapassenger = epapassenger;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public int getTowingcapacity() {
        return towingcapacity;
    }

    public void setTowingcapacity(int towingcapacity) {
        this.towingcapacity = towingcapacity;
    }

    public double getTrunkcapacity() {
        return trunkcapacity;
    }

    public void setTrunkcapacity(double trunkcapacity) {
        this.trunkcapacity = trunkcapacity;
    }

    public int getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(int horsepower) {
        this.horsepower = horsepower;
    }

    public int getHorsepowerrpm() {
        return horsepowerrpm;
    }

    public void setHorsepowerrpm(int horsepowerrpm) {
        this.horsepowerrpm = horsepowerrpm;
    }

    public int getTorque() {
        return torque;
    }

    public void setTorque(int torque) {
        this.torque = torque;
    }

    public int getTorquerpm() {
        return torquerpm;
    }

    public void setTorquerpm(int torquerpm) {
        this.torquerpm = torquerpm;
    }

    public int getMpgcity() {
        return mpgcity;
    }

    public void setMpgcity(int mpgcity) {
        this.mpgcity = mpgcity;
    }

    public int getMpghighway() {
        return mpghighway;
    }

    public void setMpghighway(int mpghighway) {
        this.mpghighway = mpghighway;
    }

    public int getMpgcombined() {
        return mpgcombined;
    }

    public void setMpgcombined(int mpgcombined) {
        this.mpgcombined = mpgcombined;
    }

    public boolean isLuxury() {
        return luxury;
    }

    public void setLuxury(boolean luxury) {
        this.luxury = luxury;
    }

    public boolean isSport() {
        return sport;
    }

    public void setSport(boolean sport) {
        this.sport = sport;
    }

    public String toString() {
        return (carid + " - " + make + " - " + model + " - " + year + " - " + price + " - " + weight);
    }

}
