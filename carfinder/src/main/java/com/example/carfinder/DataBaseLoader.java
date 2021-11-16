package com.example.carfinder;

import javax.sql.DataSource;

import com.example.carfinder.accountdata.*;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

@Component
public class DataBaseLoader implements CommandLineRunner{
    @Autowired
    private DataSource ds;

    @Autowired
    private CarDao cd;

    @Autowired
    private UserDao ud;

    @Autowired
    private FavoriteDao fd;

    @Override
    public void run(String... args) throws Exception {
        cd.setDataSource(ds);
        ud.setDataSource(ds);
        fd.setDataSource(ds);

        //create table car
        JdbcTemplate jt = new JdbcTemplate(ds);
        jt.execute("drop table if exists car");
        jt.execute("create table car(" 
            + "carid int auto_increment primary key,"
            + "make varchar(255),"
            + "model varchar(255)," 
            + "price int," 
            + "year int," 
            + "doors int,"
            + "fueltype varchar(255),"
            + "epapassenger int,"
            + "transmission varchar(255),"
            + "engine varchar(255)," 
            + "weight numeric(9,2),"
            + "length numeric(9,2),"
            + "width numeric(9,2),"
            + "towingcapacity int,"
            + "trunkcapacity numeric(9,2),"
            + "horsepower int,"
            + "horsepowerrpm int,"
            + "torque int,"
            + "torquerpm int,"
            + "mpgcity int,"
            + "mpghighway int,"
            + "mpgcombined int,"
            + "luxury Boolean,"
            + "sport Boolean)");

        //create user table
        jt.execute("drop table if exists user");
        jt.execute("create table user("
            + "userid int auto_increment primary key,"
            + "username varchar(50) unique,"
            + "password varchar(50))"
        );

        jt.execute("drop table if exists favorite");
        jt.execute("create table favorite("
            + "userid int,"
            + "carid int,"
            + "foreign key (userid) references user(userid),"
            + "foreign key (carid) references car(carid)"
            + ")"
        );
        
        //populate with cars
        List<CarCSV> cars = new CsvToBeanBuilder<CarCSV>(
            new CSVReaderBuilder(new FileReader("carspreadsheet.csv"))
            .withSkipLines(1).build())
        .withType(CarCSV.class)
        .build()
        .parse();
        
        for (int i = 0; i < cars.size(); i++) {
            Car newObject = new Car(); 
            BeanUtils.copyProperties(cars.get(i), newObject);  
            cd.create(newObject);
        }

        //testing insertion works
        System.out.println("-------------");
        List<Car> cars0 = cd.selectAll();
        cars0.forEach(System.out::println);
        System.out.println("-------------");
        CarParams pr = new CarParams();
        pr.minprice(10000);
        pr.maxprice(50000);
        List<Car> cars4 = cd.selectByParams(pr);
        cars4.forEach(System.out::println);
        System.out.println(pr.getMap());
        System.out.println("-------------");
        List<Car> cars2 = cd.searchByName("ford mu");
        cars2.forEach(System.out::println);

        //populate with users
        ud.create("tom", "password");
        ud.create("tim", "password");
        ud.create("tam", "password");
        ud.create("tum", "password");
        ud.create("timbo", "password");
        ud.create("tip", "password");
        ud.create("tap", "password");
        System.out.println("-------------");
        List<User> u1 = ud.getAll();
        u1.forEach(System.out::println);

        ud.delete(2);
        ud.delete(4);
        ud.editPassword(3, "newpassword");
        System.out.println("-------------");
        List<User> u2 = ud.getAll();
        u2.forEach(System.out::println);

        fd.add(1, 1);
        fd.add(1, 2);
        fd.add(1, 3);

        System.out.println("-------------");
        List<Car> f1 = fd.getUserFavorites(1);
        f1.forEach(System.out::println);

        fd.delete(1, 2);

        System.out.println("-------------");
        List<Car> f2 = fd.getUserFavorites(1);
        f2.forEach(System.out::println);
        /*
        try {
            fd.add(8, 5);
        }
        catch (DataAccessException sd) {
            System.out.println("data error");
        }
        */


    }
}