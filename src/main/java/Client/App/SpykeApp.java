package Client.App;

import Client.Model.Message;
import Client.View.*;
import com.google.gson.Gson;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class SpykeApp {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
        } 
        catch (UnsupportedLookAndFeelException e) {
           e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
           e.printStackTrace();
        }
        catch (InstantiationException e) {
           e.printStackTrace();
        }
        catch (IllegalAccessException e) {
           e.printStackTrace();
        }
        
//        new Home().setVisible(true);
        new Login().setVisible(true);
    }
}
