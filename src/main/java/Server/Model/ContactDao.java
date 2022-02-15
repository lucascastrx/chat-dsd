package Server.Model;

import Server.Util.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ContactDao {
    private Connection connection;
    private Database db;

    public ContactDao() {
        connection = Database.getInstance().getConnection();
        db = Database.getInstance();
    }
    
    public User insert(int idPerson, int idFriend) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("insert into contact (person_id, friend_id) values (?, ?)");
        ps.setInt(1, idPerson);
        ps.setInt(2, idFriend);
        ps.execute();
        ps.close();
        
//        ResultSet res = db.executeQuery("select * from contact where person_id = "+ idPerson +" order by person_id desc limit 1");
//        
//        UserDao ud = new UserDao();
//        User u = ud.getById(idFriend);
//        u.setIdContact(res.getInt("id"));
        return null;
    }
    
    public void update(int idPerson, int idFriend) throws SQLException{
        db.executeUpdate("update contact set friend_id = "+idFriend+" where person_id = "+idPerson);
    }
    
    public void delete(int idPerson, int idFriend) throws SQLException{
        db.executeUpdate("delete from contact where person_id = "+idPerson+" and friend_id = " + idFriend);
        db.executeUpdate("delete from contact where person_id = "+idFriend+" and friend_id = " + idPerson);
    }
    
    public Contact getById(int id) throws SQLException{
        UserDao ud = new UserDao();
        Contact c = new Contact();
        Statement state = connection.createStatement();
        ResultSet result = state.executeQuery("select * from contact where id = "+id);
        
        if (result.next()) {
            c.setPerson(ud.getById(result.getInt("person_id")));
            c.setFriend(ud.getById(result.getInt("friend_id")));
        }
        state.close();
        return c;
    }
    
    public List<Contact> getAll() throws SQLException{
        
        List<Contact> contacts = new ArrayList<>();
        Statement state = connection.createStatement();
        ResultSet result = state.executeQuery("select * from contact");
        UserDao ud = new UserDao();
        while (result.next()) {
            contacts.add(new Contact(ud.getById(result.getInt("person_id")), ud.getById(result.getInt("friend_id"))));
        }
        state.close();
        
        return contacts;
    }
    
    public List<Integer> getAllCodes(int idPerson) throws SQLException{
        List<Integer> contacts = new ArrayList<>();
        Statement state = connection.createStatement();
        ResultSet result = state.executeQuery("select id from contact where person_id = " + idPerson);
        while (result.next()) {
            contacts.add(result.getInt("id"));
        }
        state.close();
        return contacts;
    }
    
    public List<User> getByPersonId(int idPerson) throws SQLException{
        List<User> users = new ArrayList<>();
        Statement state = connection.createStatement();
        ResultSet result = state.executeQuery("select * from contact where person_id = " + idPerson + " or friend_id = " + idPerson );
        UserDao ud = new UserDao();
        while (result.next()) {
            if (result.getInt("person_id") == idPerson) {
                users.add(ud.getById(result.getInt("friend_id")));
            } else {
                users.add(ud.getById(result.getInt("person_id")));
            }
        }
        state.close();
        return users;
    }
}
