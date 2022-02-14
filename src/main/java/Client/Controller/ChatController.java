package Client.Controller;

import Client.Model.Account;
import Client.Model.Message;
import Client.Model.User;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatController implements ChatObserved {

    private List<ChatObserver> observers = new ArrayList<>();
    private Account account;
    private User user;

    public ChatController(User user) {
        this.user = user;
        account = Account.getInstance();
    }

    public ChatController() {}



    @Override
    public void addObserver(ChatObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void nameUser() {
        for (ChatObserver obs : observers){
            obs.updateName(user.getName());
        }
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void onClose() {

    }

    @Override
    public void onSendMessage(String message) {
        Message msg = new Message();
        msg.setAction(Message.TEXT_MESSAGE);
        msg.setContent(message);
        msg.setUsernameSender(this.user.getUsername());
        Gson gson = new Gson();

        String sended = gson.toJson(msg);

        try {
            Sender sender = new Sender(user.getIp(), message);
            sender.start();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Gson gson1 = new Gson();
            Message message1 = gson1.fromJson(message, Message.class);
            showScreen(message1.getContent());
        }
    }

    public void showScreen(String msg){

    }

    @Override
    public void sendDocument(File file) {

    }

    @Override
    public Account getAccount() {
        return account;
    }
}
