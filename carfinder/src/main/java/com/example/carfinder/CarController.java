package com.example.carfinder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    
    @GetMapping("/cars/personalize")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
   public List<Car> getSpecializedCar (@RequestParam(required = false) Integer minPrice,
    		@RequestParam(required = false) Integer maxPrice,
    		@RequestParam(required = false) Integer minYear,
    		@RequestParam(required = false) Integer maxYear,
    		@RequestParam(required = false) Integer doors,
    		@RequestParam(required = false) String transmission,
    		@RequestParam(required = false) String make,
    		@RequestParam(required = false) String fueltype,
    		@RequestParam(required = false) Integer minmpgcombined,
    		@RequestParam(required = false)  Boolean luxury,
    		@RequestParam(required = false) Boolean sport){
    	CarParams pr = new CarParams();
    	personalize = new PersonalizedCar();
    	
    	if(minPrice == null && maxPrice == null && minYear == null && maxYear == null && 
    	   doors == null && transmission == null && make == null && fueltype == null && 
    	   minmpgcombined == null && luxury == null && sport == null){
    		return cd.selectAll();
    	}else {
    		pr = personalize.getPersonalizedCar(minPrice, maxPrice, minYear, maxYear, doors, transmission, make, fueltype, minmpgcombined, luxury, sport);
    	}
    	
   	return cd.selectByParams(pr);
    }
    
    @GetMapping("/cars/id")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
   public List<Car> getSpecializedCarId (@RequestParam(required = true) Integer carid){
   
    	return cd.selectById(carid);
    }
    
    @GetMapping("/cars/name")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
   public List<Car> getSpecializedCarName (@RequestParam(required = true) String names){
    	return cd.searchByName(names);
    }
    
    @PostMapping(
  		  value = "/cars", 
  		  produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
  public void addCar(@RequestBody Car car) {
    	cd.create(car);	
  }
    
    
}