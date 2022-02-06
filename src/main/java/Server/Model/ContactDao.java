package Server.Model;

import Server.Util.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        
        ResultSet res = db.executeQuery("select * from contact where person_id = "+ idPerson +" order by person_id desc limit 1");
        
        UserDao ud = new UserDao();
        User u = ud.getById(idFriend);
        u.setIdContact(res.getInt("id"));
        return u;
    }
    
    public void update(int idPerson, int idFriend) throws SQLException{
        db.executeUpdate("update contact set friend_id = "+idFriend+" where person_id = "+idPerson);
    }
    
    public void delete(int idPerson, int idFriend) throws SQLException{
        db.executeUpdate("delete from contact where person_id = "+idPerson+" and friend_id = " + idFriend);
    }
    
    public Contact getById(int id) throws SQLException{
        UserDao ud = new UserDao();
        Contact c = new Contact();
        ResultSet result = db.executeQuery("select * from contact where id = "+id);
        
        if (result.next()) {
            c.setPerson(ud.getById(result.getInt("person_id")));
            c.setFriend(ud.getById(result.getInt("friend_id")));
        }
        return c;
    }
    
    public List<Contact> getAll() throws SQLException{
        List<Contact> contacts = new ArrayList<>();
        UserDao ud = new UserDao();
        ResultSet result = db.executeQuery("select * from contact");
        while (result.next()) {            
            contacts.add(new Contact(ud.getById(result.getInt("person_id")), ud.getById(result.getInt("friend_id"))));
        }
        return contacts;
    }
    
    public List<Integer> getAllCodes(int idPerson) throws SQLException{
        List<Integer> contacts = new ArrayList<>();
        ResultSet result = db.executeQuery("select id from contact where person_id = " + idPerson);
        while (result.next()) {
            contacts.add(result.getInt("id"));
        }
        return contacts;
    }
}
