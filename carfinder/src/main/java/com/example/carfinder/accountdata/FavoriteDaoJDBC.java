package com.example.carfinder.accountdata;

import java.util.List;

import javax.sql.DataSource;

import com.example.carfinder.Car;
import com.example.carfinder.CarMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FavoriteDaoJDBC implements FavoriteDao{
    private DataSource datasource;

    @Override
    public void setDataSource(DataSource ds) {
        datasource = ds;
    }

    @Override
    public void add(int userid, int carid) {
        JdbcTemplate db = new JdbcTemplate(datasource);
        String sql = "insert into favorite (userid, carid) values(?,?)";
        db.update(sql, new Object[]{userid, carid});
    }

    @Override
    public void delete(int userid, int carid) {
        JdbcTemplate db = new JdbcTemplate(datasource);
        String sql = "delete from favorite where userid = ? and carid = ?";
        db.update(sql, new Object[]{userid, carid});        
    }

    @Override
    public List<Car> getUserFavorites(int userid) {
        JdbcTemplate db = new JdbcTemplate(datasource);
        String sql = "select car.* from car inner join favorite where favorite.userid = ? " +
        "and favorite.carid = car.carid";
        return db.query(sql, new CarMapper(), new Object[]{userid});        
    }

}