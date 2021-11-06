package com.example.carfinder.accountdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;

@RestController
@CrossOrigin
public class UserController {

	
    @Autowired
    private UserDao cd;

    @GetMapping("/users")
	   public List<User> greeting() {
	       return cd.getAll();
	   }
    
    @GetMapping("/users/id")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
	   public User byId(@RequestParam(required = true) Integer id) {
	       return cd.getUser(id);
	   }
    
    @GetMapping("/users/username")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
	   public User byId(@RequestParam(required = true) String username) {
	       return cd.getUser(username);
	   }
	    
    @PostMapping(
    		  value = "/users", 
    		  produces = "application/json")
      @ResponseStatus(HttpStatus.OK)
    public void addCar(@RequestBody User account) {
      	cd.create(account.username, account.password);
    }
    
    @DeleteMapping(value = "/users/userid")
    public void deleteCar(@RequestParam(required = true) Integer userid) {
    	cd.delete(userid);

    }
    
    @PutMapping("/users/password")
    public void updatePassword(@RequestParam(required = true) Integer userid,
    		@RequestParam(required = true) String password) {
    	cd.editPassword(userid, password);

    }
    
}
