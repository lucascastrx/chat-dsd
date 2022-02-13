package Client.Controller;

import Client.Model.Message;
import Client.Model.Server;
import com.google.gson.Gson;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class RegisterController implements RegisterObserved {
    private List<RegisterObserver> observers = new ArrayList<>();
    private Socket connection;
    private Server server;

    public RegisterController() {
        this.server = Server.getInstance();
    }

    @Override
    public void addObserver(RegisterObserver registerObserver) {
        this.observers.add(registerObserver);
    }

    @Override
    public boolean register(String name, String user, String password) {
        OutputStream os = null;
        InputStream is = null;
        try {
            if (server.connect()){
                connection = server.getConnection();
                os = connection.getOutputStream();
                is = connection.getInputStream();
            }
            Message msg = new Message();
            msg.setAction(Message.REGISTER);
            msg.setInputs(name, user, password);

            Gson gson = new Gson();
            String sended = gson.toJson(msg);
            os.write(sended.getBytes());

            String response = read(is);
            Message msgR = gson.fromJson(response, Message.class);
            if (msgR.getAction() == Message.REGISTER){
                if (msgR.getStatus().equals(Message.SUCCESS)){
                    JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!");
                    back();
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, msgR.getContent());
                    return false;
                }
            } else {
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(server.getConnection());
            return false;
        }
    }

    private String read(InputStream is) throws IOException {
        byte[] rawData = new byte[1024];
        int readBytes = is.read(rawData);
        return new String(rawData, 0, readBytes);
    }

    @Override
    public void back() {
        for (RegisterObserver obs : observers){
            obs.back();
        }
    }
}
