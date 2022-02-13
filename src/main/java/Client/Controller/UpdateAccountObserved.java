package Client.Controller;

public interface UpdateAccountObserved {
    void addObserver(UpdateAccountObserver observer);
    void showAccountData();
    boolean updateData(String name, String password, String username);
}
