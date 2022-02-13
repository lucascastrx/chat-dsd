package Server.Controller;

import Server.Model.ContactDao;
import Server.Model.Message;
import Server.Model.User;
import Server.Model.UserDao;
import com.google.gson.Gson;
import java.sql.SQLException;

public class Operation {

    private UserDao user;
    private ContactDao contact;
    private Message msg;
    private String[] inputs;

    public Operation(Message msg) {
        this.user = new UserDao();
        this.contact = new ContactDao();
        this.msg = msg;
        
        this.inputs = msg.getInputs();
    }

    public Message register() throws SQLException {
        int id = user.insert(inputs[0], inputs[1], inputs[2]);
        msg.setInputs(String.valueOf(id));
        msg.setStatus(Message.SUCCESS);
        msg.setContent("");
        return msg;
    }

    public Message login() throws SQLException {
        User u = user.login(inputs[0], inputs[1]);
        if (u != null) {
            Gson gson = new Gson();
            msg.setContent(gson.toJson(u));
            msg.setStatus(Message.SUCCESS);
        } else {
            msg.setStatus(Message.FAIL);
            msg.setContent("");
        }
        return msg;
    }

    public Message addContact() throws SQLException {
        int idFriend = user.getByUsername(inputs[0]).getId();
        int idPerson = user.getByUsername(inputs[1]).getId();
        contact.insert(idPerson, idFriend);
        return msg;
    }

    public Message removeContact() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Message updateAccount() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Message checkContactStatus() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
