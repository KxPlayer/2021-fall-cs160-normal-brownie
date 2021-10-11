package com.example.carfinder;

import javax.sql.DataSource;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

@Component
public class DataBaseLoader implements CommandLineRunner{
    @Autowired
    private DataSource ds;

    @Autowired
    private CarDao cd;

    @Override
    public void run(String... args) throws Exception {
        cd.setDataSource(ds);

        //create table car
        JdbcTemplate jt = new JdbcTemplate(ds);
        jt.execute("drop table if exists car");
        jt.execute("create table car(model varchar(250), year int, price int, doors int)");

        //populate with cars
        cd.create("Toyota Camry", 2020,25000, 4);
        cd.create("Honda Civic", 2019, 22000, 4);
        cd.create("BMW M5", 2022, 103000, 4);
        cd.create("Porsche 911", 2021, 101000, 2);
        cd.create("Hyundai Santa Cruz", 2022, 24000, 4);
        cd.create("Ford Mustang Mach-E", 2022, 43000, 4);
 
        
        
        System.out.println("-------------");
     //   List<Car> cars = cd.selectAll();
        List<Car> cars = cd.selectByDoors(2);
        List<Car> cars2 = cd.selectByYear(2022);
        List<Car> cars3 = cd.selectByModel("Honda Civic");
        cars.forEach(System.out::println);
        cars2.forEach(System.out::println);
        cars3.forEach(System.out::println);
        System.out.println("-------------");
        CarParams pr = new CarParams();
        pr.minyear(2020);
        pr.maxyear(2022);
        pr.doors(4);
        pr.minprice(40000);
        pr.maxprice(50000);
        List<Car> cars4 = cd.selectByParams(pr);
        cars4.forEach(System.out::println);
        System.out.println(pr.getMap());

    }
}