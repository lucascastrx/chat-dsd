package Client.Model;

import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private static Server instance;
    private String address;
    private int port;
    private Socket connection;
    private ObjectOutputStream os;
    private String ADDRESS = "127.0.0.1";
    
    public static Server getInstance(){
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }
    
    private Server(){
        try {
            ipconfig();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.address = ADDRESS;
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
    
    public boolean disconnect(){
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
    
    private void ipconfig() throws IOException, URISyntaxException {
        File arq = new File("ip.txt");
        ADDRESS = new String(Files.readAllBytes(arq.toPath()));
    }
    
    
}
