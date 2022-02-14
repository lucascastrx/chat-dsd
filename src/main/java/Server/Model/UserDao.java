package Server.Model;

import Server.Util.Cryptography;
import Server.Util.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private Connection connection;
    private Database db;

    public UserDao() {
        connection = Database.getInstance().getConnection();
        db = Database.getInstance();
    }

    public int insert(String name, String username, String password) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("insert into user (username, name, password) values (?, ?, ?)");
        ps.setString(1, username);
        ps.setString(2, name);
        ps.setString(3, Cryptography.md5(password));
        ps.executeUpdate();
        ps.close();
        //db.executeQuery("select id from user where username = '" + username + "'").getInt("id")
        return 0;
    }

    public void update(String username, String name, String password, int id) throws SQLException {
        db.executeUpdate("update user set username = '" + username + "', name = '" + name + "', password = '" + Cryptography.md5(password) + "' where id = " + id);
    }

    public void delete(int id) throws SQLException {
        db.executeUpdate("delete from user where id = " + id);
    }

    public User getById(int id) throws SQLException {
        
        User userR = null;
        Statement state = connection.createStatement();
        ResultSet res = state.executeQuery("select * from user where id = " + id);

        if (res.next()) {
            userR = (new User(res.getInt("id"), res.getString("username"), res.getString("name"), true));
        }
        state.close();

        return userR;
    }

    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();
        Statement state = connection.createStatement();
        ResultSet res = state.executeQuery("select * from user");
        while (res.next()) {
            users.add(new User(res.getInt("id"), res.getString("username"), res.getString("name"), true));
        }
        state.close();
       
        return users;
    }

    public User getByUsername(String username) throws SQLException {
        User userR = null;
        
        Statement state = connection.createStatement();
        ResultSet res = state.executeQuery("select * from user where username like '%" + username + "%'");

        if (res.next()) {
            userR = (new User(res.getInt("id"), res.getString("username"), res.getString("name"), true));
        }
        state.close();
        return userR;
    }

    public List<User> getByName(String name) throws SQLException {
        List<User> users = new ArrayList<>();
        Statement state = connection.createStatement();
        ResultSet res = state.executeQuery("select * from user where name like '%" + name + "%'");
        while (res.next()) {
            users.add(new User(res.getInt("id"), res.getString("username"), res.getString("name"), true));
        }
        state.close();
        
        return users;
    }

    public User login(String username, String password) throws SQLException {
        Statement state = connection.createStatement();
        ResultSet res = state.executeQuery("select * from user where username = '" + username + "' and password = '" + Cryptography.md5(password) + "'");
        User u = null;
        if (res.next()) {
            u = new User(res.getInt("id"), res.getString("username"), res.getString("name"), true);
        }
        state.close();
        return u;
    }

}
