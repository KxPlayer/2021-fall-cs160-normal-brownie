package com.example.carfinder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {

    @Autowired
    private CarDao cd;

	@GetMapping("/cars")
	public List<Car> greeting() {
		return cd.selectAll();
	}
}