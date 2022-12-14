package Client.Controller;

import java.awt.CardLayout;
import javax.swing.*;

public interface Observer {
    void updateName(String name);
    void updateOnlineContacts(DefaultListModel list);
    void addMessageScreen(String message);
    void addChat(JPanel jp, String id);
    void showChat(String id);
}
