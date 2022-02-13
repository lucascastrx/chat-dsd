package Client.Controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Sender extends Thread {
    private Socket connection;
    private String message;
    private OutputStream os;

    public Sender(String address, String message) throws IOException {
        this.connection = new Socket(address, 50002);
        this.os = connection.getOutputStream();
        this.message = message;
    }

    @Override
    public void run(){
        try {
            os.write(this.message.getBytes());
            os.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
