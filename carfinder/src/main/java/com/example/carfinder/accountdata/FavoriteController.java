package com.example.carfinder.accountdata;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.carfinder.Car;
import com.example.carfinder.CarDao;

@RestController
@CrossOrigin
public class FavoriteController {

	@Autowired
	private FavoriteDao cd;

	@Autowired
	private UserDao ud;

	@Autowired
	private CarDao cdb;

	@GetMapping("/favorites/id")
	@ResponseBody
	public ResponseEntity<List<Car>> byId(@RequestParam(required = true) Integer id) {

		List<User> test = ud.getAll();

		for (int i = 0; i < test.size(); i++) {

			if (id.equals(test.get(i).userid)) {
				return new ResponseEntity<List<Car>>(cd.getUserFavorites(id), HttpStatus.OK);
			}
		}
		return new ResponseEntity<List<Car>>(HttpStatus.BAD_REQUEST);
	}

	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/favorites/user", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity addAccount(@RequestParam(required = true) Integer userid,
			@RequestParam(required = true) Integer carid) {

		List<User> test = ud.getAll();
		List<Car> car = cdb.selectById(carid);

		for (int i = 0; i < test.size(); i++) {

			if (car.size() != 0 && userid.equals(test.get(i).userid)) {
				cd.add(userid, carid);
				return new ResponseEntity(HttpStatus.OK);
			}
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

	@SuppressWarnings("rawtypes")
	@DeleteMapping(value = "/favorites/userid")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity deleteAccount(@RequestParam(required = true) Integer userid,
			@RequestParam(required = true) Integer carid) {
		List<User> test = ud.getAll();
		List<Car> car = cdb.selectById(carid);

		for (int i = 0; i < test.size(); i++) {

			if (car.size() != 0 && userid.equals(test.get(i).userid)) {
				cd.delete(userid, carid);
				return new ResponseEntity(HttpStatus.OK);
			}
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

}
