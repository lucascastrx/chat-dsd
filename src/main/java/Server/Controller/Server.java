package Server.Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends Thread {
    private static final int PORT = 50000;
    private ServerSocket server;
    private List<Transmission> connected;

    public Server() throws IOException {
        this.connected = new ArrayList<>();
        this.server = new ServerSocket(PORT);
        this.server.setReuseAddress(true);
    }
    
    @Override
    public void run(){
        while (true) {            
            System.out.println("Aguardando conexao...");
            try {
                Socket conn = server.accept();
                Transmission tn = new Transmission(conn);
                tn.start();
                this.connected.add(tn);
                System.out.println("Alguem se conectou. (" + conn.getInetAddress().getHostAddress() + ")");
            } catch (IOException e) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
    
    public void updateConnected(){
        connected.stream().map((x)->{
            if (x.getConn().isClosed()) {
                x.setContactStatus(false);
            }
            return x;
        });
        updateConnected();
    }
    
    
    
    
}
