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

    public User getPerson() {
        return person;
    }

    public void setPerson(User person) {
        this.person = person;
    }

    public List<Integer> getContactCodes() {
        return contactCodes;
    }

    public void setContactCodes(List<Integer> contactCodes) {
        this.contactCodes = contactCodes;
    }

    public List<User> getContacts() {
        return contacts;
    }

    public void setContacts(List<User> contacts) {
        this.contacts = contacts;
    }
    
    public void addContact(User user){
        this.contacts.add(user);
    }
    
    public void removeContact(User user){
        this.contacts.remove(user);
    }
}
