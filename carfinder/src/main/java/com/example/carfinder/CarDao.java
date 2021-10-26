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
    public List<Car> selectByParams(CarParams cp);

	public List<Car> selectByPrice(int min, int max);

	public void addNewCar(Car car);

	public void deleteCar(Long id);

	public Car getById(Long id);

	public List<Car> getByModel(String model);

}