package Client.Model;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private static Account instance;
    private User person;
    private List<Integer> contactCodes;
    private List<User> contacts;
    
    public synchronized static Account getInstance(){
        if (instance ==  null) {
            instance = new Account();
        }
        return instance;
    }
    
    private Account(){
        contactCodes = new ArrayList<>();
        contacts = new ArrayList<>();
    }
    
    
}
