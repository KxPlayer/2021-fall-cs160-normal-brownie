package com.example.carfinder;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

//data access object
@Component
public class CarDaoJDBC implements CarDao {
    private DataSource datasource;

    public void setDataSource(DataSource ds) {
        datasource = ds;
    }

    public void create(String model, int year, int price, int doors) {
        JdbcTemplate insert = new JdbcTemplate(datasource);
        insert.update("insert into car (model, year, price, doors) values(?,?,?,?)",
                new Object[]{model, year, price, doors});
    }

    public List<Car> selectAll() {
        JdbcTemplate select = new JdbcTemplate(datasource);
        return select.query("select model, year, price, doors from car", new CarMapper());
    }

    public List<Car> selectByPrice(int minPrice, int maxPrice) {
        JdbcTemplate select = new JdbcTemplate(datasource);
        String sql = "select model, year, price, doors from car where price between ? and ?";
        return select.query(sql, new CarMapper(), new Object[]{minPrice, maxPrice});
    }
}
