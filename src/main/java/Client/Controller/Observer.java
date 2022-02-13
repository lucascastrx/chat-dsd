package Client.Controller;

import java.awt.CardLayout;
import javax.swing.*;

public interface Observer {
    void updateName(String name);
    void updateOnlineContacts(DefaultListModel list);
    void updateOfflineContacts(DefaultListModel list);
    void addMessageScreen(String message);
    void setChatLayout(CardLayout cl);
    void addChat(JPanel jp, String id);
    void showChat(CardLayout cl, String id);
}
