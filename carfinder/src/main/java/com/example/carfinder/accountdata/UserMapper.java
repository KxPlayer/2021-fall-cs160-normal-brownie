package com.example.carfinder.accountdata;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.userid = rs.getInt(1);
        user.username = rs.getString(2);
        user.password = rs.getString(3);
        return user;
    }
    
}