package Client.Controller;

public interface ActionObserver {
    void addObserver(Observer observer);
    void clickedList(String name);
    void updateData();
    void addContact(String username);
    void updateHomeScreen();
    void startListener();
}
