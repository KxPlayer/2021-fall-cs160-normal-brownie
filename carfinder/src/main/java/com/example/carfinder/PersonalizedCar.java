package com.example.carfinder;

import com.example.carfinder.CarParams;


public class PersonalizedCar extends CarParams{

	private CarParams pr;
		 
	public CarParams getPersonalizedCar(Integer minPrice,Integer maxPrice,Integer minYear,
			 Integer maxYear, Integer doors, String transmission, 
			 String make, String fueltype, Integer minmpgcombined,
			 Boolean luxury, Boolean sport){
        
      pr = new CarParams();
     
      if(minPrice != null) {
    	  pr.minprice(minPrice);
      }
      if(maxPrice != null) {
    	  pr.maxprice(maxPrice);
      }
      if(minYear != null) {
    	  pr.minyear(minYear);
      }
      if(maxYear != null) {
    	  pr.maxyear(maxYear);
      }
      if(doors != null) {
    	  pr.doors(doors);
      }
      if(transmission != null) {
    	  pr.transmission(transmission);
      }
      if(make != null) {
    	  pr.make(make);
      }
      if(fueltype != null) {
    	  pr.fueltype(fueltype);
      }
      if(minmpgcombined != null) {
    	  pr.minmpgcombined(minmpgcombined);
      }
      if(luxury != null) {
    	  pr.luxury(luxury);
      }
      if(sport != null) {
    	  pr.sport(sport);
      }else {
    	  System.out.print("error");
      }
    		 
      return pr;
	}
	 
	 //works
	 public CarParams getPersonalizedCar(String make) {
		    
		 pr = new CarParams();
		 pr.make(make);
		 
		  System.out.println("EnteredHere");
		 
		 return pr;
	 }
	 //works
	 public CarParams getPersonalizedCarDoor(int doors) {
		    
		 pr = new CarParams();
		 pr.doors(doors);
		  
		 return pr;
	 }
	 //works
	 public CarParams getPersonalizedCarTransmission(String transmission) {
		    
		 pr = new CarParams();
		 pr.transmission(transmission);		  
		 return pr;
	 }
	 
	 //works,case sensitive
	 public CarParams getPersonalizedCarFuel(String fueltype) {
		    
		 pr = new CarParams();
		 pr.fueltype(fueltype);		  
		 return pr;
	 }
	 // works
	 public CarParams getPersonalizedCarMPG(int minmpgcombined) {
		    
		 pr = new CarParams();
		 pr.minmpgcombined(minmpgcombined);
		  
		 return pr;
	 }
	 //works
	 public CarParams getPersonalizedCarLuxury(boolean luxury) {
		    
		 pr = new CarParams();
		 pr.luxury(luxury);
		  
		 return pr;
	 }
	 //works
	 public CarParams getPersonalizedCarSport(boolean sport) {
		    
		 pr = new CarParams();
		 pr.sport(sport);
		  
		 return pr;
	 }
	 //works
	 public CarParams getPersonalizedCarMinPrice(int minPrice) {
		    
		 pr = new CarParams();
		 pr.minprice(minPrice);
		  
		 return pr;
	 }
	 //works
	 public CarParams getPersonalizedCarMaxPrice(int maxPrice) {
		    
		 pr = new CarParams();
		 pr.maxprice(maxPrice);
		  
		 return pr;
	 }
	 
	 //works
	 public CarParams getPersonalizedCarMinYear(int minYear) {
		    
		 pr = new CarParams();
		 pr.minyear(minYear);
		  
		 return pr;
	 }
	 //works
	 public CarParams getPersonalizedCarMaxYear(int maxYear) {
		    
		 pr = new CarParams();
		 pr.maxyear(maxYear);
		  
		 return pr;
	 }
	 //works
	 public CarParams getPersonalizedCarCombo(boolean luxury,boolean sport) {
		    
		 pr = new CarParams();
		 pr.sport(sport);
		 pr.luxury(luxury);
		  
		 return pr;
	 }
	 
}
