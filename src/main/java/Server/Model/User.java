package Server.Model;


import java.util.ArrayList;
import java.util.List;

public class User {
    private String username, name, password;
    private int id, idContact;
    private List<Integer> contactCodes = new ArrayList<>();
    private boolean isOnline;

    public User(String username, String name, boolean isOnline) {
        this.username = username;
        this.name = name;
        this.isOnline = isOnline;
    }
    
    public User(int id, String username, String name, boolean isOnline) {
        this.username = username;
        this.name = name;
        this.isOnline = isOnline;
        this.id = id;
    }

    public User() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdContact() {
        return idContact;
    }

    public void setIdContact(int idContact) {
        this.idContact = idContact;
    }

    public List<Integer> getConCode() {
        return contactCodes;
    }

    public void setConCode(List<Integer> conCode) {
        this.contactCodes = conCode;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    @Override
    public String toString() {
        return "Nome de usuario: " + username;
    }
    
    
}
