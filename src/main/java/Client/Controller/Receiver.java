package Client.Controller;

import Client.Model.Message;
import Client.Model.User;
import Client.View.Chat;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Receiver extends Thread {
    private Socket connection;
    private Controller controller;
    private User user;

    public Receiver(Socket connection, Controller controller) {
        this.connection = connection;
        this.controller = controller;
    }

    @Override
    public void run(){
        try {
            InputStream is = connection.getInputStream();
            String response = read(is);
            Gson gson = new Gson();
            Message msg = gson.fromJson(response, Message.class);
            if (msg.getAction() == Message.TEXT_MESSAGE){
                textMessage(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void textMessage(Message msg) {
        String finalMsg = msg.getUsernameSender() + ": " + msg.getContent();
        if (!verifyOpenedChat(msg)){
            controller.clickedList(msg.getUsernameSender());
            textMessage(msg);
        } else {
            Chat chat = controller.getOpenChat(user.getUsername());
            chat.getTaChat().append(finalMsg + "\n");
        }

    }

    private boolean verifyOpenedChat(Message msg) {
        for (User u : controller.getOpenChats()){
            if (msg.getUsernameSender().equals(u.getUsername())){
                this.user = u;
                return true;
            }
        }
        return false;
    }

    private String read(InputStream is) throws IOException {
        byte[] data = new byte[1024];
        int bytesRead = is.read(data);
        return new String(data, 0, bytesRead);
    }
}
