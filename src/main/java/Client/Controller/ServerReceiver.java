package Client.Controller;

import Client.Model.Account;
import Client.Model.Message;
import Client.Model.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ServerReceiver extends Thread {
    private static int port = 50000;
    private Server server;
    private InputStream is;
    private ObjectInputStream object;
    private Controller controller;
    private Account account;

    public ServerReceiver(Server server, Controller controller){
        this.server = server;
        this.controller = controller;
        account = Account.getInstance();
        try {
            is = server.getConnection().getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        while (true){
            try {
                this.object = new ObjectInputStream(is);
                Message msg = (Message) object.readObject();
                switch (msg.getAction()){
                    case Message.UPDATE_CONTACTS:
                        updateContactsListMessage(msg);
                        break;
                    case Message.ADD_CONTACT:
                        addContactMessage(msg);
                        break;
                    case Message.REMOVE_CONTACT:
                        removeContactMessage(msg);
                        break;
                    case Message.UPDATE_ACCOUNT:
                        updateAccountMessage(msg);
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    private void updateAccountMessage(Message msg) {
    }

    private void removeContactMessage(Message msg) {
        if (msg.getContent().equals("success")){
            messageScreen("Pessoa removida dos contatos.");
        } else {
            messageScreen("Falha ao remover contato.");
        }
    }

    private void addContactMessage(Message msg) {
        if (msg.getContent().equals("success")){
            messageScreen("Pessoa adicionada aos contatos.");
        } else {
            messageScreen("Falha ao adicionar contato.");
        }
    }

    private void updateContactsListMessage(Message msg) {

    }

    private void messageScreen(String msg){
        controller.messageScreen(msg);
    }

}
