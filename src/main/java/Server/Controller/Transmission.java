package Server.Controller;

import Server.Model.Contact;
import Server.Model.ContactDao;
import Server.Model.UserDao;
import java.net.Socket;

public class Transmission extends Thread {
    private Socket conn;
    private Contact contact;
    private UserDao ud;
    private ContactDao cd;
    
    public Transmission(Socket conn){
        this.ud = new UserDao();
        this.cd = new ContactDao();
        this.conn = conn;
    }

    public Socket getConn() {
        return conn;
    }
    
    public void setContactStatus(boolean status){
        this.contact.getPerson().setIsOnline(status);
    }
}
