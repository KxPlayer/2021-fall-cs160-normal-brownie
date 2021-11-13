package com.example.carfinder.accountdata;
import javax.sql.DataSource;

import com.example.carfinder.Car;

import java.util.List;

public interface FavoriteDao {
    public void setDataSource(DataSource ds);

    //creates user
    public void add(int userid, int carid);

    public void delete(int userid, int carid);

    public List<Car> getUserFavorites(int userid); 

}