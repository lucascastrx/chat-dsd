package Server.Model;

import Server.Util.Cryptography;
import Server.Util.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private Connection connection;
    private Database db;

    public UserDao() {
        connection = Database.getInstance().getConnection();
        db = Database.getInstance();
    }
    
    public int insert(String username, String name, String password) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("insert into user (username, name, password) values (?, ?, ?)");
        ps.setString(1, username);
        ps.setString(2, name);
        ps.setString(3, Cryptography.md5(password));
        ps.execute();
        ps.close();
        
        return db.executeQuery("select id from user where username = '" + username + "'").getInt("id");
    }
    
    public void update(String username, String name, String password, int id) throws SQLException{
        db.executeUpdate("update user set username = '"+ username +"', name = '"+name+"', password = '"+Cryptography.md5(password)+"' where id = "+id);
    }
    
    public void delete(int id) throws SQLException{
        db.executeUpdate("delete from user where id = "+id);
    }
    
    public User getById(int id) throws SQLException{
        ResultSet result = db.executeQuery("select * from user where id = "+id);
        if (result.next()) {
            return new User(result.getInt("id"), result.getString("username"), result.getString("name"), true);
        }
        return null;
    }
    
    public List<User> getAll() throws SQLException{
        List<User> users = new ArrayList<>();
        ResultSet result = db.executeQuery("select * from user");
        while (result.next()) {            
            users.add(new User(result.getInt("id"), result.getString("username"), result.getString("name"), true));
        }
        return users;
    }
    
    public List<User> getByUsername(String username) throws SQLException{
        List<User> users = new ArrayList<>();
        ResultSet result = db.executeQuery("select * from user where username like '%"+ username +"%'");
        while (result.next()) {            
            users.add(new User(result.getInt("id"), result.getString("username"), result.getString("name"), true));
        }
        return users;
    }
    
    public List<User> getByName(String name) throws SQLException{
        List<User> users = new ArrayList<>();
        ResultSet result = db.executeQuery("select * from user where name like '%"+ name +"%'");
        while (result.next()) {            
            users.add(new User(result.getInt("id"), result.getString("username"), result.getString("name"), true));
        }
        return users;
    }
    
    public User login(String username, String password) throws SQLException{
        ResultSet result = db.executeQuery("select * from user where username = '"+username+"' and password = '"+ Cryptography.md5(password) +"'");
        if (result.next()) {
            return new User(result.getInt("id"), result.getString("username"), result.getString("name"), true);
        }
        return null;
    }
    
    
    
}
