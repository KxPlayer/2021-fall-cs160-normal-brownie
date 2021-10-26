package com.example.carfinder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class CarController {

	
	PersonalizedCar personalize;
	
    @Autowired
    private CarDao cd;

    @GetMapping("/cars")
    public List<Car> greeting() {
        return cd.selectAll();
    }
    
    @GetMapping("/cars/{price}")
    public List<Car> getCarByPrice(@PathVariable("id") int min , int max) {
        return cd.selectByPrice(min, max);
    }
    
    
    @RequestMapping("/cars/{minPrice}/{maxPrice}/{minYear}/{maxYear}/{doors}/{transmission}/{make}/{fueltype}/{minmpgcombined}/{luxury}/{sport}")
    public List<Car> getSpecializedCar(@PathVariable("minPrice") Integer minPrice,
    		@PathVariable("maxPrice") Integer maxPrice,
    		@PathVariable("minYear") Integer minYear,
    		@PathVariable("maxYear") Integer maxYear,
    		@PathVariable("doors") Integer doors,
    		@PathVariable("transmission") String transmission,
    		@PathVariable("make") String make,
    		@PathVariable("fueltype") String fueltype,
    		@PathVariable("minmpgcombined") Integer minmpgcombined,
    		@PathVariable("luxury") boolean luxury,
    		@PathVariable("sport") boolean sport
    		) {
    	
    	
    	CarParams pr = new CarParams();
    	personalize = new PersonalizedCar();
    	
    	pr = personalize.getPersonalizedCar(minPrice, maxPrice, minYear, maxYear, doors, transmission, make, fueltype, minmpgcombined, luxury, sport);
    	if (pr == null)
            throw new IllegalArgumentException("The argument cannot be null");
    	
    	return cd.selectByParams(pr);
    }
    
    
    @PostMapping(
    		  value = "/cars", 
    		  produces = "application/json")
    public void addCar(@RequestBody Car car) {
    	cd.addNewCar(car);	
    }
    
    @GetMapping("/{carid}")
    public Car getCarID(@PathVariable("carid") Long id) {
    	return cd.getById(id);
    	
    }
    
    @GetMapping("/cars/model/{model}")
    public List<Car> getCarModel(@PathVariable("model") String model) {
    	return cd.getByModel(model);
    	
    }
    
    @DeleteMapping(value = "/cars/{carid}")
    public void deleteCar(@PathVariable("carid") Long id) {
    	cd.deleteCar(id);
    	
    }
    
}