package com.example.carfinder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class CarController {

    @Autowired
    private CarDao cd;

    @GetMapping("/cars")
    public List<Car> greeting() {
        return cd.selectAll();
    }
    
    @GetMapping("/cars/doors")
    public List<Car> greeting2() {
    	 return cd.selectDoors();
    }
    
    @GetMapping("/cars/year")
    public List<Car> greeting3() {
    	 return cd.selectYear();
    	// return cd.selectByYear(2022);
    }
    
    @GetMapping("/cars/model")
    public List<Car> greeting4() {
    	 return cd.selectModel();
    	// return cd.selectByModel("Honda Civic");
    }
}
