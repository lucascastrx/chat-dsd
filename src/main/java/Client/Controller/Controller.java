package Client.Controller;

import Client.Model.Account;
import Client.Model.Message;
import Client.Model.Server;
import Client.Model.User;
import Client.View.Chat;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.awt.CardLayout;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Controller implements ActionObserver {
    private static Controller instance;
    private List<Observer> observers = new ArrayList<>();
    private List<User> contacts = new ArrayList<>();
    private Account account;
    private ArrayList<Chat> openChats = new ArrayList<>(); 
    private DefaultListModel onlineContacts;
    private DefaultListModel offlineContacts;
    private Socket connection = null;
    private Server server;
    private CardLayout cl;

    public static Controller getInstance(){
        if (instance == null){
            instance = new Controller();
        }
        return instance;
    }

    private Controller(){
        account = Account.getInstance();
        account.getPerson().setIsOnline(true);
        server = Server.getInstance();
        connection = server.getConnection();
        contacts = account.getContacts();
        ServerReceiver receiver = new ServerReceiver(server, this);
        receiver.start();
        initChat();
    }

    @Override
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void clickedList(String name) {
        String userS = getUsernameByName(name);
        boolean openChat = false;
        if (openChats.size() > 0) {
            for (Chat chat : openChats) {
                if (chat.getController().getUser().getUsername().equals(userS)) {
                    for (Observer observer : observers) {
                        observer.showChat(cl, userS);
                    }
                    openChat = true;
                }
            }
        }
        if (!openChat) {
            for (User contact : contacts) {
                if (contact.getUsername().equals(userS)) {
                    Chat chat = new Chat(new ChatController(contact), this);
                    for (Observer observer : observers) {
                        observer.addChat(chat, contact.getUsername());
                        observer.showChat(cl, userS);
                    }
                    openChats.add(chat);
                    break;
                }
            }
        }
    }
    
    public Chat getOpenChat(String username){
        Chat chat = null;
        for (Chat openChat : openChats) {
            if (openChat.getController().getUser().getUsername().equals(username)) {
                chat = openChat;
            }
        }
        return chat;
    }
    
    public void newChat(String name, String msg){
        String user = getUsernameByName(name);
        for (User contact : contacts) {
            if (contact.getUsername().equals(user)) {
                Chat chat = new Chat(new ChatController(contact), this);
                chat.getTaChat().append(msg);
                for (Observer observer : observers) {
                    observer.addChat(chat, contact.getUsername());
                    observer.showChat(cl, contact.getUsername());
                }
                openChats.add(chat);
                break;
            }
        }
    }
    
    public void removeChat(Chat chat){
        this.openChats.remove(chat);
    }

    @Override
    public void updateData() {

    }

    @Override
    public void addContact(String username) {
        OutputStream os = null;
        InputStream is = null;
        
        try {
            if (server.connect()) {
                connection = server.getConnection();
                os = connection.getOutputStream();
                is = connection.getInputStream();
            }
            Message msg = new Message();
            msg.setAction(Message.ADD_CONTACT);
            msg.setInputs(username, account.getPerson().getUsername());
            Gson gson = new Gson();
            String send = gson.toJson(msg);
            os.write(send.getBytes());
            
            String response = read(is);
            Message m = gson.fromJson(response, Message.class);
            
            User us = new User();
            us.setUsername(username);
            us.setIsOnline(true);
            us.setId(Integer.parseInt(m.getInputs()[2]));
            account.addContact(us);
            updateHomeScreen();
            
        } catch (JsonSyntaxException | IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
    
    public void removeContact(String username){
        OutputStream os = null;
        InputStream is = null;
        
        try {
            if (server.connect()) {
                connection = server.getConnection();
                os = connection.getOutputStream();
                is = connection.getInputStream();
            }
            Message msg = new Message();
            msg.setAction(Message.REMOVE_CONTACT);
            msg.setInputs(username, account.getPerson().getUsername());
            Gson gson = new Gson();
            String send = gson.toJson(msg);
            os.write(send.getBytes());
            
            String response = read(is);
            Message m = gson.fromJson(response, Message.class);
            
            User us = new User();
            us.setUsername(username);
            us.setIsOnline(true);
            us.setId(Integer.parseInt(m.getInputs()[2]));
            account.removeContact(us);
            updateHomeScreen();
            
        } catch (JsonSyntaxException | IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateHomeScreen() {
        this.contacts = account.getContacts();
        updateNameUser();
        updateLists(contacts);
    }

    public List<User> getOpenChats() {
        User[] users = new User[openChats.size()];
        int marker = 0;
        for (Chat chat : openChats) {
            users[marker] = chat.getController().getUser();
            marker++;
        }
        return contacts;
    }
    
    private void updateNameUser(){
        for (Observer observer : observers) {
            observer.updateName(account.getPerson().getName());
        }
    }
    
    private void updateLists(List<User> list){
        this.account.setContacts(list);
        this.onlineContacts = new DefaultListModel();
        this.offlineContacts = new DefaultListModel();
        for (User contact : account.getContacts()) {
            if (contact.isOnline()) {
                onlineContacts.addElement(contact.getUsername());
            } else {
                offlineContacts.addElement(contact.getUsername());
            }
        }
        
        // atualizar lista de on e off
        for (Observer observer : observers) {
            observer.updateOnlineContacts(onlineContacts);
            observer.updateOfflineContacts(offlineContacts);
        }
    }

    @Override
    public void startListener() {
        Listener listener;
        try {
            listener = new Listener(this);
            listener.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void messageScreen(String msg) {
        for (Observer observer : observers) {
            observer.addMessageScreen(msg);
        }
    }
    
    private String getUsernameByName(String name){
        String userS = "";
        for (User contact : contacts) {
            if (contact.getUsername().equals(name)) {
                userS = contact.getUsername();
            }
        }
        return userS;
    }

    private void initChat() {
        cl = new CardLayout();
        for (Observer observer : observers) {
            observer.setChatLayout(cl);
        }
    }
    
    private String read(InputStream is) throws IOException {
        byte[] data = new byte[1024];
        int bytesRead = is.read(data);
        return new String(data, 0, bytesRead);
    }
}
