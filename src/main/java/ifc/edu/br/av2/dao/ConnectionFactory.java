package ifc.edu.br.av2.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    
    public static Connection connection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/jeffersonmendes_pgm4?useSSL=false";
        return DriverManager.getConnection(url, "root", "testando");
    }
    
}
