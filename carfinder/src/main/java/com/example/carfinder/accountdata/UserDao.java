package com.example.carfinder.accountdata;
import javax.sql.DataSource;
import java.util.List;

public interface UserDao {
    public void setDataSource(DataSource ds);

    //creates user
    public void create(String username, String password);

    public void delete(int userid);

    public void editPassword(int userid, String newpassword); 

    public User getUser(String username);

    public User getUser(int userid);

    public List<User> getAll();

}
