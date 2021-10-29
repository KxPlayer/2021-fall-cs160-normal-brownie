package com.example.carfinder;

import java.util.List;
import javax.sql.DataSource;
public interface CarDao {
    public void setDataSource(DataSource ds);
    //creates car
    public void create(Car c);
    
    //gets all cars in database
    public List<Car> selectAll();

    //select by parameter object
    //see CarParams for all possible parameters
    public List<Car> selectByParams(CarParams cp);

    //gets all cars between min and max price
    public List<Car> selectByPrice(int minPrice, int maxPrice);

    //select car by car id
    public List<Car> selectById(int carid);

    //select cars where string s is a subset of make/model
    //case insensitive
    public List<Car> searchByName(String s);


}