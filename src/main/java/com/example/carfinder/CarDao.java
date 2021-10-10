package com.example.carfinder;
import java.util.List;
import javax.sql.DataSource;

public interface CarDao {

    public void setDataSource(DataSource ds);

    //creates car
    public void create(String model, int year, int price, int doors);
    
    //gets all cars in database
    public List<Car> selectAll();
    
    public List<Car> selectDoors();
    
    public List<Car> selectYear();
    
    public List<Car> selectModel();

    //gets all cars between min and max price
    public List<Car> selectByPrice(int minPrice, int maxPrice);
    
    public List<Car> selectByDoors(int door);
    
    public List<Car> selectByYear(int year);
    
    public List<Car> selectByModel(String model);

}
