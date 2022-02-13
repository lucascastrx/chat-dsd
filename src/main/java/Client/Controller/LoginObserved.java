package Client.Controller;

public interface LoginObserved {
    void addObserver(LoginObserver loginObserver);
    boolean login(String username, String password);
}
