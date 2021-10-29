package com.example.carfinder;

import java.sql.SQLException;
import java.sql.ResultSet;

import org.springframework.jdbc.core.RowMapper;

public class CarMapper implements RowMapper<Car> {
    
    public Car mapRow(ResultSet rs, int line) throws SQLException{
        Car car = new Car();
        
        car.carid = rs.getInt(1);
        car.make = rs.getString(2);
        car.model = rs.getString(3);
        car.price = rs.getInt(4);
        car.year = rs.getInt(5);
        car.doors = rs.getInt(6);
        car.fueltype = rs.getString(7);
        car.epapassenger = rs.getInt(8);
        car.transmission = rs.getString(9);
        car.engine = rs.getString(10);
        car.weight = rs.getDouble(11);
        car.length = rs.getDouble(12);
        car.width = rs.getDouble(13);
        car.towingcapacity = rs.getInt(14);
        car.trunkcapacity = rs.getDouble(15);
        car.horsepower = rs.getInt(16);
        car.horsepowerrpm = rs.getInt(17);
        car.torque = rs.getInt(18);
        car.torquerpm = rs.getInt(19);
        car.mpgcity = rs.getInt(20);
        car.mpghighway = rs.getInt(21);
        car.mpgcombined = rs.getInt(22);
        car.luxury = rs.getBoolean(23);
        car.sport = rs.getBoolean(24);
        return car;
    }
}
