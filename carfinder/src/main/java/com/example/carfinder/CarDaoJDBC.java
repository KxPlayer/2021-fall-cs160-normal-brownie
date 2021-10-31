package com.example.carfinder;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
//data access object
@Component
public class CarDaoJDBC implements CarDao {
    private DataSource datasource;
    public void setDataSource(DataSource ds) {
        datasource = ds;
    }
	@Override
	public void create(Car c) {
        JdbcTemplate insert = new JdbcTemplate(datasource);
        insert.update("insert into car (make, model, price, year, doors, fueltype," + 
        "epapassenger, transmission, engine, weight, length, width, towingcapacity, trunkcapacity," +
        "horsepower, horsepowerrpm, torque, torquerpm, mpgcity, mpghighway, mpgcombined, luxury, sport)" + 
        " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                new Object[]{c.make, c.model, c.price, c.year, c.doors, c.fueltype, c.epapassenger,
                c.transmission, c.engine, c.weight, c.length, c.width, c.towingcapacity, 
                c.trunkcapacity, c.horsepower, c.horsepowerrpm, c.torque, c.torquerpm,
                c.mpgcity, c.mpghighway, c.mpgcombined, c.luxury, c.sport});
	}
    public List<Car> selectAll() {
        JdbcTemplate select = new JdbcTemplate(datasource);
        return select.query("select * from car", new CarMapper());
    }
    
    public List<Car> selectByParams(CarParams cp) {
        NamedParameterJdbcTemplate select = new NamedParameterJdbcTemplate(datasource);
        StringJoiner sj = new StringJoiner(" and ", " where ", "");
        sj.setEmptyValue("");
        Map<String, Object> mp = cp.getMap();
        String sql = "select * from car";
        if (mp.containsKey("minyear"))
            sj.add("year >= :minyear");
        if (mp.containsKey("maxyear"))
            sj.add("year <= :maxyear");
        if (mp.containsKey("minprice"))
            sj.add("price >= :minprice");
        if (mp.containsKey("maxprice"))
            sj.add("price <= :maxprice");
        if (mp.containsKey("doors"))
            sj.add("doors = :doors");
        if (mp.containsKey("transmission"))
            sj.add("transmission = :transmission");
        if (mp.containsKey("make"))
            sj.add("make = :make");
        if (mp.containsKey("fueltype"))
            sj.add("fueltype = :fueltype");
        if (mp.containsKey("minmpgcombined"))
            sj.add("mpgcombined >= :minmpgcombined");
        if (mp.containsKey("luxury"))
            sj.add("luxury = :luxury");
        if (mp.containsKey("sport"))
            sj.add("sport = :sport");
        return select.query(sql + sj, mp, new CarMapper());
    }
	
    public List<Car> selectByPrice(int minPrice, int maxPrice) {
        JdbcTemplate select = new JdbcTemplate(datasource);
        String sql = "select * from car where price between ? and ?";
        return select.query(sql, new CarMapper(), new Object[]{minPrice, maxPrice});
    }
    

    public List<Car> selectById(int carid) {
        JdbcTemplate select = new JdbcTemplate(datasource);
        String sql = "select * from car where carid = ?";
        return select.query(sql, new CarMapper(), new Object[]{carid});
    }

    public List<Car> searchByName(String s) {
        String[] words = s.split("\\s+");
        JdbcTemplate select = new JdbcTemplate(datasource);
        String sql = "select * from car";
        StringJoiner sj = new StringJoiner(" and ", " where ", "");
        sj.setEmptyValue("");
        Object[] doublewords = new Object[words.length*2];
        for (int i = 0; i < words.length; i++) {
            String newstring = words[i] + "%";
            doublewords[2*i] = newstring;
            doublewords[2*i+1] = newstring;
            sj.add("(lower(make) like ? or lower(model) like ?)");
        }
        return select.query(sql + sj, new CarMapper(), (Object[])doublewords);
    }
}