package com.example.carfinder;

import com.example.carfinder.CarParams;


public class PersonalizedCar extends CarParams{

	private CarParams pr;
		 
	 public CarParams getPersonalizedCar(Integer minPrice,Integer maxPrice,Integer minYear,
			 Integer maxYear, Integer doors, String transmission, 
			 String make, String fueltype, Integer minmpgcombined,
			 boolean luxury, boolean sport){
        
      pr = new CarParams();
      pr.minprice(minPrice);
      pr.maxprice(maxPrice);
      pr.minyear(minYear);
      pr.maxyear(maxYear);
      pr.doors(doors);
      pr.transmission(transmission);
      pr.make(make);
      pr.fueltype(fueltype);
      pr.minmpgcombined(minmpgcombined);
      pr.luxury(luxury);
      pr.sport(sport);
      
      return pr;
	
	 }
}
