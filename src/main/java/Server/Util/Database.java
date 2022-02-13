package Server.Util;

import Server.Controller.Server;
import Server.Model.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Callable;

public class Database {
    private Connection connection = null;
    private static Database instance = null;
    
    private Database(){
        try {
            connection = DriverManager.getConnection("jdbc:sqlite::resource:" + Server.class.getResource("/database.sqlite"));
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
        } catch (SQLException e) {
            System.err.println("Erro de conexão ao banco de dados.");
        }
    }
    
    public static Database getInstance(){
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
    
    public void closeConnection(){
        try {
            this.connection.close();
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão do banco.");
        }
    }
    
    public ResultSet executeQuery(String query) throws SQLException{
        Statement state = connection.createStatement();
        ResultSet res = state.executeQuery(query);
        state.close();
        return res;
    }
    
    public void executeUpdate(String query) throws SQLException{
        Statement state = connection.createStatement();
        state.executeUpdate(query);
        state.close();
    }
}
