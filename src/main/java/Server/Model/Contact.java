package Server.Model;

import net.minidev.json.JSONObject;

public class Contact {
    private int id;
    private User person, friend;

    public Contact(User person, User friend) {
        this.person = person;
        this.friend = friend;
    }

    public Contact() {}

    public User getPerson() {
        return person;
    }

    public void setPerson(User person) {
        this.person = person;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }
    
    public JSONObject toJson(){
        JSONObject object = new JSONObject();
        object.put("person", this.person);
        object.put("frient", this.friend);
        return object;
    }

    @Override
    public String toString() {
        return "Pessoa: " + person + ", Amigo: " + friend;
    }    
}
