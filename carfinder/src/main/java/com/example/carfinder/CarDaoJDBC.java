package com.example.carfinder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.ArrayList;
import java.util.Iterator; 

//data access object
@Component
public class CarDaoJDBC implements CarDao {
    private DataSource datasource;
    
    @Autowired
    private CarDao cd;
    
    public void setDataSource(DataSource ds) {
        datasource = ds;
    }

	@Override
	public void create(Car c) {
        JdbcTemplate insert = new JdbcTemplate(datasource);
        insert.update("insert into car (make, model, price, year, doors, fueltype," + 
        "epapassenger, transmission, engine, weight, length, width, towingcapacity, trunkcapacity," +
        "horsepower, horsepowerrpm, torque, torquerpm, mpgcity, mpghighway, mpgcombined, luxury, sport)" + 
        " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                new Object[]{c.make, c.model, c.price, c.year, c.doors, c.fueltype, c.epapassenger,
                c.transmission, c.engine, c.weight, c.length, c.width, c.towingcapacity, 
                c.trunkcapacity, c.horsepower, c.horsepowerrpm, c.torque, c.torquerpm,
                c.mpgcity, c.mpghighway, c.mpgcombined, c.luxury, c.sport});
	}

    public List<Car> selectAll() {
        JdbcTemplate select = new JdbcTemplate(datasource);
        return select.query("select * from car", new CarMapper());
    }
    
    public List<Car> selectByParams(CarParams cp) {
        NamedParameterJdbcTemplate select = new NamedParameterJdbcTemplate(datasource);
        StringJoiner sj = new StringJoiner(" and ", " where ", "");
        sj.setEmptyValue("");
        Map<String, Object> mp = cp.getMap();
        String sql = "select * from car";
        if (mp.containsKey("minyear"))
            sj.add("year >= :minyear");
        if (mp.containsKey("maxyear"))
            sj.add("year <= :maxyear");
        if (mp.containsKey("minprice"))
            sj.add("price >= :minprice");
        if (mp.containsKey("maxprice"))
            sj.add("price <= :maxprice");
        if (mp.containsKey("doors"))
            sj.add("doors = :doors");
        if (mp.containsKey("transmission"))
            sj.add("transmission = :transmission");
        if (mp.containsKey("make"))
            sj.add("make = :make");
        if (mp.containsKey("fueltype"))
            sj.add("fueltype = :fueltype");
        if (mp.containsKey("minmpgcombined"))
            sj.add("mpgcombined >= :minmpgcombined");
        if (mp.containsKey("luxury"))
            sj.add("luxury = :luxury");
        if (mp.containsKey("sport"))
            sj.add("sport = :sport");

        return select.query(sql + sj, mp, new CarMapper());
    }
	
    public List<Car> selectByPrice(int minPrice, int maxPrice) {
        JdbcTemplate select = new JdbcTemplate(datasource);
        String sql = "select * from car where price between ? and ?";
        return select.query(sql, new CarMapper(), new Object[]{minPrice, maxPrice});
    }

    
    @ResponseStatus(HttpStatus.OK)
	public void addNewCar(Car car) {
		create(car);
	//	System.out.print(car);
	}


    @ResponseStatus(HttpStatus.OK)
	public void deleteCar(Long id) {
	
		Iterator<Car> iterator = cd.selectAll().iterator();  
		while(iterator.hasNext())  
		{  
		Car cars=iterator.next(); 
		if(cars.carid == id)  
		{  
		//	System.out.print(cars.carid  + " removed");
			iterator.remove();  
		}		
		}
	}

	@Override
	public Car getById(Long id) {
		Iterator<Car> iterator = cd.selectAll().iterator();  
		while(iterator.hasNext())  
		{  
		Car cars=iterator.next(); 
		if(cars.carid == id)  
		{  
			return cars;
		}		
		}
		return null;
		
	}

	@Override
	public List<Car> getByModel(String model) {
		Iterator<Car> iterator = cd.selectAll().iterator();
		Car cars = null;
		List<Car> namedCars = new ArrayList<Car>();
		
		while(iterator.hasNext())  
		{  
		 cars=iterator.next(); 
		if(cars.model.equals(model))  	{ 
			namedCars.add(cars);
		}		
		}
		
		return namedCars;
	}
}
