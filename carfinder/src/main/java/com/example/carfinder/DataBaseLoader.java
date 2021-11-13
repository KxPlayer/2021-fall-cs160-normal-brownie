package com.example.carfinder;

import javax.sql.DataSource;

import com.example.carfinder.accountdata.*;

import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLErrorCodes;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataAccessException;

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
        Car c1 = new Car();
        c1.make = "Toyota";
        c1.model = "Camry";
        c1.price = 25000;
        c1.year = 2020;
        c1.doors = 4;
        c1.fueltype = "Gas";
        c1.epapassenger = 1000;
        c1.transmission = "Automatic";
        c1.engine = "4-Cyl, 2.5 Liter";
        c1.weight = 3310;
        c1.length = 192.1;
        c1.width = 72.4;
        c1.towingcapacity = 0;
        c1.trunkcapacity = 15.1;
        c1.horsepower = 203;
        c1.horsepowerrpm = 6600;
        c1.torque = 184;
        c1.torquerpm = 5000;
        c1.mpgcity = 28;
        c1.mpghighway = 39;
        c1.mpgcombined = 32;
        c1.luxury = false;
        c1.sport = false;

        Car c2 = new Car();
        c2.make = "Ford";
        c2.model = "Mustang MACH-E";
        c2.price = 42895;
        c2.year = 2021;
        c2.doors = 4;
        c2.fueltype = "Electric";
        c2.epapassenger = 0;
        c2.transmission = "n/a";
        c2.engine = "Electric Motor";
        c2.weight = 4394;
        c2.length = 186;
        c2.width = 74.1;
        c2.towingcapacity = 0;
        c2.trunkcapacity = 59.7;
        c2.horsepower = 266;
        c2.horsepowerrpm = 0;
        c2.torque = 317;
        c2.torquerpm = 0;
        c2.mpgcity = 105;
        c2.mpghighway = 100;
        c2.mpgcombined = 93;
        c2.luxury = false;
        c2.sport = true;

        Car c3 = new Car();
        c3.make = "BMW";
        c3.model = "sorcery";
        c3.price = 60000;
        c3.year = 2024;
        c3.doors = 4;
        c3.fueltype = "Gas";
        c3.epapassenger = 1000;
        c3.transmission = "Manual";
        c3.engine = "100-Cyl, 3.7 Gallon";
        c3.weight = 3310;
        c3.length = 192.1;
        c3.width = 72.4;
        c3.towingcapacity = 0;
        c3.trunkcapacity = 15.1;
        c3.horsepower = 203;
        c3.horsepowerrpm = 6600;
        c3.torque = 184;
        c3.torquerpm = 5000;
        c3.mpgcity = 28;
        c3.mpghighway = 70;
        c3.mpgcombined = 70;
        c3.luxury = true;
        c3.sport = true;

        Car c4 = new Car();
        c4.make = "Fish";
        c4.model = "Musky";
        c4.price = 4000;
        c4.year = 2023;
        c4.doors = 4;
        c4.fueltype = "Electric";
        c4.epapassenger = 1000;
        c4.transmission = "Automatic";
        c4.engine = "100-Water, 12 liter";
        c4.weight = 3310;
        c4.length = 192.1;
        c4.width = 72.4;
        c4.towingcapacity = 0;
        c4.trunkcapacity = 15.1;
        c4.horsepower = 203;
        c4.horsepowerrpm = 6600;
        c4.torque = 184;
        c4.torquerpm = 5000;
        c4.mpgcity = 28;
        c4.mpghighway = 70;
        c4.mpgcombined = 70;
        c4.luxury = false;
        c4.sport = false;

        cd.create(c1);
        cd.create(c2);
        cd.create(c3);
        cd.create(c4);
        
        System.out.println("-------------");
        List<Car> cars = cd.selectAll();
        cars.forEach(System.out::println);
        System.out.println("-------------");
        CarParams pr = new CarParams();
        //pr.doors(4);
        pr.minprice(10000);
        pr.maxprice(50000);
        //pr.make("BMW");
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
