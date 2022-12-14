package Client.Controller;

import Client.Model.Account;
import Client.Model.Message;
import Client.Model.User;
import com.google.gson.Gson;
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
    public void setStatus() {
        for (ChatObserver obs : observers){
            if (user.isOnline()) {
                obs.setStatus("online");
            } else {
                obs.setStatus("offline");
            }
        }
    }

    @Override
    public User getUser() {
        return user;
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
            for (ChatObserver obs : observers){
                obs.updateScreen("Você: " + message);
            }
        }
    }

    @Override
    public Account getAccount() {
        return account;
    }
}
