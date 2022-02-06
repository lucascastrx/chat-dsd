package Client.Model;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Server {
    private static Server instance;
    private String address;
    private int port;
    private Socket connection;
    private ObjectOutputStream os;
    
    public static Server getInstance(){
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }
    
    private Server(){
        this.address = "localhost";
        this.port = 50000;
    }

    public Server(String address, int port) {
        this.address = address;
        this.port = port;
    }
    
    public void sendMessage(Message message){
        try {
            Gson gs = new Gson();
            String msg = gs.toJson(message);
            os.write(msg.getBytes());
        } catch (Exception e) {
        }
    }
    
    public void closeOs(){
        try {
            os.close();
        } catch (Exception e) {
        }
    }
    
    public boolean connect(){
        try {
            connection = new Socket(this.address, this.port);
            connection.setReuseAddress(true);
        } catch (IOException e) {
         return false;   
        }
        return true;
    }
    
    public boolean desconnect(){
        try {
            connection.close();
        } catch (IOException e) {
            return false;
        }
         return true;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public Socket getConnection() {
        return connection;
    }
    
    
}
