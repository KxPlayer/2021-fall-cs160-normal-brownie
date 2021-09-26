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
        cd.create("Toyota Camry", 2022, 25000, 4);
        cd.create("Honda Civic", 2022, 22000, 4);
        cd.create("BMW M5", 2022, 103000, 4);
        cd.create("Porsche 911", 2022, 101000, 2);
        cd.create("Hyundai Santa Cruz", 2022, 24000, 4);
        cd.create("Ford Mustang Mach-E", 2022, 43000, 4);

        System.out.println("-------------");
        List<Car> cars = cd.selectAll();
        cars.forEach(System.out::println);
        System.out.println("-------------");

    }
}