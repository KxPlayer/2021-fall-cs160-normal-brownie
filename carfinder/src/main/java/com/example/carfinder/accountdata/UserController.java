package com.example.carfinder.accountdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @ResponseStatus(HttpStatus.OK)
	   public List<User> greeting() {
	       return cd.getAll();
	   }
    
    @GetMapping("/users/id")
    @ResponseBody
	   public ResponseEntity <User> byId(@RequestParam(required = true) Integer id) {
    	
    	List<User> test = cd.getAll();

    	for(int i = 0; i < test.size(); i++) {
    		
        if(id.equals(test.get(i).userid)) {
        	 return new ResponseEntity <User> (cd.getUser(id),HttpStatus.OK);  
        }        
   }
    	return new ResponseEntity <User> (HttpStatus.BAD_REQUEST);
}
    
    @GetMapping("/users/username")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
	   public ResponseEntity <User> byUsername(@RequestParam(required = true) String username) {
    	
    	List<User> test = cd.getAll();
    	User testUser;
    	
    	for(int i=0; i < test.size(); i++) {
    		 testUser = test.get(i);
    		 if(testUser.username.equalsIgnoreCase(username)) {
    			 return new ResponseEntity <User> (cd.getUser(username),HttpStatus.OK);
    		 }
    	}
    	return new ResponseEntity <User> (HttpStatus.BAD_REQUEST);
	   }
	    
    @PostMapping(
    		  value = "/users", 
    		  produces = "application/json")
      @ResponseStatus(HttpStatus.OK)
    public void addAccount(@RequestBody User account) {
      	cd.create(account.username, account.password);
    }
    
    @SuppressWarnings("rawtypes")
	@DeleteMapping(value = "/users/userid")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity deleteAccount(@RequestParam(required = true) Integer userid) {
    	List<User> test = cd.getAll();

    	for(int i = 0; i < test.size(); i++) {
    		
        if(userid.equals(test.get(i).userid)) {
        	 cd.delete(userid);
        	 return new ResponseEntity (HttpStatus.OK);  
        }        
   }
    	return new ResponseEntity (HttpStatus.BAD_REQUEST);    
}
    
    @PutMapping("/users/password")
    public void updatePassword(@RequestParam(required = true) Integer userid,
    		@RequestParam(required = true) String password) {
    	cd.editPassword(userid, password);

    }
    
}
