package Client.Controller;

public interface ActionObserver {
    void addObserver(Observer observer);
    void clickedList(String name);
    void addContact(String username);
    void removeContact(String username);
    void updateHomeScreen();
    void updateContactsAccount();
    void startListener();
}
