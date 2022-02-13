package Client.Controller;

public interface RegisterObserved {
    void addObserver(RegisterObserver registerObserver);
    boolean register(String name, String user, String password);
    void back();
}
