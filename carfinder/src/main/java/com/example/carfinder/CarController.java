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
}
