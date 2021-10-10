package com.example.carfinder;

import java.sql.SQLException;
import java.sql.ResultSet;

import org.springframework.jdbc.core.RowMapper;

public class CarMapper implements RowMapper<Car> {
    
    public Car mapRow(ResultSet rs, int line) throws SQLException{
        Car car = new Car();
        car.setModel(rs.getString(1));
        car.setYear(rs.getInt(2));
        car.setPrice(rs.getInt(3));
        car.setDoors(rs.getInt(4));
        return car;
    }
}
