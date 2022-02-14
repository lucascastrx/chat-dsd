package Client.Controller;

public interface ChatObserver {
    void updateName(String name);
    void setStatus(String status);
    void updateScreen(String message);
}
