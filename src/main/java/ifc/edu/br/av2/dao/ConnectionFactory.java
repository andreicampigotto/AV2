package ifc.edu.br.av2.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    
    public static Connection connection(String db, String user, String pass) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/" + db + "?useSSL=false";
        return DriverManager.getConnection(url, user, pass);
    }
    
}
