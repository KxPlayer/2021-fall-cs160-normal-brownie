package com.example.carfinder.accountdata;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserDaoJDBC implements UserDao {
    private DataSource datasource;

    @Override
    public void setDataSource(DataSource ds) {
        datasource = ds;
    }

    @Override
    public void create(String username, String password) {
        JdbcTemplate db = new JdbcTemplate(datasource);
        String sql = "insert into user (username, password) values(?,?)";
        db.update(sql, new Object[]{username, password});
    }

    @Override
    public void delete(int userid) {
        JdbcTemplate db = new JdbcTemplate(datasource);
        String sql = "delete from user where userid = ?";
        db.update(sql, new Object[]{userid});
    }

    @Override
    public void editPassword(int userid, String newpassword) {
        JdbcTemplate db = new JdbcTemplate(datasource);
        String sql = "update user set password = ? where userid = ?;";
        db.update(sql, new Object[]{newpassword, userid});
    }

    @Override
    public User getUser(String username) {
        JdbcTemplate db = new JdbcTemplate(datasource);
        String sql = "select * from user where username = ?";
        List<User> res = db.query(sql, new UserMapper(), new Object[]{username});
        if (res.size() == 0) {
            return null;
        } else {
        return res.get(0);
        }
    }

    public User getUser(int userid) {
        JdbcTemplate db = new JdbcTemplate(datasource);
        String sql = "select * from user where userid = ?";
        List<User> res = db.query(sql, new UserMapper(), new Object[]{userid});
        if (res.size() == 0) {
            return null;
        } else {
        return res.get(0);
        }
    }

    @Override
    public List<User> getAll() {
        JdbcTemplate select = new JdbcTemplate(datasource);
        return select.query("select * from user", new UserMapper());
    }
    
}
