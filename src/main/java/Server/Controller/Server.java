package Server.Controller;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends Thread {
    private static final int PORT = 50000;
    private ServerSocket server;
    public static List<Transmission> connected;

    public Server() throws IOException {
        this.connected = new ArrayList<>();
        this.server = new ServerSocket(PORT);
        this.server.setReuseAddress(true);
    }
    
    @Override
    public void run(){
        initTasks();
        while (true) {            
            System.out.println("Aguardando conexao...");
            try {
                Socket conn = server.accept();
                Transmission tn = new Transmission(conn);
                tn.start();
                System.out.println("Alguem se conectou. (" + conn.getInetAddress().getHostAddress() + ")");
            } catch (IOException e) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
    

    private void initTasks(){
        (new Thread(() -> {
            new Timer().schedule(
                    new TimerTask() {
                        @Override
                        public void run() {                            
                            Iterator<Transmission> iterator = Server.connected.listIterator();
                            while (iterator.hasNext()){
                                boolean alive = false;
                                Transmission tr = iterator.next();
                                try {
                                    Socket socket = new Socket();
                                    SocketAddress socketAddress = new InetSocketAddress(tr.getContact().getIp(), 50002);
                                    socket.connect(socketAddress);
                                    socket.close();
                                    alive = true;
                                } catch (IOException ex) {
                                }
                                if (!alive) {
                                    tr.setContact(null);
                                    tr.stop();
                                    iterator.remove();
                                }
                            }
//                            Server.connected.removeIf(tr -> tr.getConn().isClosed());
                            System.out.println("Executou task");
                            System.out.println(Server.connected.toString());
                        }
                    }, 20000, 15000
            );
        })).start();
    }
    
    
    
    
}
