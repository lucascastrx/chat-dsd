package Client.Controller;

import Client.Model.Account;
import Client.Model.User;

import java.io.File;

public interface ChatObserved {
    void addObserver(ChatObserver observer);
    void nameUser();
    void setStatus();
    User getUser();
    void onClose();
    void onSendMessage(String message);
    void sendDocument(File file);
    Account getAccount();
}
