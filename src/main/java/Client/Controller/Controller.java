package Client.Controller;

import Client.Model.Account;
import Client.Model.Message;
import Client.Model.Server;
import Client.Model.User;
import Client.View.Chat;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Controller implements ActionObserver {
    private static Controller instance;
    private List<Observer> observers = new ArrayList<>();
    private List<User> contacts = new ArrayList<>();
    private Account account;
    private static ArrayList<Chat> openChats = new ArrayList<>(); 
    private DefaultListModel onlineContacts;
    private Socket connection = null;
    private Server server;

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
    }

    @Override
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void clickedList(String username) {
        boolean openChat = false;
        if (openChats.size() > 0) {
            for (Chat chat : openChats) {
                if (chat.getController().getUser().getUsername().equals(username)) {
                    for (Observer observer : observers) {
                        observer.showChat(username);
                        break;
                    }
                    openChat = true;
                    break;
                }
            }
        }
        if (!openChat) {
            for (User contact : contacts) {
                if (contact.getUsername().equals(username)) {
                    
                    OutputStream os = null;
                    InputStream is = null;

                    try {
                        if (server.connect()) {
                            connection = server.getConnection();
                            os = connection.getOutputStream();
                            is = connection.getInputStream();
                        }
                        Message msg = new Message();
                        msg.setAction(Message.GET_USER);
                        msg.setInputs(username);
                        Gson gson = new Gson();
                        String send = gson.toJson(msg);
                        os.write(send.getBytes());

                        String response = read(is);
                        System.out.println("GET_USER: "+ response);
                        Message m = gson.fromJson(response, Message.class);

                        if (m.getStatus().equals(Message.SUCCESS)) {
                            contact = gson.fromJson(m.getContent(), User.class);
                        }

                    } catch (JsonSyntaxException | IOException | NumberFormatException e) {
                        e.printStackTrace();
                    }
                    
                    Chat chat = new Chat(new ChatController(contact), this);
                    for (Observer observer : observers) {
                        observer.addChat(chat, contact.getUsername());
                        observer.showChat(username);
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
    
    public void newChatWithMessage(String username, String msg){
        for (User contact : contacts) {
            if (contact.getUsername().equals(username)) {
                User contactServer = null;
                try {
                    OutputStream os = null;
                    InputStream is = null;
                    
                    if (server.connect()) {
                        connection = server.getConnection();
                        os = connection.getOutputStream();
                        is = connection.getInputStream();
                    }
                    Message msg2 = new Message();
                    msg2.setAction(Message.GET_USER);
                    msg2.setInputs(username);
                    Gson gson = new Gson();
                    String send = gson.toJson(msg);
                    os.write(send.getBytes());

                    String response = read(is);
                    System.out.println("GET_USER: "+ response);
                    Message m = gson.fromJson(response, Message.class);

                    if (m.getStatus().equals(Message.SUCCESS)) {
                        contactServer = gson.fromJson(m.getContent(), User.class);
                        contacts.remove(contact);
                        contacts.add(contactServer);
                    }

                } catch (JsonSyntaxException | IOException | NumberFormatException e) {
                    e.printStackTrace();
                }
                Chat chat = null;
                if (contactServer != null) {
                    chat = new Chat(new ChatController(contactServer), this);
                } else {
                    chat = new Chat(new ChatController(contact), this);
                }
                chat.getTaChat().append(msg);
                for (Observer observer : observers) {
                    observer.addChat(chat, contact.getUsername());
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
            
            System.out.println(response);
            
            if (m.getStatus().equals(Message.FAIL)) {
                messageScreen(m.getContent());
            }
            
            updateContactsAccount();
            
        } catch (JsonSyntaxException | IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
    
    @Override
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
            
            if (m.getStatus().equals(Message.FAIL)) {
                messageScreen(m.getContent());
            }
            
            updateContactsAccount();
            
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
    
    @Override
    public void updateContactsAccount(){
        OutputStream os = null;
        InputStream is = null;
        openChats.clear();
        try {
            if (server.connect()) {
                connection = server.getConnection();
                os = connection.getOutputStream();
                is = connection.getInputStream();
            }
            Message msg = new Message();
            msg.setAction(Message.GET_CONTACTS);
            msg.setInputs(String.valueOf(account.getPerson().getId()));
            Gson gson = new Gson();
            String send = gson.toJson(msg);
            os.write(send.getBytes());
            
            String response = read(is);
            Message m = gson.fromJson(response, Message.class);

            Type listType = new TypeToken<ArrayList<String>>(){}.getType();
            ArrayList<String> usernames = gson.fromJson(m.getContent(), listType);
            
            List<User> users = new ArrayList<>();
            for (String username : usernames) {
                users.add(new User(username, true));
            }
            this.account.setContacts(users);
            updateHomeScreen();
            
        } catch (JsonSyntaxException | IOException | NumberFormatException e) {
            e.printStackTrace();
        }
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
    
    public Chat getChatByUsername(String username){
        for (Chat chat : openChats){
            if (chat.getController().getUser().getUsername().equals(username)){
                return chat;
            }
        }
        return null;
    }
    
    private void updateNameUser(){
        for (Observer observer : observers) {
            observer.updateName(account.getPerson().getName());
        }
    }
    
    private void updateLists(List<User> list){
        this.account.setContacts(list);
        this.onlineContacts = new DefaultListModel();
//        this.offlineContacts = new DefaultListModel();
        for (User contact : account.getContacts()) {
            if (contact.isOnline()) {
                onlineContacts.addElement(contact.getUsername());
            } else {
//                offlineContacts.addElement(contact.getUsername());
            }
        }
        
        System.out.println(this.contacts.toString());
        System.out.println(onlineContacts.toString());
        
        // atualizar lista de on e off
        for (Observer observer : observers) {
            observer.updateOnlineContacts(onlineContacts);
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

    
    private String read(InputStream is) throws IOException {
        byte[] data = new byte[1024];
        int bytesRead = is.read(data);
        return new String(data, 0, bytesRead);
    }
}
