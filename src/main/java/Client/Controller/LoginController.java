package Client.Controller;

import Client.Model.Account;
import Client.Model.Message;
import Client.Model.Server;
import Client.Model.User;
import Client.View.Home;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

public class LoginController implements LoginObserved {
    
    private JFrame loginScreen;
    private Server server;
    private Socket connection;
    private Account account;
    private List<LoginObserver> observers = new ArrayList<>();

    public LoginController(JFrame loginScreen) {
        this.loginScreen = loginScreen;
    }

    public LoginController() {
        server = Server.getInstance();
        account = account.getInstance();
    }


    public boolean login(String username, String password){
        
        InputStream is = null;
        OutputStream os = null;
        
        try {
            if (server.connect()) {
                connection = server.getConnection();
                is = connection.getInputStream();
                os = connection.getOutputStream();
            }
            
            Message msg = new Message();
            msg.setAction(Message.LOGIN);
            msg.setInputs(username, password);
            
            Gson gs = new Gson();
            String msgJson = gs.toJson(msg);
            os.write(msgJson.getBytes());
            
            String response = read(is);
            Message m = gs.fromJson(response, Message.class);
            
            if (!m.getStatus().equals(Message.FAIL)) {
                account.setPerson(gs.fromJson(m.getContent(), User.class));
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
        return true;
    }

    @Override
    public void addObserver(LoginObserver loginObserver) {
        observers.add(loginObserver);
    }
    
    private String read(InputStream is) throws IOException {
        byte[] data = new byte[1024];
        int bytesRead = is.read(data);
        return new String(data, 0, bytesRead);
    }
}
