package Client.Controller;

import Client.Model.Account;
import Client.Model.Message;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Sender extends Thread {
    private Socket connection;
    private OutputStream os;
    private Message msg;

    public Sender(String address, String message) throws IOException {
        this.msg = new Message();
        msg.setAction(Message.TEXT_MESSAGE);
        msg.setContent(message);
        msg.setUsernameSender(Account.getInstance().getPerson().getUsername());
        this.connection = new Socket(address, 50002);
        this.os = connection.getOutputStream();
    }

    @Override
    public void run(){
        try {
            Gson gson = new Gson();
            String send = gson.toJson(msg);
            os.write(send.getBytes());
            os.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
