package Client.Controller;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Listener extends Thread {
    private ServerSocket server;
    private Socket connection = null;
    private Controller controller;

    public Listener(Controller controller) throws IOException {
        server = new ServerSocket();
        server.setReuseAddress(true);
        server.bind(new InetSocketAddress(50002));
        this.controller = controller;
    }

    @Override
    public void run(){
        while (true){
            try {
                System.out.println("Esperando por conexao...");
                connection = server.accept();
                System.out.println("Conectado com " + connection.getInetAddress().getHostAddress());
                Receiver receiver = new Receiver(connection, controller);
                receiver.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
