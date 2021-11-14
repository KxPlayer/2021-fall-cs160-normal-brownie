package com.example.carfinder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.carfinder.accountdata.User;

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
    
    @GetMapping("/cars/price")
    @ResponseBody
    public ResponseEntity <List<Car>> getCarByPrice(@RequestParam(required = true) Integer minPrice,
    		@RequestParam(required = true) Integer maxPrice) {
    	
    	List<Car> test = cd.selectByPrice(minPrice, maxPrice);
    	
        if(test.size() == 0) {
        	return new ResponseEntity <List<Car>> (HttpStatus.BAD_REQUEST);
        }
    	return new ResponseEntity <List<Car>> (cd.selectByPrice(minPrice, maxPrice),HttpStatus.OK); 
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
   public ResponseEntity <List<Car>> getSpecializedCarId (@RequestParam(required = true) Integer carid){
    	List<Car> test = cd.selectAll();

    	for(int i = 0; i < test.size(); i++) {
    		
        if(carid.equals(test.get(i).carid)) {
        	 return new ResponseEntity <List<Car>> (cd.selectById(carid),HttpStatus.OK);  
        }        
   }
    	return new ResponseEntity <List<Car>> (HttpStatus.BAD_REQUEST);
}
    
    @GetMapping("/cars/name")
    @ResponseBody
   public ResponseEntity <List<Car>> getSpecializedCarName (@RequestParam(required = true) String names){
    	List<Car> test = cd.selectAll();
    	Car testCar;
    	
    	for(int i=0; i < test.size(); i++) {
    		 testCar = test.get(i);
    		 if(testCar.model.equalsIgnoreCase(names)) {
    			 return new ResponseEntity <List<Car>> (cd.searchByName(names),HttpStatus.OK);
    		 }
    	}
    	
    	return new ResponseEntity <List<Car>> (HttpStatus.BAD_REQUEST);
    }
    

	@SuppressWarnings("rawtypes")
	@PostMapping(
  		  value = "/cars", 
  		  produces = "application/json")
  public ResponseEntity addCar(@RequestBody Car car) {
    	personalize = new PersonalizedCar();

    	if(personalize.Validcar(car) == false) {
    		return new ResponseEntity (HttpStatus.BAD_REQUEST);
    	}else {
    		cd.create(car);	
    	}
    		return new ResponseEntity (HttpStatus.CREATED);
    
  }
    
    
}