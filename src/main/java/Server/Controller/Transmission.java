package Server.Controller;

import Server.Model.Contact;
import Server.Model.ContactDao;
import Server.Model.Message;
import Server.Model.UserDao;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Transmission extends Thread {
    private Socket conn;
    private Contact contact;

    
    public Transmission(Socket conn) throws IOException{
        this.conn = conn;
    }
    
    @Override
    public void run(){
        try {
            InputStream is = conn.getInputStream();
            
            do{
                Gson gson = new Gson();
                String json = read(is);
                Message msg = gson.fromJson(json, Message.class);
                Operation operation = new Operation(msg);
                
                try {
                    switch(msg.getAction()){
                    case Message.REGISTER:
                        msg = operation.register();
                        break;
                    case Message.LOGIN:
                        msg = operation.login();
                        break;
                    case Message.ADD_CONTACT:
                        msg = operation.addContact();
                        break;
                    case Message.REMOVE_CONTACT:
                        msg = operation.removeContact();
                        break;
                    case Message.UPDATE_ACCOUNT:
                        msg = operation.updateAccount();
                        break;
                    case Message.CHECK_CONTACT_STATUS:
                        msg = operation.checkContactStatus();
                        break;
                    case Message.GET_CONTACTS:
                        msg = operation.getContacts();
                        break;
                    case Message.GET_USER:
                        msg = operation.getUser();
                    }
                    sendMessage(msg);
                } catch (SQLException ex) {
                    Logger.getLogger(Transmission.class.getName()).log(Level.SEVERE, null, ex);
                    msg.setStatus(Message.FAIL);
                    msg.setContent("O nome de usuario deve ser unico.");
                    sendMessage(msg);
                    System.err.println(ex);
                }
                
            } while(true);
        } catch (JsonSyntaxException | IOException e) {
            System.err.println(e);
        }
    }

    public Socket getConn() {
        return conn;
    }
    
    public void setContactStatus(boolean status){
        this.contact.getPerson().setIsOnline(status);
    }
    
    private void sendMessage(Message msg){
        try {
            Gson gson = new Gson();
            OutputStream os = conn.getOutputStream();
            String send = gson.toJson(msg);
            os.write(send.getBytes());
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    private String read(InputStream is) throws IOException {
        byte[] data = new byte[1024];
        int bytesRead = is.read(data);
        return new String(data, 0, bytesRead);
    }
}
